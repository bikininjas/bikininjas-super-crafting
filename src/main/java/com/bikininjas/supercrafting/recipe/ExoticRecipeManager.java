package com.bikininjas.supercrafting.recipe;

import com.bikininjas.corelib.recipe.RecipeAPI;
import com.bikininjas.corelib.recipe.RecipeBuilder;
import com.bikininjas.supercrafting.item.ModFoods;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exotic shaped recipes for super foods.
 * Each recipe uses a unique 3×3 pattern with rare/dimension-specific ingredients.
 */
public final class ExoticRecipeManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExoticRecipeManager.class);

    private ExoticRecipeManager() {}

    public static void registerAll() {
        registerSuperApple();
        registerSuperCarrot();
        registerSuperMelon();
        registerSuperChorus();
        registerUltimateFeast();
        LOGGER.info("Registered 5 exotic food recipes");
    }

    // ── Super Apple: iron ingot ring + apple center ──
    private static void registerSuperApple() {
        RecipeAPI.addRecipe(RecipeBuilder.create()
                .id("super_crafting", "exotic/super_apple")
                .shaped(new ItemStack(ModFoods.SUPER_APPLE.get()),
                        "III",
                        "IAI",
                        "III")
                .defined('I', Ingredient.of(Items.IRON_INGOT))
                .defined('A', Ingredient.of(Items.APPLE))
                .build().orElseThrow()
        );
        LOGGER.debug("Exotic recipe: super_apple");
    }

    // ── Super Carrot: golden carrot corners + carrot center ──
    private static void registerSuperCarrot() {
        RecipeAPI.addRecipe(RecipeBuilder.create()
                .id("super_crafting", "exotic/super_carrot")
                .shaped(new ItemStack(ModFoods.SUPER_CARROT.get()),
                        "NGN",
                        "GCG",
                        "NGN")
                .defined('N', Ingredient.of(Items.GOLD_NUGGET))
                .defined('G', Ingredient.of(Items.GOLDEN_CARROT))
                .defined('C', Ingredient.of(Items.CARROT))
                .build().orElseThrow()
        );
        LOGGER.debug("Exotic recipe: super_carrot");
    }

    // ── Super Melon: diamond + glowstone cross around melon ──
    private static void registerSuperMelon() {
        RecipeAPI.addRecipe(RecipeBuilder.create()
                .id("super_crafting", "exotic/super_melon")
                .shaped(new ItemStack(ModFoods.SUPER_MELON.get()),
                        "DGD",
                        "GMG",
                        "DGD")
                .defined('D', Ingredient.of(Items.DIAMOND))
                .defined('G', Ingredient.of(Items.GLOWSTONE_DUST))
                .defined('M', Ingredient.of(Items.MELON_SLICE))
                .build().orElseThrow()
        );
        LOGGER.debug("Exotic recipe: super_melon");
    }

    // ── Super Chorus: ender pearl + phantom membrane cross around chorus ──
    private static void registerSuperChorus() {
        RecipeAPI.addRecipe(RecipeBuilder.create()
                .id("super_crafting", "exotic/super_chorus")
                .shaped(new ItemStack(ModFoods.SUPER_CHORUS.get()),
                        "EPE",
                        "PCP",
                        "EPE")
                .defined('E', Ingredient.of(Items.ENDER_PEARL))
                .defined('P', Ingredient.of(Items.PHANTOM_MEMBRANE))
                .defined('C', Ingredient.of(Items.CHORUS_FRUIT))
                .build().orElseThrow()
        );
        LOGGER.debug("Exotic recipe: super_chorus");
    }

    // ── Ultimate Feast: cooked beef + super apple + nether star ──
    private static void registerUltimateFeast() {
        RecipeAPI.addRecipe(RecipeBuilder.create()
                .id("super_crafting", "exotic/ultimate_feast")
                .shaped(new ItemStack(ModFoods.ULTIMATE_FEAST.get()),
                        "BSB",
                        "SNS",
                        "BSB")
                .defined('B', Ingredient.of(Items.COOKED_BEEF))
                .defined('S', Ingredient.of(ModFoods.SUPER_APPLE.get()))
                .defined('N', Ingredient.of(Items.NETHER_STAR))
                .build().orElseThrow()
        );
        LOGGER.debug("Exotic recipe: ultimate_feast");
    }
}
