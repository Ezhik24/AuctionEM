package org.ezhik.eMAuction.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ezhik.eMAuction.AhEM;

public class ClickEvent implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        AhEM ah = new AhEM();
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().contains(ah.auctionTitle)) {
            if(event.getSlot() == 50){
                if (ah.page * 45 + 45 >= ah.lots.size()) return;
                ah.page++;
                event.getView().close();
                ah.openauction((Player) event.getWhoClicked());
            }
            if(event.getSlot() == 48){
                if (ah.page == 0) return;
                ah.page--;
                event.getView().close();
                ah.openauction((Player) event.getWhoClicked());
            }
            if (event.getSlot() == 49) {
                event.getView().close();
                ah.openauction((Player) event.getWhoClicked());
            }
            if (event.getSlot() >= 0 && event.getSlot() < 45) {
                if (ah.lots.size() <= event.getSlot() + ah.page * 45) return;
                ah.buy((Player) event.getWhoClicked(), ((ItemStack) ah.lots.get(event.getSlot() + ah.page * 45).get("item")).clone());
            }
            event.setCancelled(true);
        }
        if (event.getView().getTitle().contains(ah.BuyTitle)) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Отмена")) {
                event.getView().close();
                ah.openauction((Player) event.getWhoClicked());
            }
            event.setCancelled(true);
        }
    }

}