package eu.virtusdevelops.virtuscore.utils.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface CloseAction {

    void onClose(Player player, Inventory inventory);

}

