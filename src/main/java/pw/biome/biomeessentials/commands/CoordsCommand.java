package pw.biome.biomeessentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("coords")
@Description("Coords-related commands")
public class CoordsCommand extends BaseCommand {

    @Default
    @Description("Broadcasts the coords of the command sender")
    public void onCommand(Player player) {
        String worldName = player.getWorld().getName();
        String world = switch (worldName) {
            case "world_the_end" -> ChatColor.AQUA + "End";
            case "world" -> ChatColor.YELLOW + "Overworld";
            case "world_nether" -> ChatColor.RED + "Nether";
            default -> worldName;
        };

        Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + "'s coords are "
                + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " +
                player.getLocation().getBlockZ() + ", World: " + world);
    }
}
