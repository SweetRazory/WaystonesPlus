package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.ItemBuilder;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.waystone.Waystone;

public class ParticleMenu extends Menu {
    public ParticleMenu() {
        super(45, ColoredText.getText("&8Particle settings"), 0);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {
        ItemStack enchant = new ItemBuilder(Material.ENCHANTED_BOOK).persistentData("action", "enchantedParticle").build();
        setItem(0, enchant);

        ItemStack hearts = new ItemBuilder(Material.RED_DYE).persistentData("action", "heartsParticle").build();
        setItem(1, hearts);

        ItemStack angry = new ItemBuilder(Material.FIRE_CHARGE).persistentData("action", "angryParticle").build();
        setItem(2, angry);

        ItemStack bubble = new ItemBuilder(Material.WATER_BUCKET).persistentData("action", "bubbleParticle").build();
        setItem(3, bubble);

        ItemStack cherry = new ItemBuilder(Material.DRAGON_BREATH).persistentData("action", "dragonParticle").build();
        setItem(4, cherry);

        ItemStack sculk = new ItemBuilder(Material.SCULK_SENSOR).persistentData("action", "sculkParticle").build();
        setItem(5, sculk);

        ItemStack happy = new ItemBuilder(Material.GREEN_DYE).persistentData("action", "happyParticle").build();
        setItem(6, happy);

        ItemStack note = new ItemBuilder(Material.NOTE_BLOCK).persistentData("action", "noteParticle").build();
        setItem(7, note);

        ItemStack totem = new ItemBuilder(Material.TOTEM_OF_UNDYING).persistentData("action", "totemParticle").build();
        setItem(8, totem);

        ItemStack off = new ItemBuilder(Material.BARRIER).persistentData("action", "offParticle").build();
        setItem(8, off);
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
                    waystone.setParticle(Particle.VILLAGER_ANGRY);
                    break;
                case "bubbleParticle":
                    waystone.setParticle(Particle.BUBBLE_COLUMN_UP);
                    break;
                case "dragonParticle":
                    waystone.setParticle(Particle.DRAGON_BREATH);
                    break;
                case "sculkParticle":
                    waystone.setParticle(Particle.SCULK_CHARGE_POP);
                    break;
                case "happyParticle":
                    waystone.setParticle(Particle.VILLAGER_HAPPY);
                    break;
                case "noteParticle":
                    waystone.setParticle(Particle.NOTE);
                    break;
                case "totemParticle":
                    waystone.setParticle(Particle.TOTEM);
                    break;
                case "enchantedParticle":
                    waystone.setParticle(Particle.ENCHANTMENT_TABLE);
                    break;
                case "heartsParticle":
                    waystone.setParticle(Particle.HEART);
                    break;
            }
        }
    }
}
