package com.kasp.lobbylib.Events;

import com.kasp.lobbylib.Main;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;

public class LobbyEvents implements Listener {

    private Main main;

    public LobbyEvents(Main main) {
        this.main = main;
    }

    FileConfiguration lobbyConfig = YamlConfiguration.loadConfiguration(new File("plugins/LobbyLib/lobby.yml"));

    @EventHandler
    public void onHungerLoss(FoodLevelChangeEvent event) {
        if (event.getEntity().getWorld().getName().equals(lobbyConfig.getString("world"))) {
            if (!lobbyConfig.getBoolean("Options.lose-hunger")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getWorld().getName().equals(lobbyConfig.getString("world"))) {
            if (!lobbyConfig.getBoolean("Options.break-blocks")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix")) + ChatColor.RED + "You cannot break blocks at spawn");
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getWorld().getName().equals(lobbyConfig.getString("world"))) {
            if (!lobbyConfig.getBoolean("Options.place-blocks")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix")) + ChatColor.RED + "You cannot place blocks at spawn");
            }
        }
    }

    @EventHandler
    public void onMobSpawning(CreatureSpawnEvent event) {
        if (event.getEntity().getWorld().getName().equals(lobbyConfig.getString("world"))) {
            if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
                if (!lobbyConfig.getBoolean("Options.natural-mob-spawning")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity().getWorld().getName().equals(lobbyConfig.getString("world"))) {
            if (!lobbyConfig.getBoolean("Options.entity-damage")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerFall(PlayerMoveEvent event) {
        if (event.getPlayer().getWorld().getName().equals(lobbyConfig.getString("world"))) {
            if (lobbyConfig.getBoolean("Options.teleport-on-y-level.enabled")) {
                if (event.getPlayer().getLocation().getY() <= lobbyConfig.getInt("Options.teleport-on-y-level.y")) {
                    String world = (String) lobbyConfig.get("world");
                    double x = lobbyConfig.getDouble("x");
                    double y = lobbyConfig.getDouble("y");
                    double z = lobbyConfig.getDouble("z");
                    double pitch = lobbyConfig.getDouble("pitch");
                    double yaw = lobbyConfig.getDouble("yaw");
                    event.getPlayer().teleport(new Location(Bukkit.getWorld(world), x, y, z, (float)yaw, (float)pitch));
                }
            }

            if (!main.getConfig().getBoolean("Double-jump.allow-in-air")) {
                Location loc = event.getPlayer().getLocation();
                loc.setY(loc.getY() - 1);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE && loc.getBlock().getType() != Material.AIR && !loc.getBlock().isLiquid()) {
                    if (main.getConfig().getBoolean("Double-jump.enabled")) {
                        event.getPlayer().setAllowFlight(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (event.getEntity().getWorld().getName().equals(lobbyConfig.getString("world"))) {
            if (!lobbyConfig.getBoolean("Options.enable-creeper-explosions")) {
                event.setCancelled(true);
            }
        }
    }
}
