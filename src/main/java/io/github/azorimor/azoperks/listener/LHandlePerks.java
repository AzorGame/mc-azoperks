package io.github.azorimor.azoperks.listener;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.Perk;
import io.github.azorimor.azoperks.perks.PerkPlayer;
import io.github.azorimor.azoperks.perks.PerksManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

/**
 * Here are all Perks managed, which need to be managed inside events.
 */
public class LHandlePerks implements Listener {

    private PerksManager perksManager;

    public LHandlePerks(AzoPerks instance) {
        this.perksManager = instance.getPerksManager();
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player){
            PerkPlayer perkPlayer = perksManager.getPerkPlayerByID(event.getEntity().getUniqueId());
            if (perkPlayer.isPlayerPerkActive(Perk.NO_FALL_DAMAGE)) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
                    event.setCancelled(true);
            }
            if (perkPlayer.isPlayerPerkActive(Perk.NO_FIRE_DAMAGE)) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FIRE ||
                        event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
                        event.getCause() == EntityDamageEvent.DamageCause.LAVA)
                    event.setCancelled(true);
            }
            if (perkPlayer.isPlayerPerkActive(Perk.NO_DROWNING_DAMAGE)) {
                if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING)
                    event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event){
        if(event.getEntity() instanceof Player){
            PerkPlayer perkPlayer = perksManager.getPerkPlayerByID(event.getEntity().getUniqueId());
            if(perkPlayer.isPlayerPerkActive(Perk.NO_HUNGER)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        PerkPlayer perkPlayer = perksManager.getPerkPlayerByID(event.getEntity().getUniqueId());
        if(perkPlayer.isPlayerPerkActive(Perk.KEEP_INVENTORY)){
            event.setKeepInventory(true);
        }
        if(perkPlayer.isPlayerPerkActive(Perk.KEEP_XP)){
            event.setKeepLevel(true);
        }
    }

    @EventHandler
    public void onExPChange(PlayerExpChangeEvent event){
        PerkPlayer perkPlayer = perksManager.getPerkPlayerByID(event.getPlayer().getUniqueId());
        if(perkPlayer.isPlayerPerkActive(Perk.DOUBLE_XP)){
            event.setAmount(event.getAmount() * 2);
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof  Player){
            Player player = (Player) event.getDamager();
            PerkPlayer perkPlayer = perksManager.getPerkPlayerByID(player.getUniqueId());
            if(perkPlayer.isPlayerPerkActive(Perk.DOUBLE_DAMAGE)){
                event.setDamage(event.getDamage() * 2);
            }
        }
    }

}
