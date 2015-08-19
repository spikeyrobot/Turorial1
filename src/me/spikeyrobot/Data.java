package me.spikeyrobot;

//* IMPORTS: JDK/JRE
	import java.io.Serializable;
	import java.sql.Timestamp;
	import java.util.HashMap;
	import java.util.Map;
//* IMPORTS: BUKKIT
	import org.bukkit.entity.EnderDragon;
//* IMPORTS: OTHER
	//* NOT NEEDED

public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Map<String, Timestamp>			players;
	public Map<String, String>			bannedPlayers;
	public Map<String, Timestamp>			lastDeath;
	public Map<String, Map<Integer, Integer>>	currentHealth;
	public Map<String, Map<Integer, Integer>>	hitCount;
	public Map<String, Map<Integer, Map<String, Integer>>>	damage;
	public transient Map<String, Map<EnderDragon, Integer>>	dragons;

	public Data() {
		players		= new HashMap<String, Timestamp>();
		bannedPlayers	= new HashMap<String, String>();
		lastDeath	= new HashMap<String, Timestamp>();
		currentHealth	= new HashMap<String, Map<Integer, Integer>>();
		hitCount	= new HashMap<String, Map<Integer, Integer>>();
		damage		= new HashMap<String, Map<Integer, Map<String, Integer>>>();
		dragons		= new HashMap<String, Map<EnderDragon, Integer>>();
	}
}