package com.bikininjas.supercrafting;

import com.bikininjas.corelib.color.ColorAPI;
import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.corelib.network.NetworkHandler;
import com.bikininjas.corelib.recipe.RecipeAPI;
import com.bikininjas.supercrafting.item.ModItems;
import com.bikininjas.supercrafting.recipe.ExoticRecipeManager;
import com.bikininjas.supercrafting.recipe.FusionRecipeManager;
import com.bikininjas.supercrafting.SuperFunnyIntegration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
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

        ColorAPI.tintItems(modBus, 0xFFAAAAAA,
                ModItems.SUPER_IRON_SWORD, ModItems.SUPER_IRON_PICKAXE,
                ModItems.SUPER_IRON_AXE, ModItems.SUPER_IRON_SHOVEL,
                ModItems.SUPER_IRON_HOE, ModItems.SUPER_IRON_HELMET,
                ModItems.SUPER_IRON_CHESTPLATE, ModItems.SUPER_IRON_LEGGINGS,
                ModItems.SUPER_IRON_BOOTS);
        ColorAPI.tintItems(modBus, 0xFFFFD700,
                ModItems.SUPER_GOLD_SWORD, ModItems.SUPER_GOLD_PICKAXE,
                ModItems.SUPER_GOLD_AXE, ModItems.SUPER_GOLD_SHOVEL,
                ModItems.SUPER_GOLD_HOE, ModItems.SUPER_GOLD_HELMET,
                ModItems.SUPER_GOLD_CHESTPLATE, ModItems.SUPER_GOLD_LEGGINGS,
                ModItems.SUPER_GOLD_BOOTS);
        ColorAPI.tintItems(modBus, 0xFF88FFFF,
                ModItems.SUPER_DIAMOND_SWORD, ModItems.SUPER_DIAMOND_PICKAXE,
                ModItems.SUPER_DIAMOND_AXE, ModItems.SUPER_DIAMOND_SHOVEL,
                ModItems.SUPER_DIAMOND_HOE, ModItems.SUPER_DIAMOND_HELMET,
                ModItems.SUPER_DIAMOND_CHESTPLATE, ModItems.SUPER_DIAMOND_LEGGINGS,
                ModItems.SUPER_DIAMOND_BOOTS);
        ColorAPI.tintItems(modBus, 0xFFAA44FF,
                ModItems.SUPER_NETHERITE_SWORD, ModItems.SUPER_NETHERITE_PICKAXE,
                ModItems.SUPER_NETHERITE_AXE, ModItems.SUPER_NETHERITE_SHOVEL,
                ModItems.SUPER_NETHERITE_HOE, ModItems.SUPER_NETHERITE_HELMET,
                ModItems.SUPER_NETHERITE_CHESTPLATE, ModItems.SUPER_NETHERITE_LEGGINGS,
                ModItems.SUPER_NETHERITE_BOOTS);
        ColorAPI.tintItems(modBus, 0xFFFF44FF,
                ModItems.ULTIMATE_SWORD, ModItems.ULTIMATE_PICKAXE,
                ModItems.ULTIMATE_AXE, ModItems.ULTIMATE_SHOVEL,
                ModItems.ULTIMATE_HOE, ModItems.ULTIMATE_HELMET,
                ModItems.ULTIMATE_CHESTPLATE, ModItems.ULTIMATE_LEGGINGS,
                ModItems.ULTIMATE_BOOTS);

        NeoForge.EVENT_BUS.addListener((ServerAboutToStartEvent event) -> {
            var server = event.getServer();
            int idx = 0;
            for (var recipe : FusionRecipeManager.createAll()) {
                var id = MODID + ":fusion_" + (idx++);
                RecipeAPI.addRecipe(id, new RecipeHolder<>(
                        ResourceLocation.parse(id), recipe));
            }
            for (var recipe : ExoticRecipeManager.createAll()) {
                var id = MODID + ":exotic_" + (idx++);
                RecipeAPI.addRecipe(id, new RecipeHolder<>(
                        ResourceLocation.parse(id), recipe));
            }
            RecipeAPI.applyPending(server);
            LOGGER.info("Super Crafting recipes registered");
        });
    }
}
