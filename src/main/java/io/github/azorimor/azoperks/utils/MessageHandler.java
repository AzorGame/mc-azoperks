package io.github.azorimor.azoperks.utils;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.PlayerPerk;
import io.github.azorimor.azoperks.storage.file.ConfigFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MessageHandler {

    private final String prefix;
    private final String noPlayer;

    private final String noCommandPermission;
    private final String wrongCommandUsage;
    private final String openPerksGUI;
    private final String noPerk;

    private final String changePerkStatusSuccess;
    private final String changePerkStatusFailure;

    public MessageHandler(AzoPerks instance){
        final ConfigFile config = instance.getConfigFile();;

        this.prefix = config.getColorTranslatedString("prefix");
        this.noPlayer = config.getColorTranslatedString("command.message.noPlayer");
        this.noCommandPermission = config.getColorTranslatedString("command.message.noPermission");
        this.wrongCommandUsage = config.getColorTranslatedString("command.message.wrongUsage");
        this.openPerksGUI = config.getColorTranslatedString("command.perks.openPerksGUI");
        this.noPerk = config.getColorTranslatedString("command.perk.noPerk");

        this.changePerkStatusSuccess = config.getColorTranslatedString("perk.changestatus.success");
        this.changePerkStatusFailure = config.getColorTranslatedString("perk.changestatus.failure");
    }

    public void sendPluginMessage(CommandSender sender, String message){
        sender.sendMessage(prefix+message);
    }

    public void sendNoPlayer(CommandSender sender){
        sender.sendMessage(prefix+ noPlayer);
    }

    public void sendNoCommandPermission(CommandSender sender, Command command){
        sender.sendMessage(prefix+ noCommandPermission.replace("%command%",command.getName()));
    }

    public void sendWrongCommandUsage(CommandSender sender, Command command){
        sender.sendMessage(prefix+wrongCommandUsage
                .replace("%command%",command.getName())
                .replace("%usage%",command.getUsage()));
    }

    public void sendCommandOpenPerksGUI(CommandSender sender){
        sender.sendMessage(prefix+openPerksGUI);
    }

    public void sendPerkChangeStatusSuccess(CommandSender sender, PlayerPerk playerPerk){
        sender.sendMessage(prefix+changePerkStatusSuccess.replace("%perkname%",playerPerk.getPerk().getName()).replace("%status%",playerPerk.getStatus().toString()));
    }
    public void sendPerkChangeStatusFailure(CommandSender sender, PlayerPerk playerPerk){
        sender.sendMessage(prefix+changePerkStatusFailure.replace("%perkname%",playerPerk.getPerk().getName()).replace("%status%",playerPerk.getStatus().toString()));
    }

    public void sendMessageBlockHeaderFooter(CommandSender sender, String blockName){
        sender.sendMessage(prefix + "§7§m          §r§7[§6AzoPerks - §f"+blockName+"§7]§m          ");
    }

    public void sendMessageBlock(CommandSender sender, String blockName, String... msg){
        sendMessageBlockHeaderFooter(sender,blockName);
        for (int i = 0; i < msg.length; i++) {
            sender.sendMessage(prefix + msg[i]);
        }
        sendMessageBlockHeaderFooter(sender,blockName);
    }

    public void sendNoPerk(CommandSender sender, String noPerkArgument){
        sender.sendMessage(prefix + noPerk.replace("%noperk%",noPerkArgument));
    }
}
