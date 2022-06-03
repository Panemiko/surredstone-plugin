package surredstone;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Message {
    public static String ON_PLUGIN_ENABLE = ChatColor.GREEN + "Plugin Surredstone habilitado";
    public static String ON_PLUGIN_DISABLE = ChatColor.RED + "Plugin Surredstone desabilitado";
    public static String INVALID_VILLAGE_STORAGE_ERROR = ChatColor.RED + "Armazenamento da Vila inv\u00E1lido";
    public static String COMMAND_MUST_BE_EXECUTED_BY_PLAYER = ChatColor.RED
            + "Este comando deve ser executado por um player";
    public static String PLAYER_WITHOUT_VILLAGE = ChatColor.RED
            + "Voc\u00EA deve estar em uma vila para executar esse comando";
    public static String PERMISSIONS_INSUFICIENTS = ChatColor.RED + "Permiss\u00F5es insuficientes";
    public static String VILLAGE_NOT_FOUND = ChatColor.RED + "Vila n\u00E3o encontrada";
    public static String SUBCOMMAND_INVALID = ChatColor.RED + "Subcomando inv\u00E1lido";
    public static String DISCORD = ChatColor.AQUA + "[DC]";
    public static String GLOBAL = ChatColor.GOLD + "Global";
    public static String VILLAGE = ChatColor.GOLD + "Vila";
    public static String BOT_LOGIN_FAILURE = ChatColor.RED + "Falha ao conectar ao Discord";
    public static String BOT_LOGIN_SUCCESS = ChatColor.GREEN + "Bot connectado como %s";
    public static String INFO_INFO = ChatColor.AQUA + "Informa\u00E7\u00F5es: %s";
    public static String INFO_MEMBERS = ChatColor.AQUA + "Membros online: %s";
    public static String PARAM_INSUFICIENTS = ChatColor.RED + "Par\u00E2metros insuficientes";

    private String name;
    private String message;
    private boolean fromDiscord;
    private Player player;

    public Message(String name, String message, boolean fromDiscord) {
        this.name = name;
        this.message = message;

        this.player = Plugin.getInstance().getServer().getPlayer(name);
    }

    private String getDiscordNotation() {
        return (fromDiscord) ? DISCORD : null;
    }

    private String getPlayerCompleteVillagePrefix() {
        if (player == null) {
            return "";
        }

        Village playerVillage = Village.getVillageByPlayer(player);

        return playerVillage.getTextColor() + "[" + playerVillage.getName() + "]";
    }

    private String getPlayerAbbreviationVillagePrefix() {
        if (player == null) {
            return null;
        }

        Village playerVillage = Village.getVillageByPlayer(player);

        return playerVillage.getTextColor() + "[" + playerVillage.getAbbreviation().toUpperCase() + "]";
    }

    private String getPlayerName() {
        return ChatColor.WHITE + name + ":";
    }

    private void consoleLogMessage(String message) {
        Plugin.getInstance().getServer().getConsoleSender().sendMessage(message);
    }

    private void sendMessageToEveryone(String message) {
        for (Player player : Plugin.getInstance().getServer().getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    private void sendMessageToVillage(Village village, String message) {
        for (Player player : village.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public String sendGlobalMessage() {
        List<String> splittedMessage = new ArrayList<String>();

        splittedMessage.add(getDiscordNotation());
        splittedMessage.add(GLOBAL);
        splittedMessage.add(getPlayerCompleteVillagePrefix());
        splittedMessage.add(getPlayerName());
        splittedMessage.add(message);

        splittedMessage.removeIf(message -> (message == null));
        String finalMessage = String.join(" ", splittedMessage);

        consoleLogMessage(finalMessage);
        sendMessageToEveryone(finalMessage);

        return finalMessage;
    }

    public String sendVillageMessage(Village village) {
        List<String> splittedMessage = new ArrayList<String>();

        splittedMessage.add(getDiscordNotation());
        splittedMessage.add(VILLAGE);
        splittedMessage.add(getPlayerAbbreviationVillagePrefix());
        splittedMessage.add(getPlayerName());
        splittedMessage.add(message);

        splittedMessage.removeIf(message -> (message == null));
        String finalMessage = String.join(" ", splittedMessage);

        consoleLogMessage(finalMessage);
        sendMessageToVillage(village, finalMessage);
        Bot.getInstance().sendMessageToVillage(village, finalMessage);

        return finalMessage;
    }
}
