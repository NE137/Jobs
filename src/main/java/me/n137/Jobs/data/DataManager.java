package me.n137.Jobs.data;

import me.n137.Jobs.Jobs;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class DataManager {
    private final Jobs plugin;
    private FileConfiguration jobConfig = null;
    private File jobConfigFile = null;
    public DataManager(Jobs p) { this.plugin = p;}

    public void load() {
        if (this.jobConfigFile == null) {
            this.jobConfigFile = new File(this.plugin.getDataFolder(), "data.yml");

        }

        this.jobConfig = YamlConfiguration.loadConfiguration(this.jobConfigFile);
        this.plugin.getServer().getConsoleSender().sendMessage("Loaded " + this.jobConfig.getKeys(false).size() + " players");
    }

    public void save() {
        if (this.jobConfig != null && this.jobConfigFile != null) {
            try {
                this.jobConfig.save(this.jobConfigFile);
            } catch (Exception e) {
                this.plugin.getServer().getConsoleSender().sendMessage("Could not save config to " + this.jobConfigFile + ": " + e.getMessage());
            }
        }
    }

    public void loadHashMapFromConfig() {
        this.load();
        try {
            if (this.jobConfig.getConfigurationSection("jobs").getKeys(false).size() > 0) {
                this.jobConfig.getConfigurationSection("jobs").getKeys(false).forEach(key ->{
                    UUID userID = UUID.fromString(key);
                    List<String> jobs = this.jobConfig.getStringList("jobs." + key);
                    this.plugin.getJobList().put(userID, jobs);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveHashMapToConfig() {
        if (this.plugin.getJobList().size() > 0) {
            for (Map.Entry<UUID, List<String>> entry : this.plugin.getJobList().entrySet()) {
                this.jobConfig.set("jobs." + entry.getKey(), entry.getValue());
            }
        }
        this.save();
    }

    public boolean isPlayerEmployedInJob(Player player, String job) {
        if (this.plugin.getJobList().containsKey(player.getUniqueId())) {
            List<String> playerJobs = this.plugin.getJobList().get(player.getUniqueId());
            return playerJobs.contains(job);
        } else {  return false; }
    }

    public void employPlayerInJob(Player player, String job) {
        if (isPlayerEmployedInJob(player, job)) return;
        if (this.plugin.getJobList().containsKey(player.getUniqueId())) {
            if ( this.plugin.getJobList().get(player.getUniqueId()).size() >= this.plugin.getConfig().getInt("config.maxJobs")) {
                player.sendMessage(this.plugin.getConfig().getString("messages.chat.max_jobs_reached").replace("%amount%", String.valueOf(this.plugin.getConfig().getInt("config.maxJobs"))).replace("&", "§"));
                return;
            }
            // currently already has a a job
            List<String> currentJobs = this.plugin.getJobList().get(player.getUniqueId());
            if (currentJobs.contains(job)) return;
            currentJobs.add(job);
            this.plugin.getJobList().remove(player.getUniqueId());
            this.plugin.getJobList().put(player.getUniqueId(), currentJobs);
            this.plugin.sendEmploymentMessage(player, job.substring(0, 1).toUpperCase() + job.substring(1));
        } else {
            List<String> jobs = Arrays.asList(job);
            this.plugin.getJobList().put(player.getUniqueId(), jobs);
        }
    }
    public void hirePlayerInJob(HumanEntity admin, Player player, String job) {
        if (isPlayerEmployedInJob(player, job)) return;
        if (this.plugin.getJobList().containsKey(player.getUniqueId())) {
            if ( this.plugin.getJobList().get(player.getUniqueId()).size() >= this.plugin.getConfig().getInt("config.maxJobs")) {
                admin.sendMessage(Jobs.getPlugin().getConfig().getString("messages.chat.max_jobs_reached_hired")
                        .replace("%job%", job).replace("%player%", player.getName()).replace("&", "§"));
                return;
            }
            // currently already has a a job
            List<String> currentJobs = this.plugin.getJobList().get(player.getUniqueId());
            if (currentJobs.contains(job)) return;
            currentJobs.add(job);
            this.plugin.getJobList().remove(player.getUniqueId());
            this.plugin.getJobList().put(player.getUniqueId(), currentJobs);
            this.plugin.composeHiredMessage(job.substring(0, 1).toUpperCase() + job.substring(1));
        } else {
            List<String> jobs = Arrays.asList(job);
            this.plugin.getJobList().put(player.getUniqueId(), jobs);
        }
    }

    public void firePlayerFromJob(Player player, String job) {
        if (!(isPlayerEmployedInJob(player, job))) return;
        if (!(this.plugin.getJobList().containsKey(player.getUniqueId()))) return;
        if (this.plugin.getJobList().containsKey(player.getUniqueId())) {
            List<String> currentJobs = this.plugin.getJobList().get(player.getUniqueId());
            if (!(currentJobs.contains(job))) return;
            currentJobs.remove(job);
            this.plugin.getJobList().remove(player.getUniqueId());
            this.plugin.getJobList().put(player.getUniqueId(), currentJobs);
        }
    }
    public List<String> listPlayerJobs(Player player) {
        if (!(this.plugin.getJobList().containsKey(player.getUniqueId()))) return Collections.singletonList("No jobs found");
        return this.plugin.getJobList().get(player.getUniqueId());
    }
}
