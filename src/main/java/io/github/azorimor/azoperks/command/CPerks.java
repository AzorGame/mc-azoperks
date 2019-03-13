package io.github.azorimor.azoperks.command;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.PerksManager;
import io.github.azorimor.azoperks.utils.MessageHandler;
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
    private MessageHandler messageHandler;

    public CPerks(AzoPerks instance) {
        this.perksManager = instance.getPerksManager();
        this.messageHandler = instance.getMessageHandler();
        this.emptyList = new ArrayList<String>(0);
    }


    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            if (commandSender.hasPermission("azoperks.command.perms")) {
                if(args.length == 0){
                    Player player = (Player) commandSender;
                    player.openInventory(perksManager.getPerkPlayerByID(player.getUniqueId()).getPerkGUI());
                    messageHandler.sendCommandOpenPerksGUI(commandSender);
                } else {
                    messageHandler.sendWrongCommandUsage(commandSender,command);
                }

            } else {
                messageHandler.sendNoCommandPermission(commandSender,command);
            }

        } else {
            messageHandler.sendNoPlayer(commandSender);
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return emptyList;
    }
}
