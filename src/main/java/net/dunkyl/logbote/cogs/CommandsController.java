package net.dunkyl.logbote.cogs;

import dev.qixils.quasicolon.Quasicord;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CommandsController {

    Guild guild;

    Quasicord library;

    CompletableFuture<Optional<String>> TryGetInvite() {
        return guild.retrieveInvites().map(invites ->
            invites.stream().findFirst().map(Invite::getUrl)
        ).submit();
    }

    CompletableFuture<String> MakeInvite() {
        return null;
    }

    CompletableFuture<String> GetOrMakeInvite() {
        return TryGetInvite().thenCompose(
            invite -> invite.map(CompletableFuture::completedFuture).orElseGet(this::MakeInvite)
        );
    }

    void sendThinking() {

    };
}
