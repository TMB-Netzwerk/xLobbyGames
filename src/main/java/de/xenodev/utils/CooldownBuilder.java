package de.xenodev.utils;

import de.xenodev.xLobbyGames;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class CooldownBuilder {
    public static HashMap<Player, Integer> fireballSpeedCooldown = new HashMap<>();
    public static HashMap<Player, Integer> fireballDodgeCooldown = new HashMap<>();
    public static HashMap<Player, Integer> fireballSpawnCooldown = new HashMap<>();

    public static void handlePlayerCooldown(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(xLobbyGames.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player players : Bukkit.getOnlinePlayers()){
                    if(fireballSpeedCooldown.containsKey(players)){
                        fireballSpeedCooldown.replace(players, fireballSpeedCooldown.get(players), fireballSpeedCooldown.get(players) - 1);
                        if(fireballSpeedCooldown.get(players) == 0) fireballSpeedCooldown.remove(players);
                    }
                    if(fireballDodgeCooldown.containsKey(players)){
                        fireballDodgeCooldown.replace(players, fireballDodgeCooldown.get(players), fireballDodgeCooldown.get(players) - 1);
                        if(fireballDodgeCooldown.get(players) == 0) fireballDodgeCooldown.remove(players);
                    }
                    if(fireballSpawnCooldown.containsKey(players)){
                        fireballSpawnCooldown.replace(players, fireballSpawnCooldown.get(players), fireballSpawnCooldown.get(players) - 1);
                        if(fireballSpawnCooldown.get(players) == 0) fireballSpawnCooldown.remove(players);
                    }
                }
            }
        }, 0, 20L);
    }

}
