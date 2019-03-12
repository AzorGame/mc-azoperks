package io.github.azorimor.azoperks.perks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.UUID;

public class PerkPlayer {

    private ArrayList<PlayerPerk> perks;
    private Inventory perkGUI;
    private UUID playerUUID;


    public PerkPlayer(UUID playerUUID, Inventory perkGUI) { //TODO gui hier automatisch generieren
        this.perks = loadPlayerPerks();
        this.perkGUI = perkGUI;
        this.playerUUID = playerUUID;
    }

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

    public void changePerkOwnedStatus(Perk perk, boolean owned){
        getPlayerPerk(perk).setOwned(owned);
    }

    public void changePerkActiveStatus(Perk perk, boolean active){
        getPlayerPerk(perk).setOwned(active);
    }

    public PlayerPerk getPlayerPerk(Perk perk){
        for (PlayerPerk playerPerk :
                perks) {
            if(playerPerk.getPerk() == perk)
                return playerPerk;
        }
        return null;
    }

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
}
