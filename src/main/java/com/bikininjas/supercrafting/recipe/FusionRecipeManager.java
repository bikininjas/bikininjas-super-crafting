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
import java.util.Collections;
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

    /**
     * Returns an unmodifiable view of the recipe IDs in creation order.
     * Must be called AFTER {@link #createAll()}.
     */
    public static @NotNull List<ResourceLocation> getRecipeIds() {
        return Collections.unmodifiableList(recipeIds);
    }

    // == Recipe ingredient arrays — thematic per tool type & tier ==============
    //
    // Each fusion recipe uses 9 items: 1 core + 8 extras.
    // Extras are: 2× more vanilla tools + 2× tier emblem + 2× enhancer A + 2× enhancer B
    //
    // Design: "You fuse 3 identical tools with rare materials to forge one super tool."

    /** Emblem blocks per tier — the iconic block of each tier. */
    private static final ItemLike IRON_BLK  = Items.IRON_BLOCK;
    private static final ItemLike GOLD_BLK  = Items.GOLD_BLOCK;
    private static final ItemLike DIAM_BLK  = Items.DIAMOND_BLOCK;
    private static final ItemLike NETH_ING  = Items.NETHERITE_INGOT;
    private static final ItemLike NETH_BLK  = Items.NETHERITE_BLOCK;

    // -- Sword enhancers (combat: fire, sharpness, death) --
    private static final ItemLike[] SWORD_T1 = {Items.IRON_SWORD, Items.IRON_SWORD, IRON_BLK, IRON_BLK, Items.FLINT, Items.FLINT, Items.BONE, Items.BONE};
    private static final ItemLike[] SWORD_T2 = {Items.GOLDEN_SWORD, Items.GOLDEN_SWORD, GOLD_BLK, GOLD_BLK, Items.GLOWSTONE_DUST, Items.GLOWSTONE_DUST, Items.BLAZE_POWDER, Items.BLAZE_POWDER};
    private static final ItemLike[] SWORD_T3 = {Items.DIAMOND_SWORD, Items.DIAMOND_SWORD, DIAM_BLK, DIAM_BLK, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP, Items.GHAST_TEAR, Items.GHAST_TEAR};
    private static final ItemLike[] SWORD_T4 = {Items.NETHERITE_SWORD, Items.NETHERITE_SWORD, NETH_BLK, NETH_BLK, Items.WITHER_SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.BLAZE_ROD, Items.BLAZE_ROD};
    private static final ItemLike[] SWORD_T5 = {Items.NETHERITE_SWORD, Items.NETHERITE_SWORD, Items.NETHER_STAR, Items.NETHER_STAR, Items.DRAGON_BREATH, Items.DRAGON_BREATH, Items.END_CRYSTAL, Items.END_CRYSTAL};

    // -- Pickaxe enhancers (mining: fortune, speed, durability) --
    private static final ItemLike[] PICK_T1 = {Items.IRON_PICKAXE, Items.IRON_PICKAXE, IRON_BLK, IRON_BLK, Items.REDSTONE_BLOCK, Items.REDSTONE_BLOCK, Items.LAPIS_BLOCK, Items.LAPIS_BLOCK};
    private static final ItemLike[] PICK_T2 = {Items.GOLDEN_PICKAXE, Items.GOLDEN_PICKAXE, GOLD_BLK, GOLD_BLK, Items.QUARTZ_BLOCK, Items.QUARTZ_BLOCK, Items.GLOWSTONE, Items.GLOWSTONE};
    private static final ItemLike[] PICK_T3 = {Items.DIAMOND_PICKAXE, Items.DIAMOND_PICKAXE, DIAM_BLK, DIAM_BLK, Items.OBSIDIAN, Items.OBSIDIAN, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP};
    private static final ItemLike[] PICK_T4 = {Items.NETHERITE_PICKAXE, Items.NETHERITE_PICKAXE, NETH_BLK, NETH_BLK, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS, Items.CRYING_OBSIDIAN, Items.CRYING_OBSIDIAN};
    private static final ItemLike[] PICK_T5 = {Items.NETHERITE_PICKAXE, Items.NETHERITE_PICKAXE, Items.NETHER_STAR, Items.NETHER_STAR, Items.END_CRYSTAL, Items.END_CRYSTAL, Items.DRAGON_HEAD, Items.DRAGON_HEAD};

    // -- Axe enhancers (chopping + combat: sharp edge, ancient wood) --
    private static final ItemLike[] AXE_T1  = {Items.IRON_AXE, Items.IRON_AXE, IRON_BLK, IRON_BLK, Items.FLINT, Items.FLINT, Items.OAK_LOG, Items.OAK_LOG};
    private static final ItemLike[] AXE_T2  = {Items.GOLDEN_AXE, Items.GOLDEN_AXE, GOLD_BLK, GOLD_BLK, Items.HONEYCOMB, Items.HONEYCOMB, Items.AMETHYST_SHARD, Items.AMETHYST_SHARD};
    private static final ItemLike[] AXE_T3  = {Items.DIAMOND_AXE, Items.DIAMOND_AXE, DIAM_BLK, DIAM_BLK, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP, Items.ECHO_SHARD, Items.ECHO_SHARD};
    private static final ItemLike[] AXE_T4  = {Items.NETHERITE_AXE, Items.NETHERITE_AXE, NETH_BLK, NETH_BLK, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS, Items.WITHER_SKELETON_SKULL, Items.WITHER_SKELETON_SKULL};
    private static final ItemLike[] AXE_T5  = {Items.NETHERITE_AXE, Items.NETHERITE_AXE, Items.NETHER_STAR, Items.NETHER_STAR, Items.END_CRYSTAL, Items.END_CRYSTAL, Items.TOTEM_OF_UNDYING, Items.TOTEM_OF_UNDYING};

    // -- Shovel enhancers (earth: digging, terraforming) --
    private static final ItemLike[] SHVL_T1 = {Items.IRON_SHOVEL, Items.IRON_SHOVEL, IRON_BLK, IRON_BLK, Items.GRAVEL, Items.GRAVEL, Items.CLAY, Items.CLAY};
    private static final ItemLike[] SHVL_T2 = {Items.GOLDEN_SHOVEL, Items.GOLDEN_SHOVEL, GOLD_BLK, GOLD_BLK, Items.SAND, Items.SAND, Items.SOUL_SAND, Items.SOUL_SAND};
    private static final ItemLike[] SHVL_T3 = {Items.DIAMOND_SHOVEL, Items.DIAMOND_SHOVEL, DIAM_BLK, DIAM_BLK, Items.OBSIDIAN, Items.OBSIDIAN, Items.MYCELIUM, Items.MYCELIUM};
    private static final ItemLike[] SHVL_T4 = {Items.NETHERITE_SHOVEL, Items.NETHERITE_SHOVEL, NETH_BLK, NETH_BLK, Items.SOUL_SOIL, Items.SOUL_SOIL, Items.BASALT, Items.BASALT};
    private static final ItemLike[] SHVL_T5 = {Items.NETHERITE_SHOVEL, Items.NETHERITE_SHOVEL, Items.NETHER_STAR, Items.NETHER_STAR, Items.DRAGON_EGG, Items.DRAGON_EGG, Items.BEACON, Items.BEACON};

    // -- Hoe enhancers (farming: nature, growth, harvest) --
    private static final ItemLike[] HOE_T1  = {Items.IRON_HOE, Items.IRON_HOE, IRON_BLK, IRON_BLK, Items.BONE_MEAL, Items.BONE_MEAL, Items.WHEAT, Items.WHEAT};
    private static final ItemLike[] HOE_T2  = {Items.GOLDEN_HOE, Items.GOLDEN_HOE, GOLD_BLK, GOLD_BLK, Items.GOLDEN_CARROT, Items.GOLDEN_CARROT, Items.GLISTERING_MELON_SLICE, Items.GLISTERING_MELON_SLICE};
    private static final ItemLike[] HOE_T3  = {Items.DIAMOND_HOE, Items.DIAMOND_HOE, DIAM_BLK, DIAM_BLK, Items.EXPERIENCE_BOTTLE, Items.EXPERIENCE_BOTTLE, Items.PUMPKIN, Items.PUMPKIN};
    private static final ItemLike[] HOE_T4  = {Items.NETHERITE_HOE, Items.NETHERITE_HOE, NETH_BLK, NETH_BLK, Items.NETHER_WART, Items.NETHER_WART, Items.CHORUS_FRUIT, Items.CHORUS_FRUIT};
    private static final ItemLike[] HOE_T5  = {Items.NETHERITE_HOE, Items.NETHERITE_HOE, Items.NETHER_STAR, Items.NETHER_STAR, Items.ENCHANTED_GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.TOTEM_OF_UNDYING, Items.TOTEM_OF_UNDYING};

    // -- Armor enhancers (defense: protection, resilience) --
    private static final ItemLike[] ARMOR_T1 = {Items.IRON_CHESTPLATE, Items.IRON_CHESTPLATE, IRON_BLK, IRON_BLK, Items.LEATHER, Items.LEATHER, Items.CHAIN, Items.CHAIN};
    private static final ItemLike[] ARMOR_T2 = {Items.GOLDEN_CHESTPLATE, Items.GOLDEN_CHESTPLATE, GOLD_BLK, GOLD_BLK, Items.HONEYCOMB, Items.HONEYCOMB, Items.AMETHYST_SHARD, Items.AMETHYST_SHARD};
    private static final ItemLike[] ARMOR_T3 = {Items.DIAMOND_CHESTPLATE, Items.DIAMOND_CHESTPLATE, DIAM_BLK, DIAM_BLK, Items.OBSIDIAN, Items.OBSIDIAN, Items.SHIELD, Items.SHIELD};
    private static final ItemLike[] ARMOR_T4 = {Items.NETHERITE_CHESTPLATE, Items.NETHERITE_CHESTPLATE, NETH_BLK, NETH_BLK, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS, Items.WITHER_ROSE, Items.WITHER_ROSE};
    private static final ItemLike[] ARMOR_T5 = {Items.NETHERITE_CHESTPLATE, Items.NETHERITE_CHESTPLATE, Items.NETHER_STAR, Items.NETHER_STAR, Items.TOTEM_OF_UNDYING, Items.TOTEM_OF_UNDYING, Items.ELYTRA, Items.ELYTRA};

    // -- Bow enhancers (ranged: precision, velocity) --
    private static final ItemLike[] BOW_T1 = {Items.BOW, Items.BOW, Items.FEATHER, Items.FEATHER, Items.FEATHER, Items.STRING, Items.STRING, Items.STRING};
    private static final ItemLike[] BOW_T2 = {Items.BOW, Items.BOW, Items.SPECTRAL_ARROW, Items.SPECTRAL_ARROW, Items.SPECTRAL_ARROW, Items.STRING, Items.STRING, Items.STRING};
    private static final ItemLike[] BOW_T3 = {Items.BOW, Items.BOW, Items.TIPPED_ARROW, Items.TIPPED_ARROW, Items.TIPPED_ARROW, Items.STRING, Items.STRING, Items.STRING};
    private static final ItemLike[] BOW_T4 = {Items.BOW, Items.BOW, Items.SPECTRAL_ARROW, Items.SPECTRAL_ARROW, Items.SPECTRAL_ARROW, Items.NETHER_STAR, Items.NETHER_STAR, Items.NETHER_STAR};
    private static final ItemLike[] BOW_T5 = {Items.BOW, Items.BOW, Items.SPECTRAL_ARROW, Items.SPECTRAL_ARROW, Items.SPECTRAL_ARROW, Items.DRAGON_BREATH, Items.END_CRYSTAL, Items.END_CRYSTAL};

    // -- Shield enhancers (defense: blocking, resilience) --
    private static final ItemLike[] SHIELD_T1 = {Items.SHIELD, Items.SHIELD, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_BLOCK, Items.IRON_BLOCK, Items.IRON_BLOCK};
    private static final ItemLike[] SHIELD_T2 = {Items.SHIELD, Items.SHIELD, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_BLOCK, Items.GOLD_BLOCK, Items.GOLD_BLOCK};
    private static final ItemLike[] SHIELD_T3 = {Items.SHIELD, Items.SHIELD, Items.DIAMOND, Items.DIAMOND, Items.DIAMOND, Items.OBSIDIAN, Items.OBSIDIAN, Items.OBSIDIAN};
    private static final ItemLike[] SHIELD_T4 = {Items.SHIELD, Items.SHIELD, Items.NETHERITE_INGOT, Items.NETHERITE_INGOT, Items.NETHERITE_INGOT, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS};
    private static final ItemLike[] SHIELD_T5 = {Items.SHIELD, Items.SHIELD, Items.NETHERITE_BLOCK, Items.END_CRYSTAL, Items.END_CRYSTAL, Items.ENDER_PEARL, Items.ENDER_PEARL, Items.ENDER_PEARL};

    // -- Fishing Rod enhancers (aquatic: lure, luck of the sea) --
    private static final ItemLike[] ROD_T1 = {Items.FISHING_ROD, Items.FISHING_ROD, Items.STRING, Items.STRING, Items.STRING, Items.COD, Items.COD, Items.COD};
    private static final ItemLike[] ROD_T2 = {Items.FISHING_ROD, Items.FISHING_ROD, Items.STRING, Items.STRING, Items.STRING, Items.SALMON, Items.SALMON, Items.SALMON};
    private static final ItemLike[] ROD_T3 = {Items.FISHING_ROD, Items.FISHING_ROD, Items.STRING, Items.STRING, Items.STRING, Items.PUFFERFISH, Items.PUFFERFISH, Items.PUFFERFISH};
    private static final ItemLike[] ROD_T4 = {Items.FISHING_ROD, Items.FISHING_ROD, Items.NAUTILUS_SHELL, Items.NAUTILUS_SHELL, Items.NAUTILUS_SHELL, Items.HEART_OF_THE_SEA, Items.HEART_OF_THE_SEA, Items.HEART_OF_THE_SEA};
    private static final ItemLike[] ROD_T5 = {Items.FISHING_ROD, Items.FISHING_ROD, Items.TRIDENT, Items.TRIDENT, Items.HEART_OF_THE_SEA, Items.NAUTILUS_SHELL, Items.NAUTILUS_SHELL, Items.NAUTILUS_SHELL};

    // == Recipe creation =======================================================

    /**
     * Register all fusion recipes.
     * Called from {@code SuperCraftingMod} on {@code ServerAboutToStartEvent}.
     */
    public static @NotNull List<ShapelessRecipe> createAll() {
        var recipes = new ArrayList<ShapelessRecipe>();
        var ns = "super_crafting";

        // === Sword recipes (3 blades + combat essence) ===
        addFusion(recipes, ns, "fusion_iron_sword",      Items.IRON_SWORD,      ModItems.SUPER_IRON_SWORD,      SWORD_T1);
        addFusion(recipes, ns, "fusion_gold_sword",      Items.GOLDEN_SWORD,    ModItems.SUPER_GOLD_SWORD,      SWORD_T2);
        addFusion(recipes, ns, "fusion_diamond_sword",   Items.DIAMOND_SWORD,   ModItems.SUPER_DIAMOND_SWORD,   SWORD_T3);
        addFusion(recipes, ns, "fusion_netherite_sword", Items.NETHERITE_SWORD, ModItems.SUPER_NETHERITE_SWORD, SWORD_T4);
        addFusion(recipes, ns, "fusion_ultimate_sword",  ModItems.SUPER_NETHERITE_SWORD, ModItems.ULTIMATE_SWORD, SWORD_T5);

        // === Pickaxe recipes (3 picks + mining essence) ===
        addFusion(recipes, ns, "fusion_iron_pickaxe",      Items.IRON_PICKAXE,      ModItems.SUPER_IRON_PICKAXE,      PICK_T1);
        addFusion(recipes, ns, "fusion_gold_pickaxe",      Items.GOLDEN_PICKAXE,    ModItems.SUPER_GOLD_PICKAXE,      PICK_T2);
        addFusion(recipes, ns, "fusion_diamond_pickaxe",   Items.DIAMOND_PICKAXE,   ModItems.SUPER_DIAMOND_PICKAXE,   PICK_T3);
        addFusion(recipes, ns, "fusion_netherite_pickaxe", Items.NETHERITE_PICKAXE, ModItems.SUPER_NETHERITE_PICKAXE, PICK_T4);
        addFusion(recipes, ns, "fusion_ultimate_pickaxe",  ModItems.SUPER_NETHERITE_PICKAXE, ModItems.ULTIMATE_PICKAXE, PICK_T5);

        // === Axe recipes (3 axes + edge essence) ===
        addFusion(recipes, ns, "fusion_iron_axe",      Items.IRON_AXE,      ModItems.SUPER_IRON_AXE,      AXE_T1);
        addFusion(recipes, ns, "fusion_gold_axe",      Items.GOLDEN_AXE,    ModItems.SUPER_GOLD_AXE,      AXE_T2);
        addFusion(recipes, ns, "fusion_diamond_axe",   Items.DIAMOND_AXE,   ModItems.SUPER_DIAMOND_AXE,   AXE_T3);
        addFusion(recipes, ns, "fusion_netherite_axe", Items.NETHERITE_AXE, ModItems.SUPER_NETHERITE_AXE, AXE_T4);
        addFusion(recipes, ns, "fusion_ultimate_axe",  ModItems.SUPER_NETHERITE_AXE, ModItems.ULTIMATE_AXE, AXE_T5);

        // === Shovel recipes (3 shovels + earth essence) ===
        addFusion(recipes, ns, "fusion_iron_shovel",      Items.IRON_SHOVEL,      ModItems.SUPER_IRON_SHOVEL,      SHVL_T1);
        addFusion(recipes, ns, "fusion_gold_shovel",      Items.GOLDEN_SHOVEL,    ModItems.SUPER_GOLD_SHOVEL,      SHVL_T2);
        addFusion(recipes, ns, "fusion_diamond_shovel",   Items.DIAMOND_SHOVEL,   ModItems.SUPER_DIAMOND_SHOVEL,   SHVL_T3);
        addFusion(recipes, ns, "fusion_netherite_shovel", Items.NETHERITE_SHOVEL, ModItems.SUPER_NETHERITE_SHOVEL, SHVL_T4);
        addFusion(recipes, ns, "fusion_ultimate_shovel",  ModItems.SUPER_NETHERITE_SHOVEL, ModItems.ULTIMATE_SHOVEL, SHVL_T5);

        // === Hoe recipes (3 hoes + nature essence) ===
        addFusion(recipes, ns, "fusion_iron_hoe",      Items.IRON_HOE,      ModItems.SUPER_IRON_HOE,      HOE_T1);
        addFusion(recipes, ns, "fusion_gold_hoe",      Items.GOLDEN_HOE,    ModItems.SUPER_GOLD_HOE,      HOE_T2);
        addFusion(recipes, ns, "fusion_diamond_hoe",   Items.DIAMOND_HOE,   ModItems.SUPER_DIAMOND_HOE,   HOE_T3);
        addFusion(recipes, ns, "fusion_netherite_hoe", Items.NETHERITE_HOE, ModItems.SUPER_NETHERITE_HOE, HOE_T4);
        addFusion(recipes, ns, "fusion_ultimate_hoe",  ModItems.SUPER_NETHERITE_HOE, ModItems.ULTIMATE_HOE, HOE_T5);

        // === Armor recipes (3 armor pieces + defense essence) ===
        addFusion(recipes, ns, "fusion_iron_helmet",      Items.IRON_HELMET,      ModItems.SUPER_IRON_HELMET,      ARMOR_T1);
        addFusion(recipes, ns, "fusion_gold_helmet",      Items.GOLDEN_HELMET,    ModItems.SUPER_GOLD_HELMET,      ARMOR_T2);
        addFusion(recipes, ns, "fusion_diamond_helmet",   Items.DIAMOND_HELMET,   ModItems.SUPER_DIAMOND_HELMET,   ARMOR_T3);
        addFusion(recipes, ns, "fusion_netherite_helmet", Items.NETHERITE_HELMET, ModItems.SUPER_NETHERITE_HELMET, ARMOR_T4);
        addFusion(recipes, ns, "fusion_ultimate_helmet",  ModItems.SUPER_NETHERITE_HELMET, ModItems.ULTIMATE_HELMET, ARMOR_T5);

        addFusion(recipes, ns, "fusion_iron_chestplate",      Items.IRON_CHESTPLATE,      ModItems.SUPER_IRON_CHESTPLATE,      ARMOR_T1);
        addFusion(recipes, ns, "fusion_gold_chestplate",      Items.GOLDEN_CHESTPLATE,    ModItems.SUPER_GOLD_CHESTPLATE,      ARMOR_T2);
        addFusion(recipes, ns, "fusion_diamond_chestplate",   Items.DIAMOND_CHESTPLATE,   ModItems.SUPER_DIAMOND_CHESTPLATE,   ARMOR_T3);
        addFusion(recipes, ns, "fusion_netherite_chestplate", Items.NETHERITE_CHESTPLATE, ModItems.SUPER_NETHERITE_CHESTPLATE, ARMOR_T4);
        addFusion(recipes, ns, "fusion_ultimate_chestplate",  ModItems.SUPER_NETHERITE_CHESTPLATE, ModItems.ULTIMATE_CHESTPLATE, ARMOR_T5);

        addFusion(recipes, ns, "fusion_iron_leggings",      Items.IRON_LEGGINGS,      ModItems.SUPER_IRON_LEGGINGS,      ARMOR_T1);
        addFusion(recipes, ns, "fusion_gold_leggings",      Items.GOLDEN_LEGGINGS,    ModItems.SUPER_GOLD_LEGGINGS,      ARMOR_T2);
        addFusion(recipes, ns, "fusion_diamond_leggings",   Items.DIAMOND_LEGGINGS,   ModItems.SUPER_DIAMOND_LEGGINGS,   ARMOR_T3);
        addFusion(recipes, ns, "fusion_netherite_leggings", Items.NETHERITE_LEGGINGS, ModItems.SUPER_NETHERITE_LEGGINGS, ARMOR_T4);
        addFusion(recipes, ns, "fusion_ultimate_leggings",  ModItems.SUPER_NETHERITE_LEGGINGS, ModItems.ULTIMATE_LEGGINGS, ARMOR_T5);

        addFusion(recipes, ns, "fusion_iron_boots",      Items.IRON_BOOTS,      ModItems.SUPER_IRON_BOOTS,      ARMOR_T1);
        addFusion(recipes, ns, "fusion_gold_boots",      Items.GOLDEN_BOOTS,    ModItems.SUPER_GOLD_BOOTS,      ARMOR_T2);
        addFusion(recipes, ns, "fusion_diamond_boots",   Items.DIAMOND_BOOTS,   ModItems.SUPER_DIAMOND_BOOTS,   ARMOR_T3);
        addFusion(recipes, ns, "fusion_netherite_boots", Items.NETHERITE_BOOTS, ModItems.SUPER_NETHERITE_BOOTS, ARMOR_T4);
        addFusion(recipes, ns, "fusion_ultimate_boots",  ModItems.SUPER_NETHERITE_BOOTS, ModItems.ULTIMATE_BOOTS, ARMOR_T5);

        // === Bow recipes (3 bows + ranged essence) ===
        addFusion(recipes, ns, "fusion_iron_bow",      Items.BOW, ModItems.SUPER_IRON_BOW,      BOW_T1);
        addFusion(recipes, ns, "fusion_gold_bow",      Items.BOW, ModItems.SUPER_GOLD_BOW,      BOW_T2);
        addFusion(recipes, ns, "fusion_diamond_bow",   Items.BOW, ModItems.SUPER_DIAMOND_BOW,   BOW_T3);
        addFusion(recipes, ns, "fusion_netherite_bow", Items.BOW, ModItems.SUPER_NETHERITE_BOW, BOW_T4);
        addFusion(recipes, ns, "fusion_ultimate_bow",  ModItems.SUPER_NETHERITE_BOW, ModItems.ULTIMATE_BOW, BOW_T5);

        // === Shield recipes (3 shields + defense essence) ===
        addFusion(recipes, ns, "fusion_iron_shield",      Items.SHIELD, ModItems.SUPER_IRON_SHIELD,      SHIELD_T1);
        addFusion(recipes, ns, "fusion_gold_shield",      Items.SHIELD, ModItems.SUPER_GOLD_SHIELD,      SHIELD_T2);
        addFusion(recipes, ns, "fusion_diamond_shield",   Items.SHIELD, ModItems.SUPER_DIAMOND_SHIELD,   SHIELD_T3);
        addFusion(recipes, ns, "fusion_netherite_shield", Items.SHIELD, ModItems.SUPER_NETHERITE_SHIELD, SHIELD_T4);
        addFusion(recipes, ns, "fusion_ultimate_shield",  ModItems.SUPER_NETHERITE_SHIELD, ModItems.ULTIMATE_SHIELD, SHIELD_T5);

        // === Fishing Rod recipes (3 rods + aquatic essence) ===
        addFusion(recipes, ns, "fusion_iron_fishing_rod",      Items.FISHING_ROD, ModItems.SUPER_IRON_FISHING_ROD,      ROD_T1);
        addFusion(recipes, ns, "fusion_gold_fishing_rod",      Items.FISHING_ROD, ModItems.SUPER_GOLD_FISHING_ROD,      ROD_T2);
        addFusion(recipes, ns, "fusion_diamond_fishing_rod",   Items.FISHING_ROD, ModItems.SUPER_DIAMOND_FISHING_ROD,   ROD_T3);
        addFusion(recipes, ns, "fusion_netherite_fishing_rod", Items.FISHING_ROD, ModItems.SUPER_NETHERITE_FISHING_ROD, ROD_T4);
        addFusion(recipes, ns, "fusion_ultimate_fishing_rod",  ModItems.SUPER_NETHERITE_FISHING_ROD, ModItems.ULTIMATE_FISHING_ROD, ROD_T5);

        LOGGER.info("Created {} fusion recipes", recipes.size());
        return recipes;
    }

    // == Chain helpers =========================================================

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
