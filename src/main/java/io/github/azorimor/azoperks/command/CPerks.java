package io.github.azorimor.azoperks.command;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.PerksManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CPerks implements CommandExecutor, TabCompleter {

    private List<String> emptyList;
    private PerksManager perksManager;

    public CPerks(AzoPerks instance) {
        this.perksManager = instance.getPerksManager();
        this.emptyList = new ArrayList<String>(0);
    }


    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {

            if (commandSender.hasPermission("azoperms.command.perms")) {

                if(args.length == 0){
                    Player player = (Player) commandSender;
                    player.openInventory(perksManager.getPerkPlayerByID(player.getUniqueId()).getPerkGUI());
                } else {
                    //TODO Message wrongUsage
                }

            } else {
                //TODO Message noPermission
            }

        } else {
            //TODO Message noPlayer
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return emptyList;
    }
}
