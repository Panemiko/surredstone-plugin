package surredstone.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import surredstone.Message;
import surredstone.Village;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Village playerVillage = Village.getVillageByPlayer(event.getPlayer());

        if (playerVillage == null) {
            return;
        }

        new Message(event.getPlayer().getName(), event.getMessage(), false).sendVillageMessage(playerVillage);

        event.setCancelled(true);
    }
}