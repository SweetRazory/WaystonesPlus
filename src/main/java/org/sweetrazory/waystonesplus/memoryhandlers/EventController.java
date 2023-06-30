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
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.eventhandlers.*;
import org.sweetrazory.waystonesplus.gui.inventory.DragHandler;
import org.sweetrazory.waystonesplus.gui.inventory.MenuInventory;
import org.sweetrazory.waystonesplus.items.WaystoneSummonItem;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public void onLootGenerate(LootGenerateEvent event) {
        List<ItemStack> loot = new ArrayList<>(event.getLoot());
        if (new Random().nextInt(100) + 1 <= ConfigManager.lootSpawnChance) {
            Object[] waystoneTypes = WaystoneMemory.getWaystoneTypes().keySet().toArray(new Object[0]);
            int randomNumber = new Random().nextInt(WaystoneMemory.getWaystoneTypes().size());
            ItemStack waystoneItem = new WaystoneSummonItem()
                    .getLodestoneHead(
                            WaystonesPlus.coloredText(ConfigManager.defaultWaystoneName),
                            waystoneTypes[randomNumber].toString(),
                            null,
                            null,
                            Visibility.PRIVATE
                    );
            loot.add(waystoneItem);
        }
        event.setLoot(loot);
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
