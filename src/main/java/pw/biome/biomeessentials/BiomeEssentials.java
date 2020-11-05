package pw.biome.biomeessentials;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pw.biome.biomeessentials.commands.CoordsCommand;
import pw.biome.biomeessentials.commands.DisableSleepSkipCommand;
import pw.biome.biomeessentials.commands.SlimeChunkCommand;
import pw.biome.biomeessentials.commands.WorkstationHighlightCommand;
import pw.biome.biomeessentials.listeners.DisableVoxelMap;
import pw.biome.biomeessentials.listeners.DragonEggDrop;
import pw.biome.biomeessentials.listeners.OnePlayerSleep;
import pw.biome.biomeessentials.listeners.WanderingTraderChanges;
import pw.biome.biomeessentials.listeners.WorkstationHighlight;

public final class BiomeEssentials extends JavaPlugin {

    @Getter
    private static BiomeEssentials plugin;

    @Override
    public void onEnable() {
        plugin = this;
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(plugin);
    }

    private void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(plugin);
        manager.registerCommand(new CoordsCommand());
        manager.registerCommand(new DisableSleepSkipCommand());
        manager.registerCommand(new WorkstationHighlightCommand());
        manager.registerCommand(new SlimeChunkCommand());
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new DisableVoxelMap(), plugin);
        pluginManager.registerEvents(new DragonEggDrop(), plugin);
        pluginManager.registerEvents(new OnePlayerSleep(), plugin);
        pluginManager.registerEvents(new WorkstationHighlight(), plugin);
        pluginManager.registerEvents(new WanderingTraderChanges(), plugin);
    }
}