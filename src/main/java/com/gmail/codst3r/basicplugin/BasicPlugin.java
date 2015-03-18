package com.gmail.codst3r.basicplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class BasicPlugin extends JavaPlugin{
	
	//TODO Implement permissions for each class types
	
	@Override
	public void onEnable(){
		//Register event classes
		new PredatorListener(this);
		//TODO implement simplified event register if scheduler programming is required
		//new BruteListener(this);
		getServer().getPluginManager().registerEvents(new BruteListener(), this);
		getServer().getPluginManager().registerEvents(new VampireListener(), this);
		
		getLogger().info("BasicPlugin Enabled.");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("BasicPlugin Disabled");
	}

}