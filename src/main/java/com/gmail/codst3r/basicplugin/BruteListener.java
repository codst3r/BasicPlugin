package com.gmail.codst3r.basicplugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BruteListener implements Listener{

	@SuppressWarnings("unused")	//plugin is currently only implemented to register events
	private final BasicPlugin plugin;

	public BruteListener(BasicPlugin plugin) {
		this.plugin = plugin;
	    plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/*
	 * Brutes are naturally strong and can do bonus damage when using fists.
	 * naturalDamage checks to see if a player has damaged anything, if
	 * said player has nothing in their hand, it will increase the damage
	 * dealt to 1.5 (instead of 1.0).
	 */
	@EventHandler
	public void naturalDamage(EntityDamageByEntityEvent e){

		if(e.getDamager() instanceof Player){

			Player p = (Player) e.getDamager();

			if(p.hasPermission("basic.brute")){

				Material itemInHand = p.getItemInHand().getType();
				if(itemInHand == Material.AIR) e.setDamage(1.5);
			}
		}
	}

	//TODO Add an event handler to allow brutes to deal bonus damage upon consuming an item

}
