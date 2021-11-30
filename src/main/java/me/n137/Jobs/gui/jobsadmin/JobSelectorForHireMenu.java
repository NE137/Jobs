package me.n137.Jobs.gui.jobsadmin;

import me.n137.Jobs.Jobs;
import me.n137.Jobs.gui.Menu;
import me.n137.Jobs.gui.playerselector.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
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
            case TROPICAL_FISH:
                hirePlayer(event.getWhoClicked(), player, "fisherman");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case ANVIL:
                hirePlayer(event.getWhoClicked(), player, "blacksmith");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case GOLDEN_AXE:
                hirePlayer(event.getWhoClicked(), player, "lumberjack");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case CROSSBOW:
                hirePlayer(event.getWhoClicked(), player, "hunter");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case IRON_PICKAXE:
                hirePlayer(event.getWhoClicked(), player, "miner");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                return;
            case ENCHANTING_TABLE:
                hirePlayer(event.getWhoClicked(), player, "enchanter");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case DIAMOND_HOE:
                hirePlayer(event.getWhoClicked(), player, "farmer");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                return;
            case IRON_SHOVEL:
                hirePlayer(event.getWhoClicked(), player, "archeologist");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
        }

    }

    @Override
    public void setMenuItems() {
        inventory.setItem(0, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.TROPICAL_FISH, "messages.menuitem.jobs.fisherman", Arrays.asList("§r", "§e● §7As a fisherman, you get paid for catching fish.", "§r")));
        inventory.setItem(1, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.ANVIL, "messages.menuitem.jobs.blacksmith", Arrays.asList("§r", "§e● §7As a blacksmith, you get paid for crafting armor.", "§r")));
        inventory.setItem(2, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.GOLDEN_AXE, "messages.menuitem.jobs.lumberjack", Arrays.asList("§r", "§e● §7As a lumberjack, you get paid for cutting down trees.", "§r")));
        inventory.setItem(3, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.CROSSBOW, "messages.menuitem.jobs.hunter", Arrays.asList("§r", "§e● §7As a hunter, you get paid for killing mobs.", "§r")));
        inventory.setItem(4, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.IRON_PICKAXE, "messages.menuitem.jobs.miner", Arrays.asList("§r", "§e● §7As a miner, you get paid for mining ores.", "§r")));
        inventory.setItem(5, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.ENCHANTING_TABLE, "messages.menuitem.jobs.enchanter", Arrays.asList("§r", "§e● §7As an enchanter, you get paid for enchanting items.", "§r")));
        inventory.setItem(6, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.DIAMOND_HOE, "messages.menuitem.jobs.farmer", Arrays.asList("§r", "§e● §7As a farmer, you get paid for farming crops.", "§r")));
        inventory.setItem(7, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.IRON_SHOVEL, "messages.menuitem.jobs.archeologist", Arrays.asList("§r", "§e● §7As an archeologist, you get paid for digging up stuff.", "§r")));
   }

    public void hirePlayer(HumanEntity admin, Player target, String job) {
        if (!(Jobs.getPlugin().getDataManager().isPlayerEmployedInJob(target, job))) {
            Jobs.getPlugin().getDataManager().hirePlayerInJob(admin, target, job);
            admin.sendMessage(Jobs.getPlugin().getConfig().getString("messages.chat.admin_hired_success")
                    .replace("%job%", job).replace("%player%", target.getName()).replace("&", "§"));
        }
    }

}
