package com.bikininjas.supercrafting.recipe;

import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.supercrafting.item.ModItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers 45 fusion (9→1 shapeless) recipes for super-crafting.
 * <p>
 * 9 chains × 5 tiers each:
 * <ul>
 *   <li>5 tool chains: sword, pickaxe, axe, shovel, hoe</li>
 *   <li>4 armor chains: helmet, chestplate, leggings, boots</li>
 * </ul>
 */
public final class FusionRecipeManager {

    private static final ModLogger LOGGER = LogManager.getLogger("super_crafting", FusionRecipeManager.class);

    private FusionRecipeManager() {}

    /** All registered fusion recipe IDs. */
    private static final List<ResourceLocation> recipeIds = new ArrayList<>();

    /**
     * Register all fusion recipes.
     * Called from {@code SuperCraftingMod} on {@code ServerAboutToStartEvent}.
     *
     * @return list of recipe IDs for debugging/validation
     */
    public static @NotNull List<ShapelessRecipe> createAll() {
        var recipes = new ArrayList<ShapelessRecipe>();
        var ns = "super_crafting";

        // === Tool chains (5) ===
        toolChain(recipes, ns, "sword",
                Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD,
                ModItems.SUPER_IRON_SWORD, ModItems.SUPER_GOLD_SWORD, ModItems.SUPER_DIAMOND_SWORD,
                ModItems.SUPER_NETHERITE_SWORD, ModItems.ULTIMATE_SWORD);

        toolChain(recipes, ns, "pickaxe",
                Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE,
                ModItems.SUPER_IRON_PICKAXE, ModItems.SUPER_GOLD_PICKAXE, ModItems.SUPER_DIAMOND_PICKAXE,
                ModItems.SUPER_NETHERITE_PICKAXE, ModItems.ULTIMATE_PICKAXE);

        toolChain(recipes, ns, "axe",
                Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE,
                ModItems.SUPER_IRON_AXE, ModItems.SUPER_GOLD_AXE, ModItems.SUPER_DIAMOND_AXE,
                ModItems.SUPER_NETHERITE_AXE, ModItems.ULTIMATE_AXE);

        toolChain(recipes, ns, "shovel",
                Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL,
                ModItems.SUPER_IRON_SHOVEL, ModItems.SUPER_GOLD_SHOVEL, ModItems.SUPER_DIAMOND_SHOVEL,
                ModItems.SUPER_NETHERITE_SHOVEL, ModItems.ULTIMATE_SHOVEL);

        toolChain(recipes, ns, "hoe",
                Items.IRON_HOE, Items.GOLDEN_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE,
                ModItems.SUPER_IRON_HOE, ModItems.SUPER_GOLD_HOE, ModItems.SUPER_DIAMOND_HOE,
                ModItems.SUPER_NETHERITE_HOE, ModItems.ULTIMATE_HOE);

        // === Armor chains (4) ===
        armorChain(recipes, ns, "helmet",
                Items.IRON_HELMET, Items.GOLDEN_HELMET, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET,
                ModItems.SUPER_IRON_HELMET, ModItems.SUPER_GOLD_HELMET, ModItems.SUPER_DIAMOND_HELMET,
                ModItems.SUPER_NETHERITE_HELMET, ModItems.ULTIMATE_HELMET);

        armorChain(recipes, ns, "chestplate",
                Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE,
                ModItems.SUPER_IRON_CHESTPLATE, ModItems.SUPER_GOLD_CHESTPLATE, ModItems.SUPER_DIAMOND_CHESTPLATE,
                ModItems.SUPER_NETHERITE_CHESTPLATE, ModItems.ULTIMATE_CHESTPLATE);

        armorChain(recipes, ns, "leggings",
                Items.IRON_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS,
                ModItems.SUPER_IRON_LEGGINGS, ModItems.SUPER_GOLD_LEGGINGS, ModItems.SUPER_DIAMOND_LEGGINGS,
                ModItems.SUPER_NETHERITE_LEGGINGS, ModItems.ULTIMATE_LEGGINGS);

        armorChain(recipes, ns, "boots",
                Items.IRON_BOOTS, Items.GOLDEN_BOOTS, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS,
                ModItems.SUPER_IRON_BOOTS, ModItems.SUPER_GOLD_BOOTS, ModItems.SUPER_DIAMOND_BOOTS,
                ModItems.SUPER_NETHERITE_BOOTS, ModItems.ULTIMATE_BOOTS);

        LOGGER.info("Created {} fusion recipes", recipes.size());
        return recipes;
    }

    private static void toolChain(List<ShapelessRecipe> recipes, String ns, String type,
                                   ItemLike vanIron, ItemLike vanGold, ItemLike vanDiamond, ItemLike vanNether,
                                   ItemLike supIron, ItemLike supGold, ItemLike supDiamond,
                                   ItemLike supNether, ItemLike supUlt) {
        // Tier 0: 9 vanilla → super iron
        addFusion(recipes, ns, "fusion_iron_" + type, vanIron, supIron);
        addFusion(recipes, ns, "fusion_gold_" + type, vanGold, supGold);
        addFusion(recipes, ns, "fusion_diamond_" + type, vanDiamond, supDiamond);
        addFusion(recipes, ns, "fusion_netherite_" + type, vanNether, supNether);
        // Tier 4: 9 netherite_plus → ultimate (no vanilla source)
        addFusion(recipes, ns, "fusion_ultimate_" + type, supNether, supUlt);
    }

    private static void armorChain(List<ShapelessRecipe> recipes, String ns, String type,
                                    ItemLike vanIron, ItemLike vanGold, ItemLike vanDiamond, ItemLike vanNether,
                                    ItemLike supIron, ItemLike supGold, ItemLike supDiamond,
                                    ItemLike supNether, ItemLike supUlt) {
        toolChain(recipes, ns, type,
                vanIron, vanGold, vanDiamond, vanNether,
                supIron, supGold, supDiamond, supNether, supUlt);
    }

    private static void addFusion(List<ShapelessRecipe> recipes, String ns, String name,
                                   ItemLike source, ItemLike result) {
        var id = ResourceLocation.fromNamespaceAndPath(ns, name);
        var ingredients = NonNullList.withSize(9, Ingredient.of(source));
        var recipe = new ShapelessRecipe(
                "super_crafting",
                CraftingBookCategory.EQUIPMENT,
                new ItemStack(result),
                ingredients
        );
        recipes.add(recipe);
        recipeIds.add(id);
    }
}
