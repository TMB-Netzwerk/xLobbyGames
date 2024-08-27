package de.xenodev.games.fireball;

import de.xenodev.mysql.EventAPI;
import de.xenodev.mysql.PlayersAPI;
import de.xenodev.mysql.SettingAPI;
import de.xenodev.mysql.FireBallGameAPI;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.xLobbyGames;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.ArrayList;

public class FireBallBuilder {

    public static ArrayList<Player> fireballPlayers = new ArrayList<>();

    public static void quitGame(Player player){
        player.getInventory().clear();
        if(SettingAPI.getSetting(player.getUniqueId(), "enterhaken").equals("true")){
            player.getInventory().setItem(0, new ItemBuilder(Material.FISHING_ROD).setName("§7» §9Enterhaken §7«").setUnbreakable().build());
        }else if(SettingAPI.getSetting(player.getUniqueId(), "flugstab").equals("true")){
            player.getInventory().setItem(0, new ItemBuilder(Material.BLAZE_ROD).setName("§7» §9Flugstab §7«").build());
        }else if(SettingAPI.getSetting(player.getUniqueId(), "eggbomb").equals("true")){
            player.getInventory().setItem(0, new ItemBuilder(Material.EGG).setName("§7» §9Eggbomb §7«").build());
        }else if(SettingAPI.getSetting(player.getUniqueId(), "enderperl").equals("true")){
            player.getInventory().setItem(0, new ItemBuilder(Material.ENDER_PEARL).setName("§7» §9Enderperle §7«").build());
        }else if(SettingAPI.getSetting(player.getUniqueId(), "switchbow").equals("true")){
            player.getInventory().setItem(0, new ItemBuilder(Material.BOW).setName("§7» §9Switch Bow §7«").build());
        }else if(SettingAPI.getSetting(player.getUniqueId(), "switchbow").equals("false") && SettingAPI.getSetting(player.getUniqueId(), "enderperl").equals("false") && SettingAPI.getSetting(player.getUniqueId(), "eggbomb").equals("false") && SettingAPI.getSetting(player.getUniqueId(), "flugstab").equals("false") && SettingAPI.getSetting(player.getUniqueId(), "enterhaken").equals("false")){
            player.getInventory().setItem(0, new ItemBuilder(Material.BARRIER).setName("§7» §4Kein Gadget ausgewählt §7«").build());
        }
        player.getInventory().setItem(3, new ItemBuilder(Material.NETHER_STAR).setName("§7» §eLobby Games §7«").build());
        player.getInventory().setItem(5, new ItemBuilder(Material.COMPASS).setName("§7» §6Navigator §7«").build());
        if(EventAPI.getEvent().equalsIgnoreCase("Christmas")){
            player.getInventory().setItem(7, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("45368f5635ff6c3407f0f356c5b6e0947bcd5e38490c9aa8b8b582a4f21ae3cb").setName("§7» §cAdventskalender §7«").build());
        }
        player.getInventory().setItem(8, new ItemBuilder(Material.PLAYER_HEAD).setHeadByName(player.getName()).setName("§7» §aProfil §7«").build());
        File file = new File("plugins/xLobby", "config.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        Location location = cfg.getLocation("Locations.Spawn");
        player.teleport(location);
        player.setAllowFlight(true);
        int coins = FireBallGameAPI.getAlive(player.getUniqueId()) * 5;
        player.sendMessage(xLobbyGames.getPrefix() + "§7Du hast TnT Fight verlassen" + " §7[§8+§e" + coins + "§7]");
        PlayersAPI.addCoins(player.getUniqueId(), coins);
        FireBallGameAPI.setAlive(player.getUniqueId(), 0);
        player.setHealth(20);
    }

    public static void respawn(Player player, Integer time){
        player.getInventory().clear();
        Bukkit.getScheduler().runTaskLater(xLobbyGames.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.spigot().respawn();
                player.getInventory().setItem(0, new ItemBuilder(Material.FIRE_CHARGE).setName("§7» §6Feuerball §7«").setLore("§7§oWerfbar").build());
                player.getInventory().setItem(1, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6Dodge §7«").build());
                player.getInventory().setItem(2, new ItemBuilder(Material.FEATHER).setName("§7» §6Speed §7«").build());
                player.getInventory().setItem(8, new ItemBuilder(Material.BARRIER).setName("§7» §cQuit §7«").build());
                File file = new File("plugins/xLobby", "config.yml");
                YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                Location location = cfg.getLocation("Locations.Spawn");
                player.teleport(location);
            }
        }, time);
    }

    public static void getPlayerDirection(Player player, Action action) {
        Vector vector = player.getVelocity();
        double rotation = (player.getLocation().getYaw() - 180) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        boolean a = action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK);
        boolean b = action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK);
        if (0 <= rotation && rotation < 22.5) {
            if(a){
                vector.setX(-1);
            }else if(b){
                vector.setX(+1);
            }
        } else if (22.5 <= rotation && rotation < 67.5) {
            if(a){
                vector.setX(-1);
                vector.setZ(-1);
            }else if(b){
                vector.setX(1);
                vector.setZ(1);
            }
        } else if (67.5 <= rotation && rotation < 112.5) {
            if(a){
                vector.setZ(-1);
            }else if(b){
                vector.setZ(1);
            }
        } else if (112.5 <= rotation && rotation < 157.5) {
            if(a){
                vector.setX(1);
                vector.setZ(-1);
            }else if(b){
                vector.setX(-1);
                vector.setZ(1);
            }
        } else if (157.5 <= rotation && rotation < 202.5) {
            if(a){
                vector.setX(1);
            }else if(b){
                vector.setX(-1);
            }
        } else if (202.5 <= rotation && rotation < 247.5) {
            if(a){
                vector.setX(1);
                vector.setZ(1);
            }else if(b){
                vector.setX(-1);
                vector.setZ(-1);
            }
        } else if (247.5 <= rotation && rotation < 292.5) {
            if(a){
                vector.setZ(1);
            }else if(b){
                vector.setZ(-1);
            }
        } else if (292.5 <= rotation && rotation < 337.5) {
            if(a){
                vector.setX(-1);
                vector.setZ(1);
            }else if(b){
                vector.setX(1);
                vector.setZ(-1);
            }
        } else if (337.5 <= rotation && rotation < 360.0) {
            if(a){
                vector.setX(-1);
            }else if(b){
                vector.setX(+1);
            }
        } else {
            player.sendMessage("None");
        }
        player.setVelocity(vector);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 50F, 0.1F);
    }

    public static void handleFireBallUptime(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(xLobbyGames.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player players : fireballPlayers){
                    if(fireballPlayers.size() >= 3) {
                        FireBallGameAPI.addAlive(players.getUniqueId(), 1);
                    }
                }
            }
        }, 0, 20L);
    }
}
