package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.memoryhandlers.LangManager;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.menu.MenuManager;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.DB;
import org.sweetrazory.waystonesplus.utils.ItemBuilder;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.ArrayList;
import java.util.Arrays;

public class SolidBlockMenu extends Menu {
    private final Material[] elements = new ArrayList<Material>() {{
        add(Material.COBBLESTONE);
        add(Material.OAK_PLANKS);
        add(Material.SPRUCE_PLANKS);
        add(Material.BIRCH_PLANKS);
        add(Material.JUNGLE_PLANKS);
        add(Material.ACACIA_PLANKS);
        add(Material.DARK_OAK_PLANKS);
        add(Material.OAK_WOOD);
        add(Material.SPRUCE_WOOD);
        add(Material.BIRCH_WOOD);
        add(Material.JUNGLE_WOOD);
        add(Material.ACACIA_WOOD);
        add(Material.DARK_OAK_WOOD);
        add(Material.STONE);
        add(Material.GRANITE);
        add(Material.POLISHED_GRANITE);
        add(Material.DIORITE);
        add(Material.POLISHED_DIORITE);
        add(Material.ANDESITE);
        add(Material.POLISHED_ANDESITE);
        add(Material.GRASS_BLOCK);
        add(Material.DIRT);
        add(Material.COARSE_DIRT);
        add(Material.PODZOL);
        add(Material.COBBLESTONE_STAIRS);
        add(Material.OAK_STAIRS);
        add(Material.SPRUCE_STAIRS);
        add(Material.BIRCH_STAIRS);
        add(Material.JUNGLE_STAIRS);
        add(Material.ACACIA_STAIRS);
        add(Material.DARK_OAK_STAIRS);
        add(Material.SANDSTONE);
        add(Material.CHISELED_SANDSTONE);
        add(Material.CUT_SANDSTONE);
        add(Material.SMOOTH_SANDSTONE);
        add(Material.RED_SANDSTONE);
        add(Material.CHISELED_RED_SANDSTONE);
        add(Material.CUT_RED_SANDSTONE);
        add(Material.SMOOTH_RED_SANDSTONE);
        add(Material.BRICKS);
        add(Material.STONE_BRICKS);
        add(Material.MOSSY_STONE_BRICKS);
        add(Material.CRACKED_STONE_BRICKS);
        add(Material.CHISELED_STONE_BRICKS);
        add(Material.NETHER_BRICKS);
        add(Material.RED_NETHER_BRICKS);
        add(Material.QUARTZ_BLOCK);
        add(Material.CHISELED_QUARTZ_BLOCK);
        add(Material.SMOOTH_QUARTZ);
        add(Material.PRISMARINE);
        add(Material.PRISMARINE_BRICKS);
        add(Material.DARK_PRISMARINE);
        add(Material.SEA_LANTERN);
        add(Material.END_STONE);
        add(Material.BLACK_CONCRETE);
        add(Material.RED_CONCRETE);
        add(Material.GREEN_CONCRETE);
        add(Material.BROWN_CONCRETE);
        add(Material.BLUE_CONCRETE);
        add(Material.PURPLE_CONCRETE);
        add(Material.CYAN_CONCRETE);
        add(Material.LIGHT_GRAY_CONCRETE);
        add(Material.GRAY_CONCRETE);
        add(Material.PINK_CONCRETE);
        add(Material.LIME_CONCRETE);
        add(Material.YELLOW_CONCRETE);
        add(Material.LIGHT_BLUE_CONCRETE);
        add(Material.MAGENTA_CONCRETE);
        add(Material.ORANGE_CONCRETE);
        add(Material.WHITE_CONCRETE);
        add(Material.BLACK_CONCRETE_POWDER);
        add(Material.RED_CONCRETE_POWDER);
        add(Material.GREEN_CONCRETE_POWDER);
        add(Material.BROWN_CONCRETE_POWDER);
        add(Material.BLUE_CONCRETE_POWDER);
        add(Material.PURPLE_CONCRETE_POWDER);
        add(Material.CYAN_CONCRETE_POWDER);
        add(Material.LIGHT_GRAY_CONCRETE_POWDER);
        add(Material.GRAY_CONCRETE_POWDER);
        add(Material.PINK_CONCRETE_POWDER);
        add(Material.LIME_CONCRETE_POWDER);
        add(Material.YELLOW_CONCRETE_POWDER);
        add(Material.LIGHT_BLUE_CONCRETE_POWDER);
        add(Material.MAGENTA_CONCRETE_POWDER);
        add(Material.ORANGE_CONCRETE_POWDER);
        add(Material.WHITE_CONCRETE_POWDER);
        add(Material.SAND);
        add(Material.RED_SAND);
        add(Material.GRAVEL);
        add(Material.GOLD_ORE);
        add(Material.IRON_ORE);
        add(Material.COAL_ORE);
        add(Material.LAPIS_ORE);
        add(Material.DIAMOND_ORE);
        add(Material.REDSTONE_ORE);
        add(Material.EMERALD_ORE);
        add(Material.NETHER_QUARTZ_ORE);
        add(Material.COAL_BLOCK);
        add(Material.IRON_BLOCK);
        add(Material.GOLD_BLOCK);
        add(Material.DIAMOND_BLOCK);
        add(Material.EMERALD_BLOCK);
        add(Material.LAPIS_BLOCK);
        add(Material.REDSTONE_BLOCK);
        add(Material.NETHERITE_BLOCK);
        add(Material.BONE_BLOCK);
        add(Material.WHITE_WOOL);
        add(Material.ORANGE_WOOL);
        add(Material.MAGENTA_WOOL);
        add(Material.LIGHT_BLUE_WOOL);
        add(Material.YELLOW_WOOL);
        add(Material.LIME_WOOL);
        add(Material.PINK_WOOL);
        add(Material.GRAY_WOOL);
        add(Material.LIGHT_GRAY_WOOL);
        add(Material.CYAN_WOOL);
        add(Material.PURPLE_WOOL);
        add(Material.BLUE_WOOL);
        add(Material.BROWN_WOOL);
        add(Material.GREEN_WOOL);
        add(Material.RED_WOOL);
        add(Material.BLACK_WOOL);
        add(Material.GOLD_BLOCK);
        add(Material.ICE);
        add(Material.PACKED_ICE);
        add(Material.BLUE_ICE);
        add(Material.OBSIDIAN);
        add(Material.CRYING_OBSIDIAN);
        add(Material.HONEY_BLOCK);
        add(Material.HONEYCOMB_BLOCK);
        add(Material.SLIME_BLOCK);
        add(Material.BASALT);
        add(Material.POLISHED_BASALT);
        add(Material.REDSTONE_LAMP);
        add(Material.GLOWSTONE);
        add(Material.SEA_PICKLE);
        add(Material.PRISMARINE_BRICK_SLAB);
        add(Material.DARK_PRISMARINE_SLAB);
        add(Material.POLISHED_GRANITE_SLAB);
        add(Material.SMOOTH_RED_SANDSTONE_SLAB);
        add(Material.MOSSY_STONE_BRICK_SLAB);
        add(Material.POLISHED_DIORITE_SLAB);
        add(Material.MOSSY_COBBLESTONE_SLAB);
        add(Material.END_STONE_BRICK_SLAB);
        add(Material.SMOOTH_SANDSTONE_SLAB);
        add(Material.SMOOTH_QUARTZ_SLAB);
        add(Material.GRANITE_SLAB);
        add(Material.ANDESITE_SLAB);
        add(Material.RED_NETHER_BRICK_SLAB);
        add(Material.POLISHED_ANDESITE_SLAB);
        add(Material.DIORITE_SLAB);
        add(Material.POLISHED_GRANITE_STAIRS);
        add(Material.SMOOTH_RED_SANDSTONE_STAIRS);
        add(Material.MOSSY_STONE_BRICK_STAIRS);
        add(Material.POLISHED_DIORITE_STAIRS);
        add(Material.MOSSY_COBBLESTONE_STAIRS);
        add(Material.END_STONE_BRICK_STAIRS);
        add(Material.SMOOTH_SANDSTONE_STAIRS);
        add(Material.SMOOTH_QUARTZ_STAIRS);
        add(Material.GRANITE_STAIRS);
        add(Material.ANDESITE_STAIRS);
        add(Material.RED_NETHER_BRICK_STAIRS);
        add(Material.POLISHED_ANDESITE_STAIRS);
        add(Material.DIORITE_STAIRS);
    }}.toArray(new Material[0]);

    public SolidBlockMenu(int page) {
        super(54, ColoredText.getText(LangManager.blocksMenuTitle), page);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {
        ItemStack backButton = new ItemBuilder(Material.BARRIER)
                .displayName(ColoredText.getText("&cReturn to option select"))
                .persistentData("action", "menu")
                .build();
        ItemStack filler = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayName(" ").build();
        inventory.setContents(new ItemStack[]
                {filler, filler, filler, filler, filler, filler, filler, filler, filler,
                        filler, null, null, null, null, null, null, null, filler,
                        filler, null, null, null, null, null, null, null, filler,
                        filler, null, null, null, null, null, null, null, filler,
                        filler, null, null, null, null, null, null, null, filler,
                        filler, filler, filler, filler, backButton, filler, filler, filler, filler});
        if (elements.length >= 28 * page + 1) {
            setItem(50, new ItemBuilder(Material.SNOWBALL).persistentData("action", "nextPage").persistentData("page", page).build());
        }

        if (page > 0) {
            setItem(48, new ItemBuilder(Material.SNOWBALL).persistentData("action", "prevPage").persistentData("page", page).build());
        }
        Material[] newElements = Arrays.copyOfRange(elements, page * 22, elements.length);
        int k = 0;
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                ItemStack item = new ItemBuilder(newElements[k]).persistentData("action", "setIcon").displayName(ColoredText.getText("&6" + newElements[k].name().toUpperCase())).build();
                setItem(i * 9 + j + 1, item);
                k++;
            }
        }
    }

    @Override
    public void handleClick(Player player, ItemStack item) {
        String action = ItemUtils.getPersistentString(item, "action");
        if (action != null) {
            Menu iconMenu = new IconMenu();
            switch (action) {
                case "menu":
                    MenuManager.openMenu(player, iconMenu, waystone);
                    break;
                case "nextPage":
                    Menu solidMenu = new SolidBlockMenu(page + 1);
                    MenuManager.openMenu(player, solidMenu, waystone);
                    break;
                case "prevPage":
                    Menu solidMenu2 = new SolidBlockMenu(page - 1);
                    MenuManager.openMenu(player, solidMenu2, waystone);
                    break;
                case "setIcon":
                    waystone.setIcon(item.getType());
                    MenuManager.openMenu(player, iconMenu, waystone);
                    DB.updateWaystone(waystone);
                    break;
            }
        }
    }

}
