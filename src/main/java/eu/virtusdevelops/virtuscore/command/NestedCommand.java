package eu.virtusdevelops.virtuscore.command;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

public class NestedCommand {
    final AbstractCommand parent;
    final LinkedHashMap<String, AbstractCommand> children = new LinkedHashMap();


    protected NestedCommand(AbstractCommand parent) {
        this.parent = parent;
    }

    /*
        Add sub command to main command
     */
    public NestedCommand addSubCommand(AbstractCommand command) {
        command.getCommands().stream().forEach(cmd -> children.put(cmd.toLowerCase(), command));
        return this;
    }

    /*
        Add multiple sub commands.
     */
    public NestedCommand addSubCommands(AbstractCommand... commands) {
        Stream.of(commands).forEach(command -> command.getCommands().stream().forEach(cmd -> children.put(cmd.toLowerCase(), command)));
        return this;
    }

}
