package org.ezhik.eMAuction.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ezhik.eMAuction.AhEM;
import org.ezhik.eMAuction.commands.AhCMD;

public class ClickEvent implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().contains(AhCMD.ah.auctionTitle)) {
            if(event.getSlot() == 50){
                if (AhCMD.ah.page * 45 + 45 < AhCMD.ah.lots.size()) {
                    AhCMD.ah.page++;
                    event.getView().close();
                    AhCMD.ah.openauction((Player) event.getWhoClicked());
                }
            }
            if(event.getSlot() == 48) {
                if (AhCMD.ah.page != 0) {
                    AhCMD.ah.page--;
                   event.getView().close();
                    AhCMD.ah.openauction((Player) event.getWhoClicked());
                }
            }
            if (event.getSlot() == 49) {
                event.getView().close();
                AhCMD.ah.openauction((Player) event.getWhoClicked());
            }
            if (event.getSlot() >= 0 && event.getSlot() < 45) {
                if (AhCMD.ah.lots.size() <= event.getSlot() + AhCMD.ah.page * 45)
                    AhCMD.ah.buy((Player) event.getWhoClicked(), ((ItemStack) AhCMD.ah.lots.get(event.getSlot() + AhCMD.ah.page * 45).get("item")).clone());
            }
            event.setCancelled(true);
        }
        if (event.getView().getTitle().contains(AhCMD.ah.BuyTitle)) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Отмена")) {
                event.getView().close();
                AhCMD.ah.openauction((Player) event.getWhoClicked());
            }
            event.setCancelled(true);
        }
    }

}