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
            event.setCancelled(true);
        }
        if (event.getView().getTitle().contains(AhEM.BuyTitle)) {
            event.setCancelled(true);
        }
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
    }
}
