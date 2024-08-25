package org.ezhik.eMAuction.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.ezhik.eMAuction.AhEM;

public class ClickEvent implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().contains(AhEM.auctionTitle)) {
            if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Следующая")){
                if (AhEM.page * 45 + 45 >= AhEM.lots.size()) return;
                AhEM.page++;
                event.getView().close();
                AhEM.openauction((Player) event.getWhoClicked());
            }
            if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Предыдущая")){
                if (AhEM.page == 0) return;
                AhEM.page--;
                event.getView().close();
                AhEM.openauction((Player) event.getWhoClicked());
            }
            if (event.getSlot() >= 0 && event.getSlot() < 45) {
                if (AhEM.lots.size() <= event.getSlot() + AhEM.page * 45) return;
                AhEM.buy((Player) event.getWhoClicked(), AhEM.lots.get(event.getSlot() + AhEM.page * 45).get("item").clone());
            }
            event.setCancelled(true);
        }
        if (event.getView().getTitle().contains(AhEM.BuyTitle)) {
            event.setCancelled(true);
        }

    }
}