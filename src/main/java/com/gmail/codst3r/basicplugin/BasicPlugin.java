package com.gmail.codst3r.basicplugin;

import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;

public class BasicPlugin extends JavaPlugin{
	
	//TODO Implement permissions for each class types
	
	@Override
	public void onEnable(){
		new PredatorListener(this);
		VampireListener vSetup = new VampireListener(this);	//Testing this to attempt functionality of dayNightMessage().
		vSetup.dayNightMessage();	//Create instance of the bukkit scheduler
		new BruteListener(this);
		getLogger().info(Color.GREEN + "BasicPlugin successfully enabled. (I hope)");
	}
	
	@Override
	public void onDisable(){
		getLogger().info(Color.RED + "BasicPlugin sucessfully disabled");
	}
}