package eu.virtusdevelops.virtuscore.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.Base64;

public class ItemUtil {

    public String encodeItem(ItemStack itemStack) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.set("item", itemStack);
        return Base64.getEncoder().encodeToString(yamlConfiguration.saveToString().getBytes());
    }

    public ItemStack decodeItem(String encodedItem) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();

        try {
            yamlConfiguration.loadFromString(new String(Base64.getDecoder().decode(encodedItem)));
        } catch (InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage(TextUtil.colorFormat("&cError: " + e));
            return null;
        }

        return yamlConfiguration.getItemStack("item");
    }
}
