package dev.supercoolspy.hospital;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class HospitalBuilding {
    private final Hospital main;

    public HospitalBuilding(Hospital main){
        this.main = main;
    }
    public void startCountdownAndTp(UUID u) {
        main.getConfig().set("players." + u.toString() + ".hospital", true);
        main.saveConfig();
        main.getServer().getScheduler().runTaskLater(main, () -> {
            World w = Bukkit.getServer().getWorld("world");
            double x = main.getConfig().getDouble("Spawn.X");
            double y = main.getConfig().getDouble("Spawn.Y");
            double z = main.getConfig().getDouble("Spawn.Z");
            Objects.requireNonNull(Bukkit.getPlayer(u)).teleport(new Location(w, x, y, z));
            main.getConfig().set("players." + u + ".hospital", false);
            main.saveConfig();
        }, 20L * main.getConfig().getInt("countdown"));
        main.getServer().getScheduler().runTaskAsynchronously(main, () -> {
            //loop for countdown
            for(int i = main.getConfig().getInt("countdown"); i > -1; i--) {
                try {
                    //set slots 1-9 to glass pane with number of seconds left
                    ItemStack clock = new ItemStack(Material.CLOCK);
                    ItemMeta meta = clock.getItemMeta();
                    assert meta != null;
                    //lore list
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.RED + "Timer to leave hospital");
                    meta.setLore(lore);
                    meta.setDisplayName(ChatColor.RED + String.valueOf(i));
                    clock.setItemMeta(meta);
                    Objects.requireNonNull(Bukkit.getPlayer(u)).setFireTicks(0);
                    Objects.requireNonNull(Bukkit.getPlayer(u)).setFoodLevel(20);
                    Objects.requireNonNull(Bukkit.getPlayer(u)).setSaturation(5);
                    //make the glass pane be red
                    Objects.requireNonNull(Bukkit.getPlayer(u)).getInventory().setItem(8, clock);
                    if(i == 0) {
                        //if the countdown is 0, set the slots to air
                        for(int j = 0; j < 9; j++) {
                            Objects.requireNonNull(Bukkit.getPlayer(u)).getInventory().setItem(j, new ItemStack(Material.AIR));
                        }
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
