package eu.virtusdevelops.virtuscore.managers;

import eu.virtusdevelops.virtuscore.compatibility.ServerVersion;
import eu.virtusdevelops.virtuscore.utils.FileLocation;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.*;

import org.bukkit.plugin.java.JavaPlugin;

import static java.io.File.separator;

public class FileManager extends Manager<String, FileConfiguration> {
    private final String directory;
    private final LinkedHashSet<FileLocation> files;


    public FileManager(JavaPlugin plugin, LinkedHashSet<FileLocation> linkedHashSet) {
        super(plugin);
        files = linkedHashSet;
        directory = ServerVersion.isServerVersionAtLeast(ServerVersion.V1_13) ? "master" : "legacy";
        Bukkit.getConsoleSender().sendMessage("Using the " + directory + " configurations because version is " + ServerVersion.getServerVersion().name());
    }

    public void loadFiles() {
        files.forEach(fileLocation -> {
            File file = new File(plugin.getDataFolder() + separator + fileLocation.getPath());

            if (!file.exists() && (fileLocation.isRequired() || plugin.getConfig().getBoolean("system.first-load"))) {
                Bukkit.getConsoleSender().sendMessage("Creating file: " + fileLocation.getPath());
                file.getParentFile().mkdirs();

                try {
//                    System.out.println(fileLocation.getResourcePath(directory) + " : " + file.toPath());
                    copy(plugin.getResource(fileLocation.getResourcePath(directory)), Files.newOutputStream(file.toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileLocation.isRequired()) {
                FileConfiguration configuration = new YamlConfiguration();
                try {
                    configuration.load(file);
                } catch (IOException | InvalidConfigurationException e) {
                    e.printStackTrace();
                }
                add(fileLocation.getPath().replace(".yml", ""), configuration);
            }
        });

        plugin.getConfig().set("system.first-load", false);
        //plugin.saveConfig();
    }

    public FileConfiguration getConfiguration(String key) {
        return getValueUnsafe(key);
    }

    public void saveFile(String key){
        File file = new File(plugin.getDataFolder() + separator + key);
        try {
            getConfiguration(key.replace(".yml", "")).save(file);
        }catch (Exception error){
            error.printStackTrace();
        }
    }

    public List<File> getYmlFiles(String directory) {
        File dir = new File(plugin.getDataFolder() + separator + directory + separator);
        File[] allFiles = dir.listFiles();
        List<File> output = new ArrayList<>();

        if (allFiles == null) {
            return output;
        }

        Optional.ofNullable(dir.listFiles((dir1, filename) -> filename.endsWith(".yml"))).ifPresent(list -> {
            output.addAll(Arrays.asList(list));
        });

        Arrays.stream(allFiles)
                .filter(File::isDirectory)
                .filter(s -> !s.getName().equalsIgnoreCase("old"))
                .forEach(f -> output.addAll(getYmlFiles(directory + separator + f.getName())));

        return output;
    }

    private static void copy(InputStream input, OutputStream output) {
        int n;
        byte[] buffer = new byte[1024 * 4];

        try {
            while ((n = input.read(buffer)) != -1) {
                output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
