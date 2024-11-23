package com.brekfst.advancedcommands.utils;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final int cooldownTime;

    public CooldownManager(int cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public boolean isOnCooldown(Player player) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid)) return false;

        long lastUsed = cooldowns.get(uuid);
        return (System.currentTimeMillis() - lastUsed) < cooldownTime * 1000L;
    }

    public void setCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public int getRemainingTime(Player player) {
        if (!isOnCooldown(player)) return 0;

        long lastUsed = cooldowns.get(player.getUniqueId());
        return (int) ((cooldownTime * 1000L - (System.currentTimeMillis() - lastUsed)) / 1000L);
    }
}
