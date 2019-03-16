package io.github.azorimor.azoperks.command;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.utils.MessageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link CAzoPerks} is the ingame command <code>/azoperks</code>. This Command displays information about the plugin,
 * for example the version, name, website, ... .
 */
public class CAzoPerks implements CommandExecutor, TabCompleter {

    private List<String> emptyList;
    private MessageHandler messageHandler;
    private String[] messages;

    public CAzoPerks(AzoPerks instance) {
        this.messageHandler = instance.getMessageHandler();
        this.emptyList = new ArrayList<String>(0);

        PluginDescriptionFile pdf = instance.getDescription();
        this.messages = new String[6];
        this.messages[0] = "§ePlugin: §f" + pdf.getName();
        this.messages[1] = "§eVersion: §f" + pdf.getVersion();
        this.messages[2] = "§eAuthor: §f" + pdf.getAuthors().get(0);
        this.messages[3] = "§eWebsite: §f" + pdf.getWebsite();
        try {
            if(instance.getUpdateChecker().checkForUpdate()){
                this.messages[4] = "§eUpdate: §2available";
                this.messages[5] = "§7"+instance.getUpdateChecker().getResourceUrl();
            } else {
                this.messages[4] = "§eUpdate: §cnone";
            }
        } catch (IOException e) {
            this.messages[4] = "§eUpdate: §fCould not check for updates.";
        }
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender.hasPermission("azoperks.command.azoperks")) {
            if (args.length == 0) {
                messageHandler.sendMessageBlock(commandSender, "PluginInfo", messages);
            } else {
                messageHandler.sendWrongCommandUsage(commandSender, command);
            }
        } else {
            messageHandler.sendNoCommandPermission(commandSender, command);
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        return emptyList;
    }

}
