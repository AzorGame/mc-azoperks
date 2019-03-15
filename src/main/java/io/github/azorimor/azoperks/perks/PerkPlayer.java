package io.github.azorimor.azoperks.perks;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This class stores the player specific perk informations.
 */
public class PerkPlayer {

    private ArrayList<PlayerPerk> perks;
    private Inventory perkGUI;
    private UUID playerUUID;


    public PerkPlayer(UUID playerUUID, Inventory perkGUI) { //TODO gui hier automatisch generieren
        this.playerUUID = playerUUID;
        this.perks = loadPlayerPerks();
        this.perkGUI = perkGUI;
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
    public boolean hasPlayerPerkActive(Perk perk){
        return getPlayerPerk(perk).isActive();
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
