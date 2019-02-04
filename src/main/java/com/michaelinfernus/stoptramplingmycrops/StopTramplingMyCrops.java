package com.michaelinfernus.stoptramplingmycrops;

import com.michaelinfernus.stoptramplingmycrops.commands.TrampleCommand;
import com.michaelinfernus.stoptramplingmycrops.listeners.PlayerInteractListeners;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class StopTramplingMyCrops extends JavaPlugin {

    public String prefix;

    private List<String> worldsList;

    @Override
    public void onEnable() {
        // Plugin startup logic

        loadConfig();

        enableListeners();

        loadCommands();

        this.getServer().getConsoleSender().sendMessage(prefix + "Enabled StopTramplingMyCrops - Made By MichaelInfernus");
    }

    private void enableListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListeners(this), this);
    }

    private void loadCommands() {
        this.getCommand("stmc").setExecutor(new TrampleCommand(this));
    }

    public boolean loadConfig() {
        try {
            if(!this.getDataFolder().exists())
                this.getDataFolder().mkdirs();
            this.saveDefaultConfig();
            this.reloadConfig();
            this.prefix = ChatColor.translateAlternateColorCodes('&',
                    this.getConfig().getString("prefix", "&7[&eStopTramplingMyCrops&7] &f"));
            worldsList = this.getConfig().getStringList("trample-less-worlds");
            if(worldsList == null)
                return false; //something didn't go right. It shouldn't be null
        }
        catch(Exception e) {
            this.getServer().getConsoleSender().sendMessage(prefix + ChatColor.RED + "Failed to load config! Does it exist?");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public List<String> getWorldsList() {
        return this.worldsList;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
