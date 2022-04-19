package com.kasp.lobbylib;

import com.kasp.lobbylib.Commands.AddLobbyItem;
import com.kasp.lobbylib.Commands.LobbyLib;
import com.kasp.lobbylib.Commands.SetSpawn;
import com.kasp.lobbylib.Commands.Spawn;
import com.kasp.lobbylib.Events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static String version = "1.0";

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        new SetSpawn(this);
        new Spawn(this);
        new LobbyLib(this);
        new AddLobbyItem(this);

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix")) + ChatColor.YELLOW + "Enabled " + ChatColor.DARK_AQUA + "(v" + version + ")");
        Bukkit.getPluginManager().registerEvents(new LobbyEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinAndLeaveEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new LobbyItemsListener(), this);
        Bukkit.getPluginManager().registerEvents(new DoubleJump(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMessageEvent(this), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix")) + ChatColor.YELLOW + "Disabled");
    }
}
