package io.github.azorimor.azoperks;

import org.bukkit.plugin.java.JavaPlugin;

public class AzoPerks extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
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
}
