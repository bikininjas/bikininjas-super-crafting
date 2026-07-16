package com.bikininjas.supercrafting;

import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.corelib.network.NetworkHandler;
import com.bikininjas.corelib.recipe.RecipeAPI;
import com.bikininjas.supercrafting.item.ModItems;
import com.bikininjas.supercrafting.recipe.ExoticRecipeManager;
import com.bikininjas.supercrafting.recipe.FusionRecipeManager;
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
