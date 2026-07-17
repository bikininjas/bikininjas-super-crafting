package com.bikininjas.supercrafting.data;

import com.bikininjas.supercrafting.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * DataGen provider that generates 60 fusion (shapeless 9-ingredient) recipes
 * for mod-super-crafting.
 * <p>
 * Replaces the old {@code FusionRecipeManager} — output goes to
 * {@code src/generated/resources/data/super_crafting/recipe/*.json}.
 */
public final class FusionRecipeProvider extends RecipeProvider {

    public FusionRecipeProvider(PackOutput output,
                                @NotNull CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        buildSwords(output);
        buildPickaxes(output);
        buildAxes(output);
        buildShovels(output);
        buildHoes(output);
        buildArmor(output);
        buildBows(output);
        buildShields(output);
        buildFishingRods(output);
        buildExoticFoods(output);
    }

    private static void buildSwords(RecipeOutput out) {
        fusion(out, "fusion_iron_sword", RecipeCategory.COMBAT,
                Items.IRON_SWORD, ModItems.SUPER_IRON_SWORD,
                Items.IRON_SWORD, Items.IRON_SWORD, IRON_BLK, IRON_BLK, Items.FLINT, Items.FLINT, Items.BONE, Items.BONE);
        fusion(out, "fusion_gold_sword", RecipeCategory.COMBAT,
                Items.GOLDEN_SWORD, ModItems.SUPER_GOLD_SWORD,
                Items.GOLDEN_SWORD, Items.GOLDEN_SWORD, GOLD_BLK, GOLD_BLK, Items.GLOWSTONE_DUST, Items.GLOWSTONE_DUST, Items.BLAZE_POWDER, Items.BLAZE_POWDER);
        fusion(out, "fusion_diamond_sword", RecipeCategory.COMBAT,
                Items.DIAMOND_SWORD, ModItems.SUPER_DIAMOND_SWORD,
                Items.DIAMOND_SWORD, Items.DIAMOND_SWORD, DIAM_BLK, DIAM_BLK, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP, Items.GHAST_TEAR, Items.GHAST_TEAR);
        fusion(out, "fusion_netherite_sword", RecipeCategory.COMBAT,
                Items.NETHERITE_SWORD, ModItems.SUPER_NETHERITE_SWORD,
                Items.NETHERITE_SWORD, Items.NETHERITE_SWORD, NETH_BLK, NETH_BLK, Items.WITHER_SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.BLAZE_ROD, Items.BLAZE_ROD);
        fusion(out, "fusion_ultimate_sword", RecipeCategory.COMBAT,
                ModItems.SUPER_NETHERITE_SWORD, ModItems.ULTIMATE_SWORD,
                Items.NETHERITE_SWORD, Items.NETHERITE_SWORD, Items.NETHER_STAR, Items.NETHER_STAR, Items.DRAGON_BREATH, Items.DRAGON_BREATH, Items.END_CRYSTAL, Items.END_CRYSTAL);
    }

    private static void buildPickaxes(RecipeOutput out) {
        fusion(out, "fusion_iron_pickaxe", RecipeCategory.TOOLS,
                Items.IRON_PICKAXE, ModItems.SUPER_IRON_PICKAXE,
                Items.IRON_PICKAXE, Items.IRON_PICKAXE, IRON_BLK, IRON_BLK, Items.REDSTONE_BLOCK, Items.REDSTONE_BLOCK, Items.LAPIS_BLOCK, Items.LAPIS_BLOCK);
        fusion(out, "fusion_gold_pickaxe", RecipeCategory.TOOLS,
                Items.GOLDEN_PICKAXE, ModItems.SUPER_GOLD_PICKAXE,
                Items.GOLDEN_PICKAXE, Items.GOLDEN_PICKAXE, GOLD_BLK, GOLD_BLK, Items.QUARTZ_BLOCK, Items.QUARTZ_BLOCK, Items.GLOWSTONE, Items.GLOWSTONE);
        fusion(out, "fusion_diamond_pickaxe", RecipeCategory.TOOLS,
                Items.DIAMOND_PICKAXE, ModItems.SUPER_DIAMOND_PICKAXE,
                Items.DIAMOND_PICKAXE, Items.DIAMOND_PICKAXE, DIAM_BLK, DIAM_BLK, Items.OBSIDIAN, Items.OBSIDIAN, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP);
        fusion(out, "fusion_netherite_pickaxe", RecipeCategory.TOOLS,
                Items.NETHERITE_PICKAXE, ModItems.SUPER_NETHERITE_PICKAXE,
                Items.NETHERITE_PICKAXE, Items.NETHERITE_PICKAXE, NETH_BLK, NETH_BLK, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS, Items.CRYING_OBSIDIAN, Items.CRYING_OBSIDIAN);
        fusion(out, "fusion_ultimate_pickaxe", RecipeCategory.TOOLS,
                ModItems.SUPER_NETHERITE_PICKAXE, ModItems.ULTIMATE_PICKAXE,
                Items.NETHERITE_PICKAXE, Items.NETHERITE_PICKAXE, Items.NETHER_STAR, Items.NETHER_STAR, Items.END_CRYSTAL, Items.END_CRYSTAL, Items.DRAGON_HEAD, Items.DRAGON_HEAD);
    }

    private static void buildAxes(RecipeOutput out) {
        fusion(out, "fusion_iron_axe", RecipeCategory.TOOLS,
                Items.IRON_AXE, ModItems.SUPER_IRON_AXE,
                Items.IRON_AXE, Items.IRON_AXE, IRON_BLK, IRON_BLK, Items.FLINT, Items.FLINT, Items.OAK_LOG, Items.OAK_LOG);
        fusion(out, "fusion_gold_axe", RecipeCategory.TOOLS,
                Items.GOLDEN_AXE, ModItems.SUPER_GOLD_AXE,
                Items.GOLDEN_AXE, Items.GOLDEN_AXE, GOLD_BLK, GOLD_BLK, Items.HONEYCOMB, Items.HONEYCOMB, Items.AMETHYST_SHARD, Items.AMETHYST_SHARD);
        fusion(out, "fusion_diamond_axe", RecipeCategory.TOOLS,
                Items.DIAMOND_AXE, ModItems.SUPER_DIAMOND_AXE,
                Items.DIAMOND_AXE, Items.DIAMOND_AXE, DIAM_BLK, DIAM_BLK, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP, Items.ECHO_SHARD, Items.ECHO_SHARD);
        fusion(out, "fusion_netherite_axe", RecipeCategory.TOOLS,
                Items.NETHERITE_AXE, ModItems.SUPER_NETHERITE_AXE,
                Items.NETHERITE_AXE, Items.NETHERITE_AXE, NETH_BLK, NETH_BLK, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS, Items.WITHER_SKELETON_SKULL, Items.WITHER_SKELETON_SKULL);
        fusion(out, "fusion_ultimate_axe", RecipeCategory.TOOLS,
                ModItems.SUPER_NETHERITE_AXE, ModItems.ULTIMATE_AXE,
                Items.NETHERITE_AXE, Items.NETHERITE_AXE, Items.NETHER_STAR, Items.NETHER_STAR, Items.END_CRYSTAL, Items.END_CRYSTAL, Items.TOTEM_OF_UNDYING, Items.TOTEM_OF_UNDYING);
    }

    private static void buildShovels(RecipeOutput out) {
        fusion(out, "fusion_iron_shovel", RecipeCategory.TOOLS,
                Items.IRON_SHOVEL, ModItems.SUPER_IRON_SHOVEL,
                Items.IRON_SHOVEL, Items.IRON_SHOVEL, IRON_BLK, IRON_BLK, Items.GRAVEL, Items.GRAVEL, Items.CLAY, Items.CLAY);
        fusion(out, "fusion_gold_shovel", RecipeCategory.TOOLS,
                Items.GOLDEN_SHOVEL, ModItems.SUPER_GOLD_SHOVEL,
                Items.GOLDEN_SHOVEL, Items.GOLDEN_SHOVEL, GOLD_BLK, GOLD_BLK, Items.SAND, Items.SAND, Items.SOUL_SAND, Items.SOUL_SAND);
        fusion(out, "fusion_diamond_shovel", RecipeCategory.TOOLS,
                Items.DIAMOND_SHOVEL, ModItems.SUPER_DIAMOND_SHOVEL,
                Items.DIAMOND_SHOVEL, Items.DIAMOND_SHOVEL, DIAM_BLK, DIAM_BLK, Items.OBSIDIAN, Items.OBSIDIAN, Items.MYCELIUM, Items.MYCELIUM);
        fusion(out, "fusion_netherite_shovel", RecipeCategory.TOOLS,
                Items.NETHERITE_SHOVEL, ModItems.SUPER_NETHERITE_SHOVEL,
                Items.NETHERITE_SHOVEL, Items.NETHERITE_SHOVEL, NETH_BLK, NETH_BLK, Items.SOUL_SOIL, Items.SOUL_SOIL, Items.BASALT, Items.BASALT);
        fusion(out, "fusion_ultimate_shovel", RecipeCategory.TOOLS,
                ModItems.SUPER_NETHERITE_SHOVEL, ModItems.ULTIMATE_SHOVEL,
                Items.NETHERITE_SHOVEL, Items.NETHERITE_SHOVEL, Items.NETHER_STAR, Items.NETHER_STAR, Items.DRAGON_EGG, Items.DRAGON_EGG, Items.BEACON, Items.BEACON);
    }

    private static void buildHoes(RecipeOutput out) {
        fusion(out, "fusion_iron_hoe", RecipeCategory.TOOLS,
                Items.IRON_HOE, ModItems.SUPER_IRON_HOE,
                Items.IRON_HOE, Items.IRON_HOE, IRON_BLK, IRON_BLK, Items.BONE_MEAL, Items.BONE_MEAL, Items.WHEAT, Items.WHEAT);
        fusion(out, "fusion_gold_hoe", RecipeCategory.TOOLS,
                Items.GOLDEN_HOE, ModItems.SUPER_GOLD_HOE,
                Items.GOLDEN_HOE, Items.GOLDEN_HOE, GOLD_BLK, GOLD_BLK, Items.GOLDEN_CARROT, Items.GOLDEN_CARROT, Items.GLISTERING_MELON_SLICE, Items.GLISTERING_MELON_SLICE);
        fusion(out, "fusion_diamond_hoe", RecipeCategory.TOOLS,
                Items.DIAMOND_HOE, ModItems.SUPER_DIAMOND_HOE,
                Items.DIAMOND_HOE, Items.DIAMOND_HOE, DIAM_BLK, DIAM_BLK, Items.EXPERIENCE_BOTTLE, Items.EXPERIENCE_BOTTLE, Items.PUMPKIN, Items.PUMPKIN);
        fusion(out, "fusion_netherite_hoe", RecipeCategory.TOOLS,
                Items.NETHERITE_HOE, ModItems.SUPER_NETHERITE_HOE,
                Items.NETHERITE_HOE, Items.NETHERITE_HOE, NETH_BLK, NETH_BLK, Items.NETHER_WART, Items.NETHER_WART, Items.CHORUS_FRUIT, Items.CHORUS_FRUIT);
        fusion(out, "fusion_ultimate_hoe", RecipeCategory.TOOLS,
                ModItems.SUPER_NETHERITE_HOE, ModItems.ULTIMATE_HOE,
                Items.NETHERITE_HOE, Items.NETHERITE_HOE, Items.NETHER_STAR, Items.NETHER_STAR, Items.ENCHANTED_GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.TOTEM_OF_UNDYING, Items.TOTEM_OF_UNDYING);
    }

    private static void buildArmor(RecipeOutput out) {
        fusion(out, "fusion_iron_helmet", RecipeCategory.COMBAT,
                Items.IRON_HELMET, ModItems.SUPER_IRON_HELMET,
                Items.IRON_CHESTPLATE, Items.IRON_CHESTPLATE, IRON_BLK, IRON_BLK, Items.LEATHER, Items.LEATHER, Items.CHAIN, Items.CHAIN);
        fusion(out, "fusion_gold_helmet", RecipeCategory.COMBAT,
                Items.GOLDEN_HELMET, ModItems.SUPER_GOLD_HELMET,
                Items.GOLDEN_CHESTPLATE, Items.GOLDEN_CHESTPLATE, GOLD_BLK, GOLD_BLK, Items.HONEYCOMB, Items.HONEYCOMB, Items.AMETHYST_SHARD, Items.AMETHYST_SHARD);
        fusion(out, "fusion_diamond_helmet", RecipeCategory.COMBAT,
                Items.DIAMOND_HELMET, ModItems.SUPER_DIAMOND_HELMET,
                Items.DIAMOND_CHESTPLATE, Items.DIAMOND_CHESTPLATE, DIAM_BLK, DIAM_BLK, Items.OBSIDIAN, Items.OBSIDIAN, Items.SHIELD, Items.SHIELD);
        fusion(out, "fusion_netherite_helmet", RecipeCategory.COMBAT,
                Items.NETHERITE_HELMET, ModItems.SUPER_NETHERITE_HELMET,
                Items.NETHERITE_CHESTPLATE, Items.NETHERITE_CHESTPLATE, NETH_BLK, NETH_BLK, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS, Items.WITHER_ROSE, Items.WITHER_ROSE);
        fusion(out, "fusion_ultimate_helmet", RecipeCategory.COMBAT,
                ModItems.SUPER_NETHERITE_HELMET, ModItems.ULTIMATE_HELMET,
                Items.NETHERITE_CHESTPLATE, Items.NETHERITE_CHESTPLATE, Items.NETHER_STAR, Items.NETHER_STAR, Items.TOTEM_OF_UNDYING, Items.TOTEM_OF_UNDYING, Items.ELYTRA, Items.ELYTRA);

        fusion(out, "fusion_iron_chestplate", RecipeCategory.COMBAT,
                Items.IRON_CHESTPLATE, ModItems.SUPER_IRON_CHESTPLATE,
                Items.IRON_CHESTPLATE, Items.IRON_CHESTPLATE, IRON_BLK, IRON_BLK, Items.LEATHER, Items.LEATHER, Items.CHAIN, Items.CHAIN);
        fusion(out, "fusion_gold_chestplate", RecipeCategory.COMBAT,
                Items.GOLDEN_CHESTPLATE, ModItems.SUPER_GOLD_CHESTPLATE,
                Items.GOLDEN_CHESTPLATE, Items.GOLDEN_CHESTPLATE, GOLD_BLK, GOLD_BLK, Items.HONEYCOMB, Items.HONEYCOMB, Items.AMETHYST_SHARD, Items.AMETHYST_SHARD);
        fusion(out, "fusion_diamond_chestplate", RecipeCategory.COMBAT,
                Items.DIAMOND_CHESTPLATE, ModItems.SUPER_DIAMOND_CHESTPLATE,
                Items.DIAMOND_CHESTPLATE, Items.DIAMOND_CHESTPLATE, DIAM_BLK, DIAM_BLK, Items.OBSIDIAN, Items.OBSIDIAN, Items.SHIELD, Items.SHIELD);
        fusion(out, "fusion_netherite_chestplate", RecipeCategory.COMBAT,
                Items.NETHERITE_CHESTPLATE, ModItems.SUPER_NETHERITE_CHESTPLATE,
                Items.NETHERITE_CHESTPLATE, Items.NETHERITE_CHESTPLATE, NETH_BLK, NETH_BLK, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS, Items.WITHER_ROSE, Items.WITHER_ROSE);
        fusion(out, "fusion_ultimate_chestplate", RecipeCategory.COMBAT,
                ModItems.SUPER_NETHERITE_CHESTPLATE, ModItems.ULTIMATE_CHESTPLATE,
                Items.NETHERITE_CHESTPLATE, Items.NETHERITE_CHESTPLATE, Items.NETHER_STAR, Items.NETHER_STAR, Items.TOTEM_OF_UNDYING, Items.TOTEM_OF_UNDYING, Items.ELYTRA, Items.ELYTRA);

        fusion(out, "fusion_iron_leggings", RecipeCategory.COMBAT,
                Items.IRON_LEGGINGS, ModItems.SUPER_IRON_LEGGINGS,
                Items.IRON_CHESTPLATE, Items.IRON_CHESTPLATE, IRON_BLK, IRON_BLK, Items.LEATHER, Items.LEATHER, Items.CHAIN, Items.CHAIN);
        fusion(out, "fusion_gold_leggings", RecipeCategory.COMBAT,
                Items.GOLDEN_LEGGINGS, ModItems.SUPER_GOLD_LEGGINGS,
                Items.GOLDEN_CHESTPLATE, Items.GOLDEN_CHESTPLATE, GOLD_BLK, GOLD_BLK, Items.HONEYCOMB, Items.HONEYCOMB, Items.AMETHYST_SHARD, Items.AMETHYST_SHARD);
        fusion(out, "fusion_diamond_leggings", RecipeCategory.COMBAT,
                Items.DIAMOND_LEGGINGS, ModItems.SUPER_DIAMOND_LEGGINGS,
                Items.DIAMOND_CHESTPLATE, Items.DIAMOND_CHESTPLATE, DIAM_BLK, DIAM_BLK, Items.OBSIDIAN, Items.OBSIDIAN, Items.SHIELD, Items.SHIELD);
        fusion(out, "fusion_netherite_leggings", RecipeCategory.COMBAT,
                Items.NETHERITE_LEGGINGS, ModItems.SUPER_NETHERITE_LEGGINGS,
                Items.NETHERITE_CHESTPLATE, Items.NETHERITE_CHESTPLATE, NETH_BLK, NETH_BLK, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS, Items.WITHER_ROSE, Items.WITHER_ROSE);
        fusion(out, "fusion_ultimate_leggings", RecipeCategory.COMBAT,
                ModItems.SUPER_NETHERITE_LEGGINGS, ModItems.ULTIMATE_LEGGINGS,
                Items.NETHERITE_CHESTPLATE, Items.NETHERITE_CHESTPLATE, Items.NETHER_STAR, Items.NETHER_STAR, Items.TOTEM_OF_UNDYING, Items.TOTEM_OF_UNDYING, Items.ELYTRA, Items.ELYTRA);

        fusion(out, "fusion_iron_boots", RecipeCategory.COMBAT,
                Items.IRON_BOOTS, ModItems.SUPER_IRON_BOOTS,
                Items.IRON_CHESTPLATE, Items.IRON_CHESTPLATE, IRON_BLK, IRON_BLK, Items.LEATHER, Items.LEATHER, Items.CHAIN, Items.CHAIN);
        fusion(out, "fusion_gold_boots", RecipeCategory.COMBAT,
                Items.GOLDEN_BOOTS, ModItems.SUPER_GOLD_BOOTS,
                Items.GOLDEN_CHESTPLATE, Items.GOLDEN_CHESTPLATE, GOLD_BLK, GOLD_BLK, Items.HONEYCOMB, Items.HONEYCOMB, Items.AMETHYST_SHARD, Items.AMETHYST_SHARD);
        fusion(out, "fusion_diamond_boots", RecipeCategory.COMBAT,
                Items.DIAMOND_BOOTS, ModItems.SUPER_DIAMOND_BOOTS,
                Items.DIAMOND_CHESTPLATE, Items.DIAMOND_CHESTPLATE, DIAM_BLK, DIAM_BLK, Items.OBSIDIAN, Items.OBSIDIAN, Items.SHIELD, Items.SHIELD);
        fusion(out, "fusion_netherite_boots", RecipeCategory.COMBAT,
                Items.NETHERITE_BOOTS, ModItems.SUPER_NETHERITE_BOOTS,
                Items.NETHERITE_CHESTPLATE, Items.NETHERITE_CHESTPLATE, NETH_BLK, NETH_BLK, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS, Items.WITHER_ROSE, Items.WITHER_ROSE);
        fusion(out, "fusion_ultimate_boots", RecipeCategory.COMBAT,
                ModItems.SUPER_NETHERITE_BOOTS, ModItems.ULTIMATE_BOOTS,
                Items.NETHERITE_CHESTPLATE, Items.NETHERITE_CHESTPLATE, Items.NETHER_STAR, Items.NETHER_STAR, Items.TOTEM_OF_UNDYING, Items.TOTEM_OF_UNDYING, Items.ELYTRA, Items.ELYTRA);
    }

    private static void buildBows(RecipeOutput out) {
        fusion(out, "fusion_iron_bow", RecipeCategory.COMBAT,
                Items.BOW, ModItems.SUPER_IRON_BOW,
                Items.BOW, Items.BOW, Items.FEATHER, Items.FEATHER, Items.FEATHER, Items.STRING, Items.STRING, Items.STRING);
        fusion(out, "fusion_gold_bow", RecipeCategory.COMBAT,
                Items.BOW, ModItems.SUPER_GOLD_BOW,
                Items.BOW, Items.BOW, Items.SPECTRAL_ARROW, Items.SPECTRAL_ARROW, Items.SPECTRAL_ARROW, Items.STRING, Items.STRING, Items.STRING);
        fusion(out, "fusion_diamond_bow", RecipeCategory.COMBAT,
                Items.BOW, ModItems.SUPER_DIAMOND_BOW,
                Items.BOW, Items.BOW, Items.TIPPED_ARROW, Items.TIPPED_ARROW, Items.TIPPED_ARROW, Items.STRING, Items.STRING, Items.STRING);
        fusion(out, "fusion_netherite_bow", RecipeCategory.COMBAT,
                Items.BOW, ModItems.SUPER_NETHERITE_BOW,
                Items.BOW, Items.BOW, Items.SPECTRAL_ARROW, Items.SPECTRAL_ARROW, Items.SPECTRAL_ARROW, Items.NETHER_STAR, Items.NETHER_STAR, Items.NETHER_STAR);
        fusion(out, "fusion_ultimate_bow", RecipeCategory.COMBAT,
                ModItems.SUPER_NETHERITE_BOW, ModItems.ULTIMATE_BOW,
                Items.BOW, Items.BOW, Items.SPECTRAL_ARROW, Items.SPECTRAL_ARROW, Items.SPECTRAL_ARROW, Items.DRAGON_BREATH, Items.END_CRYSTAL, Items.END_CRYSTAL);
    }

    private static void buildShields(RecipeOutput out) {
        fusion(out, "fusion_iron_shield", RecipeCategory.COMBAT,
                Items.SHIELD, ModItems.SUPER_IRON_SHIELD,
                Items.SHIELD, Items.SHIELD, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_BLOCK, Items.IRON_BLOCK, Items.IRON_BLOCK);
        fusion(out, "fusion_gold_shield", RecipeCategory.COMBAT,
                Items.SHIELD, ModItems.SUPER_GOLD_SHIELD,
                Items.SHIELD, Items.SHIELD, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_BLOCK, Items.GOLD_BLOCK, Items.GOLD_BLOCK);
        fusion(out, "fusion_diamond_shield", RecipeCategory.COMBAT,
                Items.SHIELD, ModItems.SUPER_DIAMOND_SHIELD,
                Items.SHIELD, Items.SHIELD, Items.DIAMOND, Items.DIAMOND, Items.DIAMOND, Items.OBSIDIAN, Items.OBSIDIAN, Items.OBSIDIAN);
        fusion(out, "fusion_netherite_shield", RecipeCategory.COMBAT,
                Items.SHIELD, ModItems.SUPER_NETHERITE_SHIELD,
                Items.SHIELD, Items.SHIELD, Items.NETHERITE_INGOT, Items.NETHERITE_INGOT, Items.NETHERITE_INGOT, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS);
        fusion(out, "fusion_ultimate_shield", RecipeCategory.COMBAT,
                ModItems.SUPER_NETHERITE_SHIELD, ModItems.ULTIMATE_SHIELD,
                Items.SHIELD, Items.SHIELD, Items.NETHERITE_BLOCK, Items.END_CRYSTAL, Items.END_CRYSTAL, Items.ENDER_PEARL, Items.ENDER_PEARL, Items.ENDER_PEARL);
    }

    private static void buildFishingRods(RecipeOutput out) {
        fusion(out, "fusion_iron_fishing_rod", RecipeCategory.TOOLS,
                Items.FISHING_ROD, ModItems.SUPER_IRON_FISHING_ROD,
                Items.FISHING_ROD, Items.FISHING_ROD, Items.STRING, Items.STRING, Items.STRING, Items.COD, Items.COD, Items.COD);
        fusion(out, "fusion_gold_fishing_rod", RecipeCategory.TOOLS,
                Items.FISHING_ROD, ModItems.SUPER_GOLD_FISHING_ROD,
                Items.FISHING_ROD, Items.FISHING_ROD, Items.STRING, Items.STRING, Items.STRING, Items.SALMON, Items.SALMON, Items.SALMON);
        fusion(out, "fusion_diamond_fishing_rod", RecipeCategory.TOOLS,
                Items.FISHING_ROD, ModItems.SUPER_DIAMOND_FISHING_ROD,
                Items.FISHING_ROD, Items.FISHING_ROD, Items.STRING, Items.STRING, Items.STRING, Items.PUFFERFISH, Items.PUFFERFISH, Items.PUFFERFISH);
        fusion(out, "fusion_netherite_fishing_rod", RecipeCategory.TOOLS,
                Items.FISHING_ROD, ModItems.SUPER_NETHERITE_FISHING_ROD,
                Items.FISHING_ROD, Items.FISHING_ROD, Items.NAUTILUS_SHELL, Items.NAUTILUS_SHELL, Items.NAUTILUS_SHELL, Items.HEART_OF_THE_SEA, Items.HEART_OF_THE_SEA, Items.HEART_OF_THE_SEA);
        fusion(out, "fusion_ultimate_fishing_rod", RecipeCategory.TOOLS,
                ModItems.SUPER_NETHERITE_FISHING_ROD, ModItems.ULTIMATE_FISHING_ROD,
                Items.FISHING_ROD, Items.FISHING_ROD, Items.TRIDENT, Items.TRIDENT, Items.HEART_OF_THE_SEA, Items.NAUTILUS_SHELL, Items.NAUTILUS_SHELL, Items.NAUTILUS_SHELL);
    }

    private void buildExoticFoods(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.SUPER_APPLE)
                .pattern("GGG").pattern("GAG").pattern("GGG")
                .define('G', Items.GOLDEN_APPLE).define('A', Items.APPLE)
                .unlockedBy("has_golden_apple", has(Items.GOLDEN_APPLE))
                .save(output, ResourceLocation.fromNamespaceAndPath("super_crafting", "exotic_super_apple"));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.ULTIMATE_FEAST)
                .pattern("ACE").pattern("FNF").pattern("BDS")
                .define('A', ModItems.SUPER_APPLE).define('C', Items.GOLDEN_CARROT)
                .define('E', Items.ENCHANTED_GOLDEN_APPLE).define('F', Items.COOKED_BEEF)
                .define('N', Items.NETHER_STAR).define('B', Items.GOLDEN_APPLE)
                .define('D', Items.DRAGON_BREATH).define('S', Items.NETHERITE_SCRAP)
                .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
                .save(output, ResourceLocation.fromNamespaceAndPath("super_crafting", "exotic_ultimate_feast"));
    }

    private static final ItemLike IRON_BLK = Items.IRON_BLOCK;
    private static final ItemLike GOLD_BLK = Items.GOLD_BLOCK;
    private static final ItemLike DIAM_BLK = Items.DIAMOND_BLOCK;
    private static final ItemLike NETH_ING = Items.NETHERITE_INGOT;
    private static final ItemLike NETH_BLK = Items.NETHERITE_BLOCK;

    private static void fusion(RecipeOutput out, String name, RecipeCategory cat,
                               @NotNull ItemLike core, @NotNull ItemLike result,
                               @NotNull ItemLike... extras) {
        var builder = ShapelessRecipeBuilder.shapeless(cat, result).requires(core);
        for (var extra : extras) {
            builder.requires(extra);
        }
        builder.unlockedBy("has_" + getItemName(core), has(core))
                .save(out, ResourceLocation.fromNamespaceAndPath("super_crafting", name));
    }
}
