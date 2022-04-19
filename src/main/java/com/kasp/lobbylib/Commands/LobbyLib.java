package com.kasp.lobbylib.Commands;

import com.kasp.lobbylib.Classes.Command;
import com.kasp.lobbylib.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyLib extends Command {

    private Main main;

    public LobbyLib(Main main) {
        super("lobbylib", new String[]{}, "View all LobbyLib commands", "lobbylib.help");
        this.main = main;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission("lobbylib.help")) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix")) + ChatColor.YELLOW + "All LobbyLib v" + Main.version + " commands:\n" + ChatColor.WHITE +
                        "/setspawn" + ChatColor.GRAY + " - set the spawn location of your lobby\n" + ChatColor.WHITE +
                        "/spawn" + ChatColor.GRAY + " - teleport to the set spawn location of your lobby\n" + ChatColor.WHITE +
                        "/addlobbyitem" + ChatColor.GRAY + " - add a custom lobby item (lobbyitems.yml)\n" + ChatColor.DARK_AQUA +
                        "Please note, most of the plugin features are in config.yml and lobby.yml");
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix")) + ChatColor.RED + "Usage: /lobbylib");
            }
        }
        else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix")) + ChatColor.RED + "You cannot use this command " + ChatColor.GRAY + "(lobbylib.help)");
        }
    }
}
