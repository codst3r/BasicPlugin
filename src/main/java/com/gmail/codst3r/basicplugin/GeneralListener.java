package com.gmail.codst3r.basicplugin;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GeneralListener implements Listener{

	/*
	 * Basic listener/handler example
	 * 
	 * You have to browse the javadocs in order to find out what all the listeners are
	 * But this is only for events that trigger others.
	 * 
	 * If you want something to be defaulted and not action-based, that's a different
	 * story. I'll create a class dedicated to a permanent change to the server.
	 */
	@EventHandler
	public void playerHitPlayerEvent(EntityDamageByEntityEvent e){
		//In this instance, EntityDamageByEntityEvent is the listener name
		//playerHitPlayerEvent is simply the name I decided to give the method
		
		Entity damager = e.getDamager();	//get the damager
		if(damager instanceof Player){		//if the entity is an instanceof Player
			Player p = (Player) damager;	//cast the entity 'damager' to p
			if(p.getItemInHand().getType() == Material.BAKED_POTATO){	//self explanatory
				e.setDamage(20d);		//also self explanatory
			}
		}
		
	}	//SERIOUSLY, GRAB A FUCKING BAKED POTATO AND SLAP ANOTHER ENTITY!!!
	
}
