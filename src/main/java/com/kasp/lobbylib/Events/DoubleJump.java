package com.kasp.lobbylib.Events;

import com.kasp.lobbylib.Main;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.io.File;
import java.util.Objects;

public class DoubleJump implements Listener {

    private Main main;

    public DoubleJump(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
            FileConfiguration lobbyConfig = YamlConfiguration.loadConfiguration(new File("plugins/LobbyLib/lobby.yml"));
            if (Objects.equals(player.getWorld().getName(), lobbyConfig.getString("world"))) {
                player.setFlying(false);
                player.setVelocity(player.getLocation().getDirection().multiply(main.getConfig().getDouble("Double-jump.launch-speed")).setY(main.getConfig().getDouble("Double-jump.launch-height")));
                if (!main.getConfig().getBoolean("Double-jump.allow-in-air")) {
                    player.setAllowFlight(false);
                }
            }
        }
    }
}
