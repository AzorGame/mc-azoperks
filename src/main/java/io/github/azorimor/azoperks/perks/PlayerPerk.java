package io.github.azorimor.azoperks.perks;


public class PlayerPerk {

    private Perk perk;
    private boolean isActive;
    private boolean isOwned;

    public PlayerPerk(Perk perk, boolean isActive, boolean isOwned) {
        this.perk = perk;
        this.isActive = isActive;
        this.isOwned = isOwned;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isOwned() {
        return isOwned;
    }

    public void setOwned(boolean owned) {
        isOwned = owned;
    }

    public Perk getPerk() {
        return perk;
    }
}
