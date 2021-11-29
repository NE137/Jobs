package me.n137.Jobs.gui.jobsadmin;

import me.n137.Jobs.Jobs;
import me.n137.Jobs.gui.Menu;
import me.n137.Jobs.gui.playerselector.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

public class JobSelectorForHireMenu extends Menu {

    public JobSelectorForHireMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Hire " + playerMenuUtility.getPlayer().getDisplayName();
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        Player player = playerMenuUtility.getPlayer();

        switch (event.getCurrentItem().getType()) {
            case TROPICAL_FISH: // Fisherman
                if (!(Jobs.getPlugin().getDataManager().isPlayerEmployedInJob(player, "fisherman"))) {
                    Jobs.getPlugin().getDataManager().hirePlayerInJob(event.getWhoClicked(), player, "fisherman");
                    }
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case ANVIL: // Blacksmith
                if (!(Jobs.getPlugin().getDataManager().isPlayerEmployedInJob(player, "blacksmith"))) {
                    Jobs.getPlugin().getDataManager().hirePlayerInJob(event.getWhoClicked(), player, "blacksmith");
                    event.getWhoClicked().sendMessage(Jobs.getPlugin().getConfig().getString("messages.chat.admin_hired_success")
                            .replace("%job%", "blacksmith").replace("%player%", player.getName()).replace("&", "§"));
                }
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case GOLDEN_AXE: //Lumberjack
                if (!(Jobs.getPlugin().getDataManager().isPlayerEmployedInJob(player, "lumberjack"))) {
                    Jobs.getPlugin().getDataManager().hirePlayerInJob(event.getWhoClicked(), player, "lumberjack");
                    event.getWhoClicked().sendMessage(Jobs.getPlugin().getConfig().getString("messages.chat.admin_hired_success")
                            .replace("%job%", "lumberjack").replace("%player%", player.getName()).replace("&", "§"));
                }
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case CROSSBOW: //Hunter
                if (!(Jobs.getPlugin().getDataManager().isPlayerEmployedInJob(player, "hunter"))) {
                    Jobs.getPlugin().getDataManager().hirePlayerInJob(event.getWhoClicked(), player, "hunter");
                    event.getWhoClicked().sendMessage(Jobs.getPlugin().getConfig().getString("messages.chat.admin_hired_success")
                            .replace("%job%", "hunter").replace("%player%", player.getName()).replace("&", "§"));
                }
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case IRON_PICKAXE: //Miner
                if (!(Jobs.getPlugin().getDataManager().isPlayerEmployedInJob(player, "miner"))) {
                    Jobs.getPlugin().getDataManager().hirePlayerInJob(event.getWhoClicked(), player, "miner");
                    event.getWhoClicked().sendMessage(Jobs.getPlugin().getConfig().getString("messages.chat.admin_hired_success")
                            .replace("%job%", "miner").replace("%player%", player.getName()).replace("&", "§"));
                }
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

        }

    }

    @Override
    public void setMenuItems() {


        inventory.setItem(0, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.TROPICAL_FISH, "messages.menuitem.jobs.fisherman", Arrays.asList("§r", "§e● §7As a fisherman, you get paid for catching fish.", "§r")));
        inventory.setItem(2, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.ANVIL, "messages.menuitem.jobs.blacksmith", Arrays.asList("§r", "§e● §7As a blacksmith, you get paid for crafting armor.", "§r")));
        inventory.setItem(4, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.GOLDEN_AXE, "messages.menuitem.jobs.lumberjack", Arrays.asList("§r", "§e● §7As a lumberjack, you get paid for cutting down trees.", "§r")));
        inventory.setItem(6, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.CROSSBOW, "messages.menuitem.jobs.hunter", Arrays.asList("§r", "§e● §7As a hunter, you get paid for killing mobs.", "§r")));
        inventory.setItem(8, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.IRON_PICKAXE, "messages.menuitem.jobs.miner", Arrays.asList("§r", "§e● §7As a miner, you get paid for mining ores.", "§r")));

    }

}
