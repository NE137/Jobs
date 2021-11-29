package me.n137.Jobs.gui.playerselector;

import me.n137.Jobs.Jobs;
import me.n137.Jobs.gui.jobsadmin.JobSelectorFireMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerFireSelectorMenu extends PaginatedMenu {


    public PlayerFireSelectorMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Select a Player";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ArrayList<Player> players = new ArrayList<Player>(Jobs.getPlugin().getServer().getOnlinePlayers());

        if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
            PlayerMenuUtility playerMenuUtility = Jobs.getPlayerMenuUtility(p);
            playerMenuUtility.setPlayer(Bukkit.getPlayer(UUID.fromString(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Jobs.getPlugin(), "uuid"), PersistentDataType.STRING))));
            new JobSelectorFireMenu(playerMenuUtility).open();

        }else if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            p.closeInventory();

        }else if(e.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)){
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Previous Page")){
                if (!(page == 0)){
                    page = page - 1;
                    super.open();
                }
            }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Next Page")){
                if (!((index + 1) >= players.size())){
                    page = page + 1;
                    super.open();
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        ArrayList<Player> players = new ArrayList<>(Jobs.getPlugin().getServer().getOnlinePlayers());

        if(players != null && !players.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= players.size()) break;
                if (players.get(index) != null){
                    ItemStack playerItem = new ItemStack(Material.PLAYER_HEAD, 1);
                    ItemMeta playerMeta = playerItem.getItemMeta();

                    SkullMeta skullMeta = (SkullMeta) playerMeta;
                    assert skullMeta != null;
                    skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(players.get(index).getUniqueId()));
                    playerItem.setItemMeta(skullMeta);

                    playerMeta.setDisplayName(ChatColor.RED + players.get(index).getDisplayName());
                    playerMeta.getPersistentDataContainer().set(new NamespacedKey(Jobs.getPlugin(), "uuid"), PersistentDataType.STRING, players.get(index).getUniqueId().toString());
                    playerItem.setItemMeta(playerMeta);

                    inventory.addItem(playerItem);
                }
            }
        }
    }
}
