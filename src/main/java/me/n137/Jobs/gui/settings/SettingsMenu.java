package me.n137.Jobs.gui.settings;

import me.n137.Jobs.Jobs;
import me.n137.Jobs.gui.jobsadmin.JobsAdminMainMenu;
import me.n137.Jobs.gui.playerselector.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

public class SettingsMenu extends PaginatedMenu {


    public SettingsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Settings";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        switch (e.getCurrentItem().getType()) {
            case BOOK:
                int maxJobs = Jobs.getPlugin().getConfig().getInt("config.maxJobs");
                if (e.getClick().isRightClick()) {
                    if (maxJobs <= 1) return;
                    maxJobs--;
                } else if (e.getClick().isLeftClick()) {
                    if (maxJobs >= 9) return;
                    maxJobs++;
                }

                e.getWhoClicked().sendMessage("§aValue changed to: §6"+maxJobs);
                Jobs.getPlugin().getConfig().set("config.maxJobs", maxJobs);
                Jobs.getPlugin().saveConfig();
                e.getWhoClicked().closeInventory();
                new SettingsMenu(Jobs.getPlayerMenuUtility(player)).open();
                return;
            case EMERALD:
                Jobs.getPlugin().getConfig().set("config.randomizeEarning", !Jobs.getPlugin().getConfig().getBoolean("config.randomizeEarning"));
                Jobs.getPlugin().saveConfig();
                e.getWhoClicked().sendMessage("§aValue updated to: " + Jobs.getPlugin().getConfig().getBoolean("config.randomizeEarning"));
                e.getWhoClicked().closeInventory();
                return;
            case GOLD_INGOT:
                Jobs.getPlugin().getConfig().set("config.randomizeIncome", !Jobs.getPlugin().getConfig().getBoolean("config.randomizeIncome"));
                Jobs.getPlugin().saveConfig();
                e.getWhoClicked().sendMessage("§aValue updated to: " + Jobs.getPlugin().getConfig().getBoolean("config.randomizeIncome"));
                e.getWhoClicked().closeInventory();
                return;
            case NETHERITE_INGOT:
                if (Jobs.getDebugUsers().containsKey(e.getWhoClicked().getUniqueId())) {
                    Jobs.getDebugUsers().remove(e.getWhoClicked().getUniqueId());
                    e.getWhoClicked().sendMessage("§cVerbose Disabled.");
                } else {
                    Jobs.getDebugUsers().put(e.getWhoClicked().getUniqueId(), true);
                    e.getWhoClicked().sendMessage("§aVerbose Enabled");
                }
                e.getWhoClicked().closeInventory();
                return;

            case STRUCTURE_VOID:
                Jobs.getPlugin().reloadConfig();
                Jobs.getPlugin().clearHashmaps();
                Jobs.getPlugin().populateHashmaps();
                e.getWhoClicked().sendMessage("§aConfig reloaded.");
                return;
            case BARRIER:
                new JobsAdminMainMenu(Jobs.getPlayerMenuUtility(player)).open();
                return;

        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(10, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.BOOK, "messages.menuitem.admin.max_jobs", Arrays.asList("§r", "§e● §7Change the maximum amount of jobs a player can have.", "§r", "§7Current value: §e" + Jobs.getPlugin().getConfig().getInt("config.maxJobs"), "§e> To increase the value, leftclick on this item", "§e> To decrease the value, rightclick on this item", "§r")));
        inventory.setItem(11, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.EMERALD, "messages.menuitem.admin.toggle_earnings", Arrays.asList("§r", "§e● §7Toggle randomizing earnings.", "§e> When enabled, you will not get money every time", "§e> When disabled, you will be paid every time", "§r")));
        inventory.setItem(12, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.GOLD_INGOT, "messages.menuitem.admin.toggle_income", Arrays.asList("§r", "§e● §7Toggle randomizing income.", "§e> When enabled, you will be paid a random amount of money each time", "§e> When disabled, you will receive the value defined in the config", "§r")));
        inventory.setItem(13, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.NETHERITE_INGOT, "messages.menuitem.admin.toggle_verbose", Arrays.asList("§r", "§e● §7Output exactly what the plugin is doing in the chat.", "§r")));

        inventory.setItem(15, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.STRUCTURE_VOID, "messages.menuitem.admin.reload_configs", Arrays.asList("§r", "§e● §7Reload the configuration files.", "§r")));
        inventory.setItem(16, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.BARRIER, "messages.menuitem.admin.return", Arrays.asList("§r", "§e● §7Return to the previous menu.", "§r")));
    }
}