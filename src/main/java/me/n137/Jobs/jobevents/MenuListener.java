package me.n137.Jobs.jobevents;

import me.n137.Jobs.gui.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){

        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu) {
            e.setCancelled(true); //prevent them from fucking with the inventory
            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }

            Menu menu = (Menu) holder;
            menu.handleMenu(e);
        }

    }

}
