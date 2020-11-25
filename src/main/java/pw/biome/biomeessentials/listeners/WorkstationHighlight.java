package pw.biome.biomeessentials.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import pw.biome.biomeessentials.BiomeEssentials;
import pw.biome.biomeessentials.commands.WorkstationHighlightCommand;

import java.util.Optional;

public class WorkstationHighlight implements Listener {

    @EventHandler
    public void rightClickVillager(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity instanceof Villager && WorkstationHighlightCommand.getSelectingList().contains(player.getUniqueId())) {
            Villager villager = (Villager) entity;

            Optional.ofNullable(villager.getMemory(MemoryKey.JOB_SITE)).ifPresent(jobLocation -> {
                ArmorStand armorStand = (ArmorStand) jobLocation.getWorld().spawnEntity(jobLocation, EntityType.ARMOR_STAND);

                armorStand.setInvulnerable(true);
                armorStand.setVisible(false);

                armorStand.setCanMove(false);
                armorStand.setCanTick(false);
                armorStand.setCanPickupItems(false);
                armorStand.setCollidable(false);

                armorStand.setCustomName(ChatColor.GOLD + "Villager workstation");
                armorStand.setCustomNameVisible(true);

                player.sendMessage(ChatColor.GREEN + "Villagers job location is now illuminated: "
                        + ChatColor.GOLD + "X: " + jobLocation.getBlockX() + ", Y: " + jobLocation.getBlockY() + ", Z: " +
                        jobLocation.getBlockZ());

                WorkstationHighlightCommand.getSelectingList().remove(player.getUniqueId());

                Bukkit.getScheduler().runTaskLater(BiomeEssentials.getPlugin(), armorStand::remove, (10 * 20));
            });
        }
    }
}
