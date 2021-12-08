package me.n137.Jobs.gui;

import me.n137.Jobs.Jobs;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class MenuHandler {
    private Jobs plugin;
    public MenuHandler(Jobs p) {this.plugin = p;}

    public ItemMeta addPersistentDataContainerToItem(ItemMeta itemMeta) {
        NamespacedKey creationKey = new NamespacedKey(plugin, this.plugin.getNamespaceKey());
        itemMeta.getPersistentDataContainer().set(creationKey, PersistentDataType.STRING, this.plugin.getNamespaceKey());
        return itemMeta;
    }

    public ItemStack createMenuItem(Material material, String configPathToItemName, String lore) {
        ItemStack mat = new ItemStack(material);
        ItemMeta matMeta = mat.getItemMeta();
        assert matMeta != null;
        String matTitle = this.plugin.getConfig().getString(configPathToItemName).replace("&", "§");
        matMeta.setDisplayName(matTitle);
        List<String> itemLore = Arrays.asList("§r", this.plugin.getConfig().getString(lore).replace("&", "§"), "§r");
        matMeta.setLore(itemLore);
        matMeta = addPersistentDataContainerToItem(matMeta);
        mat.setItemMeta(matMeta);
        return mat;
    }
    public ItemStack createMenuItem(Material material, String configPathToItemName, List<String> lore) {
        ItemStack mat = new ItemStack(material);
        ItemMeta matMeta = mat.getItemMeta();
        assert matMeta != null;
        String matTitle = this.plugin.getConfig().getString(configPathToItemName).replace("&", "§");
        matMeta.setDisplayName(matTitle);
        matMeta.setLore(lore);
        matMeta = addPersistentDataContainerToItem(matMeta);
        mat.setItemMeta(matMeta);
        return mat;
    }
}
