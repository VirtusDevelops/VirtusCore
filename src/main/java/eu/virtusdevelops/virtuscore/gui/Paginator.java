package eu.virtusdevelops.virtuscore.gui;

import eu.virtusdevelops.virtuscore.gui.actions.*;
import eu.virtusdevelops.virtuscore.utils.ItemUtils;
import eu.virtusdevelops.virtuscore.utils.TextUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Paginator {

    private InventoryCreator inventoryCreator;
    private int size;
    private String name;
    private int page = 0;
    private Player player;

    public final List<NextPageAction> nextPageActions = new ArrayList<>();
    public final List<PreviousPageAction> previousPageActions = new ArrayList<>();
    public final List<InventoryCloseAction> closeActionList = new ArrayList<>();
    private final Map<Integer, Icon> fixedIcons = new HashMap<>();

    private List<Integer> slots;
    private List<Icon> icons;


    public Paginator(Player player,List<Icon> icons, List<Integer> slots, String name, int size){
        this.size = size;
        this.name = name;
        this.player = player;
        this.slots = slots;
        this.icons = icons;
        inventoryCreator = new InventoryCreator(size, name);
        setup();
    }


    public Paginator(Player player, List<Integer> slots, String name, int size){
        this.size = size;
        this.name = name;
        this.player = player;
        this.slots = slots;
        inventoryCreator = new InventoryCreator(size, name);
        setup();
    }


    public void setup(){
        inventoryCreator.addCloseActions((player1, inventory) -> {
           for(InventoryCloseAction action: closeActionList){
               action.execute(player, inventory);
           }
        });
    }


    public void page(){
        page(page);
    }


    public void page(int page){
        inventoryCreator.clean();

        for(int i : fixedIcons.keySet()){
            inventoryCreator.setIcon(i, fixedIcons.get(i));
        }
        if(page > 0) {
            previousPageIcon();
        }
        if(page*slots.size() + slots.size() < icons.size()){
            nextPageIcon();
        }

        for (int i = 0; i < slots.size(); i++){
            int index = page * slots.size() + i ;
            if(icons.size() > index) {
                int slot = slots.get(i);
                Icon icon = icons.get(index);
                inventoryCreator.setIcon(slot, icon);
            }else{
                break;
            }
        }

        player.openInventory(inventoryCreator.getInventory());
    }


    private void nextPageIcon(){
        ItemStack item = new ItemStack(Material.PAPER);
        item = ItemUtils.rename(item, TextUtils.colorFormat("&8[&6Next page&8]"));
        Icon icon = new Icon(item);

        icon.addClickAction(player -> {
            page++;
            page(page);
            for(NextPageAction action : nextPageActions){
                action.execute(player);
            }
        });

        inventoryCreator.setIcon(size-2, icon);
    }

    private void previousPageIcon(){
        ItemStack item = new ItemStack(Material.PAPER);
        item = ItemUtils.rename(item, TextUtils.colorFormat("&8[&6Previous page&8]"));
        Icon icon = new Icon(item);

        icon.addClickAction(player -> {
            if(page > 0) {
                page--;
                page(page);
                for(PreviousPageAction action : previousPageActions){
                    action.execute(player);
                }
            }
        });
        inventoryCreator.setIcon(size-8, icon);
    }

    public void addIcon(int slot, Icon icon){
        fixedIcons.put(slot, icon);
    }

    public int getPage() {
        return page;
    }
    public int getSize() {
        return size;
    }
    public String getName() {
        return name;
    }
    public void setName(String name){
        inventoryCreator.setTitle(name);
        page(page);
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }

    public void setSize(int size){
        this.size = size;
        inventoryCreator = new InventoryCreator(size, name);
        page(page);
    }

    public Paginator addNextPageAction(NextPageAction nextPageAction){
        this.nextPageActions.add(nextPageAction);
        return this;
    }
    public Paginator addPreviousPageAction(PreviousPageAction previousPageAction){
        this.previousPageActions.add(previousPageAction);
        return this;
    }

    public Paginator addCloseAction(InventoryCloseAction inventoryCloseAction){
        this.closeActionList.add(inventoryCloseAction);
        return this;
    }



}
