package pw.biome.biomeessentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
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

        // If they are already in the cooldown, remove
        if (preventListTaskMap.containsKey(uuid)) {
            Bukkit.getScheduler().cancelTask(preventListTaskMap.get(uuid));
            removeAndCheck(uuid);
            player.sendMessage(ChatColor.GOLD + "You are no longer requesting sleep!");
        } else {

            // Process cooldown check
            if (cooldownSet.contains(uuid)) {
                player.sendMessage(ChatColor.RED + "You have a cooldown on this command!");
                return;
            }

            Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + " would like the night");

            // Set the task delay for the duration of the night
            long time = world.getTime();
            long delay = 24000 - time;

            int taskId = Bukkit.getScheduler().runTaskLater(BiomeEssentials.getPlugin(), () ->
                    removeAndCheck(uuid), delay).getTaskId();

            player.sendMessage(ChatColor.GOLD + "The night will be held until you turn it off.");

            preventListTaskMap.put(uuid, taskId);

            cooldownSet.add(uuid);
            Bukkit.getScheduler().runTaskLater(BiomeEssentials.getPlugin(), () -> cooldownSet.remove(uuid), 60 * 60 * 20); // 1 hr
        }
    }

    @Subcommand("force")
    @CommandPermission("preventsleep.force")
    public void forceSleep(Player player) {
        long fullTime = player.getWorld().getTime();
        long newTime = fullTime + (24000 - fullTime);

        player.getWorld().setTime(newTime);

        Bukkit.broadcastMessage(org.bukkit.ChatColor.YELLOW + player.getDisplayName() + org.bukkit.ChatColor.GOLD + " went to bed. Sweet Dreams");
    }

    @Subcommand("check")
    public void sleepDebug(CommandSender sender) {
        if (preventListTaskMap.size() == 0) {
            sender.sendMessage(ChatColor.GREEN + "No one is requesting night!");
        } else {
            sender.sendMessage(ChatColor.GOLD + "Players requesting sleep:");
            preventListTaskMap.forEach((uuid, taskId) -> {
                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                sender.sendMessage(ChatColor.RED + player.getName());
            });
        }

        sender.sendMessage(ChatColor.GOLD + "Players on cooldown for /preventsleep:");
        cooldownSet.forEach(uuid -> {
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            sender.sendMessage(ChatColor.RED + player.getName());
        });
    }

    public static void removeAndCheck(UUID uuid) {
        preventListTaskMap.remove(uuid);
        if (!isSleepDisabled()) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Sleeping is enabled again");
        }
    }
}