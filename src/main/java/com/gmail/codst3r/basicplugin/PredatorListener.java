package com.gmail.codst3r.basicplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class PredatorListener implements Listener{

	//Instance of BasicPlugin for registering events
	private final BasicPlugin plugin;

	//Constructor used to create an isntance on onEnable() for registering events
	public PredatorListener(BasicPlugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/*
	 * Used to give the predator group an ivisible effect after
	 * standing still for 100 ticks. The potion effect of
	 * invisibility will be granted.
	 */
	@EventHandler
	public void predStandingStill(PlayerVelocityEvent e){
		final Player p = e.getPlayer();

		if(p.hasPermission("basic.predator")){

			double x = e.getVelocity().getX(), 
					y = e.getVelocity().getY(), 
					z = e.getVelocity().getZ();

			if(x == 0 && y == 0 && z == 0){
				//Implement bukkit task scheduling to add a temporary delay for desired effects.
				BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
				scheduler.scheduleSyncDelayedTask(this.plugin, new Runnable() {
					@Override
					public void run() {
						//Potion effect: Invisibility 1 for 10 ticks
						p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10, 1));
					}
				}, 100L);
			}
		}
	}

}
