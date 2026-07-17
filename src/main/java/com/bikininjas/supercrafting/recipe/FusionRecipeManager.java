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
 * Registers 45 fusion (9 ingrédients différents → 1) recipes for super-crafting.
 * <p>
 * Each recipe combines a core item with 8 thematic ingredients from its tier's
 * domain — no more "9× the same item". Tiers are themed by Minecraft dimension:
 * Overworld, Magic & Ocean, Deep & Rare, Nether, End & Beyond.
 */
public final class FusionRecipeManager {

    private static final ModLogger LOGGER = LogManager.getLogger("super_crafting", FusionRecipeManager.class);

    private FusionRecipeManager() {}

    private static final List<ResourceLocation> recipeIds = new ArrayList<>();

    // == Tool ingredient tiers (8 items each) ==================================

    /** Tier 1 — Overworld Forge: earth, fire, industry. */
    private static final ItemLike[] TOOL_T1 = {
            Items.IRON_BLOCK, Items.FLINT, Items.COAL_BLOCK, Items.REDSTONE,
            Items.LAPIS_BLOCK, Items.STONE, Items.GOLD_INGOT, Items.EMERALD
    };

    /** Tier 2 — Alchemical Crucible: magic, light, crystal. */
    private static final ItemLike[] TOOL_T2 = {
            Items.GOLD_BLOCK, Items.GLOWSTONE, Items.BLAZE_POWDER, Items.QUARTZ_BLOCK,
            Items.AMETHYST_BLOCK, Items.DIAMOND, Items.ECHO_SHARD, Items.ENDER_PEARL
    };

    /** Tier 3 — Deep Vault: rare ores, obsidian, echoes. */
    private static final ItemLike[] TOOL_T3 = {
            Items.DIAMOND, Items.EMERALD, Items.OBSIDIAN, Items.CRYING_OBSIDIAN,
            Items.NETHERITE_SCRAP, Items.GOLDEN_APPLE, Items.GHAST_TEAR, Items.PHANTOM_MEMBRANE
    };

    /** Tier 4 — Nether Foundry: fire, soul, ancient power. */
    private static final ItemLike[] TOOL_T4 = {
            Items.NETHERITE_INGOT, Items.ANCIENT_DEBRIS, Items.WITHER_SKELETON_SKULL, Items.MAGMA_BLOCK,
            Items.BLAZE_ROD, Items.SOUL_SAND, Items.SKELETON_SKULL, Items.NETHER_BRICKS
    };

    /** Tier 5 — Apotheosis: end, dragon, the ultimate. */
    private static final ItemLike[] TOOL_T5 = {
            Items.NETHER_STAR, Items.ELYTRA, Items.DRAGON_BREATH, Items.END_CRYSTAL,
            Items.ENCHANTED_GOLDEN_APPLE, Items.TOTEM_OF_UNDYING, Items.SPONGE, Items.CONDUIT
    };

    // == Armor ingredient tiers (8 items each) =================================

    /** Tier 1 — Leatherworks: hide, sinew, natural protection. */
    private static final ItemLike[] ARMOR_T1 = {
            Items.LEATHER, Items.IRON_BLOCK, Items.WHITE_WOOL, Items.STRING,
            Items.BONE_MEAL, Items.RABBIT_HIDE, Items.FEATHER, Items.SLIME_BALL
    };

    /** Tier 2 — Gilded Shell: enchanted creatures, ocean treasures. */
    private static final ItemLike[] ARMOR_T2 = {
            Items.GOLD_BLOCK, Items.PHANTOM_MEMBRANE, Items.TURTLE_SCUTE, Items.HONEYCOMB,
            Items.PRISMARINE_CRYSTALS, Items.NAUTILUS_SHELL, Items.GLOW_INK_SAC, Items.AMETHYST_SHARD
    };

    /** Tier 3 — Diamond Plating: heavy minerals, mechanical precision. */
    private static final ItemLike[] ARMOR_T3 = {
            Items.DIAMOND_BLOCK, Items.EMERALD_BLOCK, Items.CHAIN, Items.IRON_BARS,
            Items.BELL, Items.COMPASS, Items.CLOCK, Items.ENDER_EYE
    };

    /** Tier 4 — Netherborn: corruption, soul-infused, volcanic. */
    private static final ItemLike[] ARMOR_T4 = {
            Items.NETHERITE_SCRAP, Items.WITHER_ROSE, Items.GILDED_BLACKSTONE, Items.SOUL_SOIL,
            Items.MAGMA_CREAM, Items.BLAZE_POWDER, Items.NETHER_WART, Items.GHAST_TEAR
    };

    /** Tier 5 — Divine Raiment: ascension, transcendence, true defense. */
    private static final ItemLike[] ARMOR_T5 = {
            Items.NETHER_STAR, Items.TOTEM_OF_UNDYING, Items.HEART_OF_THE_SEA, Items.CHORUS_FRUIT,
            Items.SHULKER_SHELL, Items.TURTLE_SCUTE, Items.HONEY_BOTTLE, Items.GOLDEN_CARROT
    };

    // == Recipe creation =======================================================

    /**
     * Register all fusion recipes.
     * Called from {@code SuperCraftingMod} on {@code ServerAboutToStartEvent}.
     */
    public static @NotNull List<ShapelessRecipe> createAll() {
        var recipes = new ArrayList<ShapelessRecipe>();
        var ns = "super_crafting";

        // === Tool chains (5 types × 5 tiers) ===
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

        // === Armor chains (4 types × 5 tiers) ===
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

    // == Chain helpers =========================================================

    private static void toolChain(List<ShapelessRecipe> recipes, String ns, String type,
                                   ItemLike vanIron, ItemLike vanGold, ItemLike vanDiamond, ItemLike vanNether,
                                   ItemLike supIron, ItemLike supGold, ItemLike supDiamond,
                                   ItemLike supNether, ItemLike supUlt) {
        addFusion(recipes, ns, "fusion_iron_" + type, vanIron, supIron, TOOL_T1);
        addFusion(recipes, ns, "fusion_gold_" + type, vanGold, supGold, TOOL_T2);
        addFusion(recipes, ns, "fusion_diamond_" + type, vanDiamond, supDiamond, TOOL_T3);
        addFusion(recipes, ns, "fusion_netherite_" + type, vanNether, supNether, TOOL_T4);
        addFusion(recipes, ns, "fusion_ultimate_" + type, supNether, supUlt, TOOL_T5);
    }

    private static void armorChain(List<ShapelessRecipe> recipes, String ns, String type,
                                    ItemLike vanIron, ItemLike vanGold, ItemLike vanDiamond, ItemLike vanNether,
                                    ItemLike supIron, ItemLike supGold, ItemLike supDiamond,
                                    ItemLike supNether, ItemLike supUlt) {
        addFusion(recipes, ns, "fusion_iron_" + type, vanIron, supIron, ARMOR_T1);
        addFusion(recipes, ns, "fusion_gold_" + type, vanGold, supGold, ARMOR_T2);
        addFusion(recipes, ns, "fusion_diamond_" + type, vanDiamond, supDiamond, ARMOR_T3);
        addFusion(recipes, ns, "fusion_netherite_" + type, vanNether, supNether, ARMOR_T4);
        addFusion(recipes, ns, "fusion_ultimate_" + type, supNether, supUlt, ARMOR_T5);
    }

    /**
     * Create a single 9-ingredient fusion recipe.
     *
     * @param recipes         list to add the recipe to
     * @param ns              mod namespace
     * @param name            recipe ID (e.g. "fusion_iron_sword")
     * @param core            the core item (vanilla tool or previous tier)
     * @param result          the output super item
     * @param tierIngredients 8 thematic ingredients for this tier
     */
    private static void addFusion(List<ShapelessRecipe> recipes, String ns, String name,
                                   ItemLike core, ItemLike result, ItemLike[] tierIngredients) {
        var id = ResourceLocation.fromNamespaceAndPath(ns, name);
        var ingredients = NonNullList.<Ingredient>createWithCapacity(9);
        ingredients.add(Ingredient.of(core));
        for (var item : tierIngredients) {
            ingredients.add(Ingredient.of(item));
        }
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
