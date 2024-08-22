package org.ezhik.eMAuction;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.ezhik.eMAuction.commands.AhCMD;
import org.ezhik.eMAuction.events.ClickEvent;

public final class EMAuction extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("ah").setExecutor(new AhCMD());
        Bukkit.getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
