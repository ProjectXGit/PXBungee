package net.projectx.bungee.functions.discord;


import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

/**
 * ~Yannick on 29.09.2019 at 11:36 o´ clock
 */
public class ProjectXBot {
    public ShardManager shardManager;

    public ProjectXBot() {
        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        builder.setToken("NjI3NDAxMjY5NTM5MjQxOTg0.XY-z-w.eL7I0Qz6AIX6_iJSKh7GcG10lnc");
        builder.setActivity(Activity.playing("Minecraft"));
        builder.setStatus(OnlineStatus.ONLINE);

        builder.addEventListeners(new VerifyDiscord(), new Join());

        try {
            shardManager = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public void stopBot() {
        shardManager.setStatus(OnlineStatus.OFFLINE);
        shardManager.shutdown();
    }
}
