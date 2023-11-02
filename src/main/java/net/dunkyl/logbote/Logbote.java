package net.dunkyl.logbote;

import dev.qixils.quasicolon.Quasicord;
import dev.qixils.quasicolon.cogs.GlobalCog;
import dev.qixils.quasicolon.cogs.GuildCog;
import dev.qixils.quasicolon.cogs.impl.AbstractCog;
import dev.qixils.quasicolon.cogs.impl.AbstractGlobalCog;
import dev.qixils.quasicolon.cogs.impl.AbstractGuildCog;
import dev.qixils.quasicolon.events.EventListener;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class Logbote {

    private final Quasicord q_cord;

    private ArrayList<GlobalCog> globalCogs = new ArrayList<>();
    private HashMap<Long, ArrayList<GuildCog>> guildCogs = new HashMap<Long, ArrayList<GuildCog>>();

    Logbote(Quasicord qcord){
        q_cord = qcord;
    }

    public final <T extends AbstractCog> void useCog(Class<T> cogType){
        // global cog
        if (AbstractGlobalCog.class.isAssignableFrom(cogType)){
            Class<? extends AbstractGlobalCog> globalCogType = cogType.asSubclass(AbstractGlobalCog.class);
            this.useGlobalCog(globalCogType);
        } else { // guild cog
            Class<? extends AbstractGuildCog> guildCogType = cogType.asSubclass(AbstractGuildCog.class);
            this.useGuildCog(guildCogType);

        }
    }

    private <T extends AbstractGlobalCog> void useGlobalCog(Class<T> cogType){
        for (var guild : q_cord.getJDA().getGuilds()) {
            try {
                var cog =  T.Load(q_cord, cogType);
                globalCogs.add(cog);
                q_cord.getEventDispatcher().registerListeners(cog);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private <T extends AbstractGuildCog> void useGuildCog(Class<T> cogType){
        for (var guild : q_cord.getJDA().getGuilds()) {
            if (!guildCogs.containsKey(guild.getIdLong())) {
                guildCogs.put(guild.getIdLong(), new ArrayList<>());
            }
            try {
                var maybe_cog = T.TryLoad(q_cord, guild.getIdLong(), cogType);
                if (maybe_cog.isPresent()) {
                    guildCogs.get(guild.getIdLong()).add(maybe_cog.get());
                    q_cord.getEventDispatcher().registerListeners(maybe_cog.get());
                }
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void x() {
        var env = q_cord.getEnvironment();
        System.out.println(env);
    }

    @EventListener
    public void onDiscordEvent(Event event) {
        System.out.println("Event: " + event);
    }

    @EventListener
    public void onMessage(MessageReceivedEvent event) {
        var content = event.getMessage().getContentRaw();
        if (content.startsWith("?quit")) {
            q_cord.shutdown();
        }
    }

//    @EventListener
//    public void onGuildEvent(GenericGuildEvent event) {
//        var cogsForGuild = guildCogs.get(event.getGuild().getIdLong());
//        if (cogsForGuild != null) {
//            for (var cog : cogsForGuild) {
//                cog.onGuildEvent(event);
//            }
//        }
//    }

    @EventListener
    public void onReady(ReadyEvent event) {
        System.out.println("Ready.");
        System.out.println("Guilds available: "+event.getGuildAvailableCount() + " of " + event.getGuildTotalCount());
        x();
    }

    public static class Builder {

        private Quasicord.Builder qcordBuilder = new Quasicord.Builder();

        public Builder() {
            qcordBuilder
                .namespace("")
                .configRoot(Path.of("."))
                .defaultLocale(Locale.ENGLISH);
        }

        public <T> Builder cog(Class<T> cogClass) {
            System.out.println("Using cog: " + cogClass);
            return this;
        }

        public Builder playing(String playing) {
            qcordBuilder.activity(Activity.playing(playing));
            return this;
        }

        public Logbote build(){
            try {
                return new Logbote(qcordBuilder.build());
            } catch (LoginException | InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
