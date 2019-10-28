package net.projectx.bungee.functions.listener;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.projectx.bungee.functions.mysql.MySQL_User;

import static net.projectx.bungee.main.Data.prefix;

/**
 * ~Yannick on 17.10.2019 at 14:46 o´ clock
 */
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (MySQL_User.isUserExists(player.getUniqueId())) {
            player.sendMessage(prefix + "Willkommen zurück §e" + player.getDisplayName() + "");
        } else {
            MySQL_User.createUser(player.getUniqueId());
            player.sendMessage(prefix + "Herzlich Willkommen auf dem Server §e" + player.getDisplayName() + "");
        }
        TextComponent component = new TextComponent();
        component.setText(prefix + "Nutz §e/help§7 für alle Commands!");
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help"));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Hier Klicken!!").create()));
        player.sendMessage(component);
    }
}
