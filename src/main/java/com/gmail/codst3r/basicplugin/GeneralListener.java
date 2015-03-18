package com.gmail.codst3r.basicplugin;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GeneralListener implements Listener{
	private boolean bool = false;


	@EventHandler
	public void movement(PlayerVelocityEvent e){
		double x = e.getVelocity().getX();
		double y = e.getVelocity().getY();
		double z = e.getVelocity().getZ();
		if(x == 0 & y == 0 & z== 0){
			Player p = e.getPlayer();
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 210, 1));
			if (!bool) {
				bool = true;
			}

		}

	}
	@EventHandler
	public void stealthDamage(EntityDamageByEntityEvent e){
		Entity p = e.getDamager();
		if (p instanceof Player){
			if (bool){
				e.setDamage(12);
				bool = false;
				
			}

		}
	}
}
