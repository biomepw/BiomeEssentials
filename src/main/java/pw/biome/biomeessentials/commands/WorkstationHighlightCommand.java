package pw.biome.biomeessentials.commands;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkstationHighlightCommand implements CommandExecutor {

    @Getter
    private static final List<UUID> selectingList = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender.hasPermission("whl.check")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                selectingList.add(player.getUniqueId());

                player.sendMessage(ChatColor.GREEN + "Okay! Now please right click the villager!");
            }
        }
        return true;
    }
}
