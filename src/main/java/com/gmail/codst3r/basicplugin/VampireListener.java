package com.gmail.codst3r.basicplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;
import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;

public class VampireListener implements Listener{

	//Class variables
	private long time;
	private final long dawn = 23031, dusk = 13187;
	private final byte thickPotionData = 32;
	private boolean isDay;
	private boolean msgQueue;

	//Instance of BasicPlugin for registering listerner events
	private final BasicPlugin plugin;

	//Declare DisguiseCraft variables
	DisguiseCraftAPI dc;
	private Disguise bat;
	private Disguise player;

	//Constructor is executed on onEnable() when loading the plugin
	public VampireListener(BasicPlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		dayNightMessage();

		dc = DisguiseCraft.getAPI();
		bat = new Disguise(dc.newEntityID(), "bat", DisguiseType.Bat);
		player = new Disguise(dc.newEntityID(), "player", DisguiseType.Player);
	}

	//Message sent to all vampires at dawn and dusk
	private void dayNightMessage(){
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			@Override
			public void run() {
				time = Bukkit.getServer().getWorld("world").getTime();
				if(time > dawn && time < (dawn + 250)){
					isDay = true;
					msgQueue = true;
				}
				if(time < dusk && time > (dusk - 300)){
					isDay = false;
					msgQueue = true;
				}
				
				if(isDay){
					for(Player vampire : Bukkit.getServer().getOnlinePlayers()){
						if(vampire.hasPermission("basic.vampire")){
							if(vampire.isFlying()){
								dc.changePlayerDisguise(vampire, player);
								vampire.setAllowFlight(false);
								vampire.setFlying(false);
								vampire.sendMessage("It is day, you cannot bat, silly vampire.");
							}
						}
					}
				}

				if(isDay && msgQueue){
					msgQueue = false;
					Bukkit.broadcast("The sun is up! Your damage has been decreased.", "basic.vampire");
					for(Player vampire : Bukkit.getServer().getOnlinePlayers()){
						if(vampire.hasPermission("basic.vampire") && vampire.isFlying()){
							vampire.setFlying(false);
							dc.changePlayerDisguise(vampire, player);
						}
					}
				}else if(!isDay && msgQueue){
					msgQueue = false;
					Bukkit.broadcast("The sun is down! Your damage has been increased.", "basic.vampire");
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

				if(isDay){

					if(itemUsed == Material.AIR) e.setDamage((e.getDamage())/0.5);
					else if(isItemSword(itemUsed)) e.setDamage(e.getDamage()/0.33333);

				}else if(!isDay){

					if(itemUsed == Material.AIR) e.setDamage((e.getDamage())*1.5);
					else if(isItemSword(itemUsed)) e.setDamage((e.getDamage())*1.33333);
				}	//Else leave damage at default values (shouldn't happen)
			}
		}
	}

	//Give vampires 10% lifesteal upon dealing damage to an entity.
	@EventHandler
	public void lifeSteal(EntityDamageByEntityEvent e){

		if(e.getDamager() instanceof Player){
			Player p = (Player) e.getDamager();

			if(p.hasPermission("basic.vampire") && !isDay){
				p.setHealth(p.getHealth() + (e.getDamage()*0.1));
			}
		}
	}

	//Allows for a vampire to transform into a bat when they consume a thick potion
	@SuppressWarnings("deprecation")
	@EventHandler
	public void transform(PlayerItemConsumeEvent e){
		Player p = e.getPlayer();
		ItemStack i = e.getItem();
		if(p.hasPermission("basic.vampire") && !isDay){
			if(i.getType() == Material.POTION && i.getData().getData() == thickPotionData){
				
				if(!p.isFlying()){
					p.setAllowFlight(true);
					dc.changePlayerDisguise(p, bat);
					p.sendMessage("You are now a bat, you can now fly!");
				}
				
			}
		}

	}

	//If a vampire logs in at night and they are still flying, setFlying(false)
	@EventHandler
	public void flightCheck(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(p.hasPermission("basic.vampire")){
			if(isDay && p.isFlying()){
				p.setAllowFlight(false);
				p.setFlying(false);
			}
		}
	}

	/**
	 * Determines if a player has a sword in their hand
	 * 
	 * @param m	Item in the player's hand of type Material
	 * @return	If the player has a sword in their hand, returns true. Else returns false.
	 */
	private boolean isItemSword(Material m){
		if(m == Material.WOOD_SWORD ||
				m == Material.IRON_SWORD ||
				m == Material.DIAMOND_SWORD ||
				m == Material.GOLD_SWORD) 
			return true;
		else return false;
	}

}
