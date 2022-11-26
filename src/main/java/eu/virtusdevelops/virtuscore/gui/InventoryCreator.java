package eu.virtusdevelops.virtuscore.gui;

import eu.virtusdevelops.virtuscore.gui.actions.InventoryCloseAction;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryCreator implements InventoryHolder {


    private final Map<Integer, Icon> icons = new  HashMap<>();
    public final List<InventoryCloseAction> closeActionList = new ArrayList<>();

    private final int size;
    private String title;

    public InventoryCreator(int size, String title) {
        this.size = size;
        this.title = title;
    }

    public void setIcon(int position, Icon icon) {
        this.icons.put(position, icon);
    }

    public Icon getIcon(int position) {
        return this.icons.get(position);
    }

    @Override
    @Nonnull
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.size, this.title);
        for (Map.Entry<Integer, Icon> entry : this.icons.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().itemStack);
        }
        return inventory;
    }

    public Inventory getInventory(InventoryType type){
        Inventory inventory = Bukkit.createInventory(this, type, this.title);
        for (Map.Entry<Integer, Icon> entry : this.icons.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().itemStack);
        }
        return inventory;
    }

    public void clean(){
        this.icons.clear();
        this.getInventory().clear();
    }

    public void setBackground(ItemStack itemStack){
        Icon icon = new Icon(itemStack);
        for(int x = 0; x < size; x++){
            if(!icons.containsKey(x)){
                setIcon(x, icon);
            }
        }
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public InventoryCreator addCloseActions(InventoryCloseAction action){
        this.closeActionList.add(action);
        return this;
    }

    public List<InventoryCloseAction> getCloseActions() {
        return this.closeActionList;
    }

    
}
