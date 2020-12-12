package pw.biome.biomeessentials.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class DragonDropChanges implements Listener {

    @EventHandler
    public void dragonDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof EnderDragon)) return;

        World world = entity.getWorld();
        if (world.getName().equals("world_the_end")) {
            Block highestBlock = world.getHighestBlockAt(0, 0);
            if (highestBlock.getType() == Material.DRAGON_EGG) return;

            Location dragonEggSpawnLocation = new Location(world, 0.0, highestBlock.getLocation().getBlockY() + 1, 0.0);
            Block block = dragonEggSpawnLocation.getBlock();
            block.setType(Material.DRAGON_EGG);

            // 1/4 chance of ely drop from dragon
            var shouldSpawnEly = ThreadLocalRandom.current().nextInt(4) == 1;
            if (shouldSpawnEly) {
                var deathLocation = entity.getLocation();
                world.dropItemNaturally(deathLocation, new ItemStack(Material.ELYTRA, 1));
            }
        }
    }
}
