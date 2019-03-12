package io.github.azorimor.azoperks;

import io.github.azorimor.azoperks.perks.PerksManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AzoPerks extends JavaPlugin {

    private PerksManager perksManager;

    @Override
    public void onEnable() {
        super.onEnable();
        this.perksManager = new PerksManager(this);
        registerCommands();
        registerListener();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerCommands(){

    }

    private void registerListener(){

    }

    //<editor-fold desc="Getter">
    public PerksManager getPerksManager() {
        return perksManager;
    }
    //</editor-fold>
}
