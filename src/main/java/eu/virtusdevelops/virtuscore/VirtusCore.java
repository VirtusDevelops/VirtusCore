package eu.virtusdevelops.virtuscore;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;

import javax.annotation.Nullable;

public class VirtusCore {



    /*
        Getting the server.
     */
    public static Server server() {
        return Bukkit.getServer();
    }
    /*
        Getting all the plugins on server.
    */
    public static PluginManager plugins() {
        return server().getPluginManager();
    }
    /*
        Getting console sender
    */
    public static ConsoleCommandSender console() {
        return server().getConsoleSender();
    }
    /*
        Execute command to console
    */
    public static void executeCommand(String command) {
        server().dispatchCommand(console(), command);
    }

    /*
        Easy way of getting worlds
     */
    @Nullable
    public static World getWorld(String name) {
        return server().getWorld(name);
    }


}
