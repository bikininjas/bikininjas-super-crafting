package com.bikininjas.supercrafting.recipe;

import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.supercrafting.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Registers 5 exotic (shaped 3×3) food recipes.
 */
public final class ExoticRecipeManager {

    private static final ModLogger LOGGER = LogManager.getLogger("super_crafting", ExoticRecipeManager.class);

    private ExoticRecipeManager() {}

    /**
     * Create all exotic food recipes.
     * Called from {@code SuperCraftingMod} on {@code ServerAboutToStartEvent}.
     */
    public static @NotNull List<ShapedRecipe> createAll() {
        var recipes = new ArrayList<ShapedRecipe>();

        recipes.add(exotic("super_apple",
                Map.of('A', Ingredient.of(Items.APPLE), 'B', Ingredient.of(Items.GOLDEN_APPLE),
                        'C', Ingredient.of(Items.GHAST_TEAR), 'D', Ingredient.of(Items.NETHER_STAR),
                        'E', Ingredient.of(Items.GOLD_BLOCK)),
                "ABA", "CDC", "EAE",
                ModItems.SUPER_APPLE.get()));

        recipes.add(exotic("super_carrot",
                Map.of('A', Ingredient.of(Items.CARROT), 'B', Ingredient.of(Items.GOLDEN_CARROT),
                        'C', Ingredient.of(Items.ENDER_PEARL), 'D', Ingredient.of(Items.SPIDER_EYE),
                        'E', Ingredient.of(Items.GOLD_NUGGET)),
                "ABA", "CDC", "EAE",
                ModItems.SUPER_CARROT.get()));

        recipes.add(exotic("super_melon",
                Map.of('A', Ingredient.of(Items.MELON_SLICE), 'B', Ingredient.of(Items.GLISTERING_MELON_SLICE),
                        'C', Ingredient.of(Items.PRISMARINE_SHARD), 'D', Ingredient.of(Items.PHANTOM_MEMBRANE),
                        'E', Ingredient.of(Items.PRISMARINE_CRYSTALS), 'F', Ingredient.of(Items.GOLD_INGOT)),
                "ABC", "DBE", "FFF",
                ModItems.SUPER_MELON.get()));

        recipes.add(exotic("super_chorus",
                Map.of('A', Ingredient.of(Items.CHORUS_FRUIT), 'B', Ingredient.of(Items.POPPED_CHORUS_FRUIT),
                        'C', Ingredient.of(Items.BLAZE_POWDER), 'D', Ingredient.of(Items.OBSIDIAN),
                        'E', Ingredient.of(Items.ENDER_PEARL)),
                "ABA", "CDC", "EAE",
                ModItems.SUPER_CHORUS.get()));

        recipes.add(exotic("ultimate_feast",
                Map.of('A', Ingredient.of(ModItems.SUPER_APPLE.get()), 'B', Ingredient.of(ModItems.SUPER_CARROT.get()),
                        'C', Ingredient.of(ModItems.SUPER_MELON.get()), 'D', Ingredient.of(ModItems.SUPER_CHORUS.get()),
                        'E', Ingredient.of(Items.NETHERITE_BLOCK)),
                "ABA", "CDC", " E ",
                ModItems.ULTIMATE_FEAST.get()));

        LOGGER.info("Created {} exotic recipes", recipes.size());
        return recipes;
    }

    private static @NotNull ShapedRecipe exotic(String name,
                                                 Map<Character, Ingredient> key,
                                                 String row1, String row2, String row3,
                                                 ItemLike output) {
        var pattern = ShapedRecipePattern.of(key, List.of(row1, row2, row3));
        return new ShapedRecipe("super_crafting", CraftingBookCategory.MISC, pattern,
                new ItemStack(output));
    }
}
