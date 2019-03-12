package io.github.azorimor.azoperks.listener;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.PerksManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LJoin implements Listener {

    private PerksManager perksManager;

    public LJoin(AzoPerks instance) {
        this.perksManager = instance.getPerksManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        perksManager.registerPlayerIfNotRegistered(player.getUniqueId());
    }
}
