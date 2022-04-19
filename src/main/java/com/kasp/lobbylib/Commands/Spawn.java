package com.kasp.lobbylib.Commands;

import com.kasp.lobbylib.Classes.Command;
import com.kasp.lobbylib.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Spawn extends Command {

    private Main main;

    public Spawn(Main main) {
        super("spawn", new String[]{"lobby"}, "Teleport to lobby's spawn location", "lobbylib.spawn");
        this.main = main;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission("lobbylib.spawn")) {
            if (args.length == 0) {
                File lobbyYml = new File("plugins/LobbyLib/lobby.yml");
                if (lobbyYml.exists()) {
                    try {
                        FileConfiguration lobbyConfig = YamlConfiguration.loadConfiguration(lobbyYml);
                        String world = lobbyConfig.getString("world");
                        double x = lobbyConfig.getDouble("x");
                        double y = lobbyConfig.getDouble("y");
                        double z = lobbyConfig.getDouble("z");
                        double pitch = lobbyConfig.getDouble("pitch");

                        double yaw = lobbyConfig.getDouble("yaw");
                        System.out.println((float) yaw);
                        System.out.println((float) pitch);
                        player.teleport(new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, (float) pitch));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix")) + ChatColor.RED + "Spawn isn't set up");
                }
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix")) + ChatColor.RED + "Usage: /spawn");
            }
        }
        else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix")) + ChatColor.RED + "You cannot use this command " + ChatColor.GRAY + "(lobbylib.spawn)");
        }
    }
}
