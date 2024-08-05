package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.memoryhandlers.LangManager;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.menu.MenuManager;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.ItemBuilder;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.waystone.Waystone;

public class ParticleMenu extends Menu {
    public ParticleMenu() {
        super(27, ColoredText.getText(LangManager.particleMenuTitle), 0);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {
        ItemStack filler = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayName(" ").build();
        inventory.setContents(new ItemStack[]
                {filler, filler, filler, filler, filler, filler, filler, filler, filler,
                        filler, filler, null, filler, filler, filler, null, filler, filler,
                        filler, filler, filler, filler, filler, filler, filler, filler, filler});
        ItemStack enchant = new ItemBuilder(Material.ENCHANTED_BOOK)
                .persistentData("action", "enchantedParticle")
                .build();
        setItem(10, enchant);

        ItemStack hearts = new ItemBuilder(Material.RED_DYE)
                .persistentData("action", "heartsParticle")
                .build();
        setItem(11, hearts);

        ItemStack angry = new ItemBuilder(Material.FIRE_CHARGE)
                .persistentData("action", "angryParticle")
                .build();
        setItem(12, angry);

        ItemStack happy = new ItemBuilder(Material.GREEN_DYE)
                .persistentData("action", "happyParticle")
                .build();
        setItem(13, happy);

        ItemStack note = new ItemBuilder(Material.NOTE_BLOCK)
                .persistentData("action", "noteParticle")
                .build();
        setItem(14, note);

        ItemStack nether = new ItemBuilder(Material.OBSIDIAN)
                .persistentData("action", "netherParticle")
                .build();
        setItem(15, nether);

        ItemStack off = new ItemBuilder(Material.BARRIER)
                .persistentData("action", "offParticle")
                .build();
        setItem(16, off);

        ItemStack back = new ItemBuilder(Material.BARRIER)
                .persistentData("action", "menu")
                .displayName(ColoredText.getText(LangManager.returnText))
                .build();
        setItem(22, back);
    }

    @Override
    public void handleClick(Player player, ItemStack item) {
        String action = ItemUtils.getPersistentString(item, "action");
        if (action != null) {
            switch (action) {
                case "offParticle":
                    waystone.setParticle(null);
                    break;
                case "angryParticle":
                    waystone.setParticle(Particle.ANGRY_VILLAGER);
                    break;
                case "happyParticle":
                    waystone.setParticle(Particle.HAPPY_VILLAGER);
                    break;
                case "noteParticle":
                    waystone.setParticle(Particle.NOTE);
                    break;
                case "netherParticle":
                    waystone.setParticle(Particle.PORTAL);
                    break;
                case "enchantedParticle":
                    waystone.setParticle(Particle.ENCHANT);
                    break;
                case "heartsParticle":
                    waystone.setParticle(Particle.HEART);
                    break;
                case "menu":
                    Menu settingsMenu = new SettingsMenu();
                    MenuManager.openMenu(player, settingsMenu, waystone);
                    break;
            }
        }
    }
}
