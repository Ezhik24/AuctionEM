package org.ezhik.eMAuction.commands;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;
import org.ezhik.eMAuction.AhEM;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class AhCMD implements CommandExecutor {
    public static Map<String,AhEM> ah = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (strings.length == 0)  {
            if (!ah.containsKey(player.getName())) ah.put(player.getName(), new AhEM());
            ah.get(player.getName()).openauction(player);
        }
        else {
            switch (strings[0]) {
                case "sell":
                    if (!ah.containsKey(player.getName())) ah.put(player.getName(), new AhEM());
                    if (strings.length != 2) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lEzhik&6&lMine &8&l>> &c&lНеверная команда. Введите так: /ah sell <цена>"));
                        break;
                    } else {
                        try {
                            ah.get(player.getName()).sell(player, Integer.parseInt(strings[1]));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lEzhik&6&lMine &8&l>> &a&lВы успешно продали предмет!"));
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lEzhik&6&lMine &8&l>> &c&lНекорректная стоимость предмета!"));
                        }
                        break;
                    }
                default:
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lEzhik&6&lMine &8&l>> &c&lНеверная команда. Введите так: /ah <параметр>"));
                    break;
            }
        }
        return false;
    }
}
