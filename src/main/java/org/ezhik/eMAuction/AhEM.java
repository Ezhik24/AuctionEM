package org.ezhik.eMAuction;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AhEM {
    public static int lotnumber = 1;
    public static int page = 0;
    public static String auctionTitle = ChatColor.translateAlternateColorCodes('&', "&c&lСтраница Аукциона.");
    public static String BuyTitle = ChatColor.translateAlternateColorCodes('&', "&c&lВы уверены, что хотите купить этот предмет?");
    public static List<Map> lots = new ArrayList();

    public static void sell(Player player, int price) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        File file = new File("plugins/EMAuctions/config.yml");
        List lots = new ArrayList();
        Map lot = new HashMap();
        if (file.exists()) {
                try {
                    yamlConfiguration.load(file);
                    lots = yamlConfiguration.getList("lots");
                } catch (IOException e) {
                    System.out.println(e);
                } catch (InvalidConfigurationException e) {
                    System.out.println(e);
                }
            }
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            lot.put("playerUUID", player.getUniqueId().toString());
            lot.put("player", player.getName());
            lot.put("price", price);
            lot.put("item", itemStack);
            lot.put("lotnumber", lotnumber);
            lots.add(lot);
            yamlConfiguration.set("lots", lots);
            try {
                yamlConfiguration.save(file);
            } catch (IOException e) {
                System.out.println(e);
            }
            lotnumber++;
        }
    public static void openauction(Player player) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        File file = new File("plugins/EMAuctions/config.yml");
        Inventory menu = Bukkit.createInventory(null, 54, auctionTitle);
        try {
            yamlConfiguration.load(file);
        } catch (IOException e) {
            System.out.println(e);
        } catch (InvalidConfigurationException e) {
            System.out.println(e);
        }
        lots = (List<Map>) yamlConfiguration.getList("lots");
        for (int i = 0; i < 45; i++) {
            if (lots.size() <= i + page * 45) break;
            Map lot = lots.get(i + page * 45);
            ItemStack item = (ItemStack) lot.get("item");
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList();
            lore.add("");
            lore.add(ChatColor.translateAlternateColorCodes('&', "&e&l<-- &a&lНажми, что бы купить."));
            lore.add("");
            lore.add(ChatColor.translateAlternateColorCodes('&', "&a&lЛот &f&l№ " + lot.get("lotnumber")));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&a&lЦена: &f&l" + lot.get("price")));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&a&lПродавец: &f&l" + lot.get("player")));
            meta.setLore(lore);
            item.setItemMeta(meta);
            menu.addItem(item);
            ItemStack previouspage = new ItemStack(Material.SPECTRAL_ARROW);
            ItemMeta previospageMeta = previouspage.getItemMeta();
            previospageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[▶] Следующая страница"));
            previouspage.setItemMeta(previospageMeta);
            if (page * 45 + 45 < lots.size()) menu.setItem(50, previouspage);
            ItemStack nextpage = new ItemStack(Material.SPECTRAL_ARROW);
            ItemMeta nextpageMeta = nextpage.getItemMeta();
            nextpageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[◀] Предыдущая страница"));
            nextpage.setItemMeta(nextpageMeta);
            if (page != 0) menu.setItem(48, nextpage);
            ItemStack storage = new ItemStack(Material.ENDER_CHEST);
            ItemMeta storageMeta = storage.getItemMeta();
            storageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[📦] Хранилище"));
            storage.setItemMeta(storageMeta);
            menu.setItem(46, storage);
            ItemStack updateauction = new ItemStack(Material.NETHER_STAR);
            ItemMeta updateauctionMeta = updateauction.getItemMeta();
            updateauctionMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[🔃] Обновить аукцион"));
            updateauction.setItemMeta(updateauctionMeta);
            menu.setItem(49, updateauction);
        }
        player.openInventory(menu);
    }

    public static void buy(Player player) {
        Inventory menu = Bukkit.createInventory(null, 27, BuyTitle);
        ItemStack accept1 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta accept1Meta = accept1.getItemMeta();
        accept1Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[✔] Купить"));
        accept1.setItemMeta(accept1Meta);
        menu.setItem(0, accept1);
        ItemStack cancel1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancel1Meta = cancel1.getItemMeta();
        cancel1Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l[❌] Отмена"));
        cancel1.setItemMeta(cancel1Meta);
        menu.setItem(8, cancel1);
        player.openInventory(menu);

    }
}

