package com.brekfst.advancedcommands;

import com.brekfst.advancedcommands.commands.GreetCommand;
import com.brekfst.advancedcommands.commands.ServerInfoCommand;
import com.brekfst.advancedcommands.commands.TimeCheckCommand;
import com.brekfst.advancedcommands.utils.MessagesUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedCommands extends JavaPlugin {

    private MessagesUtil messagesUtil;

    @Override
    public void onEnable() {
        getLogger().info("AdvancedCommands plugin enabled!");

        // Initialize MessagesUtil
        messagesUtil = new MessagesUtil(this);

        // Register commands
        getCommand("greet").setExecutor(new GreetCommand(this, messagesUtil));
        getCommand("timecheck").setExecutor(new TimeCheckCommand(this, messagesUtil));
        getCommand("serverinfo").setExecutor(new ServerInfoCommand(this, messagesUtil));

        // Save default config
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("AdvancedCommands plugin disabled!");
    }
}