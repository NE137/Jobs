package me.n137.Jobs.commands;

import me.n137.Jobs.Jobs;
import me.n137.Jobs.gui.jobs.JobsMenu;
import me.n137.Jobs.gui.jobsadmin.JobsAdminMainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;


public class Commands {
    private Jobs plugin;

    public Commands(Jobs p) {this.plugin = p;}

        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

            if (cmd.getName().equalsIgnoreCase("jobs")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cThis command cannot be ran from console");
                    return true;
                }

                if (sender.hasPermission("jobs.join")) {
                    Player player = (Player) sender;
                    new JobsMenu(Jobs.getPlayerMenuUtility(player)).open();
                    return true;
                } else {
                    sender.sendMessage(this.plugin.getConfig().getString("messages.chat.no_permission").replace("&", "§"));
                    return true;
                }


            } else if (cmd.getName().equalsIgnoreCase("jobsadmin")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cThis command cannot be ran from console");
                    return true;
                }
                if (sender.hasPermission("jobsadmin.admin")) {

                if (args.length == 0) {
                    Player player = (Player) sender;
                    new JobsAdminMainMenu(Jobs.getPlayerMenuUtility(player)).open();
                    return true;
                }
            } else {
                    sender.sendMessage(this.plugin.getConfig().getString("messages.chat.no_permission").replace("&", "§"));
                    return true;
                }
            } else if(cmd.getName().equalsIgnoreCase("quitjobs")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cThis command cannot be ran from console");
                    return true;
                }
                Player player = (Player) sender;
                Jobs.getPlugin().getDataManager().deleteUser(player);
                sender.sendMessage(this.plugin.getConfig().getString("messages.chat.left_all_jobs").replace("&", "§"));
                return true;
            }
                return true;

        }

    public void sendAdminAboutInfo(CommandSender sender) {
        PluginDescriptionFile pdf = this.plugin.getServer().getPluginManager().getPlugin("Jobs").getDescription();
        sender.sendMessage("§7§m-------------------------------------------\n" +
                "§e                   §lJobs Admin Panel\n" +
                "§a \n" +
                " §e● §7Author: §6N137\n" +
                " §e● §7Version: §6" + pdf.getVersion() + "\n" +
                " §e➥ §7Help-Command §8- §6/jobsadmin help\n" +
                "§a \n" +
                "§7§m-------------------------------------------\n");
    }

    public void sendAdminHelpInfo(CommandSender sender) {

        StringBuilder help = new StringBuilder();
        help.append("§7§m-------------------------------------------\n" +
                "§e                   §lJobs Admin Panel\n" +
                "§a \n");
          help.append(" §e● §6/jobsadmin hire <player> <job>:\n §e➥ §7Hire <player> into <job>\n");
          help.append(" §e● §6/jobsadmin fire <player> <job>:\n §e➥ §7Fire <player> from <job>\n");
          help.append(" §e● §6/jobsadmin list <player>:\n §e➥ §7Show all jobs <player> has\n");
          help.append(" §e● §6/jobsadmin listjobs\n §e➥ §7Show all jobs\n");
          help.append("§cDebug Commands: §c§l[DANGEROUS]\n");
          help.append(" §e● §6/jobsadmin debug:\n §e➥ §7Toggle debug mode for more verbose output\n");
          help.append(" §e● §6/jobsadmin savedata:\n §e➥ §7Save the current hashmap to data.yml\n");
          help.append(" §e● §6/jobsadmin listconfig:\n §e➥ §7Show the contents of config.yml\n");

        help.append("§a \n§7§m-------------------------------------------\n");
        sender.sendMessage(help.toString());
    }
}
