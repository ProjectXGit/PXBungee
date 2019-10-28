package net.projectx.bungee.main;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.projectx.bungee.functions.discord.DiscordBungee;
import net.projectx.bungee.functions.discord.ProjectXBot;
import net.projectx.bungee.functions.listener.JoinListener;
import net.projectx.bungee.functions.mysql.MySQL;

import java.util.HashMap;

import static net.projectx.bungee.functions.mysql.MySQL_User.createUserTable;
import static net.projectx.bungee.main.Data.projectXBot;
import static net.projectx.bungee.main.Data.requestedBy;

/**
 * ~Yannick on 16.10.2019 at 17:28 o´ clock
 */
public class Main extends Plugin {
    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        Data.instance = this;
        projectXBot = new ProjectXBot();
        requestedBy = new HashMap<>();
        MySQL.connect();
        registerCommands();
        registerListener();
        createTables();
    }

    @Override
    public void onDisable() {
        MySQL.close();
        projectXBot.stopBot();
    }

    public void registerCommands() {
        PluginManager pm = BungeeCord.getInstance().getPluginManager();
        pm.registerCommand(this, new DiscordBungee());
    }

    public void registerListener() {
        PluginManager pm = BungeeCord.getInstance().getPluginManager();
        pm.registerListener(this, new JoinListener());
    }

    public void createTables() {
        createUserTable();
    }


}
