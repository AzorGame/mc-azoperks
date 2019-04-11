package io.github.azorimor.azoperks.perks;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class stores the player specific perk informations.
 */
public class PerkPlayer {

    private ArrayList<PlayerPerk> perks;
    private Inventory perkGUI;
    private UUID playerUUID;
    private Player player;

    private ItemStack perkActivated, perkOwned, perkUnOwned;


    public PerkPlayer(UUID playerUUID, Inventory perkGUI, ItemStack perkActivated, ItemStack perkOwned, ItemStack perkUnOwned) { //TODO gui hier automatisch generieren
        this.playerUUID = playerUUID;
        this.perks = loadPlayerPerks();
        this.perkGUI = perkGUI;
        this.player = Bukkit.getPlayer(playerUUID);

        this.perkActivated = perkActivated;
        this.perkOwned = perkOwned;
        this.perkUnOwned = perkUnOwned;
        this.perkGUI = generatePerkInventoryForPlayer(PerksManager.GUI_NAME);
    }

    /**
     * Loads all {@link PlayerPerk} information about the player.
     * @return {@link ArrayList} with all {@link PlayerPerk} information loaded.
     */
    private ArrayList<PlayerPerk> loadPlayerPerks(){
        ArrayList<PlayerPerk> playerPerks = new ArrayList<PlayerPerk>(Perk.values().length);
        Player player = Bukkit.getPlayer(playerUUID); //TODO Offlineplayer behandeln (UUIDFethcer verwenden)
        for (Perk perk :
                Perk.values()) {
            if(player.hasPermission(perk.getPermissionString())){
                playerPerks.add(new PlayerPerk(perk,false,true));
            } else {
                playerPerks.add(new PlayerPerk(perk,false,false));
            }
        }
        return playerPerks;
    }

    /**
     * Looking for the equivalent {@link PlayerPerk} with the specified {@link Perk}.
     * @param perk {@link Perk} for which the corosponding {@link PlayerPerk} is searched.
     * @return {@link PlayerPerk} for the {@link Perk} of the {@link PerkPlayer}
     */
    public PlayerPerk getPlayerPerk(Perk perk){
        for (PlayerPerk playerPerk :
                perks) {
            if(playerPerk.getPerk() == perk)
                return playerPerk;
        }
        return null;
    }

    /**
     * Checks whether a specifig {@link PlayerPerk} is active or not.
     * @param perk {@link Perk}, for which the {@link PlayerPerk} is searched.
     * @return <code>true</code>, if the {@link PlayerPerk} is active.
     */
    @Deprecated
    //Old Method used before implementing plotsquared support
    public boolean hasPlayerPerkActive(Perk perk){
        return getPlayerPerk(perk).isActive();
    }

    public boolean isPlayerPerkActive(Perk perk){
        return getPlayerPerk(perk).isActive() && perk.getPerkAreaManager().isPerkUsedInAllowedArea(player);
    }

    public List<PlayerPerk> updatePerkAllowedActive(){
        List<PlayerPerk> disabledPerks = new ArrayList<PlayerPerk>(5);

        for (PlayerPerk playerPerk : perks) {
            if(!playerPerk.getPerk().getPerkAreaManager().isPerkUsedInAllowedArea(player)){
                disabledPerks.add(playerPerk);
                playerPerk.togglePerkStatus();
            }
            System.out.println(playerPerk);
            System.out.println("Allowed Perk Usage: "+ playerPerk.getPerk().getPerkAreaManager().isPerkUsedInAllowedArea(player));
        }
        this.perkGUI = generatePerkInventoryForPlayer(PerksManager.GUI_NAME);
        return disabledPerks;
    }


    public boolean updateGUIItem(PlayerPerk playerPerk, int slot){

        switch (playerPerk.getStatus()) {
            case NOT_ACTIVE:
                perkGUI.setItem(slot, perkActivated);
                playerPerk.setStatus(PlayerPerkStatus.ACTIVE);
                return true;
            case ACTIVE:
                if (playerPerk.isOwned()) {
                    perkGUI.setItem(slot, perkOwned);
                    playerPerk.setStatus(PlayerPerkStatus.NOT_ACTIVE);
                } else {
                    perkGUI.setItem(slot,perkUnOwned);
                    playerPerk.setStatus(PlayerPerkStatus.NOT_OWNED);
                }
                return true;
        }
        return false;
    }


    public Inventory generatePerkInventoryForPlayer(String guiName) {
        Inventory gui = Bukkit.createInventory(null, 54, guiName);

        for (Perk perk :
                Perk.values()) {
            gui.setItem(perk.getGuiItemSlot(),perk.getGuiItem());
            PlayerPerk playerPerk = getPlayerPerk(perk);
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

    //<editor-fold desc="Getter and Setter">
    public ArrayList<PlayerPerk> getPerks() {
        return perks;
    }

    public Inventory getPerkGUI() {
        return perkGUI;
    }

    public void setPerkGUI(Inventory perkGUI) {
        this.perkGUI = perkGUI;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }
    //</editor-fold>
}
