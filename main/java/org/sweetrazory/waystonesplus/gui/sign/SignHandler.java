package org.sweetrazory.waystonesplus.gui.sign;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.sweetrazory.waystonesplus.Main;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

public class SignHandler implements Listener {
    private final ProtocolManager protocolManager;
    private final Map<UUID, BiFunction<Player, String[], String[]>> callbacks;

    public SignHandler() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.callbacks = new HashMap<>();
        Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
        protocolManager.addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                UUID playerId = player.getUniqueId();

                if (callbacks.containsKey(playerId)) {
                    String[] lines = event.getPacket().getStringArrays().read(0);
                    BiFunction<Player, String[], String[]> callback = callbacks.get(playerId);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            String[] editedLines = callback.apply(player, lines);
                            if (editedLines != null && editedLines.length == 4) {
                                sendSignChangePacket(player);
                            }
                        }
                    }.runTask(plugin);

                    callbacks.remove(playerId);
                }
            }
        });
    }

    public void open(Player player, BiFunction<Player, String[], String[]> callback) {
        Location signLocation = player.getLocation();
        BlockPosition position = new BlockPosition(signLocation.getBlockX(), signLocation.getBlockY(),
                signLocation.getBlockZ());

        PacketContainer signEditorPacket = protocolManager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        signEditorPacket.getBlockPositionModifier().write(0, position);

        try {
            protocolManager.sendServerPacket(player, signEditorPacket);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (callback != null) {
            callbacks.put(player.getUniqueId(), callback);
        }
    }

    private void sendSignChangePacket(Player player) throws FieldAccessException {
        PacketContainer signChangePacket = protocolManager.createPacket(PacketType.Play.Server.TILE_ENTITY_DATA);

        try {
            protocolManager.sendServerPacket(player, signChangePacket);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null &&
                event.getClickedBlock().getState() instanceof org.bukkit.block.Sign) {
            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();
            UUID playerId = player.getUniqueId();

            if (callbacks.containsKey(playerId)) {
                event.setCancelled(true);
                String[] lines = sign.getLines();
                BiFunction<Player, String[], String[]> callback = callbacks.get(playerId);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        String[] editedLines = callback.apply(player, lines);
                        if (editedLines != null && editedLines.length >= 4) {
                            Block clickedBlock = event.getClickedBlock();
                            if (clickedBlock != null) {
                                sendSignChangePacket(player);
                            }
                        }
                    }
                }.runTask(Main.getInstance());


                callbacks.remove(playerId);
            }
        }
    }
}
