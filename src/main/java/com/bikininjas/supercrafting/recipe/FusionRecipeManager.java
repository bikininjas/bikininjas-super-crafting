package com.bikininjas.supercrafting.recipe;

import com.bikininjas.corelib.recipe.RecipeAPI;
import com.bikininjas.corelib.recipe.RecipeBuilder;
import com.bikininjas.supercrafting.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Programmatically registers all fusion recipes for the super-crafting system.
 * <p>
 * Fusion pattern: 9 × item(N) → 1 × item(N+1).
 * All recipes are shapeless and use the vanilla crafting grid (3×3).
 */
public final class FusionRecipeManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(FusionRecipeManager.class);
    private static final String RECIPE_GROUP = "fusion";

    private FusionRecipeManager() {}

    /**
     * Register all fusion recipes. Called during mod construction after all
     * {@link ModItems} fields are initialized and their {@link net.neoforged.neoforge.registries.DeferredRegister}
     * is registered on the mod bus.
     */
    public static void registerAll() {
        List<FusionChain> chains = buildChains();
        int count = 0;
        for (FusionChain chain : chains) {
            count += registerChain(chain);
        }
        LOGGER.info("Registered {} fusion recipes across {} chains", count, chains.size());
    }

    // ──────────────────────────────────────────────
    //  Chain definitions
    // ──────────────────────────────────────────────

    /**
     * Build the list of all fusion chains. Each chain defines a progression
     * from a vanilla source item through N super tiers.
     */
    private static List<FusionChain> buildChains() {
        List<FusionChain> chains = new ArrayList<>();

        // -- Swords --
        chains.add(new FusionChain("sword", Items.IRON_SWORD,
                ModItems.SUPER_IRON_SWORD, ModItems.SUPER_GOLD_SWORD,
                ModItems.SUPER_DIAMOND_SWORD, ModItems.SUPER_NETHERITE_SWORD,
                ModItems.ULTIMATE_SWORD));

        // -- Pickaxes --
        chains.add(new FusionChain("pickaxe", Items.IRON_PICKAXE,
                ModItems.SUPER_IRON_PICKAXE, ModItems.SUPER_GOLD_PICKAXE,
                ModItems.SUPER_DIAMOND_PICKAXE, ModItems.SUPER_NETHERITE_PICKAXE,
                ModItems.ULTIMATE_PICKAXE));

        // -- Axes --
        chains.add(new FusionChain("axe", Items.IRON_AXE,
                ModItems.SUPER_IRON_AXE, ModItems.SUPER_GOLD_AXE,
                ModItems.SUPER_DIAMOND_AXE, ModItems.SUPER_NETHERITE_AXE,
                ModItems.ULTIMATE_AXE));

        // -- Shovels --
        chains.add(new FusionChain("shovel", Items.IRON_SHOVEL,
                ModItems.SUPER_IRON_SHOVEL, ModItems.SUPER_GOLD_SHOVEL,
                ModItems.SUPER_DIAMOND_SHOVEL, ModItems.SUPER_NETHERITE_SHOVEL,
                ModItems.ULTIMATE_SHOVEL));

        // -- Hoes --
        chains.add(new FusionChain("hoe", Items.IRON_HOE,
                ModItems.SUPER_IRON_HOE, ModItems.SUPER_GOLD_HOE,
                ModItems.SUPER_DIAMOND_HOE, ModItems.SUPER_NETHERITE_HOE,
                ModItems.ULTIMATE_HOE));

        // -- Helmets --
        chains.add(new FusionChain("helmet", Items.IRON_HELMET,
                ModItems.SUPER_IRON_HELMET, ModItems.SUPER_GOLD_HELMET,
                ModItems.SUPER_DIAMOND_HELMET, ModItems.SUPER_NETHERITE_HELMET,
                ModItems.ULTIMATE_HELMET));

        // -- Chestplates --
        chains.add(new FusionChain("chestplate", Items.IRON_CHESTPLATE,
                ModItems.SUPER_IRON_CHESTPLATE, ModItems.SUPER_GOLD_CHESTPLATE,
                ModItems.SUPER_DIAMOND_CHESTPLATE, ModItems.SUPER_NETHERITE_CHESTPLATE,
                ModItems.ULTIMATE_CHESTPLATE));

        // -- Leggings --
        chains.add(new FusionChain("leggings", Items.IRON_LEGGINGS,
                ModItems.SUPER_IRON_LEGGINGS, ModItems.SUPER_GOLD_LEGGINGS,
                ModItems.SUPER_DIAMOND_LEGGINGS, ModItems.SUPER_NETHERITE_LEGGINGS,
                ModItems.ULTIMATE_LEGGINGS));

        // -- Boots --
        chains.add(new FusionChain("boots", Items.IRON_BOOTS,
                ModItems.SUPER_IRON_BOOTS, ModItems.SUPER_GOLD_BOOTS,
                ModItems.SUPER_DIAMOND_BOOTS, ModItems.SUPER_NETHERITE_BOOTS,
                ModItems.ULTIMATE_BOOTS));

        return chains;
    }

    // ──────────────────────────────────────────────
    //  Registration logic
    // ──────────────────────────────────────────────

    private static int registerChain(FusionChain chain) {
        int count = 0;

        // tier 0: vanilla source → first super tier
        if (registerOne(chain.name + "_t0", chain.source, chain.tiers[0])) {
            count++;
        }

        // tier N → tier N+1
        for (int i = 0; i < chain.tiers.length - 1; i++) {
            String suffix = chain.name + "_t" + (i + 1);
            if (registerOne(suffix, chain.tiers[i], chain.tiers[i + 1])) {
                count++;
            }
        }

        return count;
    }

    private static boolean registerOne(String suffix, Item input, Supplier<Item> output) {
        return registerOne(suffix, input, output.get());
    }

    private static boolean registerOne(String suffix, Supplier<Item> input, Supplier<Item> output) {
        return registerOne(suffix, input.get(), output.get());
    }

    private static boolean registerOne(String suffix, Item input, Item output) {
        try {
            ItemStack[] ingredients = new ItemStack[9];
            Arrays.fill(ingredients, new ItemStack(input));
            ItemStack result = ModItems.createEnchantedStack(output);

                    RecipeAPI.addRecipe(
                    RecipeBuilder.create()
                            .id("super_crafting", RECIPE_GROUP + "/" + suffix)
                            .shapeless(result, ingredients)
                            .build().orElseThrow()
            );
            LOGGER.debug("Fusion recipe: 9 × {} → 1 × {}", input, output);
            return true;
        } catch (Exception e) {
            LOGGER.error("Failed to register fusion recipe {}: {}", suffix, e.getMessage());
            return false;
        }
    }

    // ──────────────────────────────────────────────
    //  Data carrier
    // ──────────────────────────────────────────────

    /**
     * A fusion chain: a vanilla source item that can be fused into a progression
     * of super-tier items.
     *
     * @param name   short identifier for logging / recipe IDs
     * @param source the vanilla {@link Item} that starts the chain (e.g. {@link Items#IRON_SWORD})
     * @param tiers  ordered array of super-tier item suppliers (lowest → highest)
     */
    private record FusionChain(String name, Item source, Supplier<Item>[] tiers) {

        @SafeVarargs
        public FusionChain(String name, Item source, DeferredHolder<Item, ?>... tierHolders) {
            this(name, source, toSuppliers(tierHolders));
        }

        private static Supplier<Item>[] toSuppliers(DeferredHolder<Item, ?>[] holders) {
            @SuppressWarnings("unchecked")
            Supplier<Item>[] suppliers = new Supplier[holders.length];
            for (int i = 0; i < holders.length; i++) {
                final int idx = i;
                suppliers[i] = () -> holders[idx].get();
            }
            return suppliers;
        }
    }
}
