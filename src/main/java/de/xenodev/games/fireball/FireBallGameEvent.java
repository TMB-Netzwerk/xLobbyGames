package de.xenodev.games.fireball;

import de.xenodev.mysql.PlayersAPI;
import de.xenodev.mysql.FireBallGameAPI;
import de.xenodev.xLobbyGames;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class FireBallGameEvent implements Listener {

    @EventHandler
    public void handlePlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        Player killer = player.getKiller();

        FireBallBuilder.respawn(player, 3);
        int currentCoins = FireBallGameAPI.getAlive(player.getUniqueId()) * 5;
        int killerCoins = currentCoins / 2;
        int playerCoins = currentCoins - killerCoins;

        FireBallGameAPI.addDeaths(player.getUniqueId(), 1);
        PlayersAPI.addCoins(player.getUniqueId(), playerCoins);
        if(player != killer){
            FireBallGameAPI.addKills(killer.getUniqueId(), 1);
            PlayersAPI.addCoins(killer.getUniqueId(), killerCoins + 10);
            player.sendMessage(xLobbyGames.getPrefix() + "§cDu wurdest von " + killer.getName() + " getötet" + " §7[§8+§e" + playerCoins + "§7]");
            killer.sendMessage(xLobbyGames.getPrefix() + "§aDu hast " + player.getName() + " getötet" + " §7[§8+§e" + killerCoins + "§7]");
        }else{
            player.sendMessage(xLobbyGames.getPrefix() + "§cDu hast dich selbst getötet" + " §7[§8+§e" + playerCoins + "§7]");
        }
        event.setDeathMessage("");
        FireBallGameAPI.setAlive(player.getUniqueId(), 0);
    }

    @EventHandler
    public void handlePlayerDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(event.getDamager() instanceof Fireball) {
                Fireball fireball = (Fireball) event.getDamager();
                Player shooter = (Player) fireball.getShooter();
                if (FireBallBuilder.fireballPlayers.contains(player)) {
                    if (shooter != player) {
                        if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
                            event.setDamage(4);
                        } else {
                            event.setCancelled(true);
                        }
                    } else {
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void handleTnTExplode(EntityExplodeEvent event){
        if(event.getEntityType().equals(EntityType.PRIMED_TNT)){
            event.blockList().clear();
        }
    }
}
