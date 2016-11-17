package com.cloudcraftgaming.novagameslib.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Nova Fox on 11/14/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings("unused")
public class InventoryToBase64 {
    public static String toBase64(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());

            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static Inventory fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * Converts the inventory to string contents and stores it in the specified YML file.
     * @param p The player to save an inventory for.
     * @param yml The Yml to save the inventory to.
     * @return The yml so you can save it.
     */
    public static YamlConfiguration toYml(Player p, YamlConfiguration yml) {
        yml.set("Inventory.Armor", p.getInventory().getArmorContents());
        yml.set("inventory.content", p.getInventory().getContents());
        return yml;
    }

    /**
     * Converts the file from string  contents to an inventory and sets it for the player.
     * @param p The player to load an inventory for.
     * @param yml The Yml to load the inventory from.
     */
    @SuppressWarnings("unchecked")
    public static void fromYml(Player p, YamlConfiguration yml) {
        ItemStack[] content = ((List<ItemStack>) yml.get("Inventory.Armor")).toArray(new ItemStack[0]);
        p.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) yml.get("Inventory.Content")).toArray(new ItemStack[0]);
        p.getInventory().setContents(content);
    }
}