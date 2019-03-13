package io.github.azorimor.azoperks.listener;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.Perk;
import io.github.azorimor.azoperks.perks.PerksManager;
import io.github.azorimor.azoperks.perks.PlayerPerk;
import io.github.azorimor.azoperks.utils.MessageHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LPerksGUI implements Listener {


    private MessageHandler messageHandler;
    private PerksManager perksManager;

    public LPerksGUI(AzoPerks instance) {
        this.messageHandler = instance.getMessageHandler();
        this.perksManager = instance.getPerksManager();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Inventory gui = event.getClickedInventory();
        if(gui != null){
            if(gui.getName().equals(perksManager.getGUI_NAME())){
                int clickedSlot = event.getSlot();
                ItemStack clickedItem = event.getCurrentItem();
                if(clickedItem.equals(perksManager.getPerkActivated())
                        || clickedItem.equals(perksManager.getPerkOwned() )
                        || clickedItem.equals(perksManager.getPerkUnOwned())){
                    Player player = (Player) event.getWhoClicked();
                    PlayerPerk requestedPerk = perksManager.getPlayerPerkByToggleItem(player.getUniqueId(),clickedSlot);
                    if(perksManager.updatePerkGUIItem(requestedPerk,clickedSlot,player.getUniqueId())){

                        Perk perk = requestedPerk.getPerk();
                        if(perk == Perk.FAST_RUN){
                            if(requestedPerk.isActive())
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,2,false,false,false));
                            else
                                player.removePotionEffect(PotionEffectType.SPEED);
                        } else if(perk == Perk.SUPER_JUMP){
                            if(requestedPerk.isActive())
                                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,2,false,false,false));
                            else
                                player.removePotionEffect(PotionEffectType.JUMP);
                        }

                        messageHandler.sendPluginMessage(player,"§7You §asuccessfully§7 changed the perkstatus for the perk: §a"+requestedPerk.getPerk().getName());

                    } else {
                        messageHandler.sendPluginMessage(player,"§7The status of the perk §c"+requestedPerk.getPerk().getName()+"§7 could §cnot§7 be changed.");
                    }
                }
                event.setCancelled(true);
            }
        }
    }

}