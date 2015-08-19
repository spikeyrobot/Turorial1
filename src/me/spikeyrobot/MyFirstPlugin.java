package me.spikeyrobot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.PortalType;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MyFirstPlugin extends JavaPlugin implements Listener{
	//private MyFirstPlugin plugin;
	public Configuration config;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();

		if (!(entity instanceof EnderDragon))
			return;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityCreatePortal(EntityCreatePortalEvent event) {
		Entity entity = event.getEntity();
		
		if (!(entity instanceof EnderDragon))
			return;
		
		getLogger().info("Removing EnderDragon Portal");
		
		List<BlockState> blocks = new ArrayList<BlockState>(event.getBlocks());

		for (BlockState block : event.getBlocks()) {
			if (block.getType().getId() == 122 /*&& !plugin.config.spawnEgg*/)
				blocks.remove(block);

			/*if (plugin.config.spawnPortal)
				continue;*/

			if (block.getType().getId() == 7 || block.getType().getId() == 119)
				blocks.remove(block);
			else if (block.getType().getId() == 0 || block.getType().getId() == 50)
				blocks.remove(block);
			else if (block.getType().getId() == 122 /*&& plugin.config.spawnEgg*/) {
				blocks.remove(block);

				//Location location = entity.getLocation();
				//ItemStack item = new ItemStack(block.getType());

				//entity.getWorld().dropItemNaturally(location, item);
			}
		}

		if (blocks.size() != event.getBlocks().size()) {
			event.setCancelled(true);

			LivingEntity newEntity = (LivingEntity) entity;
			PortalType type = event.getPortalType();
			EntityCreatePortalEvent newEvent;
			newEvent = new EntityCreatePortalEvent(newEntity, blocks, type);

			//plugin.getServer().getPluginManager().callEvent(newEvent);

			if (!newEvent.isCancelled()) {
				for(BlockState blockState : blocks) {
					int id		= blockState.getTypeId();
					byte data	= blockState.getRawData();
					blockState.getBlock().setTypeIdAndData(id, data, false);
				}
			}
		}
		return;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if(cmd.getName().equalsIgnoreCase("brb") && sender instanceof Player) {
			Player player = (Player) sender;
			
			player.sendMessage(player.getName() + " says they'll Be Right Back!");
			
			return true;
		}
		
		return false;
		
	}
}
