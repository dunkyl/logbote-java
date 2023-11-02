package net.dunkyl.logbote;

import net.dunkyl.logbote.cogs.TextCommand;
import net.dunkyl.logbote.cogs.Joinlog;

public class Main {
    public static void main(String[] args) {

        Logbote lgbt = new Logbote.Builder()
            .playing("IN A TEST ENVIRONMENT")
//            .cog(Joinlog.class)
            .build();

        TextCommand my_command =  (ctx, cmd_args) -> {
            String name = ctx.getMessage().getAuthor().getName();
            ctx.getMessage().reply(String.format("Hello, %s!", name)).queue();
        };

        lgbt.registerTextCommand(
                "hello",
                "Says hello.",
                new Parser[] { lgbt.findParserFor( String[].class) },
                my_command
        );

    }
}

