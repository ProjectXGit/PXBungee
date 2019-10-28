package net.projectx.bungee.functions.discord;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import javax.annotation.Nonnull;

import static net.projectx.bungee.main.Data.prefix;
import static net.projectx.bungee.main.Data.requestedBy;

/**
 * ~Yannick on 29.09.2019 at 12:19 o´ clock
 */
public class VerifyDiscord extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();
        String name;

        if (event.isFromType(ChannelType.PRIVATE)) {
            if (message.startsWith("/verify ")) {
                name = message.replaceAll("/verify ", "");
                final boolean[] found = {false};
                if (name.equalsIgnoreCase("")) {
                    event.getChannel().sendMessage("Du musst einen Namen angeben!");
                    return;
                }
                BungeeCord.getInstance().getPlayers().forEach(entry -> {
                    if (entry.getName().equalsIgnoreCase(name)) {
                        found[0] = true;
                        entry.sendMessage(prefix + "Der User " + event.getAuthor().getName() + " will sich mit dir verifizieren");
                        TextComponent component = new TextComponent();
                        component.setText(prefix + "Klicke hier um dich zu §6verifizieren§7!");
                        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/discord verify " + event.getAuthor().getId()));
                        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Hier Klicken!!").create()));
                        entry.sendMessage(component);
                        requestedBy.put(entry.getUniqueId(), event.getAuthor().getId());

                    }
                });

                if (found[0] == false) {
                    event.getChannel().sendMessage("Du musst auf dem Server online sein um dich verifizieren zu können!").queue();
                }
            }
        }
    }
}
