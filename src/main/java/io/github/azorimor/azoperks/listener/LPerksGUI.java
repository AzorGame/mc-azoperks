package io.github.azorimor.azoperks.listener;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.PerksManager;
import io.github.azorimor.azoperks.perks.PlayerPerk;
import io.github.azorimor.azoperks.utils.MessageHandler;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Manages the Perks GUI interaction.
 */
public class LPerksGUI implements Listener {


    private MessageHandler messageHandler;
    private PerksManager perksManager;

    public LPerksGUI(AzoPerks instance) {
        this.messageHandler = instance.getMessageHandler();
        this.perksManager = instance.getPerksManager();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory gui = event.getClickedInventory();
        if (gui != null) {
            if (gui.getName().equals(perksManager.getGUI_NAME())) {
                int clickedSlot = event.getSlot();
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem.equals(perksManager.getPerkActivated())
                        || clickedItem.equals(perksManager.getPerkOwned())
                        || clickedItem.equals(perksManager.getPerkUnOwned())) {
                    Player player = (Player) event.getWhoClicked();
                    PlayerPerk requestedPerk = perksManager.getPlayerPerkByToggleItem(player.getUniqueId(), clickedSlot);
                    if (requestedPerk.getPerk().getPerkAreaManager().isPerkUsedInAllowedArea(player)) {
                        if (perksManager.updatePerkGUIItem(requestedPerk, clickedSlot, player.getUniqueId())) {
                            perksManager.updatePotionEffects(player,requestedPerk);
                            messageHandler.sendPerkChangeStatusSuccess(player, requestedPerk);
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                        } else {
                            messageHandler.sendPerkChangeStatusFailure(player, requestedPerk);
                        }
                    } else {
                        messageHandler.sendPluginMessage(player, "You are not allowed to use the perk here.");
                    }
                }
                event.setCancelled(true);
            }
        }
    }
}
