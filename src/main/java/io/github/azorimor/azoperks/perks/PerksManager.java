package io.github.azorimor.azoperks.perks;


import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.storage.file.ConfigFile;
import io.github.azorimor.azoperks.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This class manages all {@link PerkPlayer}. And many features of the perk system.
 */
public class PerksManager {

    private ArrayList<PerkPlayer> perkPlayers;
    private ItemStack perkOwned, perkUnOwned, perkActivated;
    private final String GUI_NAME = "§6§lPerks";
    private AzoPerks instance;

    public PerksManager(AzoPerks instance) {
        this.instance = instance;
        this.perkPlayers = new ArrayList<PerkPlayer>(instance.getServer().getMaxPlayers());
        this.perkOwned = new ItemBuilder(Material.ROSE_RED)
                .setDisplayName("§eOwned")
                .setLore("§7You can use this perk.")
                .build();
        this.perkUnOwned = new ItemBuilder(Material.GRAY_DYE)
                .setDisplayName("§cNot owned")
                .setLore("§7You need to buy this perk first.")
                .build();
        this.perkActivated = new ItemBuilder(Material.LIME_DYE)
                .setDisplayName("§aActive")
                .setLore("§7You are using this perk right now.")
                .build();
    }



    /**
     * Generates the GUI to manage the perks settings.
     *
     * @param uuid {@link UUID} of the {@link org.bukkit.entity.Player} for who this GUI ({@link Inventory}) is generated.
     * @return {@link Inventory} player specific generated GUI.
     */
    public Inventory generatePerkInventoryForPlayer(UUID uuid) {
        Inventory gui = Bukkit.createInventory(null, 54, GUI_NAME);

        for (Perk perk :
                Perk.values()) {
            gui.setItem(perk.getGuiItemSlot(),perk.getGuiItem());
            PlayerPerk playerPerk = getPlayerPerkForPlayer(uuid,perk);
            switch (playerPerk.getStatus()){
                case ACTIVE:
                    gui.setItem(perk.getToggleGuiItemSlot(),perkActivated);
                    break;
                case NOT_ACTIVE:
                    gui.setItem(perk.getToggleGuiItemSlot(),perkOwned);
                    break;
                case NOT_OWNED:
                    gui.setItem(perk.getToggleGuiItemSlot(),perkUnOwned);
                    break;
            }
        }
        return gui;
    }

    public void registerPlayerIfNotRegistered(UUID uuid) {
        if (!isPlayerRegistered(uuid)) {
            PerkPlayer perkPlayer = new PerkPlayer(uuid, null);
            this.perkPlayers.add(perkPlayer);
            perkPlayer.setPerkGUI(generatePerkInventoryForPlayer(uuid));
        }
    }

    public void unregisterPlayerIfRegistered(UUID uuid){
        if(isPlayerRegistered(uuid)){
            this.perkPlayers.remove(getPerkPlayerByID(uuid));
        }
    }

    public Perk getPerkByToggleItem(int slot) {
        for (Perk perk :
                Perk.values()) {
            if (perk.getToggleGuiItemSlot() == slot)
                return perk;
        }
        return null;
    }

    public void resetPlayerPerkGUI(UUID uuid) {
        getPerkPlayerByID(uuid).setPerkGUI(generatePerkInventoryForPlayer(uuid));
    }

    public void updatePlayerPerkGUISinglePerk(UUID uuid, int slot) {
        updatePerkInformationGUIItem(getPlayerPerkByToggleItem(uuid, slot), slot, getPerkPlayerByID(uuid).getPerkGUI());
    }

    public boolean updatePerkGUIItem(PlayerPerk playerPerk, int slot, UUID uuid) {

        Inventory gui = getPerkPlayerByID(uuid).getPerkGUI();
        switch (playerPerk.getStatus()) {
            case NOT_ACTIVE:
                gui.setItem(slot, perkActivated);
                playerPerk.setStatus(PlayerPerkStatus.ACTIVE);
                return true;
            case ACTIVE:
                if (playerPerk.isOwned()) {
                    gui.setItem(slot, perkOwned);
                    playerPerk.setStatus(PlayerPerkStatus.NOT_ACTIVE);
                } else {
                    gui.setItem(slot,perkUnOwned);
                    playerPerk.setStatus(PlayerPerkStatus.NOT_OWNED);
                }
                return true;
        }
        return false;
    }

    private void updatePerkInformationGUIItem(PlayerPerk playerPerk, int slot, Inventory gui) {
        if (playerPerk.isActive())
            gui.setItem(slot, perkActivated);
        else if (!playerPerk.isOwned())
            gui.setItem(slot, perkUnOwned);
        else if (playerPerk.isOwned())
            gui.setItem(slot, perkOwned);
    }

    public PlayerPerk getPlayerPerkByToggleItem(UUID uuid, int slot) {
        return getPlayerPerkForPlayer(uuid, getPerkByToggleItem(slot));
    }

    public boolean isPlayerRegistered(UUID uuid) {
        return getPerkPlayerByID(uuid) != null;
    }

    public void updateGUIToggleItems(){
        ConfigFile config = instance.getConfigFile();
        String path = "perk.toggleItem";
        if(config.isSet(path+".owned"))
            this.perkOwned = new ItemBuilder(config.getItemStack(path+".owned")).build();
        if(config.isSet(path+".unowned"))
            this.perkUnOwned = new ItemBuilder(config.getItemStack(path+".unowned")).build();
        if(config.isSet(path+".active"))
            this.perkActivated = new ItemBuilder(config.getItemStack(path+".active")).build();
    }

    public void updatePotionEffects(Player player, PlayerPerk requestedPerk){
        Perk perk = requestedPerk.getPerk();
        if (perk == Perk.FAST_RUN) {
            if (requestedPerk.isActive())
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false, false));
            else
                player.removePotionEffect(PotionEffectType.SPEED);
        } else if (perk == Perk.SUPER_JUMP) {
            if (requestedPerk.isActive())
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2, false, false, false));
            else
                player.removePotionEffect(PotionEffectType.JUMP);
        } else if (perk == Perk.NIGHT_VISION) {
            if (requestedPerk.isActive())
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 2, false, false, false));
            else
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        } else if (perk == Perk.FLY) {
            if (requestedPerk.isActive())
                player.setAllowFlight(true);
            else
                player.setAllowFlight(false);
        } else if (perk == Perk.FAST_MINING) {
            if (requestedPerk.isActive())
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 2, false, false, false));
            else
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
        }
    }

    //<editor-fold desc="Almost everything here must be updated for performance">
    public PerkPlayer getPerkPlayerByID(UUID uuid) {
        for (PerkPlayer perkPlayer :
                perkPlayers) {
            if (perkPlayer.getPlayerUUID() == uuid)
                return perkPlayer;
        }
        return null;
    }

    public boolean isPerkActive(UUID toCheck, Perk perk) {
        return getPlayerPerkForPlayer(toCheck, perk).isActive();
    }

    public void changePerkStatus(UUID playerID, Perk perk, PlayerPerkStatus status) {
        getPlayerPerkForPlayer(playerID,perk).setStatus(status);
    }


    public boolean togglePerkStatus(UUID playerID, Perk perk){
        return getPlayerPerkForPlayer(playerID,perk).togglePerkStatus();
    }

    public ArrayList<PlayerPerk> getAllOwnedPlayerPerksForPlayer(UUID playerID) {
        ArrayList<PlayerPerk> ownedPerks = new ArrayList<PlayerPerk>();

        for (PlayerPerk perk :
                getPlayerPerksForPlayer(playerID)) {
            if (perk.isOwned())
                ownedPerks.add(perk);
        }

        return ownedPerks;
    }

    public ArrayList<PlayerPerk> getAllActivePlayerPerksForPlayer(UUID playerID) {
        ArrayList<PlayerPerk> activePerks = new ArrayList<PlayerPerk>();

        for (PlayerPerk perk :
                getAllOwnedPlayerPerksForPlayer(playerID)) {
            if (perk.isActive())
                activePerks.add(perk);
        }

        return activePerks;
    }

    public boolean isPlayerPerkOwned(UUID uuid, Perk perk) {
        return getPlayerPerkForPlayer(uuid, perk).isOwned();
    }

    public boolean isPlayerPerkActive(UUID uuid, Perk perk) {
        return getPlayerPerkForPlayer(uuid, perk).isActive();
    }

    public ArrayList<PlayerPerk> getPlayerPerksForPlayer(UUID uuid) {
        for (PerkPlayer current : perkPlayers) {
            if (current.getPlayerUUID() == uuid)
                return current.getPerks();
        }
        return null;
    }

    public PlayerPerk getPlayerPerkForPlayer(UUID uuid, Perk perk) {
        for (PlayerPerk currentPerk :
                getPlayerPerksForPlayer(uuid)) {
            if (currentPerk.getPerk() == perk)
                return currentPerk;
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold desc="Getter and Setter">
    public ArrayList<PerkPlayer> getPerkPlayers() {
        return perkPlayers;
    }

    public String getGUI_NAME() {
        return GUI_NAME;
    }

    public ItemStack getPerkOwned() {
        return perkOwned;
    }

    public ItemStack getPerkUnOwned() {
        return perkUnOwned;
    }

    public ItemStack getPerkActivated() {
        return perkActivated;
    }
    //</editor-fold>


}
