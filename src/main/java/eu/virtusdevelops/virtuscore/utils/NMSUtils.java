package eu.virtusdevelops.virtuscore.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class NMSUtils {
    public static String getServerVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf('.') + 1) + ".";
    }
    public static Class<?> getNMSClass(String className) {
        try {
            String fullName = "net.minecraft.server." + getServerVersion() + className;
            Class<?> theClass = Class.forName(fullName);
            return theClass;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static int getServerVersionNumber() {
        String name = getServerVersion().substring(3);
        return Integer.valueOf(name.substring(0, name.length() - 4));
    }

    public static Class<?> getCraftClass(String className) {
        try {
            String fullName = "org.bukkit.craftbukkit." + getServerVersion() + className;
            Class<?> theClass = Class.forName(fullName);
            return theClass;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String name, boolean declared) {
        try {
            Field field;

            if (declared) {
                field = clazz.getDeclaredField(name);
            } else {
                field = clazz.getField(name);
            }

            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getFieldObject(Object object, Field field) {
        try {
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendPacket(Player player, Object packet) {
        if (packet == null) return;
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
