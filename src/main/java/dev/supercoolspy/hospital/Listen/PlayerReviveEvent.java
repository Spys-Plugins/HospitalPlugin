package dev.supercoolspy.hospital.Listen;

import dev.supercoolspy.hospital.Hospital;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Objects;

public class PlayerReviveEvent implements Listener {
    @EventHandler
    public void playerReviveEvent(PlayerRespawnEvent e) {
        Hospital plugin = Hospital.getPlugin(Hospital.class);
        e.setRespawnLocation(Objects.requireNonNull(plugin.getConfig().getLocation("HospitalSpawn")));
    }
}
