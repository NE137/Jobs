package me.n137.Jobs.gui.jobsadmin;

import me.n137.Jobs.Jobs;
import me.n137.Jobs.gui.Menu;
import me.n137.Jobs.gui.playerselector.PlayerFireSelectorMenu;
import me.n137.Jobs.gui.playerselector.PlayerHireSelectorMenu;
import me.n137.Jobs.gui.playerselector.PlayerInspectMenu;
import me.n137.Jobs.gui.playerselector.PlayerMenuUtility;
import me.n137.Jobs.gui.settings.SettingsMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class JobsAdminMainMenu extends Menu {

    public JobsAdminMainMenu(PlayerMenuUtility playerMenuUtility) {super(playerMenuUtility);}

    @Override
    public String getMenuName() {
        return Objects.requireNonNull(Jobs.getPlugin().getConfig().getString("messages.gui.job_admin_main_menu_title").replace("&", "§"));
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        switch (e.getCurrentItem().getType()) {
            case IRON_SWORD:
                new PlayerHireSelectorMenu(Jobs.getPlayerMenuUtility(player)).open();
                return;
            case BLAZE_POWDER:
                new PlayerFireSelectorMenu(Jobs.getPlayerMenuUtility(player)).open();
                return;
            case SPYGLASS:
                new PlayerInspectMenu(Jobs.getPlayerMenuUtility(player)).open();
                return;
            case COMMAND_BLOCK:
                new SettingsMenu(Jobs.getPlayerMenuUtility(player)).open();
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(10, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.IRON_SWORD, "messages.menuitem.admin.hire", "messages.menuitem.adminlore.hire"));
        inventory.setItem(12, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.BLAZE_POWDER, "messages.menuitem.admin.fire", "messages.menuitem.adminlore.fire"));
        inventory.setItem(14, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.SPYGLASS, "messages.menuitem.admin.list", "messages.menuitem.adminlore.list"));
        inventory.setItem(16, Jobs.getPlugin().getMenuHandler().createMenuItem(Material.COMMAND_BLOCK, "messages.menuitem.admin.settings", "messages.menuitem.adminlore.settings"));
    }
    }