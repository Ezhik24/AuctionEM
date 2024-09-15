package org.ezhik.eMAuction.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.ezhik.eMAuction.AhEM;
import org.ezhik.eMAuction.commands.AhCMD;

public class ClickEvent implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().contains(AhCMD.ah.get(player.getName()).auctionTitle)) {
            if(event.getSlot() == 50){
                if (AhCMD.ah.get(player.getName()).page * 45 + 45 < AhCMD.ah.get(player.getName()).lots.size()) {
                    AhCMD.ah.get(player.getName()).page++;
                    event.getView().close();
                    AhCMD.ah.get(player.getName()).openauction((Player) event.getWhoClicked());
                }
            }
            if(event.getSlot() == 48) {
                if (AhCMD.ah.get(player.getName()).page != 0) {
                    AhCMD.ah.get(player.getName()).page--;
                   event.getView().close();
                    AhCMD.ah.get(player.getName()).openauction((Player) event.getWhoClicked());
                }
            }
            if (event.getSlot() == 49) {
                event.getView().close();
                AhCMD.ah.get(player.getName()).openauction((Player) event.getWhoClicked());
            }
            if (event.getSlot() == 46) {
                event.getView().close();
                AhCMD.ah.get(player.getName()).storagemenu((Player) event.getWhoClicked());
            }
            if (event.getSlot() >= 0 && event.getSlot() < 45) {
                if (!(AhCMD.ah.get(player.getName()).lots.size() <= event.getSlot() + AhCMD.ah.get(player.getName()).page * 45)) {
                    AhCMD.ah.get(player.getName()).itemForSaleIndex = event.getSlot() + AhCMD.ah.get(player.getName()).page * 45;
                    AhCMD.ah.get(player.getName()).buymenu((Player) event.getWhoClicked(), ((ItemStack) AhCMD.ah.get(player.getName()).lots.get(event.getSlot() + AhCMD.ah.get(player.getName()).page * 45).get("item")).clone());
                }
            }
            event.setCancelled(true);
        }
        if (event.getView().getTitle().contains(AhCMD.ah.get(player.getName()).BuyTitle)) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Отмена")) {
                event.getView().close();
                AhCMD.ah.get(player.getName()).openauction((Player) event.getWhoClicked());
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Купить")) {
                Integer itemid = AhCMD.ah.get(player.getName()).itemForSaleIndex;
                if ((Integer) AhCMD.ah.get(player.getName()).lots.get(itemid).get("price") <= AhEM.getballance(player)){
                    AhCMD.ah.get(player.getName()).buy(itemid,player);
                    event.getView().close();
                    AhCMD.ah.get(player.getName()).openauction((Player) event.getWhoClicked());
                }

            }
            event.setCancelled(true);
        }

    }

}