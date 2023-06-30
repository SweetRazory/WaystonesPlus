package org.sweetrazory.waystonesplus.memoryhandlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.eventhandlers.AnvilRenameEvent;
import org.sweetrazory.waystonesplus.eventhandlers.InventoryClickEventHandler;
import org.sweetrazory.waystonesplus.eventhandlers.OnWaystoneBreak;
import org.sweetrazory.waystonesplus.eventhandlers.OnWaystoneCraft;
import org.sweetrazory.waystonesplus.eventhandlers.PlayerInteractListener;
import org.sweetrazory.waystonesplus.eventhandlers.WaystoneInteract;
import org.sweetrazory.waystonesplus.gui.inventory.DragHandler;
import org.sweetrazory.waystonesplus.gui.inventory.MenuInventory;
import org.sweetrazory.waystonesplus.waystone.Waystone;

public class EventController implements Listener {
    private final WaystoneMemory waystoneMemory;

    private final WaystoneInteract waystoneInteract;

    public EventController(WaystoneMemory waystoneMemory) {
        this.waystoneMemory = waystoneMemory;

        waystoneInteract = new WaystoneInteract();
    }


    @EventHandler
    public void onWaystoneCraft(CraftItemEvent event) {
        new OnWaystoneCraft(event);
    }

    @EventHandler
    public void onWaystoneInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Waystone waystone = waystoneInteract.getInteractedWaystone(event);

        if (waystone != null) {
            MenuInventory menu = new MenuInventory(waystone);

            menu.openGUI(player, waystone);

            Bukkit.getPluginManager().registerEvents(new InventoryClickEventHandler(player, menu), WaystonesPlus.getInstance());
        }
    }


    @EventHandler
    public void anvilRenameEvent(PrepareAnvilEvent event) {
        new AnvilRenameEvent(event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        new PlayerInteractListener(waystoneMemory).onBlockPlace(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWaystoneBreak(BlockBreakEvent e) {
        new OnWaystoneBreak(waystoneMemory, e);
    }


    @EventHandler
    public void dragHandler(InventoryDragEvent e) {
        new DragHandler(e);
    }
}
