package eu.virtusdevelops.virtuscore.gui.actions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface InventoryCloseAction {
    void execute(Player player, Inventory inventory);
}
