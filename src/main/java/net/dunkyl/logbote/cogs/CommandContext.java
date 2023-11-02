package net.dunkyl.logbote.cogs;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

public interface CommandContext {

    public Guild getGuild();

    public Message getMessage();
}
