package io.github.azorimor.azoperks.perks;

import io.github.azorimor.azoperks.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Perk {

    NO_FALL_DAMAGE("azoperks.perk.nofalldamage",
            new ItemBuilder(Material.DIAMOND_BOOTS)
            .setDisplayName("§No Falldamage")
            .setLore("§7You will not recive","§7falldamage anymore.").addEnchantment(Enchantment.PROTECTION_FALL,2)
            .build());

    private String permissionString;
    private ItemStack guiItem;
    private String displayName;

    Perk(String permissionString, ItemStack guiItem) {
        this.permissionString = permissionString;
        this.guiItem = guiItem;
        this.displayName = guiItem.getItemMeta().getDisplayName();
    }

    public String getDisplayName() {
        return displayName;
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
