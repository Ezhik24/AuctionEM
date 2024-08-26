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
        if (event.getView().getTitle().contains(AhEM.auctionTitle)) {
            if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Следующая")){
                if (getpage(event.getClickedInventory()) * 45 + 45 >= AhEM.lots.size()) return;
                AhEM.setPage(getpage(event.getClickedInventory()) + 1);
                event.getView().close();
                AhEM.openauction((Player) event.getWhoClicked());
            }
            if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Предыдущая")){
                if (getpage(event.getClickedInventory()) == 0) return;
                AhEM.setPage(getpage(event.getClickedInventory()) - 1);
                event.getView().close();
                AhEM.openauction((Player) event.getWhoClicked());
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Обновить")) {
                event.getView().close();
                AhEM.openauction((Player) event.getWhoClicked());
            }
            if (event.getSlot() >= 0 && event.getSlot() < 45) {
                if (AhEM.lots.size() <= event.getSlot() + getpage(event.getClickedInventory()) * 45) return;
                AhEM.buy((Player) event.getWhoClicked(), ((ItemStack) AhEM.lots.get(event.getSlot() + getpage(event.getClickedInventory()) * 45).get("item")).clone());
            }
            event.setCancelled(true);
        }
        if (event.getView().getTitle().contains(AhEM.BuyTitle)) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Отмена")) {
                event.getView().close();
                AhEM.openauction((Player) event.getWhoClicked());
            }
        }
    }
    private static Integer getpage(Inventory auctmenu){
        return Integer.parseInt(auctmenu.getItem(49).getItemMeta().getLore().get(1)) - 1;
    }

}