package io.github.azorimor.azoperks.storage.file;

import io.github.azorimor.azoperks.AzoPerks;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class PluginFile {

    private static final char ALT_COLOR_CHAR = '&';

    private File file;
    private FileConfiguration cfg;
    private AzoPerks instance;

    public PluginFile(AzoPerks instance, String filename) {
        this.instance = instance;
        this.file = new File(instance.getDataFolder(),filename);
        this.cfg = YamlConfiguration.loadConfiguration(file);
        copyDefaults();
        saveFile();
    }

    private void copyDefaults(){
        cfg.options().copyDefaults(true);
        cfg.addDefaults(generateDefaultValues());
    }

    protected abstract HashMap<String, Object> generateDefaultValues();

    public String getColorTranslatedString(String path){
        if (cfg.isSet(path))
            return ChatColor.translateAlternateColorCodes(ALT_COLOR_CHAR,cfg.getString(path));
        return path;
    }

    public List<String> getColorTranslatedStringList(String path){
        List<String> list;
        if(cfg.isSet(path)){
            list = cfg.getStringList(path);
            for (String string :
                    list) {
                ChatColor.translateAlternateColorCodes(ALT_COLOR_CHAR,string);
            }
            return list;
        }
        list = new ArrayList<String>(1);
        list.add(path);
        return list;
    }

    public int getInt(String path){
        return cfg.getInt(path,-1);
    }

    public double getDouble(String path){
        return cfg.getDouble(path,-1.1);
    }

    public void saveFile(){
        try {
            cfg.save(file);
        } catch (IOException e) {
            instance.getLogger().warning("The file " + file.getAbsolutePath() + " could not been saved.");
        }
    }
}
