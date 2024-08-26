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
    public static String auctionTitle = ChatColor.translateAlternateColorCodes('&', "&c&lСтраница Аукциона.");
    public static String BuyTitle = ChatColor.translateAlternateColorCodes('&', "&c&lВы уверены, что хотите купить этот предмет?");
    public static List<Map> lots = new ArrayList();
    private static Inventory auctionmenu = Bukkit.createInventory(null, 54, auctionTitle);

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
        ItemStack updateauction = new ItemStack(Material.NETHER_STAR);
        ItemMeta updateauctionMeta = updateauction.getItemMeta();
        updateauctionMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[🔃] Обновить аукцион"));
        Integer pages = ((lots.size() / 45) + 1);
        Integer page = getPage() + 1;
        updateauctionMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&7&lСтраница "), page.toString(), " из ", pages.toString()));
        updateauction.setItemMeta(updateauctionMeta);
        auctionmenu.setItem(49, updateauction);
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        File file = new File("plugins/EMAuctions/config.yml");
        try {
            yamlConfiguration.load(file);
        } catch (IOException e) {
            System.out.println(e);
        } catch (InvalidConfigurationException e) {
            System.out.println(e);
        }
        lots = (List<Map>) yamlConfiguration.getList("lots");
        Map lot = new HashMap();
        ItemStack item = null;
        for (int i = 0; i < 45; i++) {
            if (lots.size() <= i + getPage() * 45) item = null;
            else {
                lot = lots.get(i + getPage() * 45);
                item = ((ItemStack) lot.get("item")).clone();
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList();
                if (meta.hasLore()) {
                    lore = meta.getLore();
                }
                lore.add(ChatColor.translateAlternateColorCodes('&', "&a&l==============================="));
                lore.add(ChatColor.translateAlternateColorCodes('&', "&e&l<-- &a&lНажми, что бы купить."));
                lore.add("");
                lore.add(ChatColor.translateAlternateColorCodes('&', "&a&lЛот &f&l№ " + lot.get("lotnumber")));
                lore.add(ChatColor.translateAlternateColorCodes('&', "&a&lЦена: &f&l" + lot.get("price")));
                lore.add(ChatColor.translateAlternateColorCodes('&', "&a&lПродавец: &f&l" + lot.get("player")));
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
                auctionmenu.addItem(item);
                ItemStack previouspage = new ItemStack(Material.SPECTRAL_ARROW);
                ItemMeta previospageMeta = previouspage.getItemMeta();
                previospageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[▶] Следующая страница"));
                previouspage.setItemMeta(previospageMeta);
                if (getPage() * 45 + 45 < lots.size()) auctionmenu.setItem(50, previouspage);
                ItemStack nextpage = new ItemStack(Material.SPECTRAL_ARROW);
                ItemMeta nextpageMeta = nextpage.getItemMeta();
                nextpageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[◀] Предыдущая страница"));
                nextpage.setItemMeta(nextpageMeta);
                if (getPage() != 0) auctionmenu.setItem(48, nextpage);
                ItemStack storage = new ItemStack(Material.ENDER_CHEST);
                ItemMeta storageMeta = storage.getItemMeta();
                storageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[📦] Хранилище"));
                storage.setItemMeta(storageMeta);
                auctionmenu.setItem(46, storage);

        }
        player.openInventory(auctionmenu);
    }

    public static void buy(Player player, ItemStack sellingitem) {
        Inventory menu = Bukkit.createInventory(null, 27, BuyTitle);
        menu.setItem(13, sellingitem);
        ItemStack accept1 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta accept1Meta = accept1.getItemMeta();
        accept1Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[✔] Купить"));
        accept1.setItemMeta(accept1Meta);
        menu.setItem(0, accept1);
        ItemStack accept2 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta accept2Meta = accept2.getItemMeta();
        accept2Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[✔] Купить"));
        accept2.setItemMeta(accept2Meta);
        menu.setItem(1, accept2);
        ItemStack accept3 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta accept3Meta = accept3.getItemMeta();
        accept3Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[✔] Купить"));
        accept3.setItemMeta(accept3Meta);
        menu.setItem(2, accept3);
        ItemStack obvodkaitem1 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta obvodkaitem1Meta = obvodkaitem1.getItemMeta();
        obvodkaitem1Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&l[?]"));
        obvodkaitem1.setItemMeta(obvodkaitem1Meta);
        menu.setItem(3, obvodkaitem1);
        ItemStack obvodkaitem2 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta obvodkaitem2Meta = obvodkaitem2.getItemMeta();
        obvodkaitem2Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&l[?]"));
        obvodkaitem2.setItemMeta(obvodkaitem2Meta);
        menu.setItem(4, obvodkaitem2);
        ItemStack obvodkaitem3 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta obvodkaitem3Meta = obvodkaitem3.getItemMeta();
        obvodkaitem3Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&l[?]"));
        obvodkaitem3.setItemMeta(obvodkaitem3Meta);
        menu.setItem(5, obvodkaitem3);
        ItemStack cancel1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancel1Meta = cancel1.getItemMeta();
        cancel1Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l[❌] Отмена"));
        cancel1.setItemMeta(cancel1Meta);
        menu.setItem(6, cancel1);
        ItemStack cancel2 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancel2Meta = cancel2.getItemMeta();
        cancel2Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l[❌] Отмена"));
        cancel2.setItemMeta(cancel2Meta);
        menu.setItem(7, cancel2);
        ItemStack cancel3 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancel3Meta = cancel3.getItemMeta();
        cancel3Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l[❌] Отмена"));
        cancel3.setItemMeta(cancel3Meta);
        menu.setItem(8, cancel3);
        ItemStack accept4 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta accept4Meta = accept4.getItemMeta();
        accept4Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[✔] Купить"));
        accept4.setItemMeta(accept4Meta);
        menu.setItem(9, accept4);
        ItemStack accept5 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta accept5Meta = accept5.getItemMeta();
        accept5Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[✔] Купить"));
        accept5.setItemMeta(accept5Meta);
        menu.setItem(10, accept5);
        ItemStack accept6 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta accept6Meta = accept6.getItemMeta();
        accept6Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[✔] Купить"));
        accept6.setItemMeta(accept6Meta);
        menu.setItem(11, accept6);
        ItemStack obvodkaitem4 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta obvodkaitem4Meta = obvodkaitem4.getItemMeta();
        obvodkaitem4Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&l[?]"));
        obvodkaitem4.setItemMeta(obvodkaitem4Meta);
        menu.setItem(12, obvodkaitem4);
        ItemStack obvodkaitem6 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta obvodkaitem6Meta = obvodkaitem6.getItemMeta();
        obvodkaitem6Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&l[?]"));
        obvodkaitem6.setItemMeta(obvodkaitem6Meta);
        menu.setItem(14, obvodkaitem6);
        ItemStack cancel4 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancel4Meta = cancel4.getItemMeta();
        cancel4Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l[❌] Отмена"));
        cancel4.setItemMeta(cancel4Meta);
        menu.setItem(15, cancel4);
        ItemStack cancel5 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancel5Meta = cancel5.getItemMeta();
        cancel5Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l[❌] Отмена"));
        cancel5.setItemMeta(cancel5Meta);
        menu.setItem(16, cancel5);
        ItemStack cancel6 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancel6Meta = cancel6.getItemMeta();
        cancel6Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l[❌] Отмена"));
        cancel6.setItemMeta(cancel6Meta);
        menu.setItem(17, cancel6);
        ItemStack accept7 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta accept7Meta = accept7.getItemMeta();
        accept7Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[✔] Купить"));
        accept7.setItemMeta(accept7Meta);
        menu.setItem(18, accept7);
        ItemStack accept8 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta accept8Meta = accept8.getItemMeta();
        accept8Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[✔] Купить"));
        accept8.setItemMeta(accept8Meta);
        menu.setItem(19, accept8);
        ItemStack accept9 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta accept9Meta = accept9.getItemMeta();
        accept9Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l[✔] Купить"));
        accept9.setItemMeta(accept9Meta);
        menu.setItem(20, accept9);
        ItemStack obvodkaitem7 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta obvodkaitem7Meta = obvodkaitem7.getItemMeta();
        obvodkaitem7Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&l[?]"));
        obvodkaitem7.setItemMeta(obvodkaitem7Meta);
        menu.setItem(21, obvodkaitem7);
        ItemStack obvodkaitem8 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta obvodkaitem8Meta = obvodkaitem8.getItemMeta();
        obvodkaitem8Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&l[?]"));
        obvodkaitem8.setItemMeta(obvodkaitem8Meta);
        menu.setItem(22, obvodkaitem8);
        ItemStack obvodkaitem9 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta obvodkaitem9Meta = obvodkaitem9.getItemMeta();
        obvodkaitem9Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&l[?]"));
        obvodkaitem9.setItemMeta(obvodkaitem9Meta);
        menu.setItem(23, obvodkaitem9);
        ItemStack cancel7 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancel7Meta = cancel7.getItemMeta();
        cancel7Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l[❌] Отмена"));
        cancel7.setItemMeta(cancel7Meta);
        menu.setItem(24, cancel7);
        ItemStack cancel8 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancel8Meta = cancel8.getItemMeta();
        cancel8Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l[❌] Отмена"));
        cancel8.setItemMeta(cancel8Meta);
        menu.setItem(25, cancel8);
        ItemStack cancel9 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancel9Meta = cancel9.getItemMeta();
        cancel9Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l[❌] Отмена"));
        cancel9.setItemMeta(cancel9Meta);
        menu.setItem(26, cancel9);
        player.openInventory(menu);
    }
    public static void setPage(Integer page) {
        page++;
        auctionmenu.getItem(49).getItemMeta().getLore().set(1, page.toString());
    }

    private static Integer getPage() {
        ItemStack updateauction = auctionmenu.getItem(49);
        if (updateauction == null) return 0;
        return Integer.parseInt(updateauction.getItemMeta().getLore().get(1)) - 1;
    }
}
