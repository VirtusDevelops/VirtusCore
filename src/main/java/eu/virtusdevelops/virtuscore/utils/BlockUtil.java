package eu.virtusdevelops.virtuscore.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil {
    public static List<Block> getSquare(Block start, int radius){
        if (radius < 0) {
            return new ArrayList<>(0);
        }
        int iterations = (radius * 2) + 1;
        List<Block> blocks = new ArrayList<>(iterations * iterations * iterations);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    blocks.add(start.getRelative(x, y, z));
                }
            }
        }
        return blocks;
    }


    public static List<Block> getSquare(Block start, int radius, int height){
        if (radius < 0) {
            return new ArrayList<>(0);
        }
        int iterations = (radius * 2) + 1;
        int iterations2 = (height * 2) + 1;

        List<Block> blocks = new ArrayList<>(iterations * iterations2 * iterations);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -height; y <= height; y++) {
                for (int z = -radius; z <= radius; z++) {
                    blocks.add(start.getRelative(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static List<Block> getFlatSquare(Block start, int radius){
        if (radius < 0) {
            return new ArrayList<>(0);
        }
        int iterations = (radius * 2) + 1;
        List<Block> blocks = new ArrayList<>(iterations * iterations);
        int y = start.getY();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                blocks.add(start.getRelative(x, y, z));
            }

        }
        return blocks;
    }

    private static List<Block> getBlocks(final Location base, final int changeX, final int changeY, final int changeZ) {
        final List<Block> blocks = new ArrayList<>();
        for (int x = base.getBlockX() - changeX; x <= base.getBlockX() + changeX; ++x) {
            for (int y = base.getBlockY() - changeY; y <= base.getBlockY() + changeY; ++y) {
                for (int z = base.getBlockZ() - changeZ; z <= base.getBlockZ() + changeZ; ++z) {
                    final Location loc = new Location(base.getWorld(), (double)x, (double)y, (double)z);
                    final Block b = loc.getBlock();
                    if (!b.getType().equals(Material.AIR) && b != null) {
                        blocks.add(b);
                    }
                }
            }
        }
        return blocks;
    }
}
