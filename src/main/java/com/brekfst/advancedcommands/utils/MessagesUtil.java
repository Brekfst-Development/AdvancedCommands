package com.brekfst.advancedcommands.utils;

import com.brekfst.advancedcommands.AdvancedCommands;
import org.bukkit.ChatColor;

public class MessagesUtil {

    private final AdvancedCommands plugin;
    private final String prefix;

    public MessagesUtil(AdvancedCommands plugin) {
        this.plugin = plugin;
        // Load the prefix from the config, or use a default value
        this.prefix = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.prefix", "&8[&6AdvancedCommands&8] "));
    }

    public String formatWithPrefix(String message) {
        return prefix + ChatColor.translateAlternateColorCodes('&', message);
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}