package me.spikeyrobot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class SpikeyCraft extends JavaPlugin implements Listener{
	
	public	Map<String, Integer>	worlds	= new HashMap<String, Integer>();
	public	Map<String, Integer> 	xCoords	= new HashMap<String, Integer>();
	public	Map<String, Integer> 	yCoords	= new HashMap<String, Integer>();
	public	Map<String, Integer> 	zCoords	= new HashMap<String, Integer>();
	public static String OwnerName = "spikeyrobot";
	
	public ArrayList<String> BRBList = new ArrayList<String>();
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		
		OwnerName = getConfig().getString("OPMEOwner");
		
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public void reload() {
		
		saveDefaultConfig();
		
		OwnerName = getConfig().getString("OPMEOwner");
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityCreatePortal(EntityCreatePortalEvent event) {
		if(getConfig().getBoolean("PreventEnderPortal")) {
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
		}
		return;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

		Player player = (Player) sender;
		
		//toggle brb off
		if(findBRB(player.getName()) != -1 && getConfig().getBoolean("EnableBRB") && !cmd.getName().equalsIgnoreCase("brb")) {
			brb(player);
		}
		
		if(cmd.getName().equalsIgnoreCase("brb") && sender instanceof Player && getConfig().getBoolean("EnableBRB")) {
			return brb(player);
		} else if(cmd.getName().equalsIgnoreCase("opme") && sender instanceof Player && player.getName().equals(OwnerName) && getConfig().getString("EnableOPME").equalsIgnoreCase("true")) {
			new OwnerJoin(OwnerName, this);
			return true;
		} else if(
				(
						cmd.getName().equalsIgnoreCase("opme")
					 || cmd.getName().equalsIgnoreCase("brb")
				) 
				&& sender instanceof Player
				) {
			
			player.sendMessage("Unknown command. Type \"/?\" for help.");
			
			
			return true;
		}
		
		return false;
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		if(findBRB(event.getPlayer().getName()) != -1) {
			brb(event.getPlayer());
		}
		return;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		if(event.getFrom() != event.getTo()) {
			
			if(getConfig().getBoolean("EnableBRB") && findBRB(event.getPlayer().getName()) != -1) {
				brb(event.getPlayer());
			}
			
		}
	}
	
	/**
	 * @param player
	 */
	private boolean brb(Player player) {
		
		if(findBRB(player.getName()) == -1) {
			Bukkit.getServer().broadcastMessage("§7* " + player.getDisplayName() + " §7" + getConfig().getString("BRBMessage"));
			BRBList.add(player.getName());
		} else {
			Bukkit.getServer().broadcastMessage("§7* " + player.getDisplayName() + " §7" + getConfig().getString("BackMessage"));
			BRBList.remove(findBRB(player.getName()));
		}
		return true;
	}
	/**
	 * @return
	 */
	private int findBRB(String playername) {
		for(int i=0; i<BRBList.size(); i++) {
			if(playername.equals(BRBList.get(i))) {
				return i;
			}
		}
		return -1;
	}

	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event) {
		if(event.getPlayer().getName().equals(OwnerName)) {
			
			if(getConfig().getBoolean("AnnounceOwnerJoin"))
				Bukkit.broadcastMessage(ChatColor.GREEN + " " + getConfig().getString("AnnounceJoinMessage"));
			
			if(getConfig().getBoolean("OPMEOnJoin"))
				new OwnerJoin(OwnerName, this);
		}
	}
	@EventHandler
	public void onPlayerLogout(PlayerLoginEvent event) {
		if(event.getPlayer().getName().equals(OwnerName) && getConfig().getBoolean("AnnounceOwnerLeave"))
			Bukkit.broadcastMessage(ChatColor.RED + " " + getConfig().getString("AnnounceLeaveMessage"));
	}
}
