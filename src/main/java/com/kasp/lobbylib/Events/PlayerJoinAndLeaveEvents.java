package com.kasp.lobbylib.Events;

import com.kasp.lobbylib.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlayerJoinAndLeaveEvents implements Listener {

    private Main main;

    public PlayerJoinAndLeaveEvents(Main main) {
        this.main = main;
    }

    FileConfiguration lobbyConfig = YamlConfiguration.loadConfiguration(new File("plugins/LobbyLib/lobby.yml"));

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (Objects.equals(player.getWorld().getName(), lobbyConfig.getString("world"))) {
            if (main.getConfig().getBoolean("Disable-join-msg")) {
                event.setJoinMessage(null);
            }

            if (main.getConfig().getBoolean("Custom-join-msg.enabled")) {
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Custom-join-msg.formatting").replaceAll("\\{%player}", player.getName()).replaceAll("\\{%online}", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))));
            }

            if (main.getConfig().getBoolean("Double-jump.enabled")) {
                player.setAllowFlight(true);
            }

            if (main.getConfig().getBoolean("Tp-on-join")) {
                String world = lobbyConfig.getString("world");
                double x = lobbyConfig.getDouble("x");
                double y = lobbyConfig.getDouble("y");
                double z = lobbyConfig.getDouble("z");
                double pitch = lobbyConfig.getDouble("pitch");
                double yaw = lobbyConfig.getDouble("yaw");

                player.teleport(new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, (float) pitch));
            }

            if (main.getConfig().getBoolean("Lobby-items")) {
                FileConfiguration itemsConfig = YamlConfiguration.loadConfiguration(new File("plugins/LobbyLib/lobbyitems.yml"));

                player.getInventory().clear();

                for (int i = 0; i < 9; i++) {
                    if (itemsConfig.getBoolean("slot" + i + ".enabled")) {
                        ItemStack itemstack = new ItemStack(Material.matchMaterial(itemsConfig.getString("slot" + i + ".item")), itemsConfig.getInt("slot" + i + ".amount"));
                        ItemMeta meta = itemstack.getItemMeta();
                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemsConfig.getString("slot" + i + ".name")));
                        meta.setLore(itemsConfig.getStringList("slot" + i + ".lore").stream().map(string -> ChatColor.translateAlternateColorCodes('&', string)).collect(Collectors.toList()));
                        itemstack.setItemMeta(meta);

                        player.getInventory().setItem(i, itemstack);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (Objects.equals(event.getPlayer().getWorld().getName(), lobbyConfig.getString("world"))) {
            if (main.getConfig().getBoolean("Disable-leave-msg")) {
                event.setQuitMessage(null);
            }

            if (main.getConfig().getBoolean("Custom-leave-msg.enabled")) {
                event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Custom-leave-msg.formatting").replaceAll("\\{%player}", event.getPlayer().getName()).replaceAll("\\{%online}", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))));
            }
        }
    }
}
