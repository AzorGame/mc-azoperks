package io.github.azorimor.azoperks.listener;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.PerksManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

public class LJoinQuit implements Listener {

    private PerksManager perksManager;

    public LJoinQuit(AzoPerks instance) {
        this.perksManager = instance.getPerksManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        perksManager.registerPlayerIfNotRegistered(player.getUniqueId());
        if(player.hasPotionEffect(PotionEffectType.SPEED))
            player.removePotionEffect(PotionEffectType.SPEED);
        if(player.hasPotionEffect(PotionEffectType.JUMP))
            player.removePotionEffect(PotionEffectType.JUMP);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        perksManager.unregisterPlayerIfRegistered(player.getUniqueId());
    }
}
