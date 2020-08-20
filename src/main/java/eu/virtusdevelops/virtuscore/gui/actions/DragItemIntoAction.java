package eu.virtusdevelops.virtuscore.gui.actions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface DragItemIntoAction {
    void execute(Player player, ItemStack item);
}
