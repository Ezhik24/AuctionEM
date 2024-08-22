package org.ezhik.eMAuction;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.ezhik.eMAuction.commands.AhCMD;
import org.ezhik.eMAuction.events.ClickEvent;

public final class EMAuction extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Пожалуйста,подпишитесь на мой тг канал: https://t.me/ezhichek11");
        System.out.println("Please,subscribe for my telegram channel: https://t.me/ezhichek11");
        getCommand("ah").setExecutor(new AhCMD());
        Bukkit.getServer().getPluginManager().registerEvents(new ClickEvent(), this);
    }

    @Override
    public void onDisable() {
    }
}
