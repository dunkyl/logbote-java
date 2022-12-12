package net.dunkyl.logbote.cogs;

import dev.qixils.quasicolon.Quasicord;
import dev.qixils.quasicolon.cogs.impl.AbstractGuildCog;
import dev.qixils.quasicolon.cogs.impl.GuildCogConfig;
import dev.qixils.quasicolon.decorators.slash.DefaultPermissions;
import dev.qixils.quasicolon.decorators.slash.SlashCommand;
import dev.qixils.quasicolon.decorators.slash.SlashCommandGroup;
import dev.qixils.quasicolon.events.EventListener;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberUpdateEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.nio.channels.Channel;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class Joinlog extends AbstractGuildCog {

    protected static class JoinlogConfig extends GuildCogConfig {
        @Nullable protected Channel joinsLog;
        @Nullable protected Channel usernamesLog;
        @Nullable protected Channel editsLog;
        @Nullable protected Channel deletesLog;
    }

//    @Override
    public @NonNull Class<? extends GuildCogConfig> getConfigClass() { return JoinlogConfig.class; }

    public Joinlog(@NonNull Quasicord library, @NonNull Guild guild) {
        super(library, guild);
    }

    @Override
    protected Collection<GatewayIntent> getRequiredIntents() {
        return Arrays.asList(
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_PRESENCES
        );
    }

    @SlashCommandGroup("")
    public static class Commands extends CommandsController {
        /**
         * Unban a user, and send them and invite back to the server if possible.
         * @param user The user to unban.
         */
        @SlashCommand("unban")
        @DefaultPermissions(Permission.BAN_MEMBERS)
        public CompletableFuture<String> unban(User user, String reason) {
            sendThinking();
            return guild.unban(user).reason(reason).map(
                __ -> {
                    var dm = user.openPrivateChannel().map(
                        channel -> channel.sendMessage(
                            "You have been unbanned from " + guild.getName() +
                                  ".\nHere is an invite to the server: " + GetOrMakeInvite().join()
                        ).submit().join()
                    );
                    var invite_report = dm.map(
                        _message -> " and sent them an invite to the server."
                    ).onErrorMap(
                        error -> " but could not send them an invite to the server: " + error.getMessage()
                    );
                    return "Unbanned " + user.getAsMention() + invite_report.submit().join();
                }
            ).onErrorMap(
                error -> "User is not unbanned: " + error.getMessage()
            ).submit();
        }
    }

    private final CommandsGroup commands = new CommandsGroup("joinlog");

    @SlashCommand(name="unban", group=commands)
    @DefaultPermissions(Permission.BAN_MEMBERS)
    public CompletableFuture<String> unban(User user, String reason) {
        this.sendThinking();
        // ...
    }

    @Override
    public void onLoad() {

    }

    @EventListener
    public void onJoin(GuildMemberJoinEvent event) {
        System.out.println("test");
    }

    @EventListener
    public void onLeave(GuildMemberRemoveEvent event) {

    }

    @EventListener
    public void onMemberUpdate(GuildMemberUpdateEvent event) {

        var oldNickname = event.getRawData();

    }



}
