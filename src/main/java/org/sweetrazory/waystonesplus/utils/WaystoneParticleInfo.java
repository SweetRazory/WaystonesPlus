package org.sweetrazory.waystonesplus.utils;

import org.bukkit.Location;
import org.bukkit.Particle;

public class WaystoneParticleInfo {
    private final String id;
    private final Particle particle;
    private final Location location;

    public WaystoneParticleInfo(String id, Particle particle, Location location) {
        this.id = id;
        this.particle = particle;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public Particle getParticle() {
        return particle;
    }

    public Location getLocation() {
        return location;
    }
}