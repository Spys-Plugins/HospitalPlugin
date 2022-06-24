package dev.supercoolspy.hospital;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HospitalCommand implements CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            assert player != null;
            if(player.hasPermission("hospital.use")) {
                if(args.length == 0) {
                    player.sendMessage(ChatColor.GOLD + "Hospital by super_cool_spy");
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Help Menu");
                    player.sendMessage("§aUsage: /hospital setRespawn");
                    player.sendMessage("§aUsage: /hospital setSpawn");
                    player.sendMessage("§aUsage: /hospital reload");
                    player.sendMessage("§aUsage: /hospital cooldown <seconds>");
                }
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("setRespawn")) {
                        Hospital plugin = Hospital.getPlugin(Hospital.class);
                        Location loc = player.getLocation();
                        plugin.getConfig().set("Hospital.World" , Objects.requireNonNull(loc.getWorld()).getName());
                        plugin.getConfig().set("Hospital.X" , loc.getX());
                        plugin.getConfig().set("Hospital.Y" , loc.getY());
                        plugin.getConfig().set("Hospital.Z" , loc.getZ());
                        plugin.getConfig().set("Hospital.Yaw" , loc.getYaw());
                        plugin.getConfig().set("Hospital.Pitch" , loc.getPitch());
                        plugin.saveConfig();
                        plugin.reloadConfig();
                    }
                    if(args[0].equalsIgnoreCase("setSpawn")) {
                        Hospital plugin = Hospital.getPlugin(Hospital.class);
                        Location loc = player.getLocation();
                        plugin.getConfig().set("Spawn.World" , Objects.requireNonNull(loc.getWorld()).getName());
                        plugin.getConfig().set("Spawn.X" , loc.getX());
                        plugin.getConfig().set("Spawn.Y" , loc.getY());
                        plugin.getConfig().set("Spawn.Z" , loc.getZ());
                        plugin.getConfig().set("Spawn.Yaw" , loc.getYaw());
                        plugin.getConfig().set("Spawn.Pitch" , loc.getPitch());
                        plugin.saveConfig();
                        plugin.reloadConfig();
                    }
                    if(args[0].equalsIgnoreCase("reload")) {
                        Hospital plugin = Hospital.getPlugin(Hospital.class);
                        plugin.reloadConfig();
                    }

                    else {
                        player.sendMessage(ChatColor.GOLD + "Hospital by super_cool_spy");
                        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Help Menu");
                        player.sendMessage("§aUsage: /hospital setRespawn");
                        player.sendMessage("§aUsage: /hospital setSpawn");
                        player.sendMessage("§aUsage: /hospital reload");
                        player.sendMessage("§aUsage: /hospital cooldown <seconds>");
                    }
                }
                if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("cooldown")) {
                        Hospital plugin = Hospital.getPlugin(Hospital.class);
                        int cd;
                        try {
                            cd = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "Please enter a number");
                            return true;
                        }
                        plugin.getConfig().set("countdown" , cd);
                        plugin.saveConfig();
                        plugin.reloadConfig();
                    }
                    else {
                        player.sendMessage(ChatColor.GOLD + "Hospital by super_cool_spy");
                        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Help Menu");
                        player.sendMessage("§aUsage: /hospital setRespawn");
                        player.sendMessage("§aUsage: /hospital setSpawn");
                        player.sendMessage("§aUsage: /hospital reload");
                        player.sendMessage("§aUsage: /hospital cooldown <seconds>");
                    }
                }
            }
        }
        return false;
    }
}
