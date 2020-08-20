package eu.virtusdevelops.virtuscore.gui;

import eu.virtusdevelops.virtuscore.gui.actions.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GuiListener implements Listener {
    private final Handler handler;
    private final Plugin plugin;

    public GuiListener(Handler handler, Plugin plugin){
        this.handler = handler;
        this.plugin = plugin;
    }

    @EventHandler
    public void onInvOpen(InventoryOpenEvent event){
        handler.addToList(event.getPlayer().getUniqueId());
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event){
        // remove player
        handler.removeFromList(event.getPlayer().getUniqueId());

        //Get our CustomHolder
        InventoryCreator customHolder;
        if(event.getView().getTopInventory().getHolder() instanceof InventoryCreator) {
            customHolder = (InventoryCreator) event.getView().getTopInventory().getHolder();
        }else {
            return;
        }

        if(customHolder != null){
            for(InventoryCloseAction closeAction : customHolder.getCloseActions()){
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(!handler.hasOpened(event.getPlayer().getUniqueId())) {
                            closeAction.execute((Player) event.getPlayer(), event.getInventory());
                        }
                    }
                }.runTaskLater(plugin, 2L);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            //Check if the item the player clicked on is valid
            ItemStack itemStack = event.getCurrentItem();
            if (itemStack == null || itemStack.getType() == Material.AIR) return;

            //Get our CustomHolder
            InventoryCreator customHolder;
            if(event.getView().getTopInventory().getHolder() instanceof InventoryCreator) {
                customHolder = (InventoryCreator) event.getView().getTopInventory().getHolder();
            }else {
                return;
            }

            //Check if the clicked slot is any icon
            if(customHolder == null) return;
            Icon icon = customHolder.getIcon(event.getRawSlot());
            if (icon == null) return;

            event.setCancelled(true);
            if(event.getClick() == ClickType.LEFT){
                for(LeftClickAction leftClickAction : icon.getLeftClickActions()){
                    leftClickAction.execute(player);
                }
            }
            if(event.getClick() == ClickType.RIGHT){
                for(RightClickAction rightClickAction : icon.getRightclickActions()){
                    rightClickAction.execute(player);
                }
            }
            if(event.getClick() == ClickType.SHIFT_LEFT){
                for(ShiftLClickAction action: icon.getShiftLclickActions()){
                    action.execute(player);
                }
            }
            if(event.getClick() == ClickType.SHIFT_RIGHT){
                for(ShiftRClickAction action: icon.getShiftRclickActions()){
                    action.execute(player);
                }
            }
            for(DragItemIntoAction action : icon.getDragItemIntoActions()){
                action.execute(player, event.getCursor());
                event.getCursor().setType(Material.AIR);
            }

            //Execute all the actions
            for (ClickAction clickAction : icon.getClickActions()) {
                clickAction.execute(player);
            }

        }
    }
}
