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
import java.util.Arrays;

public class AddLobbyItem extends Command {

    private Main main;

    public AddLobbyItem(Main main) {
        super("addlobbyitem", new String[]{}, "Add a lobby item", "lobbylib.addlobbyitem");
        this.main = main;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission("lobbylib.addlobbyitem")) {
            if (args.length == 0) {
                FileConfiguration itemsConfig = YamlConfiguration.loadConfiguration(new File("plugins/LobbyLib/lobbyitems.yml"));

                int slot = player.getInventory().getHeldItemSlot();

                itemsConfig.createSection("slot0");
                itemsConfig.set("slot0.enabled", false);
                itemsConfig.createSection("slot1");
                itemsConfig.set("slot1.enabled", false);
                itemsConfig.createSection("slot2");
                itemsConfig.set("slot2.enabled", false);
                itemsConfig.createSection("slot3");
                itemsConfig.set("slot3.enabled", false);
                itemsConfig.createSection("slot4");
                itemsConfig.set("slot4.enabled", false);
                itemsConfig.createSection("slot5");
                itemsConfig.set("slot5.enabled", false);
                itemsConfig.createSection("slot6");
                itemsConfig.set("slot6.enabled", false);
                itemsConfig.createSection("slot7");
                itemsConfig.set("slot7.enabled", false);
                itemsConfig.createSection("slot8");
                itemsConfig.set("slot8.enabled", false);

                itemsConfig.set("slot" + slot + ".enabled", true);
                itemsConfig.set("slot" + slot + ".item", player.getItemInHand().getType().toString());
                itemsConfig.set("slot" + slot + ".amount", player.getItemInHand().getAmount());
                itemsConfig.set("slot" + slot + ".name", "name");
                itemsConfig.set("slot" + slot + ".lore", Arrays.asList("line1", "line2"));
                itemsConfig.set("slot" + slot + ".command", "lobbylib");

                try {itemsConfig.save(new File("plugins/LobbyLib/lobbyitems.yml"));} catch (IOException e) {e.printStackTrace();}

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix")) + ChatColor.GREEN + "Successfully added a lobby item (lobbyitems.yml)");
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix")) + ChatColor.RED + "Usage: /addlobbyitem");
            }
        }
        else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix")) + ChatColor.RED + "You cannot use this command " + ChatColor.GRAY + ChatColor.ITALIC + "(lobbylib.addlobbyitem)");
        }
    }
}
