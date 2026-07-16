package com.bikininjas.supercrafting;

import com.bikininjas.corelib.network.NetworkHandler;
import com.bikininjas.supercrafting.item.ModItems;
import com.bikininjas.supercrafting.recipe.ExoticRecipeManager;
import com.bikininjas.supercrafting.recipe.FusionRecipeManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Super Crafting mod entry point.
 * <p>
 * Registers the item DeferredRegister and core-lib network handler on the mod
 * bus, and force-loads the recipe/enchant modules on {@link ServerAboutToStartEvent}.
 */
@Mod(SuperCraftingMod.MODID)
public final class SuperCraftingMod {

    public static final String MODID = "super_crafting";

    private static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public SuperCraftingMod(IEventBus modBus) {
        // Register items on the mod bus
        ModItems.ITEMS.register(modBus);
        ModItems.CREATIVE_TABS.register(modBus);

        // Register core-lib network payloads
        NetworkHandler.register(modBus);

        // Static initializer registers EnchantHandler on the NeoForge bus
        EnchantHandler.init();

        // Force-load recipe managers + enchant handler on server start
        NeoForge.EVENT_BUS.addListener((ServerAboutToStartEvent event) -> {
            FusionRecipeManager.createAll();
            ExoticRecipeManager.createAll();
            EnchantHandler.init();
            LOGGER.info("Super Crafting modules initialized");
        });
    }
}
