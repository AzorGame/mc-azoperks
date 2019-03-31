package io.github.azorimor.azoperks.listener;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.Perk;
import io.github.azorimor.azoperks.perks.PerksManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

/**
 * Manages the Join and Quit Events.
 */
public class LJoinQuit implements Listener {

    private PerksManager perksManager;

    public LJoinQuit(AzoPerks instance) {
        this.perksManager = instance.getPerksManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        perksManager.registerPlayerIfNotRegistered(player.getUniqueId());
        if(player.hasPotionEffect(PotionEffectType.SPEED) && !perksManager.getPerkPlayerByID(player.getUniqueId()).isPlayerPerkActive(Perk.FAST_RUN))
            player.removePotionEffect(PotionEffectType.SPEED);
        if(player.hasPotionEffect(PotionEffectType.JUMP) && !perksManager.getPerkPlayerByID(player.getUniqueId()).isPlayerPerkActive(Perk.SUPER_JUMP))
            player.removePotionEffect(PotionEffectType.JUMP);
        if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION) && !perksManager.getPerkPlayerByID(player.getUniqueId()).isPlayerPerkActive(Perk.NIGHT_VISION))
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING) && !perksManager.getPerkPlayerByID(player.getUniqueId()).isPlayerPerkActive(Perk.FAST_MINING))
            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
        if(player.getGameMode() != GameMode.CREATIVE || !perksManager.getPerkPlayerByID(player.getUniqueId()).isPlayerPerkActive(Perk.FLY))
            player.setAllowFlight(false); //TODO evtl hier nochmal mit fliegen schauen, ob das auch so passt

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        perksManager.unregisterPlayerIfRegistered(player.getUniqueId());
    }
}
