package com.gmail.codst3r.basicplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class BasicPlugin extends JavaPlugin implements Listener{

	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		new PredatorListener(this);
		new VampireListener(this);
		new BruteListener(this);
		new HunterListener(this);
		getLogger().info("BasicPlugin successfully enabled. (I hope)");
	}

	@Override
	public void onDisable(){
		getLogger().info("BasicPlugin sucessfully disabled");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(sender instanceof Player){
			Player p = (Player) sender;
			PermissionUser user = PermissionsEx.getUser(p);
			if(cmd.getName().equalsIgnoreCase("join")){
				if(args.length < 1) return false;
				else if(args[0].equalsIgnoreCase("brute")){
					if(!hasGroup(p)){
						p.sendMessage("You have joined the brutes");
						user.addPermission("basic.brute");
					}else p.sendMessage("You are in a class already!");
				}
				else if(args[0].equalsIgnoreCase("vampire")){
					if(!hasGroup(p)){
						p.sendMessage("You have joined the vampires");
						user.addPermission("basic.vampire");
					}else p.sendMessage("You are in a class already!");
				}
				else if(args[0].equalsIgnoreCase("hunter")){
					if(!hasGroup(p)){
						p.sendMessage("You have joined the hunters");
						user.addPermission("basic.hunter");
					}else p.sendMessage("You are in a class already!");
				}
				else if(args[0].equalsIgnoreCase("predator")){
					if(!hasGroup(p)){
						p.sendMessage("You have joined the predators");
						user.addPermission("basic.predator");
					}else p.sendMessage("You are in a class already!");
				}else return false;
			}else return false;
		}
		return false;
	}

	private boolean hasGroup(Player p){
		if(p.hasPermission("basic.vampire") ||
				p.hasPermission("basic.hunter") ||
				p.hasPermission("basic.predator") ||
				p.hasPermission("basic.brute")) return true;
		else return false;
	}
}