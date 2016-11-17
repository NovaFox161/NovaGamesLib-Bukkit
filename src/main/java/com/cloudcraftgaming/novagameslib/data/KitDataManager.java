package com.cloudcraftgaming.novagameslib.data;

import com.cloudcraftgaming.novagameslib.utils.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nova Fox on 11/17/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class KitDataManager {
	/**
	 * Creates a new kit with the specified name.
	 * This will save all items in the player's inventory to the new kit.
	 * This will save all items, with custom names and enchants.
	 * @param player The player who is creating a kit.
	 * @param kitName The name of the kit to be created.
	 * @return <code>true</code> is successful, else <code>false</code>.
	 */
	public static Boolean createKit(Player player, String kitName) {
		if (!FileManager.getKitsYml().contains("Kits." + kitName)) {
			YamlConfiguration kits = FileManager.getKitsYml();
			List<String> kitItems = kits.getStringList("Kits." + kitName + ".Items.List");
			for (ItemStack item : player.getInventory().getContents()) {
				if (item != null) {
					if (!kitItems.contains(item.getType().name())) {
						kitItems.add(item.getType().name());
						kits.set("Kits." + kitName + ".Items." + item.getType().name() + ".Amount", item.getAmount());
						if (item.hasItemMeta()) {
							ItemMeta meta = item.getItemMeta();
							if (meta.hasDisplayName()) {
								kits.set("Kits." + kitName + ".Items." + item.getType().name() + ".Meta.DisplayName", meta.getDisplayName());
							}
							if (meta.hasEnchants()) {
								List<String> enchants = kits.getStringList("Kits." + kitName + ".Items." + item.getType().name() + ".Enchants.List");
								for (Enchantment enchant : meta.getEnchants().keySet()) {
									Integer level = meta.getEnchants().get(enchant);
									String enchantName = enchant.getName();
									enchants.add(enchantName);
									kits.set("Kits." + kitName + ".Items." + item.getType().name() + ".Enchants." + enchantName + ".Level", level);
								}
								kits.set("Kits." + kitName + ".Items." + item.getType().name() + ".Enchants.List", enchants);
							}
						}
					}
				}
			}
			kits.set("Kits." + kitName + ".Items.List", kitItems);
			FileManager.saveKitFile(kits);
			return true;
		}
		return false;
	}

	/**
	 * Deletes or removes the specified kit from file.
	 * @param kitName The name of the kit to remove.
	 * @return <code>true</code> is successful, else <code>false</code>.
	 */
	public static Boolean removeKit(String kitName) {
		if (FileManager.getKitsYml().contains("Kits." + kitName)) {
			YamlConfiguration kits = FileManager.getKitsYml();
			kits.set("Kits." + kitName, null);
			FileManager.saveKitFile(kits);
			return true;
		}
		return false;
	}

	/**
	 * Gets an ArrayList containing all items from the specified kit.
	 * Will return an empty ArrayList if the kit does not exist.
	 * @param kitName The name of the kit to get.
	 * @return An <code>ArrayList</code> containing all items in the kit.
	 */
	public static ArrayList<ItemStack> getKit(String kitName) {
		ArrayList<ItemStack> items = new ArrayList<>();
		if (kitExists(kitName)) {
			YamlConfiguration kits = FileManager.getKitsYml();
			List<String> itemStringList = kits.getStringList("Kits." + kitName + ".Items.List");

			for (String itemName : itemStringList) {
				Integer itemAmount = kits.getInt("Kits." + kitName + ".Items." + itemName + ".Amount");
				Material itemMat = Material.getMaterial(itemName);
				ItemStack item = new ItemStack(itemMat, itemAmount);
				ItemMeta meta = item.getItemMeta();

				if (kits.contains("Kits." + kitName + ".Items." + item.getType().name() + ".Meta.DisplayName")) {
					String displayName = kits.getString("Kits." + kitName + ".Items." + item.getType().name() + ".Meta.DisplayName");
					meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
				}
				if (kits.contains("Kits." + kitName + ".Items." + item.getType().name() + ".Enchants.List")) {
					for (String enchantName : kits.getStringList("Kits." + kitName + ".Items." + item.getType().name() + ".Enchants.List")) {
						Integer level = kits.getInt("Kits." + kitName + ".Items." + item.getType().name() + ".Enchants." + enchantName + ".Level");
						Enchantment enchant = Enchantment.getByName(enchantName);
						meta.addEnchant(enchant, level, false);
					}
				}

				item.setItemMeta(meta);
				items.add(item);
			}
		}
		return items;
	}

	/**
	 * Checks if the specified kit exists.
	 * @param kitName The kit to check.
	 * @return <code>true</code> is it exists, else <code>false</code>.
	 */
	public static Boolean kitExists(String kitName) {
		return FileManager.getKitsYml().contains("Kits." + kitName);
	}
}