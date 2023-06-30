package org.sweetrazory.waystonesplus.memoryhandlers;

public class MemoryManager {
    private static final WaystoneMemory waystoneMemory = new WaystoneMemory();

    public static WaystoneMemory getWaystoneMemory() {
        return waystoneMemory;
    }
}
