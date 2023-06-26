package org.sweetrazory.waystonesplus.memoryhandlers;

import org.sweetrazory.waystonesplus.gui.inventory.screens.InventoryMemory;
import org.sweetrazory.waystonesplus.types.WaystoneType;

import java.util.List;

public class MemoryManager {
    private static WaystoneMemory waystoneMemory;
    private static InventoryMemory inventoryMemory;
    private static List<WaystoneType> waystoneTemplates;

    public MemoryManager() {
        waystoneMemory = new WaystoneMemory();
        inventoryMemory = new InventoryMemory();
    }

    public static WaystoneMemory getWaystoneMemory() {
        return waystoneMemory;
    }

    public static InventoryMemory getInventoryMemory() {
        return inventoryMemory;
    }

    public static List<WaystoneType> getWaystoneTemplates() {
        return waystoneTemplates;
    }
}
