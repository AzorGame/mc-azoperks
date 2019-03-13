package io.github.azorimor.azoperks.perks;


public class PlayerPerk {

    private Perk perk;
    private boolean isActive;
    private boolean isOwned;
    private PlayerPerkStatus status;

    public PlayerPerk(Perk perk, boolean isActive, boolean isOwned) {
        this.perk = perk;
        this.isActive = isActive;
        this.isOwned = isOwned;
        if(isActive)
            status = PlayerPerkStatus.ACTIVE;
        else if(isOwned)
            status = PlayerPerkStatus.OWNED;
        else status = PlayerPerkStatus.NOT_OWNED;
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

    public PlayerPerkStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerPerkStatus status) {
        this.status = status;
        if(status == PlayerPerkStatus.ACTIVE)
            this.isActive = true;
        else if(status == PlayerPerkStatus.OWNED)
            this.isActive = false;
        else{
            this.isOwned = false;
        }
    }
}
