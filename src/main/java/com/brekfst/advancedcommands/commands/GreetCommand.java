package com.brekfst.advancedcommands.commands;

import com.brekfst.advancedcommands.AdvancedCommands;
import com.brekfst.advancedcommands.utils.CooldownManager;
import com.brekfst.advancedcommands.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GreetCommand implements CommandExecutor {
    private final AdvancedCommands plugin;
    private final CooldownManager cooldownManager;
    private final MessagesUtil messagesUtil;

    public GreetCommand(AdvancedCommands plugin, MessagesUtil messagesUtil) {
        this.plugin = plugin;
        int cooldownTime = plugin.getConfig().getInt("cooldowns.greet", 10);
        this.cooldownManager = new CooldownManager(cooldownTime);
        this.messagesUtil = messagesUtil;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messagesUtil.formatWithPrefix(ChatColor.RED + "Only players can use this command!"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("acommands.greet")) {
            player.sendMessage(messagesUtil.formatWithPrefix(ChatColor.RED + "You don't have permission to use this command."));
            return true;
        }

        if (cooldownManager.isOnCooldown(player)) {
            int timeLeft = cooldownManager.getRemainingTime(player);
            player.sendMessage(messagesUtil.formatWithPrefix(ChatColor.RED + "You must wait " + timeLeft + " seconds to use this command again."));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(messagesUtil.formatWithPrefix(ChatColor.RED + "Usage: /greet <player>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(messagesUtil.formatWithPrefix(ChatColor.RED + "Player not found."));
            return true;
        }

        // Send multi-line greeting
        List<String> messages = plugin.getConfig().getStringList("messages.greet");
        for (String line : messages) {
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', line
                    .replace("%player%", target.getName())
                    .replace("%sender%", player.getName())));
        }

        player.sendMessage(messagesUtil.formatWithPrefix(ChatColor.GREEN + "Greeting sent to " + target.getName() + "!"));
        cooldownManager.setCooldown(player);
        return true;
    }
}