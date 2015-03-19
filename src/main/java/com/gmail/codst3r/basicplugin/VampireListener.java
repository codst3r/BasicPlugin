package com.gmail.codst3r.basicplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class VampireListener implements Listener{
	
	private long time;
	private final long dawn = 23031, dusk = 13187;
	
	private final BasicPlugin plugin;

	public VampireListener(BasicPlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Send a message to all Vampires if the day cycles into dawn or dusk.
	 * 
	 * Note: This can be buggy since it relies on a consistent tick rate.
	 * If the server skips ticks to keep up with system time, there is a
	 * possibility that a message may not get broadcast to the Vampires.
	 */
	public void dayNightMessage(){

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			@Override
			public void run() {
				time = Bukkit.getServer().getWorld("world").getTime();
				
				if(time > dawn && time < dawn + 15){
					//TODO get all online players and parse to an array to check players' permissions
				}
			}
		}, 0L, 200L);//Run immediately upon execution, run every 200 ticks (10 seconds).
	}
	
	/*
	 * Reduces a vampire's damage if the sun is up and
	 * increases their damage at dawn, dusk, or dark.
	 */
	@EventHandler
	public void reduceDamage(EntityDamageByEntityEvent e){

		if(e.getDamager() instanceof Player){

			Player p = (Player) e.getDamager();

			if(p.hasPermission("basic.vampire")){

				Material itemUsed = p.getItemInHand().getType();
				long time = p.getWorld().getTime();

				if(time < dusk && time > dawn){	//Day

					if(itemUsed == Material.AIR) e.setDamage((e.getDamage())/0.5);
					else if(isItemSword(itemUsed)) e.setDamage(e.getDamage()/1.33333);

				}else if(time >= dusk && time <= dawn){	//Night

					if(itemUsed == Material.AIR) e.setDamage((e.getDamage())*1.5);
					else if(isItemSword(itemUsed)) e.setDamage((e.getDamage())*1.33333);
				}	//Else leave damage at default values (shouldn't happen)
			}
		}
	}

	private boolean isItemSword(Material m){
		if(m == Material.WOOD_SWORD ||
				m == Material.IRON_SWORD ||
				m == Material.DIAMOND_SWORD ||
				m == Material.GOLD_SWORD) 
			return true;
		else return false;
	}

}
