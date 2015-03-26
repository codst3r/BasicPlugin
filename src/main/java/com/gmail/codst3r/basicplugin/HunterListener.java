package com.gmail.codst3r.basicplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HunterListener implements Listener {

	@SuppressWarnings("unused")
	private final BasicPlugin plugin;

	public HunterListener(BasicPlugin plugin){
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public void onHunterSprint(PlayerToggleSprintEvent e){

		if(e.getPlayer().hasPermission("basic.hunter")){
			Player p = e.getPlayer();
			if(p.getFoodLevel() > 6){
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 3));
				p.setFoodLevel(p.getFoodLevel() - 4);
			}
		}

	}

}
