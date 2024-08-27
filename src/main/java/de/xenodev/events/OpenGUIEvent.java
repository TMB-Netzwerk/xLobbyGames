package de.xenodev.events;

import de.xenodev.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class OpenGUIEvent implements Listener {

    @EventHandler
    public void handleOpenGui(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(event.getItem().getType().equals(Material.NETHER_STAR)) {
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §eLobby Games §7«")) {
                Player player = event.getPlayer();

                Inventory gamesInventory = Bukkit.createInventory(player, 9*3, "§7» §eLobby Games §7«");

                for (int i = 0; i < 27; i++) {
                    gamesInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                }

                gamesInventory.setItem(1, new ItemBuilder(Material.FIRE_CHARGE).setName("§7» §cFireball Fight §7«").build());

                player.openInventory(gamesInventory);
            }
        }
    }

}
