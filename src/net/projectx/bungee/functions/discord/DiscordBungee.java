package net.projectx.bungee.functions.discord;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.projectx.bungee.functions.mysql.MySQL_User;
import net.projectx.bungee.main.Data;
import net.projectx.bungee.util.UUIDFetcher;

import static net.projectx.bungee.main.Data.*;


/**
 * ~Yannick on 18.10.2019 at 17:12 o´ clock
 */
public class DiscordBungee extends Command {
    StringBuilder builder = new StringBuilder();

    public DiscordBungee() {
        super("discord");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args.length == 0) {
            if (commandSender.hasPermission("px.discord.discord")) {
                builder.append(Data.prefix + "§7§lHilfsübersicht:§r\n");
                add("discord verify", "Verifiziert dich mit Discord!");
                add("discord info", "Zeigt den Discod Namen an!");
                add("discord set", "Setzt den Discord Namen eines Spielers");
                commandSender.sendMessage(builder.toString());
            } else {
                commandSender.sendMessage(Data.noperm);
            }
        } else {
            if (args[0].equalsIgnoreCase("verify")) {
                if (commandSender.hasPermission("px.discord.verify")) {
                    if (args.length == 2) {
                        if (commandSender instanceof ProxiedPlayer) {
                            if (requestedBy.containsKey(((ProxiedPlayer) commandSender).getUniqueId())) {
                                if (requestedBy.get(((ProxiedPlayer) commandSender).getUniqueId()).equalsIgnoreCase(args[1])) {
                                    MySQL_User.setDiscordId(((ProxiedPlayer) commandSender).getUniqueId(), args[1]);
                                    commandSender.sendMessage(prefix + "Du wurdest erfolgreich mit " + projectXBot.shardManager.getUserById(args[1]).getAsTag());
                                    return;
                                }
                            }
                            commandSender.sendMessage(prefix + "§cDu wurdest nicht angefragt");
                        } else {
                            commandSender.sendMessage(Data.noconsole);
                        }
                    } else {
                        commandSender.sendMessage(prefix + "§cDu musst eine id angeben!");
                    }
                } else {
                    commandSender.sendMessage(noperm);
                }
            } else if (args[0].equalsIgnoreCase("info")) {
                if (commandSender.hasPermission("px.discord.info")) {
                    if (args.length == 2) {
                        if (!MySQL_User.getDiscordId(UUIDFetcher.getUUID(args[1])).equalsIgnoreCase("-1")) {
                            commandSender.sendMessage(prefix + "Der Spieler §e" + args[1] + "§7 ist verifiziert mit §e" + projectXBot.shardManager.getUserById(MySQL_User.getDiscordId(UUIDFetcher.getUUID(args[1]))).getAsTag());
                        } else {
                            commandSender.sendMessage(prefix + "§cDer Spieler ist nicht mit Discord verifiziert!");
                        }
                    } else {
                        commandSender.sendMessage(prefix + "Du musst einen Spieler angeben!");
                    }
                } else {
                    commandSender.sendMessage(noperm);
                }
            } else if (args[0].equalsIgnoreCase("set")) {
                if (commandSender.hasPermission("px.discord.set")) {
                    if (args.length == 3) {
                        if (MySQL_User.isUserExists(UUIDFetcher.getUUID(args[1]))) {
                            MySQL_User.setDiscordId(UUIDFetcher.getUUID(args[0]), projectXBot.shardManager.getUserByTag(args[1]).getId());
                            commandSender.sendMessage(prefix + "Dem Spieler §e" + args[1] + "§7 wurde der Discord Account §e" + args[2] + "§7 zugewiesen!");
                        } else {
                            commandSender.sendMessage(prefix + "§cDer User " + args[1] + " existiert nicht!");
                        }
                    } else {
                        commandSender.sendMessage(prefix + "Du musst einen Spieler und einen Namen mit Tag angeben!");
                    }
                } else {
                    commandSender.sendMessage(noperm);
                }
            } else {
                builder.append(Data.prefix + "§7§lHilfsübersicht:§r\n");
                add("verify", "Verifiziert dich mit Discord!");
                add("info", "Zeigt den Discod Namen an!");
                add("set", "Setzt den Discord Namen eines Spielers");
                commandSender.sendMessage(builder.toString());
            }
        }
    }


    private void add(String command, String usage) {
        builder.append("\n" + Data.symbol + "§e/" + command + "§8: §7 " + usage + ChatColor.RESET);
    }
}
