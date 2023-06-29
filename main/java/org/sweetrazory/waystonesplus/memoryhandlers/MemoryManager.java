package org.sweetrazory.waystonesplus.memoryhandlers;

public class MemoryManager {
    private static WaystoneMemory waystoneMemory;

    public MemoryManager() {
        waystoneMemory = new WaystoneMemory();
    }

    public static WaystoneMemory getWaystoneMemory() {
        return waystoneMemory;
    }
}
