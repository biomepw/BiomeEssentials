package pw.biome.biomeessentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("slimechunk|sc")
public class SlimeChunkCommand extends BaseCommand {

    @Default
    @Description("Returns whether or not the player's current location is a slime chunk")
    public void onCommand(Player player) {
        if (player.getLocation().getChunk().isSlimeChunk()) {
            player.sendMessage(ChatColor.GREEN + "This chunk IS a slime chunk!");
        } else {
            player.sendMessage(ChatColor.RED + "This chunk IS NOT a slime chunk!");
        }
    }
}
