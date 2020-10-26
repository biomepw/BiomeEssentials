package pw.biome.biomeessentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

@CommandAlias("workstationhighlights|whl")
@Description("Sleep management commands")
public class WorkstationHighlightCommand extends BaseCommand {

    @Getter
    private static final HashSet<UUID> selectingList = new HashSet<>();

    @Default
    public void onCommand(Player player) {
        selectingList.add(player.getUniqueId());
        player.sendMessage(ChatColor.GREEN + "Okay! Now please right click the villager!");
    }
}
