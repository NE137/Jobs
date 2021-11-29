package me.n137.Jobs.gui.jobs;

import me.n137.Jobs.Jobs;
import me.n137.Jobs.gui.Menu;
import me.n137.Jobs.gui.playerselector.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
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

        inventory.setItem(0, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.TROPICAL_FISH, "messages.menuitem.jobs.fisherman", Arrays.asList("§r", "§e● §7As a fisherman, you get paid for catching fish.", "§r")));
        inventory.setItem(2, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.ANVIL, "messages.menuitem.jobs.blacksmith", Arrays.asList("§r", "§e● §7As a blacksmith, you get paid for crafting armor.", "§r")));
        inventory.setItem(4, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.GOLDEN_AXE, "messages.menuitem.jobs.lumberjack", Arrays.asList("§r", "§e● §7As a lumberjack, you get paid for cutting down trees.", "§r")));
        inventory.setItem(6, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.CROSSBOW, "messages.menuitem.jobs.hunter", Arrays.asList("§r", "§e● §7As a hunter, you get paid for killing mobs.", "§r")));
        inventory.setItem(8, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.IRON_PICKAXE, "messages.menuitem.jobs.miner", Arrays.asList("§r", "§e● §7As a miner, you get paid for mining ores.", "§r")));

    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case TROPICAL_FISH: // Fisherman
                if (Jobs.getPlugin().getDataManager().isPlayerEmployedInJob(player, "fisherman")) {
                    Jobs.getPlugin().getDataManager().firePlayerFromJob(player, "fisherman");
                    Jobs.getPlugin().sendQuitMessage(player, "fisherman");
                } else {
                    Jobs.getPlugin().getDataManager().employPlayerInJob(player, "fisherman");

                }
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case ANVIL: // Blacksmith
                if (Jobs.getPlugin().getDataManager().isPlayerEmployedInJob(player, "blacksmith")) {
                    Jobs.getPlugin().getDataManager().firePlayerFromJob(player, "blacksmith");
                    Jobs.getPlugin().sendQuitMessage(player, "blacksmith");
                } else {
                    Jobs.getPlugin().getDataManager().employPlayerInJob(player, "blacksmith");
                }
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case GOLDEN_AXE: //Lumberjack
                if (Jobs.getPlugin().getDataManager().isPlayerEmployedInJob(player, "lumberjack")) {
                    Jobs.getPlugin().getDataManager().firePlayerFromJob(player, "lumberjack");
                    Jobs.getPlugin().sendQuitMessage(player, "lumberjack");
                } else {
                    Jobs.getPlugin().getDataManager().employPlayerInJob(player, "lumberjack");
                }
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case CROSSBOW: //Hunter
                if (Jobs.getPlugin().getDataManager().isPlayerEmployedInJob(player, "hunter")) {
                    Jobs.getPlugin().getDataManager().firePlayerFromJob(player, "hunter");
                    Jobs.getPlugin().sendQuitMessage(player, "hunter");
                } else {
                    Jobs.getPlugin().getDataManager().employPlayerInJob(player, "hunter");
                }
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case IRON_PICKAXE: //Miner
                if (Jobs.getPlugin().getDataManager().isPlayerEmployedInJob(player, "miner")) {
                    Jobs.getPlugin().getDataManager().firePlayerFromJob(player, "miner");
                    Jobs.getPlugin().sendQuitMessage(player, "miner");
                } else {
                    Jobs.getPlugin().getDataManager().employPlayerInJob(player, "miner");
                }
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

        }


    }


}
