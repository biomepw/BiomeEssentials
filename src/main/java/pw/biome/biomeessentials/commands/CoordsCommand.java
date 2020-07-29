package pw.biome.biomeessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CoordsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String worldName = player.getWorld().getName();
            String world = worldName;

            switch (worldName) {
                case "world_the_end":
                    world = ChatColor.AQUA + "End";
                    break;
                case "world":
                    world = ChatColor.YELLOW + "Overworld";
                    break;
                case "world_nether":
                    world = ChatColor.RED + "Nether";
                    break;
            }

            Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + "'s coords are "
                    + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " +
                    player.getLocation().getBlockZ() + ", World: " + world);
        }
        return true;
    }
}
