package com.kasp.lobbylib.Events;

import com.kasp.lobbylib.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;

public class PlayerMessageEvent implements Listener {

    private Main main;

    public PlayerMessageEvent(Main main) {
        this.main = main;
    }

    FileConfiguration lobbyConfig = YamlConfiguration.loadConfiguration(new File("plugins/LobbyLib/lobby.yml"));

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        if (event.getPlayer().getWorld().getName().equals(lobbyConfig.getString("world"))) {
            if (main.getConfig().getBoolean("Custom-msg-formatting.enabled")) {
                event.setFormat(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Custom-msg-formatting.formatting").replaceAll("\\{%player}", event.getPlayer().getName()).replaceAll("\\{%message}", event.getMessage())));
            }
        }
    }
}
