package com.gmail.codst3r.basicplugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class VampireListener implements Listener{
	
	/*
	 * Reduces a vampire's damage if the sun is up and
	 * increases their damage at dawn, dusk, or dark.
	 */
	@EventHandler
	public void reduceDamage(EntityDamageByEntityEvent e){
		
		if(e.getDamager() instanceof Player){
			
			Player p = (Player) e.getDamager();
			Material itemUsed = p.getItemInHand().getType();
			long time = p.getWorld().getTime();
			
			if(time < 13187 && time > 23031){	//Day
				
				if(itemUsed == Material.AIR) e.setDamage((e.getDamage())/0.5);
				else if(isItemSword(itemUsed)) e.setDamage(e.getDamage()/1.33333);
				
			}else if(time >= 13187 && time <= 23031){	//Night
				
				if(itemUsed == Material.AIR) e.setDamage((e.getDamage())*1.5);
				else if(isItemSword(itemUsed)) e.setDamage((e.getDamage())*1.33333);
			}	//Else leave damage at default values (shouldn't happen)
		}
	}
	
	private boolean isItemSword(Material m){
		if(m == Material.WOOD_SWORD ||
				m == Material.IRON_SWORD ||
				m == Material.GOLD_SWORD ||
				m == Material.DIAMOND_SWORD) 
			return true;
		else return false;
	}

}
