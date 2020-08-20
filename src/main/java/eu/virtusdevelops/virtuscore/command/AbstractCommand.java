package eu.virtusdevelops.virtuscore.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public abstract class AbstractCommand {

    private final CommandType _cmdType;
    private final boolean _hasArgs;
    private final List<String> _handledCommands = new ArrayList<>();


    public AbstractCommand(CommandType type, boolean hasArgs, String... command){
        this._handledCommands.addAll(Arrays.asList(command));
        this._hasArgs = hasArgs;
        this._cmdType = type;
    }

    protected AbstractCommand(CommandType type, String... command) {
        this._handledCommands.addAll(Arrays.asList(command));
        this._hasArgs = false;
        this._cmdType = type;
    }

    public final List<String> getCommands() {
        return Collections.unmodifiableList(_handledCommands);
    }
    /**
       * Add the subcommand:
       * @param command - String command name.
    **/
    public final void addSubCommand(String command) {
        _handledCommands.add(command);
    }
    protected abstract ReturnType runCommand(CommandSender sender, String... args);

    protected abstract List<String> onTab(CommandSender sender, String... args);

    // Permission needed to execute command
    public abstract String getPermissionNode();

    // Syntax of the command <arg1> <arg2>...
    public abstract String getSyntax();

    // Information about command.
    public abstract String getDescription();

    // Command requires args?
    public boolean hasArgs() {
        return _hasArgs;
    }
    // Check if command is player only
    public boolean isPlayerOnly() {
        return _cmdType == CommandType.PLAYER_ONLY;
    }


    public static enum ReturnType {SUCCESS, NEEDS_PLAYER, FAILURE, SYNTAX_ERROR, MISSING_ARGUMENT}
    public static enum CommandType {PLAYER_ONLY, BOTH}

}
