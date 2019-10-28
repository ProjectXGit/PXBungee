package net.projectx.bungee.functions.discord;


import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


/**
 * ~Yannick on 29.09.2019 at 14:38 o´ clock
 */
public class Join extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        System.out.println("User " + event.getUser().getAsTag() + " ist beigetreten!");
        event.getUser().openPrivateChannel().queue((privateChannel -> {
            privateChannel.sendMessage("Hey " + event.getUser().getAsMention() + "!\n" +
                    " Schön, dass du auf den ProjectX-Server gefunden hast.\n" +
                    " Gib /verify ein um dich mit deinem Minecraft Account zu verifizieren!").queue();
        }));
    }


}
