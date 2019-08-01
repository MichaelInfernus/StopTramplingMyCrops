package com.michaelinfernus.stoptramplingmycrops.listeners;

import com.michaelinfernus.stoptramplingmycrops.StopTramplingMyCrops;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

public class EntityInteractListeners implements Listener {

    private StopTramplingMyCrops plugin;

    public EntityInteractListeners(StopTramplingMyCrops pl) {
        this.plugin = pl;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteractEvent(EntityInteractEvent event) {
        if(plugin.getConfig().getBoolean("stop-trampling-enabled") && plugin.getConfig().getBoolean("stop-entity-trample")) {
            Block block = event.getBlock();
            World world = block.getWorld();
            if (plugin.getWorldsList().contains("all") || plugin.getWorldsList().contains(world.getName())) {
                if(block.getType() == Material.FARMLAND) {
                    event.setCancelled(true); //cancel the trampling
                    block.setBlockData(block.getBlockData(), true);
                    block.setType(block.getType(), true);
                }
            }
        }
    }

}
