package io.github.azorimor.azoperks.command;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.PerksManager;
import io.github.azorimor.azoperks.perks.PlayerPerk;
import io.github.azorimor.azoperks.utils.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                for (Player player :
                        Bukkit.getOnlinePlayers()) {
                    completions.add(player.getName());
                }
                break;
        }
        return completions;
    }

    private void sendPlayerPerksInfo(CommandSender sender, UUID uuid, String name) {
        messageHandler.sendPluginMessage(sender, "§7§m       §r§7[§6AzoPerks - PlayerInfo§7]§m     ");
        messageHandler.sendPluginMessage(sender, "§7Perk-information about §c" + name);
        messageHandler.sendPluginMessage(sender, "§7Name          | Owned | Active");
        for (PlayerPerk perk :
                perksManager.getPlayerPerksForPlayer(uuid)) {
            String owned = (perk.isOwned()) ? "§a✔" : "§c✖";
            String active = (perk.isActive()) ? "§a✔" : "§c✖";
            messageHandler.sendPluginMessage(sender, perk.getPerk().getDisplayName() + " §7| " + owned + " §7| " + active);
        }
        messageHandler.sendPluginMessage(sender, "§7§m       §r§7[§6AzoPerks - PlayerInfo§7]§m     ");
    }
}