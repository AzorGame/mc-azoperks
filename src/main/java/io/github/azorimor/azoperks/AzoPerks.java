package io.github.azorimor.azoperks;

import io.github.azorimor.azoperks.command.CAzoPerks;
import io.github.azorimor.azoperks.command.CPerk;
import io.github.azorimor.azoperks.command.CPerks;
import io.github.azorimor.azoperks.command.CPerksInfo;
import io.github.azorimor.azoperks.listener.LHandlePerks;
import io.github.azorimor.azoperks.listener.LJoinQuit;
import io.github.azorimor.azoperks.listener.LPerksGUI;
import io.github.azorimor.azoperks.perks.PerksManager;
import io.github.azorimor.azoperks.storage.file.ConfigFile;
import io.github.azorimor.azoperks.utils.MessageHandler;
import io.github.azorimor.azoperks.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class AzoPerks extends JavaPlugin {

    private PerksManager perksManager;
    private ConfigFile configFile;
    private MessageHandler messageHandler;
    private UpdateChecker updateChecker;
    private boolean usePlotSuqared;

    @Override
    public void onEnable() {
        super.onEnable();
        this.updateChecker = new UpdateChecker(65679,this);
        this.perksManager = new PerksManager(this);
        this.configFile = new ConfigFile(this);
        this.configFile.updatePerks(); //Loads values to change the perk enum constants
        this.perksManager.updateGUIToggleItems();
        this.messageHandler = new MessageHandler(this);

        registerPlotSquared();

        registerCommands();
        registerListener();

        registerPlayerAfterReload();

        checkUpdates();
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

        CAzoPerks cAzoPerks = new CAzoPerks(this);
        getCommand("azoperks").setExecutor(cAzoPerks);
        getCommand("azoperks").setTabCompleter(cAzoPerks);

        CPerk cPerk = new CPerk(this);
        getCommand("perk").setExecutor(cPerk);
        getCommand("perk").setTabCompleter(cPerk);
    }

    private void registerListener(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new LJoinQuit(this), this);
        pm.registerEvents(new LPerksGUI(this),this);
        pm.registerEvents(new LHandlePerks(this),this);
    }

    private void registerPlayerAfterReload(){
        for (Player current :
                Bukkit.getOnlinePlayers()) {
            perksManager.registerPlayerIfNotRegistered(current.getUniqueId());
        }
    }

    private void registerPlotSquared(){
        Plugin plotSquared = getServer().getPluginManager().getPlugin("PlotSquared");
        if(plotSquared == null){
            this.usePlotSuqared = false;
            getLogger().info("PlotSquared plugin not found. AzoPerks supports no plot operations now.");
        }else{
            this.usePlotSuqared = true;
            getLogger().info("PlotSquared plugin found. AzoPerks supports plot operations now.");
        }
    }

    /**
     * Checks for updates and shows messages in the console.
     */
    private void checkUpdates() {
        getLogger().info("Checking for updates...");
        try {
            if(updateChecker.checkForUpdate()){
                getLogger().info("Updates found. Please visit the website to download it. (" + updateChecker.getNewVersion() + ")");
                getLogger().info(updateChecker.getResourceUrl());
            } else {
                getLogger().info("No updates found. You are up to date.");
            }
        } catch (IOException e) {
            getLogger().info("Could not check for updates. Please check your internet connection.");
        }
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

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    public boolean isUsePlotSuqared() {
        return usePlotSuqared;
    }

    //</editor-fold>
}
