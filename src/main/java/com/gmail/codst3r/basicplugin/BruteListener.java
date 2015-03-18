package com.gmail.codst3r.basicplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BruteListener implements Listener{
	
	//TODO Implement event register if scheduled programming is required
	//private final BasicPlugin plugin;
	
	//public BruteListener(BasicPlugin plugin) {
	//	this.plugin = plugin;
    //    plugin.getServer().getPluginManager().registerEvents(this, plugin);
	//}

	@EventHandler
	public void superStrength(PlayerInteractEvent e){
		//TODO Code which consumes an item to render the brute strong
	}

}
