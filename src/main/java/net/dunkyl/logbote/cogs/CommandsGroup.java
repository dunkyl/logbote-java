package net.dunkyl.logbote.cogs;

public class CommandsGroup {

    private final String name;

    public CommandsGroup(String name) {
        this.name = name;
    }

    public CommandsGroup(CommandsGroup parent, String name) {
        this.name = parent.name + " " + name;
    }
}
