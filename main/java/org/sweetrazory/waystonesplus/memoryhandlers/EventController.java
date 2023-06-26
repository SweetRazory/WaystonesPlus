package org.sweetrazory.waystonesplus.memoryhandlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.sweetrazory.waystonesplus.eventhandlers.*;
import org.sweetrazory.waystonesplus.gui.inventory.ClickHandler;
import org.sweetrazory.waystonesplus.gui.inventory.DragHandler;
import org.sweetrazory.waystonesplus.gui.inventory.screens.InventoryMemory;

public class EventController implements Listener {
    private final WaystoneMemory waystoneMemory;
    private final InventoryMemory menu;

    public EventController(WaystoneMemory waystoneMemory, InventoryMemory menu) {
        this.waystoneMemory = waystoneMemory;
        this.menu = menu;
    }

    @EventHandler
    public void onWaystoneCraft(CraftItemEvent event) {
        new OnWaystoneCraft(event);
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
    public void clickHandler(InventoryClickEvent e) {
        new ClickHandler(e, menu);
    }

    @EventHandler
    public void dragHandler(InventoryDragEvent e) {
        new DragHandler(e);
    }

    @EventHandler
    public void onWaystoneInteract(PlayerInteractEvent e) {
        new OnWaystoneInteract(waystoneMemory, e, menu);
    }
}
