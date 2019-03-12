package io.github.azorimor.azoperks.perks;


import io.github.azorimor.azoperks.AzoPerks;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class PerksManager {

    private ArrayList<PerkPlayer> perkPlayers;
    private ItemStack perkOwned, perkUnOwned, perkActivated;
    
    public PerksManager(AzoPerks instance){
        this.perkPlayers = new ArrayList<PerkPlayer>(instance.getServer().getMaxPlayers());
    }


    public static String GUI_NAME = "§6Perks";

    /**
     * Generates the GUI to manage the perks settings.
     * @param uuid {@link UUID} of the {@link org.bukkit.entity.Player} for who this GUI ({@link Inventory}) is generated.
     * @return {@link Inventory} player specific generated GUI.
     */
    public Inventory generatePerkInventoryForPlayer(UUID uuid){
        Inventory gui = Bukkit.createInventory(null,54, GUI_NAME);

        int currentInventorySlot = 1;
        int currentItemNumber = 1;

        for (Perk perk:
             Perk.values()) {
            if (currentItemNumber <= 6) {
                gui.setItem(currentInventorySlot,perk.getGuiItem());
                currentInventorySlot++;
                PlayerPerk playerPerk = getPlayerPerkForPlayer(uuid,perk);
                if(playerPerk.isActive())
                    gui.setItem(currentInventorySlot,perkActivated);
                else if(!playerPerk.isOwned())
                    gui.setItem(currentInventorySlot,perkUnOwned);
                else if(playerPerk.isOwned())
                    gui.setItem(currentInventorySlot,perkOwned);
                currentInventorySlot += 8;
                currentItemNumber++;
            } else {
                if(currentItemNumber == 7)
                    currentInventorySlot = 7;

                gui.setItem(currentInventorySlot,perk.getGuiItem());
                currentInventorySlot--;
                PlayerPerk playerPerk = getPlayerPerkForPlayer(uuid,perk);
                if(playerPerk.isActive())
                    gui.setItem(currentInventorySlot,perkActivated);
                else if(!playerPerk.isOwned())
                    gui.setItem(currentInventorySlot,perkUnOwned);
                else if(playerPerk.isOwned())
                    gui.setItem(currentInventorySlot,perkOwned);
                currentInventorySlot += 9;
                currentItemNumber++;
            }
        }
        return gui;
    }

    public void registerPlayerIfNotRegistered(UUID uuid){
        if(!isPlayerRegistered(uuid))
            this.perkPlayers.add(new PerkPlayer(uuid,generatePerkInventoryForPlayer(uuid)));
    }

    public boolean isPlayerRegistered(UUID uuid){
        return getPerkPlayerByID(uuid) != null;
    }

    //<editor-fold desc="Almost everything here must be updated for performance">
    public PerkPlayer getPerkPlayerByID(UUID uuid){
        for (PerkPlayer perkPlayer :
                perkPlayers) {
            if (perkPlayer.getPlayerUUID() == uuid)
                return perkPlayer;
        }
        return null;
    }

    public boolean isPerkActive(UUID toCheck, Perk perk) {
        return getPlayerPerkForPlayer(toCheck,perk).isActive();
    }

    public void changeActivePerkStatus(UUID playerID, Perk perk, boolean avtive) {
        getPlayerPerkForPlayer(playerID,perk).setActive(true);
    }

    public boolean isPerkOwned(UUID playerID, Perk perk){
        return getPlayerPerkForPlayer(playerID,perk).isOwned();
    }

    public void changeOwnedPerkStatus(UUID playerID, Perk perk, boolean owned){
        getPlayerPerkForPlayer(playerID,perk).setOwned(owned);
    }

    public ArrayList<PlayerPerk> getAllOwnedPlayerPerksForPlayer(UUID playerID){
        ArrayList<PlayerPerk> ownedPerks = new ArrayList<PlayerPerk>();

        for (PlayerPerk perk :
                getPlayerPerksForPlayer(playerID)) {
            if(perk.isOwned())
                ownedPerks.add(perk);
        }

        return ownedPerks;
    }

    public ArrayList<PlayerPerk> getAllActivePlayerPerksForPlayer(UUID playerID){
        ArrayList<PlayerPerk> activePerks = new ArrayList<PlayerPerk>();

        for (PlayerPerk perk :
                getAllOwnedPlayerPerksForPlayer(playerID)) {
            if(perk.isActive())
                activePerks.add(perk);
        }

        return activePerks;
    }

    public ArrayList<PlayerPerk> getPlayerPerksForPlayer(UUID uuid) {
        for (PerkPlayer current : perkPlayers) {
            if (current.getPlayerUUID() == uuid)
                return current.getPerks();
        }
        return null;
    }

    public PlayerPerk getPlayerPerkForPlayer(UUID uuid, Perk perk){
        for (PlayerPerk currentPerk :
                getPlayerPerksForPlayer(uuid)) {
            if (currentPerk.getPerk() == perk)
                return currentPerk;
        }
        return null;
    }
    //</editor-fold>

    public ArrayList<PerkPlayer> getPerkPlayers() {
        return perkPlayers;
    }
}
