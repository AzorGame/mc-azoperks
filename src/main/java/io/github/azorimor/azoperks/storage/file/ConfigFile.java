package io.github.azorimor.azoperks.storage.file;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.perks.Perk;

import java.util.HashMap;

public class ConfigFile extends PluginFile {


    public ConfigFile(AzoPerks instance) {
        super(instance, "config.yml");
    }

    protected HashMap<String, Object> generateDefaultValues() {
        HashMap<String, Object> defaults = new HashMap<String, Object>();
        defaults.put("prefix","&7[&6AzoPerks&7]&r ");
        defaults.put("command.message.noPlayer","&cYou need to be a player to perform this action.");
        defaults.put("command.message.noPermission","&7You have &cno permission&7 to perform the command &c/%command%&7.");
        defaults.put("command.message.wrongUsage","&7You used the command &c/%command% &7the wrong way. Just try &c/%usage%&7.");
        defaults.put("command.perks.openPerksGUI","&7You &asuccessfully &7opened the perks GUI.");
        defaults.put("command.perk.noPerk","&7Plese enter a valid perk instead of &c%noperk%&7.");

        defaults.put("perk.changestatus.success","&7The perk &a%perkname%&7 is now &a%status%&7.");
        defaults.put("perk.changestatus.failure","&7The status of the perk &c%perkname%&7 could not be changed. It is still &c%status%&7.");

        return defaults;
    }

    public void updatePerks(){
        String path = "perk.";
        for (Perk perk :
                Perk.values()) {
            if(cfg.isSet(path+perk.toString())){
                String perkPath = path + perk.toString();
                if(cfg.isSet(perkPath+".name")){
                    perk.setName(getColorTranslatedString(perkPath+".name"));
                }
                if(cfg.isSet(perkPath+".permission")){
                    perk.setPermissionString(cfg.getString(perkPath+".permission"));
                }
                if(cfg.isSet(perkPath+".guiItemSlot")){
                    perk.setGuiItemSlot(getInt(perkPath+".guiItemSlot"));
                }
                if(cfg.isSet(perkPath+".toggleGuiItemSlot")){
                    perk.setToggleGuiItemSlot(getInt(perkPath+".toggleGuiItemSlot"));
                }
                if(cfg.isSet(perkPath+".item")){
                    perk.setGuiItem(getItemStack(perkPath+".item"));
                }
            }
        }
    }
}
