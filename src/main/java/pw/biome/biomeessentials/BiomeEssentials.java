package pw.biome.biomeessentials;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pw.biome.biomeessentials.commands.CoordsCommand;
import pw.biome.biomeessentials.commands.DisableSleepSkipCommand;
import pw.biome.biomeessentials.commands.WorkstationHighlightCommand;
import pw.biome.biomeessentials.listeners.DisableVoxelMap;
import pw.biome.biomeessentials.listeners.DragonEggDrop;
import pw.biome.biomeessentials.listeners.OnePlayerSleep;
import pw.biome.biomeessentials.listeners.ShulkerDoubleDrop;
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
        getServer().getScheduler().cancelTasks(this);
    }

    private void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(plugin);
        manager.registerCommand(new CoordsCommand());
        manager.registerCommand(new DisableSleepSkipCommand());
        manager.registerCommand(new WorkstationHighlightCommand());
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new DisableVoxelMap(), this);
        pluginManager.registerEvents(new ShulkerDoubleDrop(), this);
        pluginManager.registerEvents(new DragonEggDrop(), this);
        pluginManager.registerEvents(new OnePlayerSleep(), this);
        pluginManager.registerEvents(new WorkstationHighlight(), this);
    }
}