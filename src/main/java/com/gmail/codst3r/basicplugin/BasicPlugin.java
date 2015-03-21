package com.gmail.codst3r.basicplugin;

import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;

public class BasicPlugin extends JavaPlugin{
	
	//TODO Implement permissions for each class types
	
	@Override
	public void onEnable(){
		new PredatorListener(this);
		new VampireListener(this);
		new BruteListener(this);
		getLogger().info(Color.GREEN + "BasicPlugin successfully enabled. (I hope)");
	}
	
	@Override
	public void onDisable(){
		getLogger().info(Color.RED + "BasicPlugin sucessfully disabled");
	}
}