package de.xenodev.games.fireball;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class FireBallQuitEvent implements Listener {

    @EventHandler
    public void handleFireBallQuit(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(event.getItem().getType().equals(Material.BARRIER)) {
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §cQuit §7«")) {
                Player player = event.getPlayer();
                if(FireBallBuilder.fireballPlayers.contains(player)){
                    FireBallBuilder.fireballPlayers.remove(player);
                    FireBallBuilder.quitGame(player);
                }
            }
        }
    }

}
