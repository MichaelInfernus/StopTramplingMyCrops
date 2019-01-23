package com.michael82476.stoptramplingmycrops.listeners;

import com.michael82476.stoptramplingmycrops.StopTramplingMyCrops;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListeners implements Listener {

    private StopTramplingMyCrops plugin;

    public PlayerInteractListeners(StopTramplingMyCrops pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if(plugin.getConfig().getBoolean("stop-trampling-enabled")) {
            if(event.getAction() == Action.PHYSICAL) {
                Player player = event.getPlayer();
                World world = player.getWorld();
                if (plugin.getWorldsList().contains("all") || plugin.getWorldsList().contains(world.getName())) {
                    Block block = event.getClickedBlock();
                    if(block.getType() == Material.FARMLAND) {
                        event.setUseInteractedBlock(Event.Result.DENY);
                        event.setCancelled(true); //cancel the trampling
                        block.setBlockData(block.getBlockData(), true);
                        block.setType(block.getType(), true);
                    }
                }
            }
        }
    }

}
