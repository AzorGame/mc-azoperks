package io.github.azorimor.azoperks;

import io.github.azorimor.azoperks.command.CPerks;
import io.github.azorimor.azoperks.command.CPerksInfo;
import io.github.azorimor.azoperks.listener.LJoin;
import io.github.azorimor.azoperks.perks.PerksManager;
import io.github.azorimor.azoperks.storage.file.ConfigFile;
import io.github.azorimor.azoperks.utils.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AzoPerks extends JavaPlugin {

    private PerksManager perksManager;
    private ConfigFile configFile;
    private MessageHandler messageHandler;
    @Override
    public void onEnable() {
        super.onEnable();
        this.perksManager = new PerksManager(this);
        this.configFile = new ConfigFile(this);
        this.messageHandler = new MessageHandler(this);
        registerCommands();
        registerListener();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerCommands(){
        CPerks cPerks = new CPerks(this);
        getCommand("perks").setExecutor(cPerks);
        getCommand("perks").setTabCompleter(cPerks);

        CPerksInfo cPerksInfo = new CPerksInfo(this);
        getCommand("perksinfo").setExecutor(cPerksInfo);
        getCommand("perksinfo").setTabCompleter(cPerksInfo);
    }

    private void registerListener(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new LJoin(this), this);
    }

    //<editor-fold desc="Getter">
    public PerksManager getPerksManager() {
        return perksManager;
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }
    //</editor-fold>
}
