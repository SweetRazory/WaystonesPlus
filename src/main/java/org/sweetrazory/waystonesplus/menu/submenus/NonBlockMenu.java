package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.menu.MenuManager;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.DB;
import org.sweetrazory.waystonesplus.utils.ItemBuilder;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.Arrays;

public class NonBlockMenu extends Menu {
    private final Material[] elements = new Material[]{
            Material.DIAMOND_SWORD,
            Material.IRON_SWORD,
            Material.GOLDEN_SWORD,
            Material.STONE_SWORD,
            Material.WOODEN_SWORD,
            Material.BOW,
            Material.ARROW,
            Material.CROSSBOW,
            Material.SPECTRAL_ARROW,
            Material.TIPPED_ARROW,
            Material.DIAMOND_PICKAXE,
            Material.IRON_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.WOODEN_PICKAXE,
            Material.DIAMOND_AXE,
            Material.IRON_AXE,
            Material.GOLDEN_AXE,
            Material.STONE_AXE,
            Material.WOODEN_AXE,
            Material.DIAMOND_SHOVEL,
            Material.IRON_SHOVEL,
            Material.GOLDEN_SHOVEL,
            Material.STONE_SHOVEL,
            Material.WOODEN_SHOVEL,
            Material.DIAMOND_HOE,
            Material.IRON_HOE,
            Material.GOLDEN_HOE,
            Material.STONE_HOE,
            Material.WOODEN_HOE,
            Material.FLINT_AND_STEEL,
            Material.SHEARS,
            Material.FISHING_ROD,
            Material.CARROT_ON_A_STICK,
            Material.CROSSBOW,
            Material.POTION,
            Material.SPLASH_POTION,
            Material.LINGERING_POTION,
            Material.ENDER_PEARL,
            Material.ENDER_EYE,
            Material.FIREWORK_ROCKET,
            Material.FIREWORK_STAR,
            Material.TNT_MINECART,
            Material.HOPPER_MINECART,
            Material.POWERED_RAIL,
            Material.DETECTOR_RAIL,
            Material.RAIL,
            Material.ACTIVATOR_RAIL,
            Material.NAME_TAG,
            Material.BOOK,
            Material.ENCHANTED_BOOK,
            Material.WRITTEN_BOOK,
            Material.KNOWLEDGE_BOOK,
            Material.MAP,
            Material.PAPER,
            Material.COMPASS,
            Material.CLOCK,
            Material.SHIELD,
            Material.ELYTRA,
            Material.TOTEM_OF_UNDYING,
            Material.FIRE_CHARGE,
            Material.NETHER_STAR,
            Material.EXPERIENCE_BOTTLE,
            Material.BUCKET,
            Material.WATER_BUCKET,
            Material.LAVA_BUCKET,
            Material.MILK_BUCKET,
            Material.LEAD,
            Material.NAME_TAG,
            Material.CARROT,
            Material.POTATO,
            Material.BEETROOT,
            Material.SADDLE,
            Material.GOLDEN_APPLE,
            Material.ENCHANTED_GOLDEN_APPLE,
            Material.GLISTERING_MELON_SLICE,
            Material.SLIME_BALL,
            Material.SNOWBALL,
            Material.EGG,
            Material.GHAST_TEAR,
            Material.BLAZE_ROD,
            Material.ENDER_EYE,
            Material.ENDER_PEARL,
            Material.MAGMA_CREAM,
            Material.BLAZE_POWDER,
            Material.SPIDER_EYE,
            Material.ROTTEN_FLESH,
            Material.PHANTOM_MEMBRANE,
            Material.GOLD_NUGGET,
            Material.GOLD_INGOT,
            Material.IRON_INGOT,
            Material.DIAMOND,
            Material.EMERALD,
            Material.QUARTZ,
            Material.NETHERITE_INGOT,
            Material.LAPIS_LAZULI,
            Material.REDSTONE,
            Material.GLOWSTONE_DUST,
            Material.STRING,
            Material.LEATHER,
            Material.RABBIT_HIDE,
            Material.FEATHER,
            Material.GUNPOWDER,
            Material.BONE,
            Material.INK_SAC,
            Material.COAL,
            Material.CHARCOAL,
            Material.BRICK,
            Material.CLAY_BALL,
            Material.PRISMARINE_SHARD,
            Material.PRISMARINE_CRYSTALS,
            Material.SHULKER_SHELL,
            Material.COD,
            Material.SALMON,
            Material.TROPICAL_FISH,
            Material.PUFFERFISH,
            Material.NAUTILUS_SHELL,
            Material.QUARTZ,
            Material.COCOA_BEANS,
            Material.DRIED_KELP,
            Material.CACTUS,
            Material.SUGAR,
            Material.SWEET_BERRIES,
            Material.HONEY_BOTTLE,
            Material.HONEYCOMB,
            Material.APPLE,
            Material.GOLDEN_APPLE,
            Material.ENCHANTED_GOLDEN_APPLE,
            Material.MELON,
            Material.PUMPKIN,
            Material.CARVED_PUMPKIN,
            Material.GLOWSTONE,
            Material.JACK_O_LANTERN,
            Material.CAMPFIRE,
            Material.SOUL_CAMPFIRE,
            Material.LANTERN,
            Material.CANDLE,
            Material.WHITE_CANDLE,
            Material.ORANGE_CANDLE,
            Material.MAGENTA_CANDLE,
            Material.LIGHT_BLUE_CANDLE,
            Material.YELLOW_CANDLE,
            Material.LIME_CANDLE,
            Material.PINK_CANDLE,
            Material.GRAY_CANDLE,
            Material.LIGHT_GRAY_CANDLE,
            Material.CYAN_CANDLE,
            Material.PURPLE_CANDLE,
            Material.BLUE_CANDLE,
            Material.BROWN_CANDLE,
            Material.GREEN_CANDLE,
            Material.RED_CANDLE,
            Material.BLACK_CANDLE
    };


    public NonBlockMenu(int page) {
        super(54, "asasdasdasd", page);
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
                if (newElements.length == k) {
                    break;
                }
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
                    Menu solidMenu = new NonBlockMenu(page + 1);
                    MenuManager.openMenu(player, solidMenu, waystone);
                    break;
                case "prevPage":
                    Menu solidMenu2 = new NonBlockMenu(page - 1);
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
