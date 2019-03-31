package io.github.azorimor.azoperks.perks;

import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PerkAreaManager {

    private PerkArea activeArea;
    private List<String> perkActiveWorlds;

    /**
     * Creates a new {@link PerkAreaManager} for a {@link Perk}
     * @param activeArea {@link PerkArea} the area, where the {@link Perk} is active.
     * @param perkActiveWorlds A {@link List} of names of {@link World}, in which the {@link Perk} is active.
     */
    public PerkAreaManager(PerkArea activeArea, List<String> perkActiveWorlds) {
        this.activeArea = activeArea;
        this.perkActiveWorlds = (perkActiveWorlds == null) ? new ArrayList<String>(4) : perkActiveWorlds;
    }

    /**
     * Creates a new {@link PerkAreaManager} for a {@link Perk}
     */
    public PerkAreaManager() {
        this.activeArea = PerkArea.GLOBAL;
        this.perkActiveWorlds = null;
    }

    /**
     * Checks, whether the perk is active in this area.
     * @param player {@link Player}, for which the perk area allowed use is checked.
     * @return <code>true</code>, if the {@link Player} is in an area, where the perk is active.
     */
    public boolean isPerkUsedInAllowedArea(Player player) {

        switch (activeArea) {
            case GLOBAL:
                return true;
            case PLOT:
                return isPlotUseAllowed(player);
            case WORLDS:
                return isWorldUseAllowed(player);
            case PLOT_OR_WORLDS:
                return isPlotUseAllowed(player) || isWorldUseAllowed(player);
            default:
                return false;
        }
    }

    /**
     * Checks, whether the {@link Perk} is active in the current {@link com.github.intellectualsites.plotsquared.plot.object.Plot}.
     * @param player {@link Player}, who is in the area, for which a check is performed.
     * @return <code>true</code>, if the {@link Player} is standing on a {@link com.github.intellectualsites.plotsquared.plot.object.Plot}, on which he has rights to modify it.
     */
    private boolean isPlotUseAllowed(Player player) {
        PlotPlayer plotPlayer = PlotPlayer.wrap(player);
        return plotPlayer.getCurrentPlot().isAdded(plotPlayer.getUUID());
    }

    /**
     * Checks, whether the {@link Perk} is active in the current {@link World}.
     * @param player {@link Player}, who is in the area, for which a check is performed.
     * @return <code>true</code>, if the {@link Player} is standing on a {@link World}, on which he has rights to modify it.
     */
    private boolean isWorldUseAllowed(Player player) {
        String perkUsedWorld = player.getWorld().getName();
        for (String world :
                perkActiveWorlds) {
            if (world.equalsIgnoreCase(perkUsedWorld))
                return true;

        }
        return false;
    }

    public void setActiveArea(PerkArea activeArea) {
        this.activeArea = activeArea;
    }

    public void setPerkActiveWorlds(List<String> perkActiveWorlds) {
        this.perkActiveWorlds = (perkActiveWorlds == null) ? new ArrayList<String>(4) : perkActiveWorlds;
    }
}
