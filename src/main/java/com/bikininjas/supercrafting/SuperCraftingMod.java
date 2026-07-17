package com.bikininjas.supercrafting;

import com.bikininjas.corelib.color.ColorAPI;
import com.bikininjas.corelib.config.BikiniConfigRegistry;
import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.corelib.network.NetworkHandler;
import com.bikininjas.corelib.recipe.RecipeAPI;
import com.bikininjas.supercrafting.item.ModItems;
import com.bikininjas.supercrafting.SuperFunnyIntegration;
import com.bikininjas.supercrafting.armor.SetBonuses;
import com.bikininjas.supercrafting.item.RightClickAbilities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

/**
 * Super Crafting mod entry point.
 * <p>
 * Registers the item DeferredRegister and core-lib network handler on the mod
 * bus, and force-loads the recipe/enchant modules on {@link ServerAboutToStartEvent}.
 */
@Mod(SuperCraftingMod.MODID)
public final class SuperCraftingMod {

    public static final String MODID = "super_crafting";

    private static final ModLogger LOGGER = LogManager.getLogger(MODID, SuperCraftingMod.class);

    public SuperCraftingMod(IEventBus modBus) {
        ModItems.ITEMS.register(modBus);
        ModItems.CREATIVE_TABS.register(modBus);
        NetworkHandler.register(modBus);
        EnchantHandler.init();
        SuperFunnyIntegration.init();
        SetBonuses.init();
        RightClickAbilities.init();

        BikiniConfigRegistry.registerBool("super_crafting", "ultimate_effects", "Ultimate Effects", true);
        BikiniConfigRegistry.registerBool("super_crafting", "auto_enchant", "Auto-Enchant on Craft", true);

        ColorAPI.tintItems(modBus, 0xFFB0B0B0,
                ModItems.SUPER_IRON_SWORD, ModItems.SUPER_IRON_PICKAXE,
                ModItems.SUPER_IRON_AXE, ModItems.SUPER_IRON_SHOVEL,
                ModItems.SUPER_IRON_HOE);
        ColorAPI.tintItems(modBus, 0xFF808080,
                ModItems.SUPER_IRON_HELMET, ModItems.SUPER_IRON_CHESTPLATE,
                ModItems.SUPER_IRON_LEGGINGS, ModItems.SUPER_IRON_BOOTS);

        ColorAPI.tintItems(modBus, 0xFFFFDD00,
                ModItems.SUPER_GOLD_SWORD, ModItems.SUPER_GOLD_PICKAXE,
                ModItems.SUPER_GOLD_AXE, ModItems.SUPER_GOLD_SHOVEL,
                ModItems.SUPER_GOLD_HOE);
        ColorAPI.tintItems(modBus, 0xFFEEAA00,
                ModItems.SUPER_GOLD_HELMET, ModItems.SUPER_GOLD_CHESTPLATE,
                ModItems.SUPER_GOLD_LEGGINGS, ModItems.SUPER_GOLD_BOOTS);

        ColorAPI.tintItems(modBus, 0xFF55DDFF,
                ModItems.SUPER_DIAMOND_SWORD, ModItems.SUPER_DIAMOND_PICKAXE,
                ModItems.SUPER_DIAMOND_AXE, ModItems.SUPER_DIAMOND_SHOVEL,
                ModItems.SUPER_DIAMOND_HOE);
        ColorAPI.tintItems(modBus, 0xFF2299CC,
                ModItems.SUPER_DIAMOND_HELMET, ModItems.SUPER_DIAMOND_CHESTPLATE,
                ModItems.SUPER_DIAMOND_LEGGINGS, ModItems.SUPER_DIAMOND_BOOTS);

        ColorAPI.tintItems(modBus, 0xFFDD66FF,
                ModItems.SUPER_NETHERITE_SWORD, ModItems.SUPER_NETHERITE_PICKAXE,
                ModItems.SUPER_NETHERITE_AXE, ModItems.SUPER_NETHERITE_SHOVEL,
                ModItems.SUPER_NETHERITE_HOE);
        ColorAPI.tintItems(modBus, 0xFF9933CC,
                ModItems.SUPER_NETHERITE_HELMET, ModItems.SUPER_NETHERITE_CHESTPLATE,
                ModItems.SUPER_NETHERITE_LEGGINGS, ModItems.SUPER_NETHERITE_BOOTS);

        ColorAPI.tintItems(modBus, 0xFFFF44FF,
                ModItems.ULTIMATE_SWORD, ModItems.ULTIMATE_PICKAXE,
                ModItems.ULTIMATE_AXE, ModItems.ULTIMATE_SHOVEL,
                ModItems.ULTIMATE_HOE);
        ColorAPI.tintItems(modBus, 0xFFCC22DD,
                ModItems.ULTIMATE_HELMET, ModItems.ULTIMATE_CHESTPLATE,
                ModItems.ULTIMATE_LEGGINGS, ModItems.ULTIMATE_BOOTS);

        NeoForge.EVENT_BUS.addListener((ServerAboutToStartEvent event) -> {
            var server = event.getServer();
            // Recipes are provided as datapack JSONs (data/super_crafting/recipe/).
            // KubeJS can modify/remove them without Java re-adding duplicates.
            RecipeAPI.applyPending(server);
            LOGGER.info("Super Crafting recipes loaded from datapack");
        });
    }
}
