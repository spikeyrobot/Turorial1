/**
 * 
 */
package me.spikeyrobot;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


/**
 * @author spikeyrobot
 *
 */
public class OwnerJoin {
	
	private static SpikeyCraft plugin;

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public OwnerJoin(String OwnerName, SpikeyCraft plugin) {
		OwnerJoin.plugin = plugin;
		new BukkitRunnable() {
			@Override
			public void run() {
				if(OwnerName!=null) {
					long t = System.currentTimeMillis();
					//while(!OwnerJoin1(OwnerName) && System.currentTimeMillis()-t < 15000);
					while(!OwnerJoin2(OwnerName, plugin) && System.currentTimeMillis()-t < 15000);
				}
			}
		}.runTaskLater(OwnerJoin.plugin, 20);
	}
	public OwnerJoin(Player OwnerPlayer, SpikeyCraft plugin) {
		OwnerJoin.plugin = plugin;
		new BukkitRunnable() {
			@Override
			public void run() {
				if(OwnerPlayer!=null) {
					long t = System.currentTimeMillis();
					//while(!OwnerJoin1(OwnerPlayer) && System.currentTimeMillis()-t < 15000);
					while(!OwnerJoin2(OwnerPlayer, plugin) && System.currentTimeMillis()-t < 15000);
				}
			}
		}.runTaskLater(OwnerJoin.plugin, 20);
	}
	

	public static boolean OwnerJoin1(Player player) {
		if (!player.isOnline()) {
			return false;
		}
		
		Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "op " + player.getName());
		player.sendMessage("§2Opped Owner (" + player.getName() + ")");
		
		player.chat("/trail ender");
		player.sendMessage("§2Changed Trail");
		
		Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "gamemode 1 " + player.getName());
		player.sendMessage("§2Changed Gamemode");
		
		Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "flyspeed fly 2 " + player.getName());
		player.sendMessage("§2Changed Flyspeed");
		
		return true;
	}
	public static boolean OwnerJoin1(String playername) {
		Player player = Bukkit.getPlayer(playername);
		if (player == null) {
		    System.out.println("Error executing OwnerJoin on player " + playername);
		    return false;
		} else if (!player.isOnline()) {
			return false;
		}

		Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "op " + player.getName());
		player.sendMessage("§2Opped Owner (" + player.getName() + ")");
		
		player.chat("/trail ender");
		player.sendMessage("§2Changed Trail");
		
		Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "gamemode 1 " + player.getName());
		player.sendMessage("§2Changed Gamemode");
		
		Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "flyspeed fly 2 " + player.getName());
		player.sendMessage("§2Changed Flyspeed");
		return true;
	}
	
	public static boolean OwnerJoin2(Player player, SpikeyCraft plugin) {
		if (!player.isOnline()) {
			return false;
		}

		List<String> commands = plugin.getConfig().getStringList("Commands");

		Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "op " + player.getName());
		player.sendMessage("§2Opped Owner (" + player.getName() + ")");
		
		for(int i=0; i<commands.size(); i++) {
			player.chat(commands.get(i));
			player.sendMessage("§2Ran Command: " + commands.get(i));
		}
		
		return true;
	}
	public static boolean OwnerJoin2(String playername, SpikeyCraft plugin) {
		Player player = Bukkit.getPlayer(playername);
		if (player == null) {
		    System.out.println("Error executing OwnerJoin on player " + playername);
		    return false;
		} else if (!player.isOnline()) {
			return false;
		}

		List<String> commands = plugin.getConfig().getStringList("Commands");

		Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "op " + player.getName());
		player.sendMessage("§2Opped Owner (" + player.getName() + ")");
		
		for(int i=0; i<commands.size(); i++) {
			player.chat(commands.get(i));
			player.sendMessage("§2Ran Command: " + commands.get(i));
		}
		
		return true;
	}

}
