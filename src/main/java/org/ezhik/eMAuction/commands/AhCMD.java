package org.ezhik.eMAuction.commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ezhik.eMAuction.AhEM;


public class AhCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (strings.length == 0) AhEM.openauction(player);
        else {
            switch (strings[0]) {
                case "sell":
                    if (strings.length != 2) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lEzhik&6&lMine &8&l>> &c&lНеверная команда. Введите так: /ah sell <цена>"));
                        break;
                    } else {
                        try{
                            AhEM.sell(player, Integer.parseInt(strings[1]));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lEzhik&6&lMine &8&l>> &a&lВы успешно продали предмет!"));
                        }
                        catch (NumberFormatException e){
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
