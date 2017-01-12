package com.cloudcraftgaming.novagameslib.data;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.kit.Kit;
import com.cloudcraftgaming.novagameslib.kit.KitFailureReason;
import com.cloudcraftgaming.novagameslib.kit.KitResponse;
import com.cloudcraftgaming.novagameslib.utils.FileManager;
import com.cloudcraftgaming.novagameslib.utils.FileUtils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nova Fox on 11/17/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings("WeakerAccess")
public class KitDataManager {

	//Functionals
	/**
	 * Creates a new kit.
	 * @param kitName The name of the kit.
	 * @param player The player whose inventory is to be read.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public static Boolean createKit(String kitName, Player player) {
		if (!kitFileExists(kitName)) {
			YamlConfiguration kit = createKitFile(kitName);

			//Save kit items
			List<String> kitItems = kit.getStringList("Items.List");
			Integer itemNum = 0;
			ItemStack[] items = player.getInventory().getContents();
			for (int i = 0; i < items.length; i++) {
				ItemStack item = items[i];
				if (item != null) {
					if (!kitItems.contains(itemNum.toString())) {
						kitItems.add(itemNum.toString());
						kit.set("Items." + itemNum + ".Name", item.getType().name());
						kit.set("Items." + itemNum + ".Amount", item.getAmount());
						kit.set("Items." + itemNum + ".Durability", item.getDurability());
						kit.set("Items." + itemNum + ".Slot", i);
						if (item.hasItemMeta()) {
							ItemMeta meta = item.getItemMeta();
							if (meta.hasDisplayName()) {
								kit.set("Items." + itemNum + ".Meta.DisplayName", meta.getDisplayName());
							}
							if (meta.hasEnchants()) {
								List<String> enchants = kit.getStringList("Items." + itemNum + ".Enchants.List");
								for (Enchantment enchant : meta.getEnchants().keySet()) {
									Integer level = meta.getEnchants().get(enchant);
									String enchantName = enchant.getName();
									enchants.add(enchantName);
									kit.set("Items." + itemNum + ".Enchants." + enchantName + ".Level", level);
								}
								kit.set("Items." + itemNum + ".Enchants.List", enchants);
							}
						}
						itemNum = itemNum + 1;
					}
				}
			}
			kit.set("Items.List", kitItems);

			//Set kit Info
			kit.set("Info.DisplayName", "&6" + kitName);
			kit.set("Info.Description", "&4This is the " + kitName + " kit.");
			kit.set("Info.Cost", 0.0);
			kit.set("Info.Delay", 0);
			kit.set("Info.Item.Name", Material.APPLE.name());
			kit.set("Info.Item.Durability", null);

			//Add kit to list
			YamlConfiguration cache = FileManager.getPluginCacheYml();
			List<String> allKits = cache.getStringList("Kits.List");
			if (!allKits.contains(kitName)) {
				allKits.add(kitName);
				cache.set("Kits.List", allKits);
				FileManager.savePluginCache(cache);
			}
		}
		return false;
	}

	/**
	 * Deletes the specified kit.
	 * @param kitName The name of the kit.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public static Boolean deleteKit(String kitName) {
		if (kitExists(kitName)) {
			YamlConfiguration cache = FileManager.getPluginCacheYml();
			List<String> allKits = cache.getStringList("Kits.List");
			if (allKits.contains(kitName)) {
				allKits.remove(kitName);
				cache.set("Kits.List", allKits);
				FileManager.savePluginCache(cache);
			}
			return FileUtils.deleteFile(getKitFile(kitName));
		}
		return false;
	}

	/**
	 * Creates the kit's needed files.
	 * @param kitName The name of the kit.
	 * @return The kit Yml.
	 */
	private static YamlConfiguration createKitFile(String kitName) {
		if (!kitFileExists(kitName)) {
			YamlConfiguration kit = getKitYml(kitName);

			kit.addDefault("DO NOT DELETE.A", "NovaGamesLib is developed and managed by Shades161 (NovaFox161)");
			kit.addDefault("DO NOT DELETE.B", "This plugin is an API and is useless on its own!");
			kit.addDefault("Name", kitName);

			kit.options().copyDefaults(true);
			saveKitFile(kitName, kit);

			kit.options().copyDefaults(true);
			saveKitFile(kitName, kit);

			return kit;
		}
		return getKitYml(kitName);
	}

	/**
	 * Saves the loaded instance of the kitYml to file.
	 * @param kitName The name of the kit that is being saved.
	 * @param kit The instance of the Yml to save.
	 */
	public static void saveKitFile(String kitName, YamlConfiguration kit) {
		try {
			kit.save(getKitFile(kitName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gives the specified player to specified kit.
	 * @param kitName The name of the kit to give the player.
	 * @param player The player to give the kit to.
	 * @param chargeCost Whether or not to charge the player via econ.
	 * @return A detailed response of the action, if successful, and if not, the cause of failure.
	 */
	public static KitResponse giveKit(String kitName, Player player, Boolean chargeCost) {
		if (kitExists(kitName)) {
			Kit kit = getKit(kitName);
			if (chargeCost && kit.getCost() > 0) {
				//Charge cost if possible, then give kit if funds are good.
				if (NovaGamesLib.econ != null) {
					OfflinePlayer offPl = Bukkit.getOfflinePlayer(player.getUniqueId());
					EconomyResponse er = NovaGamesLib.econ.withdrawPlayer(offPl, kit.getCost());
					if (er.transactionSuccess()) {
						//Successfully charged money, give the player their kit.
						for (int slot : kit.getItems().keySet()) {
							ItemStack item = kit.getItems().get(slot);
							player.getInventory().setItem(slot, item);
						}
						//Success!
						return new KitResponse(true, null);
					} else {
						//Not enough funds, kit unable to be given.
						return new KitResponse(false, KitFailureReason.INSUFFICIENT_FUNDS);
					}
				} else {
					//Vault missing, kit unable to be given.
					return new KitResponse(false, KitFailureReason.VAULT_MISSING);

				}
			} else if (!chargeCost) {
				//Not charging cost, give player kit.
				for (int slot :kit.getItems().keySet()) {
					ItemStack item = kit.getItems().get(slot);
					player.getInventory().setItem(slot, item);
				}
				//Success!
				return new KitResponse(true, null);
			} else {
				//Not supposed to get here. Something obviously went wrong.
				return new KitResponse(false, KitFailureReason.INVALID_SETTING);
			}

		}
		return new KitResponse(false, KitFailureReason.KIT_DOES_NOT_EXIST);
	}

	//Booleans/Checkers
	/**
	 * Checks if the file of the specified kit exists.
	 * Please use {@link #kitExists(String)} to properly check if the kit exists.
	 * @param kitName The name of the kit to check for.
	 * @return <code>true</code> if the kit file exists, else <code>false</code>.
	 */
	public static Boolean kitFileExists(String kitName) {
		return  getKitFile(kitName).exists();
	}

	/**
	 * Checks if the kit exists. This is the preferred method to use.
	 * @param kitName The name of the kit to check for.
	 * @return <code>true</code> if the kit exists, else <code>false</code>.
	 */
	public static Boolean kitExists(String kitName) {
		return FileManager.getPluginCacheYml().getStringList("Kits.List").contains(kitName) && kitFileExists(kitName);
	}

	//Getters
	/**
	 * Gets the File of the specified kit.
	 * @param kitName The name of the kit to get.
	 * @return The file of the kit.
	 */
	public static File getKitFile(String kitName) {
		return new File(NovaGamesLib.plugin.getDataFolder() + "/Kits/" + kitName + ".yml");
	}

	/**
	 * Gets the Yml of the specified kit.
	 * @param kitName The name of the kit to get.
	 * @return The Yml of the specified kit.
	 */
	public static YamlConfiguration getKitYml(String kitName) {
		return YamlConfiguration.loadConfiguration(getKitFile(kitName));
	}

	/**
	 * Gets a new {@link Kit} object containing all needed kit data for easy use.
	 * @param kitName The name of the kit to get.
	 * @return A new {@link Kit} object.
	 */
	public static Kit getKit(String kitName) {
		HashMap<Integer, ItemStack> items = new HashMap<>();
		YamlConfiguration kitYml = getKitYml(kitName);
		List<String> itemStringList = kitYml.getStringList("Items.List");

		for (String itemNum : itemStringList) {
			String itemName = kitYml.getString("Items." + itemNum + ".Name");
			Integer itemAmount = kitYml.getInt("Items." + itemNum + ".Amount");
			Short durability = Short.valueOf(kitYml.getString("Items." + itemNum + ".Durability"));
			Integer slot = kitYml.getInt("Items." + itemName + ".Slot");
			Material itemMat = Material.getMaterial(itemName);
			ItemStack item = new ItemStack(itemMat, itemAmount, durability);
			ItemMeta meta = item.getItemMeta();

			if (kitName.contains("Items." + itemNum + ".Meta.DisplayName")) {
				String displayName = kitYml.getString("Items." + itemNum + ".Meta.DisplayName");
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
			}
			if (kitName.contains("Items." + itemNum + ".Enchants.List")) {
				for (String enchantName : kitYml.getStringList("Items." + itemNum + ".Enchants.List")) {
					Integer level = kitYml.getInt("Items." + itemNum + ".Enchants." + enchantName + ".Level");
					Enchantment enchant = Enchantment.getByName(enchantName);
					meta.addEnchant(enchant, level, false);
				}
			}

			item.setItemMeta(meta);
			items.put(slot, item);
		}

		Kit kit = new Kit(kitName, items);

		//Get and set kit data.
		kit.setDisplayName(kitYml.getString("Info.DisplayName"));
		kit.setDescription(kitYml.getString("Info.Description"));
		kit.setCost(kitYml.getDouble("Info.Cost"));
		kit.setDelay(kitYml.getInt("Info.Delay"));
		kit.setDisplayItemName(kitYml.getString("Info.Item.Name"));
		if (kitName.contains("Info.Item.Durability")) {
			kit.setDisplayItemDurability(Short.valueOf(kitYml.getString("Info.Item.Durability")));
		}

		return kit;
	}

	/**
	 * Gets a new {@link ArrayList} containing all existing {@link Kit} objects.
	 * @return A new {@link ArrayList} containing all existing {@link Kit} objects.
	 */
	public static ArrayList<Kit> getAllKits() {
		ArrayList<Kit> kits = new ArrayList<>();
		if (FileManager.getPluginCacheYml().contains("Kits.List")) {
			for (String kitName : FileManager.getPluginCacheYml().getStringList("Kits.List")) {
				if (kitExists(kitName)) {
					kits.add(getKit(kitName));
				}
			}
		}
		return kits;
	}

	/**
	 * Gets a new {@link ArrayList} containing all of the names of existing kits.
	 * @return A new {@link ArrayList} containing all of the names of existing kits.
	 */
	public static ArrayList<String> getAllKitNames() {
		ArrayList<String> kitNames = new ArrayList<>();
		if (FileManager.getPluginCacheYml().contains("Kits.List")) {
			kitNames.addAll(FileManager.getPluginCacheYml().getStringList("Kits.List"));
		}
		return kitNames;
	}

	//Setters
	/**
	 * Sets the RAW display name of the kit.
	 * @param kitName The name of the kit to set data to.
	 * @param displayName The RAW display name of the kit.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public static boolean setDisplayName(String kitName, String displayName) {
		if (kitExists(kitName)) {
			YamlConfiguration kit = getKitYml(kitName);
			kit.set("Info.DisplayName", displayName);
			saveKitFile(kitName, kit);
			return true;
		}
		return false;
	}

	/**
	 * Sets the RAW description of the kit.
	 * @param kitName The name of the kit to set data to.
	 * @param description The RAW description of the kit.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public static boolean setDescription(String kitName, String description) {
		if (kitExists(kitName)) {
			YamlConfiguration kit = getKitYml(kitName);
			kit.set("Info.Description", description);
			saveKitFile(kitName, kit);
			return true;
		}
		return false;
	}

	/**
	 * Sets the cost of the kit.
	 * @param kitName The name of the kit to set data to.
	 * @param _cost The cost of the kit, <code>0.0</code> to disable.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public static boolean setCost(String kitName, Double _cost) {
		if (kitExists(kitName)) {
			YamlConfiguration kit = getKitYml(kitName);
			kit.set("Info.Cost", _cost);
			saveKitFile(kitName, kit);
			return true;
		}
		return false;
	}

	/**
	 * Sets the delay, in seconds, for the kit.
	 * @param kitName The name of the kit to set data to.
	 * @param _delay The amount of seconds to delay use of the kit.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public static boolean setDelay(String kitName, Integer _delay) {
		if (kitExists(kitName)) {
			YamlConfiguration  kit = getKitYml(kitName);
			kit.set("Info.Delay", _delay);
			saveKitFile(kitName, kit);
			return true;
		}
		return false;
	}

	/**
	 * Sets the item to be displayed in the kit selector GUI.
	 * In one of the following formats: <code>ITEM_NAME:damageMod</code> or <code>ITEM_NAME</code>
	 * @param kitName The name of the kit to set data to.
	 * @param item The item to display.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public static boolean setItem(String kitName, String item) {
		if (kitExists(kitName)) {
			YamlConfiguration kit = getKitYml(kitName);
			if (!item.contains(":")) {
				try {
					Integer itemId = Integer.parseInt(item);
					kit.set("Info.Item.ID", itemId);
					kit.set("Info.Item.Durability", null);
					saveKitFile(kitName, kit);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			} else {
				try {
					Integer itemId = Integer.valueOf(item.split(":")[0]);
					Short damage = Short.valueOf(item.split(":")[1]);
					kit.set("Info.Item.ID", itemId);
					kit.set("Info.Item.Durability", damage);
					saveKitFile(kitName, kit);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		}
		return false;
	}
}