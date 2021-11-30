package me.n137.Jobs.jobevents;

import me.n137.Jobs.Jobs;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class Events implements Listener {

    private Jobs plugin;
    public Events(Jobs p) {this.plugin = p;}

    double money = 0.0;

// JOBS ----------------------------------------------------------------------------------------------------------------

    // Fisher
    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.isCancelled()) return;
        if (!(this.plugin.getDataManager().isPlayerEmployedInJob(event.getPlayer(), "fisherman"))) { return; }
        if (event.getCaught() == null) return;

        Item caughtItem = (Item) event.getCaught();
        assert caughtItem != null;
        String caught = caughtItem.getItemStack().getType().name();
        if (this.plugin.getFishermanList().containsKey(caught)) {
            money = this.plugin.getFishermanList().get(caught);
            money = this.plugin.modifyIncome(money);
         if (this.plugin.processEarnings()) Jobs.getEcon().depositPlayer(event.getPlayer(), money);
         if (this.plugin.processEarnings()) event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
            Objects.requireNonNull(Objects.requireNonNull(this.plugin.getConfig().getString("messages.actionbar.fish"))
                    .replace("%fish%", caught)
                    .replace("%money%", String.valueOf(money))
                    .replace("&", "§"))));
        }
    }
    // Blacksmith
    @EventHandler
    public void onItemCraft(CraftItemEvent event) {
        if (event.isCancelled()) return;
        Player crafter = (Player) event.getWhoClicked();
        if (!(this.plugin.getDataManager().isPlayerEmployedInJob(crafter, "blacksmith"))) { return; }
        Material material = event.getRecipe().getResult().getType();
        String crafted = material.name();

        if (this.plugin.getBlacksmithList().containsKey(crafted)) {
            money = this.plugin.getBlacksmithList().get(crafted);
            money = this.plugin.modifyIncome(money);
            if (this.plugin.processEarnings()) Jobs.getEcon().depositPlayer(crafter, money);
            if (this.plugin.processEarnings()) crafter.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    Objects.requireNonNull(Objects.requireNonNull(this.plugin.getConfig().getString("messages.actionbar.blacksmith"))
                            .replace("%item%", crafted)
                            .replace("%money%", String.valueOf(money*event.getRecipe().getResult().getAmount()))
                            .replace("&", "§"))));
        }

    }

    // Hunter
    @EventHandler
    public void onKill(EntityDeathEvent event) {

        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        if (!(this.plugin.getDataManager().isPlayerEmployedInJob(killer, "hunter"))) { return; }

        EntityType entity = event.getEntity().getType();

        if (this.plugin.getHunterList().containsKey(entity.name())) {
            money = this.plugin.getHunterList().get(entity.name());
            money = this.plugin.modifyIncome(money);
            if (this.plugin.processEarnings()) Jobs.getEcon().depositPlayer(killer, money);
            if (this.plugin.processEarnings()) killer.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    Objects.requireNonNull(Objects.requireNonNull(this.plugin.getConfig().getString("messages.actionbar.hunter"))
                            .replace("%mob%", entity.name())
                            .replace("%money%", String.valueOf(money))
                            .replace("&", "§"))));
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        if (event.isCancelled()) return;
        if (event.getEnchanter() == null) return;
        Player enchanter = event.getEnchanter();
        ItemStack enchantedItem = event.getItem();

        if (this.plugin.getDataManager().isPlayerEmployedInJob(enchanter, "enchanter")) {
            if (this.plugin.getEnchanterList().containsKey(enchantedItem.getType().name())) {
                money = this.plugin.getEnchanterList().get(enchantedItem.getType().name());
                money = this.plugin.modifyIncome(money);
                if (this.plugin.processEarnings()) Jobs.getEcon().depositPlayer(enchanter, money);
                if (this.plugin.processEarnings()) enchanter.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                        Objects.requireNonNull(Objects.requireNonNull(this.plugin.getConfig().getString("messages.actionbar.enchanter"))
                                .replace("%item%", enchantedItem.getType().name())
                                .replace("%money%", String.valueOf(money))
                                .replace("&", "§"))));
            }
        }
    }



    // Miner & Lumberjack & Farmer & Archeologist
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        if (event.isCancelled()) return;

            Player miner = event.getPlayer();
            Material blockType = event.getBlock().getType();


            if (this.plugin.getDataManager().isPlayerEmployedInJob(event.getPlayer(), "miner")) {
                if (this.plugin.getMinerList().containsKey(blockType.name())) {

                    if (event.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) return;

                    money = this.plugin.getMinerList().get(blockType.name());
                    money = this.plugin.modifyIncome(money);
                    if (this.plugin.processEarnings()) Jobs.getEcon().depositPlayer(miner, money);
                    if (this.plugin.processEarnings()) miner.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                            Objects.requireNonNull(Objects.requireNonNull(this.plugin.getConfig().getString("messages.actionbar.miner"))
                                    .replace("%block%", blockType.name())
                                    .replace("%money%", String.valueOf(money))
                                    .replace("&", "§"))));
                    // Miner
                }
            }
        if (this.plugin.getDataManager().isPlayerEmployedInJob(event.getPlayer(), "lumberjack")) {
            if (this.plugin.getLumberjackList().containsKey(blockType.name())) {
                money = this.plugin.getLumberjackList().get(blockType.name());
                money = this.plugin.modifyIncome(money);
                if (this.plugin.processEarnings()) Jobs.getEcon().depositPlayer(miner, money);
                if (this.plugin.processEarnings()) miner.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                        Objects.requireNonNull(Objects.requireNonNull(this.plugin.getConfig().getString("messages.actionbar.lumberjack"))
                                .replace("%block%", blockType.name())
                                .replace("%money%", String.valueOf(money))
                                .replace("&", "§"))));

                // Lumberjack

            }

        }

        if (this.plugin.getDataManager().isPlayerEmployedInJob(event.getPlayer(), "archeologist")) {
            if (this.plugin.getArcheologistList().containsKey(blockType.name())) {
                money = this.plugin.getArcheologistList().get(blockType.name());
                money = this.plugin.modifyIncome(money);
                if (this.plugin.processEarnings()) Jobs.getEcon().depositPlayer(miner, money);
                if (this.plugin.processEarnings()) miner.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                        Objects.requireNonNull(Objects.requireNonNull(this.plugin.getConfig().getString("messages.actionbar.archeologist"))
                                .replace("%block%", blockType.name())
                                .replace("%money%", String.valueOf(money))
                                .replace("&", "§"))));

                // Lumberjack

            }

        }

        if (this.plugin.getDataManager().isPlayerEmployedInJob(event.getPlayer(), "farmer")) {
            if (this.plugin.getFarmerList().containsKey(blockType.name())) {
                money = this.plugin.getFarmerList().get(blockType.name());
                money = this.plugin.modifyIncome(money);
                if (this.plugin.processEarnings()) Jobs.getEcon().depositPlayer(miner, money);
                if (this.plugin.processEarnings()) miner.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                        Objects.requireNonNull(Objects.requireNonNull(this.plugin.getConfig().getString("messages.actionbar.farmer"))
                                .replace("%block%", blockType.name())
                                .replace("%money%", String.valueOf(money))
                                .replace("&", "§"))));

                // Lumberjack

            }

        }
    }
}
