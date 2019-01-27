package com.michael82476.stoptramplingmycrops.commands;

import com.michael82476.stoptramplingmycrops.StopTramplingMyCrops;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@SuppressWarnings("StringConcatenationInLoop")
public class TrampleCommand implements CommandExecutor {

    private StopTramplingMyCrops plugin;

    public TrampleCommand(StopTramplingMyCrops pl) {
        this.plugin = pl;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            return false;
        }
        if(args[0].equalsIgnoreCase("reload"))
        {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(!player.hasPermission("trample.admin.reload") && !player.hasPermission("trample.admin.*")) {
                    return true;
                }
            }
            boolean success = plugin.loadConfig();
            if(success)
                sender.sendMessage(plugin.prefix + ChatColor.GREEN + "Successfully reloaded config!");
            else
                sender.sendMessage(plugin.prefix + ChatColor.RED + "An error occurred while reloading config.");
            return true;
        }
        else if(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(!player.hasPermission("trample.admin.modify") && !player.hasPermission("trample.admin.*")) {
                    return true;
                }
            }
            if(args[0].equalsIgnoreCase("add")) {
                if(args.length >= 2) {//they put a world to add
                    String worldToAdd = args[1];
                    if(args.length > 2) {
                        for(int i = 2; i < args.length; i++)
                            worldToAdd += " " + args[i];
                    }
                    List<String> worldsList = plugin.getWorldsList();
                    if(worldsList.contains(worldToAdd)) {
                        sender.sendMessage(plugin.prefix + ChatColor.YELLOW + "World '" + worldToAdd + "' is already added!");
                        return true;
                    }
                    else {
                        worldsList.add(worldToAdd);
                        plugin.getConfig().set("trample-less-worlds", worldsList);
                        plugin.saveConfig();
                        plugin.loadConfig();
                        sender.sendMessage(plugin.prefix + ChatColor.GREEN + "Successfully added '" + worldToAdd + "'!");
                        return true;
                    }
                }
            }
            else if(args[0].equalsIgnoreCase("remove")) {
                if(args.length >= 2) {//they put a world to add
                    String worldToRemove = args[1];
                    if(args.length > 2) {
                        for(int i = 2; i < args.length; i++)
                            worldToRemove += " " + args[i];
                    }
                    List<String> worldsList = plugin.getWorldsList();
                    if(!worldsList.contains(worldToRemove)) {
                        sender.sendMessage(plugin.prefix + ChatColor.RED + "World '" + worldToRemove + "' is not added.");
                        return true;
                    }
                    else {
                        for(int i = 0; i < worldsList.size(); i++)
                        {
                            if(worldsList.get(i).equals(worldToRemove)) {
                                worldsList.remove(i);
                                break;
                            }
                        }
                        plugin.getConfig().set("trample-less-worlds", worldsList);
                        plugin.saveConfig();
                        plugin.loadConfig();
                        sender.sendMessage(plugin.prefix + ChatColor.GREEN + "Successfully removed '" + worldToRemove + "'!");
                        return true;
                    }
                }
            }
        }
        return true;
    }

}
