package pw.biome.biomeessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoordsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + "'s coords are "
                    + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockZ());
        }
        return true;
    }
}
