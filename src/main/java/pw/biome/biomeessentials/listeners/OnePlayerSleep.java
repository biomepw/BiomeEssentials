package pw.biome.biomeessentials.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pw.biome.biomeessentials.BiomeEssentials;
import pw.biome.biomeessentials.commands.DisableSleepSkipCommand;

import java.util.HashMap;
import java.util.UUID;

public class OnePlayerSleep implements Listener {

    private final HashMap<UUID, Integer> buffer = new HashMap<>();

    @EventHandler
    public void bedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            if (DisableSleepSkipCommand.isSleepDisabled()) {
                player.sendMessage(ChatColor.YELLOW + "Sorry, you can't sleep right now!");
                event.setCancelled(true);
            } else {
                if (world.isThundering()) world.setThundering(false);
                if (world.hasStorm()) world.setStorm(false);

                Bukkit.getScheduler().runTaskLater(BiomeEssentials.getPlugin(), () -> {
                    if (player.isSleeping()) {
                        long fullTime = world.getFullTime();
                        long newTime = fullTime + (24000 - fullTime);

                        world.setFullTime(newTime);

                        Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + " went to bed. Sweet Dreams");
                    }
                }, 5 * 20);
            }
        }
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (DisableSleepSkipCommand.getPreventListTaskMap().containsKey(uuid) && !buffer.containsKey(uuid)) {
            int taskId = Bukkit.getScheduler().runTaskLater(BiomeEssentials.getPlugin(), () ->
                    DisableSleepSkipCommand.removeAndCheck(uuid), 5 * 20).getTaskId();
            buffer.put(uuid, taskId);
        }
    }

    @EventHandler
    public void playerJoin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();

        int taskId = buffer.getOrDefault(uuid, 0);
        if (taskId != 0) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }
}