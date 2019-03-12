package io.github.azorimor.azoperks.utils;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.storage.file.ConfigFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MessageHandler {

    private final ConfigFile config;

    private final String prefix;
    private final String noPlayer;

    private final String noCommandPermission;
    private final String wrongCommandUsage;
    private final String openPerksGUI;

    public MessageHandler(AzoPerks instance){
        this.config = instance.getConfigFile();

        this.prefix = config.getColorTranslatedString("prefix");
        this.noPlayer = config.getColorTranslatedString("command.message.noPlayer");
        this.noCommandPermission = config.getColorTranslatedString("command.message.noPermission");
        this.wrongCommandUsage = config.getColorTranslatedString("command.message.wrongUsage");
        this.openPerksGUI = config.getColorTranslatedString("command.perks.openPerksGUI");
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
}
