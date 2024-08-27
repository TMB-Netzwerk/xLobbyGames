package de.xenodev.games.fireball;

import de.xenodev.utils.CooldownBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FireBallItemEvent implements Listener {

    @EventHandler
    public void handleFireBallSpawn(PlayerInteractEvent event){
        if (event.getItem() == null) return;
        if (event.getItem().getType().equals(Material.FIRE_CHARGE)) {
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Feuerball §7«")) {
                Player player = event.getPlayer();
                if(!CooldownBuilder.fireballSpawnCooldown.containsKey(player)) {
                    CooldownBuilder.fireballSpawnCooldown.put(player, 3);
                    Fireball fireball = player.launchProjectile(Fireball.class);
                    fireball.setGlowing(true);
                    fireball.setShooter(player);
                }
            }
        }
    }

    @EventHandler
    public void handleDodge(PlayerInteractEvent event){
        if (event.getItem() == null) return;
        if (event.getItem().getType().equals(Material.SLIME_BALL)) {
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Dodge §7«")) {
                Player player = event.getPlayer();
                if(!CooldownBuilder.fireballDodgeCooldown.containsKey(player)) {
                    CooldownBuilder.fireballDodgeCooldown.put(player, 6);
                    FireBallBuilder.getPlayerDirection(player, event.getAction());
                }
            }
        }
    }

    @EventHandler
    public void handleSpeed(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getType().equals(Material.FEATHER)) {
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Speed §7«")) {
                Player player = event.getPlayer();
                if(!CooldownBuilder.fireballSpeedCooldown.containsKey(player)) {
                    CooldownBuilder.fireballSpeedCooldown.put(player, 5);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 3, false));
                    player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 50F, 0.1F);
                }
            }
        }
    }
}
