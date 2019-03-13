package io.github.azorimor.azoperks.perks;

import io.github.azorimor.azoperks.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Perk {

    NO_FALL_DAMAGE("No Fall Damage",
            "azoperks.perk.nofalldamage",
            new ItemBuilder(Material.DIAMOND_BOOTS)
                    .setDisplayName("§eNo Falldamage")
                    .setLore("§3You will not recive", "§3falldamage anymore.")
                    .addEnchantment(Enchantment.PROTECTION_FALL, 2)
                    .build()),

    NO_FIRE_DAMAGE("No Fire Damage",
            "azoperks.perk.nofiredamage",
            new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                    .setDisplayName("§eNo Firedamage")
                    .setLore("§3You will not recive", "§3firedamage anymore.")
                    .addEnchantment(Enchantment.PROTECTION_FIRE, 2)
                    .build()),

    NO_DROWNING_DAMAGE("No Drowning Damage",
                           "azoperks.perk.nodrowningdamage",
                           new ItemBuilder(Material.GOLDEN_HELMET)
                    .setDisplayName("§eNo Drowning")
                    .setLore("§3You will not recive", "§3damage from drowning anymore.")
                    .addEnchantment(Enchantment.WATER_WORKER, 2)
                    .build()),

    NO_HUNGER("No Hunger",
            "azoperks.perk.nohunger",
            new ItemBuilder(Material.COOKED_BEEF)
                    .setDisplayName("§eNo Hunger")
                    .setLore("§3You will not", "§3get hungry anymore.")
                    .build()),
    KEEP_INVENTORY("Keep Inventory",
                      "azoperks.perk.keepinventory",
                      new ItemBuilder(Material.CHEST)
                    .setDisplayName("§eKeep Inventory")
                    .setLore("§3You will not lose", "§3your inventory after.","§3you died.")
                    .build()),
    KEEP_XP("Keep Inventory",
            "azoperks.perk.keepxp",
            new ItemBuilder(Material.ENDER_CHEST)
                    .setDisplayName("§eKeep XP")
                    .setLore("§3You will not lose", "§3your xp after.","§3you died.")
                    .build()),
    DOUBLE_XP("Double XP",
            "azoperks.perk.doublexp",
            new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                    .setDisplayName("§eDouble XP")
                    .setLore("§3You will get", "§3double xp after.","§3gaining xp anywhere.")
                    .build()),
    FAST_RUN("Fast Run",
            "azoperks.perk.fastrun",
            new ItemBuilder(Material.SUGAR)
                    .setDisplayName("§eFast Run")
                    .setLore("§3You will run", "§3much faster.")
                    .build()),
    SUPER_JUMP("Super Jump",
            "azoperks.perk.superjump",
            new ItemBuilder(Material.GOLDEN_BOOTS)
                    .setDisplayName("§eSuper Jump")
                    .setLore("§3You will jump", "§3much higher.")
                    .build()),
    Double_DAMAGE("Double Damage",
            "azoperks.perk.doubledamage",
            new ItemBuilder(Material.DIAMOND_SWORD)
                    .setDisplayName("§eDouble Damage")
                    .setLore("§3You will make much more", "§3damage to enemies.")
                    .build());


    private String permissionString;
    private ItemStack guiItem;
    private String name;

    Perk(String name, String permissionString, ItemStack guiItem) {
        this.permissionString = permissionString;
        this.name = name;
        this.guiItem = guiItem;
    }

    public String getName() {
        return name;
    }

    public String getPermissionString() {
        return permissionString;
    }

    public ItemStack getGuiItem() {
        return guiItem;
    }

    public void setGuiItem(ItemStack guiItem) {
        this.guiItem = guiItem;
    }
}
