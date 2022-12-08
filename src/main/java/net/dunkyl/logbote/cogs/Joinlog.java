package net.dunkyl.logbote.cogs;

import dev.qixils.quasicolon.Quasicord;
import dev.qixils.quasicolon.cogs.GuildCog;
import dev.qixils.quasicolon.cogs.impl.AbstractGuildCog;
import dev.qixils.quasicolon.cogs.impl.GuildCogConfig;
import dev.qixils.quasicolon.decorators.slash.SlashCommand;
import dev.qixils.quasicolon.decorators.slash.SlashCommandGroup;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.nio.channels.Channel;
import java.util.Arrays;
import java.util.Collection;

public class Joinlog extends AbstractGuildCog {

    protected static class JoinlogConfig extends GuildCogConfig {
        @Nullable public Channel joinsLog;
        @Nullable public Channel usernamesLog;
        @Nullable public Channel editsLog;
        @Nullable public Channel deletesLog;
    }

    protected Joinlog(@NonNull Quasicord library, @NonNull Guild guild) {
        super(library, guild);
    }

    @Override
    protected Collection<GatewayIntent> getRequiredIntents() {
        return Arrays.asList(
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_PRESENCES
        );
    }

    @SlashCommandGroup("joinlog")
    public static class Commands {
        @SlashCommand("test")
        public String test() {
            return "test";
        }
    }

}
