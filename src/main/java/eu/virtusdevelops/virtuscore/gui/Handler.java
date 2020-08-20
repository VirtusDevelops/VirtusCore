package eu.virtusdevelops.virtuscore.gui;

import eu.virtusdevelops.virtuscore.VirtusCore;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Handler {
    private final List<UUID> openedInv = new ArrayList<>();
    private final GuiListener guiListener;

    public Handler(Plugin plugin){
        this.guiListener = new GuiListener(this, plugin);
        VirtusCore.plugins().registerEvents(guiListener, plugin);
    }

    public void addToList(UUID uuid){
        openedInv.add(uuid);
    }
    public void removeFromList(UUID uuid){
        openedInv.remove(uuid);
    }
    public boolean hasOpened(UUID uuid) {
        return openedInv.contains(uuid);
    }


}
