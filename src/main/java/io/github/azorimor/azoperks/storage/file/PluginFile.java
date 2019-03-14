package io.github.azorimor.azoperks.storage.file;

import io.github.azorimor.azoperks.AzoPerks;
import io.github.azorimor.azoperks.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PluginFile {

    private static final char ALT_COLOR_CHAR = '&';

    private File file;
    FileConfiguration cfg;
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

    public Material getMaterial(String path){
        return Material.valueOf(cfg.getString(path, "AIR").toUpperCase());
    }

    public Map<Enchantment, Integer> getEnchantments(String path){
        Map<Enchantment,Integer> enchantments = new HashMap<Enchantment, Integer>();
        if(cfg.getConfigurationSection(path) == null)
            return enchantments;
        Map<String,Object> section = cfg.getConfigurationSection(path).getValues(false);
        for (String key :
                section.keySet()) {
            try {
                enchantments.put(Enchantment.getByKey(NamespacedKey.minecraft(key)),(Integer) section.get(key));
            } catch (Exception e) {
                instance.getLogger().warning("Enchantments could not be loaded. An invalid level value was asigned. " +
                        "Please use numbers there. File["+file.getAbsolutePath()+"] Path["+path+"]");
            }
        }
        return enchantments;
    }

    public ItemStack getItemStack(String path){ // perk.nofly.item.<here>
        return new ItemBuilder(getMaterial(path+".material"))
                .setDisplayName(getColorTranslatedString(path+".displayname"))
                .addEnchantments(getEnchantments(path+".enchantments"))
                .setLore(getColorTranslatedStringList(path+".lore"))
                .build();
    }

    public void saveFile(){
        try {
            cfg.save(file);
        } catch (IOException e) {
            instance.getLogger().warning("The file " + file.getAbsolutePath() + " could not been saved.");
        }
    }
}
