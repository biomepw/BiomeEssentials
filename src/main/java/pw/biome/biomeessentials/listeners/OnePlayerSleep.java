package pw.biome.biomeessentials.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import pw.biome.biomeessentials.BiomeEssentials;

public class OnePlayerSleep implements Listener {

    @EventHandler
    public void bedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            if (world.isThundering()) world.setThundering(false);
            if (world.hasStorm()) world.setStorm(false);

            Bukkit.getScheduler().runTaskLater(BiomeEssentials.getPlugin(), () -> {
                if (player.isSleeping()) {
                    long fullTime = world.getTime();
                    long newTime = fullTime + (24000 - fullTime);

                    world.setTime(newTime);

                    Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + " went to bed. Sweet Dreams");
                }
            }, 5 * 20);
        }
    }
}