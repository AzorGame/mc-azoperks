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
                    .build(),1,2),

    NO_FIRE_DAMAGE("No Fire Damage",
            "azoperks.perk.nofiredamage",
            new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                    .setDisplayName("§eNo Firedamage")
                    .setLore("§3You will not recive", "§3firedamage anymore.")
                    .addEnchantment(Enchantment.PROTECTION_FIRE, 2)
                    .build(),10,11),

    NO_DROWNING_DAMAGE("No Drowning Damage",
                           "azoperks.perk.nodrowningdamage",
                           new ItemBuilder(Material.GOLDEN_HELMET)
                    .setDisplayName("§eNo Drowning")
                    .setLore("§3You will not recive", "§3damage from drowning anymore.")
                    .addEnchantment(Enchantment.WATER_WORKER, 2)
                    .build(),19,20),

    NO_HUNGER("No Hunger",
            "azoperks.perk.nohunger",
            new ItemBuilder(Material.COOKED_BEEF)
                    .setDisplayName("§eNo Hunger")
                    .setLore("§3You will not", "§3get hungry anymore.")
                    .build(),28,29),
    KEEP_INVENTORY("Keep Inventory",
                      "azoperks.perk.keepinventory",
                      new ItemBuilder(Material.CHEST)
                    .setDisplayName("§eKeep Inventory")
                    .setLore("§3You will not lose", "§3your inventory after.","§3you died.")
                    .build(),37,38),
    KEEP_XP("Keep XP",
            "azoperks.perk.keepxp",
            new ItemBuilder(Material.ENDER_CHEST)
                    .setDisplayName("§eKeep XP")
                    .setLore("§3You will not lose", "§3your xp after.","§3you died.")
                    .build(),46,47),
    DOUBLE_XP("Double XP",
            "azoperks.perk.doublexp",
            new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                    .setDisplayName("§eDouble XP")
                    .setLore("§3You will get", "§3double xp after.","§3gaining xp anywhere.")
                    .build(),7,6),
    FAST_RUN("Fast Run",
            "azoperks.perk.fastrun",
            new ItemBuilder(Material.SUGAR)
                    .setDisplayName("§eFast Run")
                    .setLore("§3You will run", "§3much faster.")
                    .build(),16,15),
    SUPER_JUMP("Super Jump",
            "azoperks.perk.superjump",
            new ItemBuilder(Material.GOLDEN_BOOTS)
                    .setDisplayName("§eSuper Jump")
                    .setLore("§3You will jump", "§3much higher.")
                    .build(),25,24),
    Double_DAMAGE("Double Damage",
            "azoperks.perk.doubledamage",
            new ItemBuilder(Material.DIAMOND_SWORD)
                    .setDisplayName("§eDouble Damage")
                    .setLore("§3You will make much more", "§3damage to enemies.")
                    .build(),34,33),
    NIGHT_VISION("Nightvision",
            "azoperks.perk.nightvision",
            new ItemBuilder(Material.ENDER_EYE)
                    .setDisplayName("§eNight Vision")
                    .setLore("§3You will be able to see", "§3things more clearly at night.")
                    .build(),43,42),
    FLY("Fly",
            "azoperks.perk.fly",
            new ItemBuilder(Material.FIREWORK_ROCKET)
                    .setDisplayName("§eFly")
                    .setLore("§3You will be able to fly", "§3around the server.")
                    .build(),52,51),
    FAST_MINING("Fast Mining",
                "azoperks.perk.fastmining",
                new ItemBuilder(Material.GOLDEN_PICKAXE)
                    .setDisplayName("§eFast Mining")
                    .setLore("§3You can break blocks", "§3faster than before.")
                    .build(),13,22);


    private String permissionString;
    private ItemStack guiItem;
    private String name;

    private int guiItemSlot;
    private int toggleGuiItemSlot;

    Perk(String name, String permissionString, ItemStack guiItem,int guiItemSlot, int toggleGuiItemSlot) {
        this.permissionString = permissionString;
        this.name = name;
        this.guiItem = guiItem;
        this.guiItemSlot = guiItemSlot;
        this.toggleGuiItemSlot = toggleGuiItemSlot;
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

    public int getGuiItemSlot() {
        return guiItemSlot;
    }

    public int getToggleGuiItemSlot() {
        return toggleGuiItemSlot;
    }

    public void setPermissionString(String permissionString) {
        this.permissionString = permissionString;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGuiItemSlot(int guiItemSlot) {
        this.guiItemSlot = guiItemSlot;
    }

    public void setToggleGuiItemSlot(int toggleGuiItemSlot) {
        this.toggleGuiItemSlot = toggleGuiItemSlot;
    }
}
