package de.xenodev.games.fireball;

import de.xenodev.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class FireBallJoinEvent implements Listener {

    @EventHandler
    public void handleFireBallJoin(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase("§7» §eLobby Games §7«")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE) || event.getCurrentItem().getType().equals(Material.AIR) || event.getCurrentItem().getType().equals(Material.NETHER_STAR)) return;

            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §cFireball Fight §7«")) {
                if (!FireBallBuilder.fireballPlayers.contains(player)) {
                    FireBallBuilder.fireballPlayers.add(player);
                    player.getInventory().clear();
                    player.setAllowFlight(false);
                    player.getInventory().setItem(0, new ItemBuilder(Material.FIRE_CHARGE).setName("§7» §6Feuerball §7«").setLore("§7§oWerfbar").build());
                    player.getInventory().setItem(1, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6Dodge §7«").build());
                    player.getInventory().setItem(2, new ItemBuilder(Material.FEATHER).setName("§7» §6Speed §7«").build());
                    player.getInventory().setItem(8, new ItemBuilder(Material.BARRIER).setName("§7» §cQuit §7«").build());
                }
            }
        }

    }

}
