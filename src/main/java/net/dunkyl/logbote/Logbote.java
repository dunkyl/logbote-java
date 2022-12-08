package net.dunkyl.logbote;

import dev.qixils.quasicolon.Quasicord;
import dev.qixils.quasicolon.cogs.Cog;
import dev.qixils.quasicolon.cogs.GuildCog;
import dev.qixils.quasicolon.cogs.impl.AbstractCog;
import dev.qixils.quasicolon.cogs.impl.AbstractGuildCog;
import dev.qixils.quasicolon.events.EventListener;
import net.dv8tion.jda.api.events.session.ReadyEvent;

import java.nio.file.Path;
import java.util.Locale;

public class Logbote {

    private final Quasicord q_cord;

    public Logbote(){
        try {
            q_cord = new Quasicord.Builder()
                    .namespace("")
                    .configRoot(Path.of("."))
                    .defaultLocale(Locale.ENGLISH)
                    .eventHandler(this)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final void useCog(Class<? extends AbstractCog> cog){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void x() {
        var env = q_cord.getEnvironment();
        System.out.println(env);
    }

    @EventListener
    public void onReady(ReadyEvent event) {
        System.out.println("Ready.");
        System.out.println("Guilds available: "+event.getGuildAvailableCount() + " of " + event.getGuildTotalCount());
    }
}
