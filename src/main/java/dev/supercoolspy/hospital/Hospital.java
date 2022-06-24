package dev.supercoolspy.hospital;

import dev.supercoolspy.hospital.Listen.PlayerDyingEvent;
import dev.supercoolspy.hospital.Listen.PlayerReviveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

public final class Hospital extends JavaPlugin {

    @Override
    public void onEnable() {
        Hospital instance = this;
        // Plugin startup logic
        System.out.println("Hospital Respawn Plugin started");
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerReviveEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerDyingEvent(this), this);
        Objects.requireNonNull(getCommand("hospital")).setExecutor(new HospitalCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
