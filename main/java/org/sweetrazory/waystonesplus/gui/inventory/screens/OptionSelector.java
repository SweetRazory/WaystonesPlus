package org.sweetrazory.waystonesplus.gui.inventory.screens;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.sweetrazory.waystonesplus.Main;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.gui.inventory.MenuInventory;
import org.sweetrazory.waystonesplus.waystone.Waystone;

public class OptionSelector {
    private final InventoryClickEvent event;
    private final MenuInventory menu;
    private final Waystone waystone;

    public OptionSelector(InventoryClickEvent event, MenuInventory menu, Waystone waystone) {
        this.event = event;
        this.menu = menu;
        this.waystone = waystone;
    }

    public void InventoryClickHandler() {
        event.setCancelled(true);
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null) {
            return;
        }

        if (currentItem.getItemMeta() == null) {
            return;
        }

        NamespacedKey clickEvent = new NamespacedKey(Main.getInstance(), "clickEvent");

        String clickEventValue = currentItem.getItemMeta().getPersistentDataContainer().get(clickEvent, PersistentDataType.STRING);
        if (clickEventValue != null) {
            switch (clickEventValue) {
                case "openWaystoneList":
                    menu.openWaystoneList(waystone);
                    break;
//                case "openWaystoneSettings":
//                    break;
                case "toggleWaystoneVisibility":
                    if (event.getWhoClicked().isOp() || waystone.getOwnerId().equals(event.getWhoClicked().getUniqueId().toString())) {
                        if (waystone.getVisibility().equals(Visibility.PRIVATE)) {
                            waystone.setVisibility(Visibility.PUBLIC);
                        } else if (waystone.getVisibility().equals(Visibility.PUBLIC)) {
                            waystone.setVisibility(Visibility.PRIVATE);
                        } else if (waystone.getVisibility().equals(Visibility.GLOBAL)) {
                            waystone.setVisibility(Visibility.PRIVATE);
                        }
                    }
                    menu.openGUI((Player) event.getWhoClicked(), waystone);
                    break;
                default:
                    break;
            }
        }
    }

}
