package io.github.azorimor.azoperks.command;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.Perk;
import io.github.azorimor.azoperks.perks.PerksManager;
import io.github.azorimor.azoperks.perks.PlayerPerk;
import io.github.azorimor.azoperks.utils.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class CPerk implements CommandExecutor, TabCompleter {

    private MessageHandler messageHandler;
    private PerksManager perksManager;

    public CPerk(AzoPerks instance) {
        this.messageHandler = instance.getMessageHandler();
        this.perksManager = instance.getPerksManager();
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            if (commandSender.hasPermission("azoperks.command.perk")) {
                if (args.length == 1) {
                    Player player = (Player) commandSender;
                    try {
                        Perk perk = Perk.valueOf(args[0].toUpperCase());
                        PlayerPerk requestedPerk = perksManager.getPlayerPerkForPlayer(player.getUniqueId(), perk);
                        if (perksManager.updatePerkGUIItem(requestedPerk,perk.getToggleGuiItemSlot(),player.getUniqueId())) { //OLD: perksManager.togglePerkStatus(player.getUniqueId(), perk)
                            messageHandler.sendPerkChangeStatusSuccess(commandSender, requestedPerk);
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                            perksManager.updatePotionEffects(player, requestedPerk);
                            //perksManager.updatePerkGUIItem(requestedPerk,perk.getToggleGuiItemSlot(),player.getUniqueId()); //TODO funktioniert nicht
                        } else {
                            messageHandler.sendPerkChangeStatusFailure(commandSender, requestedPerk);
                        }
                    } catch (IllegalArgumentException e) {
                        messageHandler.sendNoPerk(commandSender, args[0]);
                    }

                } else {
                    messageHandler.sendWrongCommandUsage(commandSender, command);
                }
            } else {
                messageHandler.sendNoCommandPermission(commandSender, command);
            }
        } else {
            messageHandler.sendNoPlayer(commandSender);
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> completions = new ArrayList<String>();

        switch (args.length) {
            case 1:
                List<String> perkNames = new ArrayList<String>(Bukkit.getOnlinePlayers().size());
                for (Perk perk :
                        Perk.values()) {
                    perkNames.add(perk.toString().toLowerCase());
                }
                StringUtil.copyPartialMatches(args[0], perkNames, completions);
                break;
        }
        return completions;
    }
}
