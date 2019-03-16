package io.github.azorimor.azoperks.command;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.PerksManager;
import io.github.azorimor.azoperks.perks.PlayerPerk;
import io.github.azorimor.azoperks.utils.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class is used for the ingame command <code>/perksinfo player</code>.
 * It should show perks information for a specific player, for example whether a perk is active or not.
 */
public class CPerksInfo implements CommandExecutor, TabCompleter {

    private MessageHandler messageHandler;
    private PerksManager perksManager;

    public CPerksInfo(AzoPerks instance){
        this.messageHandler = instance.getMessageHandler();
        this.perksManager = instance.getPerksManager();
    }
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender.hasPermission("azoperks.command.perksinfo")) {

            if (args.length == 1) {

                Player target = Bukkit.getPlayer(args[0]);
                UUID uuid;
                if (target == null)
                    messageHandler.sendPluginMessage(commandSender, "§7The player §c" + args[0] + " §7is offline."); //uuid = UUIDFetcher.getUUID(args[0]); TODO Message editable or later reciving data from database.
                else {
                    uuid = target.getUniqueId();
                    sendPlayerPerksInfo(commandSender, uuid, target.getDisplayName());
                }

            } else {
                messageHandler.sendWrongCommandUsage(commandSender, command);
            }

        } else {
            messageHandler.sendNoCommandPermission(commandSender, command);
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        List<String> completions = new ArrayList<String>(Bukkit.getOnlinePlayers().size());

        switch (args.length) {
            case 1:
                List<String> playernames = new ArrayList<String>(Bukkit.getOnlinePlayers().size());
                for (Player online :
                        Bukkit.getOnlinePlayers()) {
                    playernames.add(online.getName());
                }
                StringUtil.copyPartialMatches(args[0],playernames,completions);
                break;
        }
        return completions;
    }

    private void sendPlayerPerksInfo(CommandSender sender, UUID uuid, String name) {
        messageHandler.sendMessageBlockHeaderFooter(sender,"PlayerInfo");
        messageHandler.sendPluginMessage(sender, "§ePerk-information about §c" + name);
        messageHandler.sendPluginMessage(sender, "§7§lName                 | Owned | Active");

        String ownedSymbolYES,ownedSymbolNO, activeSymbolYES, activeSymbolNO;
        if(sender instanceof Player){
            ownedSymbolYES = "§a✔";
            ownedSymbolNO = "§c✖";

            activeSymbolYES = "§a✔";
            activeSymbolNO = "§c✖";
        } else {
            ownedSymbolYES = "§aYES";
            ownedSymbolNO = "§cNO";

            activeSymbolYES = "§aYES";
            activeSymbolNO = "§cNO";
        }
        for (PlayerPerk perk :
                perksManager.getPlayerPerksForPlayer(uuid)) {
            String owned = (perk.isOwned()) ? ownedSymbolYES : ownedSymbolNO;
            String active = (perk.isActive()) ? activeSymbolYES : activeSymbolNO;
            messageHandler.sendPluginMessage(sender, ChatColor.AQUA + String.format("%-20s",perk.getPerk().getName()) + " §7| " + owned + " §7| " + active);
        }
        messageHandler.sendMessageBlockHeaderFooter(sender,"PlayerInfo");
    }
}
