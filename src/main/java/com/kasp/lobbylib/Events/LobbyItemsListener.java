package com.kasp.lobbylib.Events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.util.Objects;

public class LobbyItemsListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        FileConfiguration lobbyConfig = YamlConfiguration.loadConfiguration(new File("plugins/LobbyLib/lobby.yml"));
        if (Objects.equals(player.getWorld().getName(), lobbyConfig.getString("world"))) {
            FileConfiguration itemsConfig = YamlConfiguration.loadConfiguration(new File("plugins/LobbyLib/lobbyitems.yml"));

            int heldItemSlot = event.getPlayer().getInventory().getHeldItemSlot();

            if (Objects.equals(itemsConfig.getString("slot" + heldItemSlot + ".item"), event.getPlayer().getItemInHand().getType().toString())) {
                if (itemsConfig.getBoolean("slot" + heldItemSlot + ".enabled")) {
                    if (!Objects.equals(itemsConfig.getString("slot" + heldItemSlot + ".command"), null)) {
                        player.performCommand(itemsConfig.getString("slot" + heldItemSlot + ".command"));
                    }
                }
            }
        }
    }
}
