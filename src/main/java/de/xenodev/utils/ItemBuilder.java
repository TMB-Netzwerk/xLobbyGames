package de.xenodev.utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;

    public ItemBuilder(Material material, short subID){
        item = new ItemStack(material, 1, subID);
        meta = item.getItemMeta();
    }

    public ItemBuilder(Material material){
        this(material, (short) 0);
    }

    public ItemBuilder setName(String name){
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setAmount(Integer amount){
        item.setAmount(amount);

        return this;
    }

    public ItemBuilder setEnchantment(Enchantment enchantment, Integer level){
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder setColor(Color color){
        LeatherArmorMeta lmeta = (LeatherArmorMeta) meta;
        lmeta.setColor(color);
        return this;
    }

    public ItemBuilder setLore(String... lore){
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setUnbreakable(){
        meta.setUnbreakable(true);
        return this;
    }

    public ItemBuilder setHeadByName(String name) {
        SkullMeta smeta = (SkullMeta) meta;
        smeta.setOwningPlayer(Bukkit.getPlayerExact(name));
        return this;
    }

    public ItemBuilder setHeadByURL(String url){
        SkullMeta smeta = (SkullMeta) meta;
        PlayerProfile playerProfile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures playerTextures = playerProfile.getTextures();
        try {
            playerTextures.setSkin(new URL("http://textures.minecraft.net/texture/" + url));
        } catch (MalformedURLException e) { throw new RuntimeException(e); }
        playerProfile.setTextures(playerTextures);
        smeta.setOwnerProfile(playerProfile);
        return this;
    }

    public ItemBuilder setHeadByUUID(UUID uuid){
        SkullMeta smeta = (SkullMeta) meta;
        PlayerProfile playerProfile = Bukkit.createPlayerProfile(uuid);
        PlayerTextures playerTextures = playerProfile.getTextures();
        playerTextures.setSkin(playerProfile.getTextures().getSkin());
        playerProfile.setTextures(playerTextures);
        smeta.setOwnerProfile(playerProfile);
        return this;
    }

    public ItemBuilder setAuthor(String author){
        BookMeta bookMeta = (BookMeta) meta;
        bookMeta.setAuthor(author);
        return this;
    }

    public ItemBuilder setTitle(String title){
        BookMeta bookMeta = (BookMeta) meta;
        bookMeta.setTitle(title);
        return this;
    }

    public ItemBuilder setFlag(ItemFlag... flag){
        meta.addItemFlags(flag);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}
