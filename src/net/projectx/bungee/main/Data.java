package net.projectx.bungee.main;

import net.md_5.bungee.api.plugin.Plugin;
import net.projectx.bungee.functions.discord.ProjectXBot;

import java.util.HashMap;
import java.util.UUID;

/**
 * ~Yannick on 16.10.2019 at 17:28 o´ clock
 */
public class Data {
    public static String symbol = "§8 » §7";
    public static String prefix = "§5Project§6X " + symbol;
    public static String noperm = prefix + "§4Dazu hast du keine Berechtigung!";
    public static String consoleonly = "§4Dieser Befehl kann nur von der Konsole ausgeführt werden!";
    public static String noconsole = "§4Dieser Befehl kann nicht von der Konsole ausgeführt werden!";

    public static Plugin instance;
    public static ProjectXBot projectXBot;
    public static HashMap<UUID, String> requestedBy;
}
