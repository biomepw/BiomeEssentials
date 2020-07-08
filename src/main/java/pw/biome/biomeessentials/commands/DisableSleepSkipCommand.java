package pw.biome.biomeessentials.commands;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (preventList.contains(uuid)) {
                preventList.remove(uuid);

                Bukkit.broadcastMessage(ChatColor.YELLOW + "Sleeping is enabled again");
            } else {
                preventList.add(uuid);


            }
        }
        return true;
    }
}
