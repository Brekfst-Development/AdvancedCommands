package com.brekfst.advancedcommands.commands;

import com.brekfst.advancedcommands.AdvancedCommands;
import com.brekfst.advancedcommands.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ServerInfoCommand implements CommandExecutor {
    private final AdvancedCommands plugin;
    private final long startTime;
    private final MessagesUtil messagesUtil;

    public ServerInfoCommand(AdvancedCommands plugin, MessagesUtil messagesUtil) {
        this.plugin = plugin;
        this.messagesUtil = messagesUtil;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("acommands.serverinfo")) {
            sender.sendMessage(messagesUtil.formatWithPrefix(ChatColor.RED + "You don't have permission to use this command."));
            return true;
        }

        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        int maxPlayers = Bukkit.getMaxPlayers();
        String version = Bukkit.getVersion();

        long uptimeMillis = System.currentTimeMillis() - startTime;
        long uptimeHours = uptimeMillis / (1000 * 60 * 60);
        long uptimeMinutes = (uptimeMillis / (1000 * 60)) % 60;
        String uptime = uptimeHours + "h " + uptimeMinutes + "m";

        List<String> serverInfoMessages = plugin.getConfig().getStringList("messages.serverinfo");
        for (String line : serverInfoMessages) {
            String formattedMessage = line
                    .replace("%version%", version)
                    .replace("%online%", String.valueOf(onlinePlayers))
                    .replace("%max%", String.valueOf(maxPlayers))
                    .replace("%uptime%", uptime);
            sender.sendMessage(messagesUtil.format(formattedMessage));
        }

        return true;
    }
}
