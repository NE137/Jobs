package me.n137.Jobs;

import me.n137.Jobs.commands.Commands;
import me.n137.Jobs.data.DataManager;
import me.n137.Jobs.gui.MenuHandler;
import me.n137.Jobs.gui.jobs.JobsMenu;
import me.n137.Jobs.gui.jobsadmin.JobsAdminMainMenu;
import me.n137.Jobs.gui.playerselector.PlayerMenuUtility;
import me.n137.Jobs.jobevents.Events;
import me.n137.Jobs.jobevents.MenuListener;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public final class Jobs extends JavaPlugin {

    private static Economy econ = null;
    private static Permission perms = null;
    private Commands commands = null;
    public JobsMenu jobsMenu = null;
    public JobsAdminMainMenu jobsAdminMainMenu = null;
    public MenuHandler menuHandler = null;
    private DataManager dataManager = null;


    private static Jobs plugin;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    private static HashMap<UUID, Boolean> debugUsers = new HashMap<>();

    private HashMap<String, Double> hunterList = new HashMap<>();
    private HashMap<String, Double> blacksmithList = new HashMap<>();
    private HashMap<String, Double> fishermanList = new HashMap<>();
    private HashMap<String, Double> minerList = new HashMap<>();
    private HashMap<String, Double> lumberjackList = new HashMap<>();

   private HashMap<UUID, List<String>> jobList = new HashMap<>();

    @Override
    public void onEnable() {
        plugin = this;
        if (!setupEconomy() ) {
            this.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        this.setupPermissions();
        this.registerEvents();

        this.dataManager = new DataManager(this);
        this.dataManager.loadHashMapFromConfig();

        this.populateHashmaps();

        this.menuHandler = new MenuHandler(this);
        this.commands = new Commands(this);


    }

    @Override
    public void onDisable() {
        this.getDataManager().saveHashMapToConfig();
        Bukkit.getScheduler().cancelTasks(this);
        Bukkit.getPluginManager().disablePlugin(this);
    }

    // removal from jobs timer


    public void populateHashmaps() {         // Fisherman
        for (String s : getConfig().getConfigurationSection("money.fisherman").getKeys(false)) {
            double v = getConfig().getDouble("money.fisherman."+s);
            fishermanList.put(s, v);
        }
        this.getServer().getConsoleSender().sendMessage("Loaded Fisherman Hashmap");
        // Blacksmith
        for (String s : getConfig().getConfigurationSection("money.blacksmith").getKeys(false)) {
            double v = getConfig().getDouble("money.blacksmith."+s);
            blacksmithList.put(s, v);
        }
        this.getServer().getConsoleSender().sendMessage("Loaded Blacksmith Hashmap");
        // Hunter
        for (String s : getConfig().getConfigurationSection("money.hunter").getKeys(false)) {
            double v = getConfig().getDouble("money.hunter."+s);
            hunterList.put(s, v);
        }
        this.getServer().getConsoleSender().sendMessage("Loaded Hunter Hashmap");
        // Miner
        for (String s : getConfig().getConfigurationSection("money.miner").getKeys(false)) {
            double v = getConfig().getDouble("money.miner."+s);
            minerList.put(s, v);
        }
        this.getServer().getConsoleSender().sendMessage("Loaded Miner Hashmap");
        // Lumberjack
        for (String s : getConfig().getConfigurationSection("money.lumberjack").getKeys(false)) {
            double v = getConfig().getDouble("money.lumberjack."+s);
            lumberjackList.put(s, v);
        }
        this.getServer().getConsoleSender().sendMessage("Loaded Lumberjack Hashmap");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return this.commands.onCommand(sender, command, label, args);
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new Events(this), this);
        this.getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    public static Economy getEcon() {
        return econ;
    }

    public HashMap<String, Double> getHunterList() {
        return hunterList;
    }

    public HashMap<String, Double> getBlacksmithList() {
        return blacksmithList;
    }

    public HashMap<String, Double> getFishermanList() {
        return fishermanList;
    }

    public HashMap<String, Double> getMinerList() {
        return minerList;
    }

    public HashMap<String, Double> getLumberjackList() {
        return lumberjackList;
    }

    public static HashMap<UUID, Boolean> getDebugUsers() { return debugUsers; }

    public String getNamespaceKey() {
        return "xjobs-by-N137";
    }

    public HashMap<UUID, List<String>> getJobList() {
        return jobList;
    }

    public MenuHandler getMenuHandler() {
        return menuHandler;
    }

    public void sendEmploymentMessage(Player player, String job) {
        player.sendMessage(this.getConfig().getString("messages.chat.joined_job").replace("%job%", job).replace("&", "§"));
    }

    public void sendQuitMessage(Player player, String job) {
        player.sendMessage(this.getConfig().getString("messages.chat.left_job").replace("%job%", job).replace("&", "§"));
    }
    public void sendFiredMessage(Player player, String job) {
        player.sendMessage(this.getConfig().getString("messages.chat.fired_job").replace("%job%", job).replace("&", "§"));
    }

    public String composeHiredMessage(String job) {
        return (this.getConfig().getString("messages.chat.hired_job").replace("%job%", job).replace("&", "§"));
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public double modifyIncome(double income) {
        final DecimalFormat df = new DecimalFormat("0.00");
        if (this.getConfig().getBoolean("config.randomizeIncome")) {
            Random randomPercentage = new Random();
            return Double.parseDouble(df.format((income * (0.5 + randomPercentage.nextDouble()))));
        }
        return income;
    }
    public boolean processEarnings() {
        if (this.getConfig().getBoolean("config.randomizeEarning")) {
            Random random = new Random();
            return random.nextBoolean();
        }
        return true;
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(p))) {
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);
            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(p);
        }
    }

    public static Jobs getPlugin() {
        return plugin;
    }

}
