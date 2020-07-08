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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DisableSleepSkipCommand implements CommandExecutor {

    @Getter
    private static final List<UUID> preventList = new ArrayList<>();

    public static boolean isSleepDisabled() {
        return preventList.isEmpty();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            World world = player.getWorld();

            if (preventList.contains(uuid)) {
                preventList.remove(uuid);

                Bukkit.broadcastMessage(ChatColor.YELLOW + "Sleeping is enabled again");
            } else {
                if (world.isThundering()) {
                    Bukkit.broadcastMessage(ChatColor.GOLD + player.getDisplayName() + " would like the thunder");
                    Bukkit.getScheduler().runTaskLater(BiomeEssentials.getPlugin(), () -> removeAndCheck(uuid), 3600);
                } else {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + " would like the night");
                    long time = world.getTime();
                    long delay;

                    if (time < 12541L) {
                        delay = 12541L - time + 3600L;
                    } else {
                        delay = 3600L;
                    }

                    Bukkit.getScheduler().runTaskLater(BiomeEssentials.getPlugin(), () -> removeAndCheck(uuid), delay);
                }
                preventList.add(uuid);
            }
        }
        return true;
    }

    public static void removeAndCheck(UUID uuid) {
        preventList.remove(uuid);
        if (!isSleepDisabled()) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Sleeping is enabled again");
        }
    }
}
