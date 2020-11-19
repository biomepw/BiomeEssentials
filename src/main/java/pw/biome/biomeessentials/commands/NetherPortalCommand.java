package pw.biome.biomeessentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("netherportal|np|netherp|portal")
@Description("Displays the converted coordinates")
public class NetherPortalCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        Location location = player.getLocation();
        String world = location.getWorld().getName();

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        if (world.equalsIgnoreCase("world")) {
            x = x / 8;
            y = y / 8;
            z = z / 8;

            player.sendMessage(ChatColor.YELLOW + "You should put your portal in the nether at: " +
                    ChatColor.GOLD + "x: " + x + ", y: " + y + ", z: " + z);
        } else if (world.equalsIgnoreCase("world_nether")) {
            x = x * 8;
            y = y * 8;
            z = z * 8;

            player.sendMessage(ChatColor.YELLOW + "You should put your portal in the overworld at: " +
                    ChatColor.GOLD + "x: " + x + ", y: " + y + ", z: " + z);
        }
    }
}