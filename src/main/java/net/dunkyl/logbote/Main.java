package net.dunkyl.logbote;

import net.dunkyl.logbote.Logbote;
import net.dunkyl.logbote.cogs.Joinlog;

public class Main {
    public static void main(String[] args) {

        var lgbt = new Logbote.Builder()
            .playing("IN A TEST ENVIRONMENT")
            .useCog(Joinlog.class)
            .build();

    }
}

