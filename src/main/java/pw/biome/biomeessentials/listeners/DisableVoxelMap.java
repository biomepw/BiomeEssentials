package pw.biome.biomeessentials.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DisableVoxelMap implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendRawMessage("§3 §6 §3 §6 §3 §6 §d ");
        player.sendRawMessage("§3 §6 §3 §6 §3 §6 §e ");
    }
}
