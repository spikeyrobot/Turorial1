package me.spikeyrobot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;


public class SpikeyCraft extends JavaPlugin implements Listener{
	
	public	Map<String, Integer>	worlds	= new HashMap<String, Integer>();
	public	Map<String, Integer> 	xCoords	= new HashMap<String, Integer>();
	public	Map<String, Integer> 	yCoords	= new HashMap<String, Integer>();
	public	Map<String, Integer> 	zCoords	= new HashMap<String, Integer>();
	public static String OwnerName = "spikeyrobot";
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		
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
			if (block.getType().getId() == 122)
				blocks.remove(block);

			if (block.getType().getId() == 7 || block.getType().getId() == 119)
				blocks.remove(block);
			else if (block.getType().getId() == 0 || block.getType().getId() == 50)
				blocks.remove(block);
			else if (block.getType().getId() == 122) {
				blocks.remove(block);
			}
		}

		if (blocks.size() != event.getBlocks().size()) {
			event.setCancelled(true);

			LivingEntity newEntity = (LivingEntity) entity;
			PortalType type = event.getPortalType();
			EntityCreatePortalEvent newEvent;
			newEvent = new EntityCreatePortalEvent(newEntity, blocks, type);

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

		Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("brb") && sender instanceof Player) {
			
			player.sendMessage("§7* " + player.getDisplayName() + " §7will Be Right Back!");
			
			return true;
		} else if(cmd.getName().equalsIgnoreCase("opme") && sender instanceof Player && player.getName().equals(OwnerName)) {
			
			OwnerJoin(player);
			
			return true;
		} else if(cmd.getName().equalsIgnoreCase("opme") && sender instanceof Player) {
			
			player.sendMessage("Unknown command. Type \"/?\" for help.");
			
			return true;
		}
		
		return false;
		
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		if(event.getPlayer().getName().equals(OwnerName)) {
			Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + ChatColor.GREEN + " THE OWNER HAS JOINED THE SERVER! :D");
			OwnerJoin(event.getPlayer());
		}
	}
	/*@EventHandler
	public void onLogout(PlayerLoginEvent event) {
		if(event.getPlayer().getName().equals(OwnerName)) {
			Bukkit.broadcastMessage(ChatColor.RED + "THE OWNER HAS LEFT THE SERVER! :(");
		}
	}*/
	
	public void OwnerJoin(Player player) {
		player.chat("/trail ender");
		player.sendMessage("§2Changed Trail");
		Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "gamemode 1 " + player.getName());
		player.sendMessage("§2Changed Gamemode");
		Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "flyspeed fly 2 " + player.getName());
		player.sendMessage("§2Changed Flyspeed");
	}
}
