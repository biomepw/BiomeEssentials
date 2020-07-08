package pw.biome.biomeessentials.commands;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pw.biome.biomeessentials.BiomeEssentials;

import java.util.HashMap;
import java.util.UUID;

public class DisableSleepSkipCommand implements CommandExecutor {

    @Getter
    private static final HashMap<UUID, Integer> preventListTaskMap = new HashMap<>();

    public static boolean isSleepDisabled() {
        return preventListTaskMap.size() != 0;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            World world = player.getWorld();

            if (preventListTaskMap.containsKey(uuid)) {
                Bukkit.getScheduler().cancelTask(preventListTaskMap.get(uuid));
                preventListTaskMap.remove(uuid);
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Sleeping is enabled again");
            } else {
                int taskId;
                if (world.isThundering()) {
                    Bukkit.broadcastMessage(ChatColor.GOLD + player.getDisplayName() + " would like the thunder");
                    taskId = Bukkit.getScheduler().runTaskLater(BiomeEssentials.getPlugin(), () ->
                            removeAndCheck(uuid), 3600).getTaskId();
                } else {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + " would like the night");
                    long time = world.getTime();
                    long delay;

                    if (time < 12541L) {
                        delay = 12541L - time + 3600L;
                    } else {
                        delay = 3600L;
                    }

                    taskId = Bukkit.getScheduler().runTaskLater(BiomeEssentials.getPlugin(), () ->
                            removeAndCheck(uuid), delay).getTaskId();
                }
                preventListTaskMap.put(uuid, taskId);
            }
        }
        return true;
    }

    public static void removeAndCheck(UUID uuid) {
        preventListTaskMap.remove(uuid);
        if (!isSleepDisabled()) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Sleeping is enabled again");
        }
    }
}
