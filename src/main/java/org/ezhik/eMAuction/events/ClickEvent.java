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
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().contains(AhEM.auctionTitle)) {
            if(event.getSlot() == 50){
                if (AhEM.getPage(player) * 45 + 45 >= AhEM.lots.size()) return;
                AhEM.incpage(player);
                event.getView().close();
                AhEM.openauction((Player) event.getWhoClicked());
            }
            if(event.getSlot() == 48){
                if (AhEM.getPage(player) == 0) return;
                AhEM.decpage(player);
                event.getView().close();
                AhEM.openauction((Player) event.getWhoClicked());
            }
            if (event.getSlot() == 49) {
                event.getView().close();
                AhEM.openauction((Player) event.getWhoClicked());
            }
            if (event.getSlot() >= 0 && event.getSlot() < 45) {
                if (AhEM.lots.size() <= event.getSlot() + AhEM.getPage(player) * 45) return;
                AhEM.buy((Player) event.getWhoClicked(), ((ItemStack) AhEM.lots.get(event.getSlot() + AhEM.getPage(player) * 45).get("item")).clone());
            }
            event.setCancelled(true);
        }
        if (event.getView().getTitle().contains(AhEM.BuyTitle)) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Отмена")) {
                event.getView().close();
                AhEM.openauction((Player) event.getWhoClicked());
            }
            event.setCancelled(true);
        }
    }

}