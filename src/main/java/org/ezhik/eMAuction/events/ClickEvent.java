package org.ezhik.eMAuction.events;

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
    }
}
