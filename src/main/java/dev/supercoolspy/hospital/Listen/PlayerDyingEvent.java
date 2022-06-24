package dev.supercoolspy.hospital.Listen;

import dev.supercoolspy.hospital.Hospital;
import dev.supercoolspy.hospital.HospitalBuilding;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerDyingEvent implements Listener {
    private final Hospital main;

    public PlayerDyingEvent(Hospital main){
        this.main = main;
    }
    @EventHandler
    public void playerDamageEvent(EntityDamageEvent e) {
        if(e.getEntity().getType() == EntityType.PLAYER) {
            Player p = (Player) e.getEntity();
            if(p.getHealth() - e.getFinalDamage() <= 0) {
                ArrayList<ItemStack> items = new ArrayList<>();
                for(int i = 0; i < p.getInventory().getSize(); i++) {
                    if(p.getInventory().getItem(i) != null) {
                        items.add(p.getInventory().getItem(i));
                    }
                }
                for(ItemStack item : items) {
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.RED + "Timer to leave hospital");
                    if(Objects.equals(Objects.requireNonNull(item.getItemMeta()).getLore(), lore)) {
                        p.getInventory().remove(item);
                    } else {
                        p.getWorld().dropItem(p.getLocation().add(0,1,0), item);
                    }
                }
                p.getInventory().clear();
                p.getInventory().setArmorContents(null);
                e.setCancelled(true);
                p.setHealth(Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
                p.setFoodLevel(20);
                p.setSaturation(5);
                p.setFireTicks(0);
                HospitalBuilding hb = new HospitalBuilding(main);
                World w = Bukkit.getServer().getWorld("world");
                double x = main.getConfig().getDouble("Hospital.X");
                double y = main.getConfig().getDouble("Hospital.Y");
                double z = main.getConfig().getDouble("Hospital.Z");
                p.teleport(new Location(w, x, y, z));
                //get the inventory of the player and drop all of the items
                hb.startCountdownAndTp(p.getUniqueId());
                p.sendMessage(ChatColor.RED + "You died, and have been sent to the hospital");
            }
        }
    }
}
