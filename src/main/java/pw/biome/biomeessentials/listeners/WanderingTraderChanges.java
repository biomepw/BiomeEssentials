package pw.biome.biomeessentials.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import pro.husk.whitelistsql.utility.MySQLHelper;
import pw.biome.biomeessentials.BiomeEssentials;
import pw.biome.biomeessentials.util.SkullCreator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class WanderingTraderChanges implements Listener {

    /**
     * Method to handle when traders spawn
     * Will automatically overwrite any trades from every wandering trader and replace them with 10 random player skulls
     *
     * @param event WanderingTrader spawn event
     */
    @EventHandler
    public void traderSpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();

        if (entity.getType() == EntityType.WANDERING_TRADER) {
            WanderingTrader wanderingTrader = (WanderingTrader) entity;

            // Run a tick later...
            Bukkit.getScheduler().runTaskLaterAsynchronously(BiomeEssentials.getPlugin(), () -> handleTrades(wanderingTrader), 1);
        }
    }

    /**
     * Helper method to populate the wandering traders' trades with skulls of 10 random players on the whitelist
     *
     * @param wanderingTrader to change trades for
     */
    private void handleTrades(WanderingTrader wanderingTrader) {
        List<MerchantRecipe> newRecipes = new ArrayList<>(10);

        // Loop through random players and create a skull object for each, and then place that skull in the newRecipes collection
        for (UUID uuid : getRandomPlayers()) {
            ItemStack skull = SkullCreator.itemFromUuid(uuid);
            if (skull != null) {
                MerchantRecipe recipe = new MerchantRecipe(skull, 3);
                recipe.addIngredient(new ItemStack(Material.EMERALD_BLOCK));
                newRecipes.add(recipe);
            }
        }

        // Return to sync, and set recipes
        Bukkit.getScheduler().runTask(BiomeEssentials.getPlugin(), () -> wanderingTrader.setRecipes(newRecipes));
    }

    /**
     * Little helper method to get 10 random player UUIDs from the whitelist cache
     *
     * @return List of 10 UUID
     */
    public HashSet<UUID> getRandomPlayers() {
        HashSet<UUID> randomPlayers = new HashSet<>(10);
        List<UUID> whitelistCache = new ArrayList<>(MySQLHelper.getWhitelistCache().keySet());

        for (int i = 0; i < 8; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(whitelistCache.size());
            UUID randomEntry = whitelistCache.get(randomInt);
            if (!randomPlayers.contains(randomEntry)) {
                randomPlayers.add(randomEntry);
            } else {
                i--;
            }
        }

        return randomPlayers;
    }
}