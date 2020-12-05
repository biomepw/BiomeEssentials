package pw.biome.biomeessentials.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShulkerDoubleDrop implements Listener {

    @EventHandler
    public void shulkerDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if (entity.getType() == EntityType.SHULKER) {
            List<ItemStack> drops = event.getDrops();

            ItemStack shulkerShell = new ItemStack(Material.SHULKER_SHELL);

            if (drops.contains(shulkerShell)) {
                drops.forEach(itemStack -> {
                    if (itemStack.getType() == Material.SHULKER_SHELL) {
                        itemStack.setAmount(itemStack.getAmount() * 2);
                    }
                });
            } else {
                shulkerShell.setAmount(2);
                drops.add(shulkerShell);
            }
        }
    }
}