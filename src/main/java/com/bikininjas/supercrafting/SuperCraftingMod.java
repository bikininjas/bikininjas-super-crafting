package com.bikininjas.supercrafting;

import com.bikininjas.supercrafting.item.ModItems;
import com.bikininjas.supercrafting.item.SuperArmorMaterial;
import com.bikininjas.supercrafting.recipe.FusionRecipeManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for the Super Crafting mod.
 * <p>
 * Registers all super-tier items and fusion recipes.
 */
@Mod(SuperCraftingMod.MODID)
public final class SuperCraftingMod {

    public static final String MODID = "super_crafting";

    private static final Logger LOGGER = LoggerFactory.getLogger(SuperCraftingMod.class);

    public SuperCraftingMod(IEventBus modBus) {
        ModItems.ITEMS.register(modBus);
        SuperArmorMaterial.ARMOR_MATERIALS.register(modBus);

        // Recipes require a running MinecraftServer (RecipeAPI.addRecipe uses
        // reflection on the server's RecipeManager), so register at server start.
        NeoForge.EVENT_BUS.addListener(ServerAboutToStartEvent.class,
                event -> FusionRecipeManager.registerAll());

        LOGGER.info("Super Crafting mod initialized");
    }
}
