package me.n137.Jobs.gui.jobs;

import me.n137.Jobs.Jobs;
import me.n137.Jobs.gui.Menu;
import me.n137.Jobs.gui.playerselector.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class JobsMenu extends Menu {

    public JobsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return Objects.requireNonNull(Jobs.getPlugin().getConfig().getString("messages.gui.job_menu_title")).replace("&", "§");
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(0, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.TROPICAL_FISH, "messages.menuitem.jobs.fisherman", "messages.menuitem.jobslore.fisherman"));
        inventory.setItem(1, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.ANVIL, "messages.menuitem.jobs.blacksmith", "messages.menuitem.jobslore.blacksmith"));
        inventory.setItem(2, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.GOLDEN_AXE, "messages.menuitem.jobs.lumberjack", "messages.menuitem.jobslore.lumberjack"));
        inventory.setItem(3, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.CROSSBOW, "messages.menuitem.jobs.hunter", "messages.menuitem.jobslore.hunter"));
        inventory.setItem(4, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.IRON_PICKAXE, "messages.menuitem.jobs.miner", "messages.menuitem.jobslore.miner"));
        inventory.setItem(5, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.ENCHANTING_TABLE, "messages.menuitem.jobs.enchanter", "messages.menuitem.jobslore.enchanter"));
        inventory.setItem(6, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.DIAMOND_HOE, "messages.menuitem.jobs.farmer", "messages.menuitem.jobslore.farmer"));
        inventory.setItem(7, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.IRON_SHOVEL, "messages.menuitem.jobs.archeologist", "messages.menuitem.jobslore.archeologist"));
        inventory.setItem(8, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.BRICKS, "messages.menuitem.jobs.builder", "messages.menuitem.jobslore.builder"));
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case TROPICAL_FISH:
                handleJob(player, "fisherman");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case ANVIL:
                handleJob(player, "blacksmith");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case GOLDEN_AXE:
                handleJob(player, "lumberjack");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case CROSSBOW:
                handleJob(player, "hunter");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case IRON_PICKAXE:
                handleJob(player, "miner");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                return;
            case ENCHANTING_TABLE:
                handleJob(player, "enchanter");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case DIAMOND_HOE:
                handleJob(player, "farmer");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case IRON_SHOVEL:
                handleJob(player, "archeologist");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case BRICKS:
                handleJob(player, "builder");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

        }


    }

    public void handleJob(Player player, String job) {
        if (Jobs.getPlugin().getDataManager().isPlayerEmployedInJob(player, job)) {
            Jobs.getPlugin().getDataManager().firePlayerFromJob(player, job);
            Jobs.getPlugin().sendQuitMessage(player, job);
        } else {
            Jobs.getPlugin().getDataManager().employPlayerInJob(player, job);
        }
    }


}
