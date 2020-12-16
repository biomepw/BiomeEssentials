package pw.biome.biomeessentials.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class SkullCreator {

    /**
     * Creates a player skull item with the skin based on a player's UUID.
     *
     * @param id The Player's UUID.
     * @return The head of the Player.
     */
    public static ItemStack itemFromUuid(UUID id) {
        return itemWithUuid(new ItemStack(Material.PLAYER_HEAD), id);
    }

    /**
     * Modifies a skull to use the skin of the player with a given UUID.
     *
     * @param item The item to apply the name to. Must be a player skull.
     * @param id   The Player's UUID.
     * @return The head of the Player.
     */
    public static ItemStack itemWithUuid(ItemStack item, UUID id) {
        if (item == null || id == null) return null;

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(id);
        if (offlinePlayer.getName() == null) return null;

        PlayerProfile playerProfile = meta.getPlayerProfile();
        if (playerProfile != null && playerProfile.complete()) {
            meta.setPlayerProfile(playerProfile);
            meta.setDisplayName(ChatColor.YELLOW + offlinePlayer.getName() + "'s head");
            item.setItemMeta(meta);
        } else return null;

        return item;
    }
}