package com.gmail.codst3r.basicplugin;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitScheduler;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;
import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;

public class VampireListener implements Listener{

	private long time;
	private final long dawn = 23031, dusk = 13187;
	private boolean isDay;

	private final BasicPlugin plugin;

	DisguiseCraftAPI dc;
	private Disguise bat;
	private Disguise player;

	public VampireListener(BasicPlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		dayNightMessage();

		dc = DisguiseCraft.getAPI();
		bat = new Disguise(dc.newEntityID(), "bat", DisguiseType.Bat);
		player = new Disguise(dc.newEntityID(), "player", DisguiseType.Player);
	}

	private void dayNightMessage(){
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			@Override
			public void run() {
				time = Bukkit.getServer().getWorld("world").getTime();

				if(time > dawn && time < dawn + 300){
					Bukkit.broadcast(Color.ORANGE + "The sun is up! Your damage has been decreased.", "basic.vampire");
					isDay = true;
				}

				if(time < dusk && time > dusk - 300){
					Bukkit.broadcast(Color.LIME + "The sun is down! Your damage has been increased.", "basic.vampire");
					isDay = false;
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
					else if(isItemSword(itemUsed)) e.setDamage(e.getDamage()/0.33333);

				}else if(time >= dusk && time <= dawn){	//Night

					if(itemUsed == Material.AIR) e.setDamage((e.getDamage())*1.5);
					else if(isItemSword(itemUsed)) e.setDamage((e.getDamage())*1.33333);
				}	//Else leave damage at default values (shouldn't happen)
			}
		}
	}

	@EventHandler
	public void lifeSteal(EntityDamageByEntityEvent e){

		if(e.getDamager() instanceof Player){
			Player p = (Player) e.getDamager();

			if(p.hasPermission("basic.vampire") && !isDay){
				p.setHealth(p.getHealth() + (e.getDamage()*0.1));
			}
		}
	}

	@EventHandler
	public void transform(PlayerToggleSneakEvent e){

		Player p = e.getPlayer();
		if(p.hasPermission("basic.vampire") && !isDay){
			if(p.isFlying()){
				p.setFlying(true);
				dc.changePlayerDisguise(p, bat);
			}else dc.changePlayerDisguise(p, player);
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
