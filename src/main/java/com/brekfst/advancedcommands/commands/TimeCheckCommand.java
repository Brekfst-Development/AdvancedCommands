package com.brekfst.advancedcommands.commands;

import com.brekfst.advancedcommands.AdvancedCommands;
import com.brekfst.advancedcommands.utils.MessagesUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimeCheckCommand implements CommandExecutor {
    private final AdvancedCommands plugin;
    private final MessagesUtil messagesUtil;

    public TimeCheckCommand(AdvancedCommands plugin, MessagesUtil messagesUtil) {
        this.plugin = plugin;
        this.messagesUtil = messagesUtil;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messagesUtil.formatWithPrefix("This command can only be used by players."));
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("acommands.timecheck")) {
            player.sendMessage(messagesUtil.formatWithPrefix(ChatColor.RED + "You don't have permission to use this command."));
            return true;
        }

        // Convert server time (ticks) into HH:mm format
        long serverTicks = player.getWorld().getTime();
        int hours = (int) ((serverTicks / 1000 + 6) % 24); // Add 6 to convert from Minecraft time to real-world time
        int minutes = (int) ((serverTicks % 1000) * 60 / 1000);
        String serverTime = String.format("%02d:%02d", hours, minutes);

        // Get the real-world time
        String realWorldTime = new SimpleDateFormat(plugin.getConfig().getString("messages.time_format", "yyyy-MM-dd HH:mm:ss"))
                .format(new Date());

        // Send the customized message
        List<String> messages = plugin.getConfig().getStringList("messages.timecheck");
        for (String line : messages) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', line
                    .replace("%servertime%", serverTime)
                    .replace("%realtime%", realWorldTime)));
        }
        return true;
    }
}
