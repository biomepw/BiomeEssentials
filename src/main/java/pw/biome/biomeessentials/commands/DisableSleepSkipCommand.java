package pw.biome.biomeessentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import pw.biome.biomeessentials.BiomeEssentials;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

@CommandAlias("preventsleep|ps")
@Description("Sleep management commands")
public class DisableSleepSkipCommand extends BaseCommand {

    @Getter
    private static final HashMap<UUID, Integer> preventListTaskMap = new HashMap<>();

    private static final HashSet<UUID> cooldownSet = new HashSet<>();

    public static boolean isSleepDisabled() {
        return preventListTaskMap.size() != 0;
    }

    @Default
    @Description("Prevents sleeping")
    public void onCommand(Player player) {
        UUID uuid = player.getUniqueId();
        World world = player.getWorld();

        if (cooldownSet.contains(uuid)) {
            player.sendMessage(ChatColor.RED + "You have a cooldown on this command!");
            return;
        }

        if (preventListTaskMap.containsKey(uuid)) {
            Bukkit.getScheduler().cancelTask(preventListTaskMap.get(uuid));
            preventListTaskMap.remove(uuid);
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Sleeping is enabled again");
        } else {
            int taskId;
            if (world.isThundering()) {
                Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + " would like the thunder");
                taskId = Bukkit.getScheduler().runTaskLater(BiomeEssentials.getPlugin(), () ->
                        removeAndCheck(uuid), 3600).getTaskId();
            } else {
                Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + " would like the night");
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

            cooldownSet.add(uuid);
            Bukkit.getScheduler().runTaskLater(BiomeEssentials.getPlugin(), () -> cooldownSet.remove(uuid), 10 * 60 * 20); // 10 minutes
        }
    }

    public static void removeAndCheck(UUID uuid) {
        preventListTaskMap.remove(uuid);
        if (!isSleepDisabled()) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Sleeping is enabled again");
        }
    }
}