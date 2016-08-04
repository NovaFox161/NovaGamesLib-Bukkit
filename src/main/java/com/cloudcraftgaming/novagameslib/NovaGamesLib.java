package com.cloudcraftgaming.novagameslib;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Nova Fox on 8/3/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
public class NovaGamesLib extends JavaPlugin {
    public static NovaGamesLib plugin;

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {
        plugin = this;

        //Do file things

        //Register events and commands

        //Do database things

        //Finally do a few more things.
    }
}