package com.kasp.lobbylib.Commands;

import com.kasp.lobbylib.Classes.Command;
import com.kasp.lobbylib.Main;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SetSpawn extends Command {

    private Main main;

    public SetSpawn(Main main) {
        super("setspawn", new String[]{"setlobby"}, "Set the lobby's spawn location", "lobbylib.setspawn");
        this.main = main;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission("lobbylib.setspawn")) {
            if (args.length == 0) {
                File spawnconfig = new File("plugins/LobbyLib/lobby.yml");
                FileConfiguration spawnConfig = YamlConfiguration.loadConfiguration(spawnconfig);

                spawnConfig.set("world", player.getWorld().getName());
                spawnConfig.set("x", player.getLocation().getX());
                spawnConfig.set("y", player.getLocation().getY());
                spawnConfig.set("z", player.getLocation().getZ());
                spawnConfig.set("yaw", player.getLocation().getYaw());
                spawnConfig.set("pitch", player.getLocation().getPitch());
                spawnConfig.set("Options.lose-hunger", true);
                spawnConfig.set("Options.break-blocks", true);
                spawnConfig.set("Options.place-blocks", true);
                spawnConfig.set("Options.natural-mob-spawning", true);
                spawnConfig.set("Options.entity-damage", true);
                spawnConfig.set("Options.enable-creeper-explosions", true);
                spawnConfig.set("Options.teleport-on-y-level.enabled", false);
                spawnConfig.set("Options.teleport-on-y-level.y", 1);

                try {spawnConfig.save(spawnconfig);} catch (IOException e) {e.printStackTrace();}

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix") + ChatColor.YELLOW + "You have successfully set the spawn\n") +
                        ChatColor.GRAY + "world: " + player.getWorld().getName() + "\n" +
                        "x: " + player.getLocation().getX() + "\n" +
                        "y: " + player.getLocation().getY() + "\n" +
                        "z: " + player.getLocation().getZ() + "\n" +
                        "yaw: " + player.getLocation().getYaw() + "\n" +
                        "pitch: " + player.getLocation().getPitch());

            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix") + ChatColor.RED + "Usage: /setlobby"));
            }
        }
        else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix")) + ChatColor.RED + "You cannot use this command " + ChatColor.GRAY + "(lobbylib.setspawn)");
        }
    }
}
