package me.spikeyrobot;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnOnJoin {
	
	private static SpikeyCraft plugin;
	
	public SpawnOnJoin(PlayerLoginEvent event, SpikeyCraft plugin) {
		SpawnOnJoin.plugin = plugin;
		new BukkitRunnable() {
			@Override
			public void run() {
				if(event.getPlayer()!=null) {
					long t = System.currentTimeMillis();
					while(!event.getPlayer().isOnline() && System.currentTimeMillis()-t < 15000);
					go(event,plugin);
				}
			}
		}.runTaskLater(SpawnOnJoin.plugin, 20);
	}
	
	public void go(PlayerLoginEvent event, SpikeyCraft plugin) {

		World world = Bukkit.getWorld(plugin.getConfig().getString("SpawnWorld"));
		
		@SuppressWarnings("unchecked")
		List<Double> CoordList = (List<Double>) plugin.getConfig().getList("SpawnCoord");
		
		double x = CoordList.get(0);
		double y = CoordList.get(1);
		double z = CoordList.get(2);
		plugin.getLogger().info("Teleporting " + event.getPlayer().getDisplayName()+ " to " + world.getName() + ": " + x +", " + y + ", " + z);
		Location location = new Location(world, x, y, z);
		event.getPlayer().teleport(location);
	}
}