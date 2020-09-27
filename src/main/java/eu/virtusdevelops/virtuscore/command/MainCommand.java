package eu.virtusdevelops.virtuscore.command;
import eu.virtusdevelops.virtuscore.utils.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class MainCommand extends AbstractCommand {

    String header = null;
    String description;
    boolean sortHelp = false;
    final String command;
    final Plugin plugin;
    protected final NestedCommand nestedCommands;

    public MainCommand(Plugin plugin, String command) {
        super(CommandType.PLAYER_ONLY, command);
        this.command = command;
        this.plugin = plugin;
        this.description = "Help info for: /" + command;
        this.nestedCommands = new NestedCommand(this);
    }

    public MainCommand setHeader(String header) {
        this.header = header;
        return this;
    }

    /*
        Specify command description
    */
    public MainCommand setDescription(String description) {
        this.description = description;
        return this;
    }

    public MainCommand setSortHelp(boolean sortHelp) {
        this.sortHelp = sortHelp;
        return this;
    }

    /*
        Add a single sub command.
    */
    public MainCommand addSubCommand(AbstractCommand command) {
        nestedCommands.addSubCommand(command);
        return this;
    }

    /*
        Add a multiple sub commands.
    */
    public MainCommand addSubCommands(AbstractCommand... commands) {
        nestedCommands.addSubCommands(commands);
        return this;
    }


    @Override
    public String getSyntax() {
        return "/" + command;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        /*
            Handled by CommandManager
        */
        return null;
    }

    @Override
    public String getPermissionNode() {
        /*
            Handled in plugin.yml
        */
        return null;
    }

    /*
        Run the command.
    */
    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        sender.sendMessage("");
        if (header != null) {
            sender.sendMessage(header);
        } else {
            sender.sendMessage(TextUtils.colorFormat("&eCoded by VirtusDevelops :3"));
        }
        if (nestedCommands != null) {
            List<String> commands = nestedCommands
                    .children
                    .values()
                    .stream()
                    .map(abstractCommand -> abstractCommand.getCommands().get(0))
                    .collect(Collectors.toList());

            if (sortHelp) {
                Collections.sort(commands);
            }
            boolean isPlayer = sender instanceof Player;
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + getSyntax() + ChatColor.GRAY + " - " + getDescription());
            for (String commandString : commands) {
                final AbstractCommand cmd = nestedCommands.children.get(commandString);
                if (cmd == null) continue;
                if (!isPlayer) {
                    sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + cmd.getSyntax() + ChatColor.GRAY + " - " + cmd.getDescription());
                } else if (cmd.getPermissionNode() == null || sender.hasPermission(cmd.getPermissionNode())) {
                    sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + cmd.getSyntax() + ChatColor.GRAY + " - " + cmd.getDescription());
                }
            }
        }
        sender.sendMessage("");
        return ReturnType.SUCCESS;
    }


}

