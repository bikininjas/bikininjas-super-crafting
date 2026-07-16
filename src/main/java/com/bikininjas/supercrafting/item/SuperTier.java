package com.bikininjas.supercrafting.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 * Five fusion tiers for super items.
 */
public enum SuperTier implements Tier {

    /**
     * 9 iron → 1 Iron Plus
     */
    IRON_PLUS(500, 4.0f, 7.0f, 15, Tiers.IRON, "iron_plus", () -> Ingredient.of(Items.IRON_INGOT)),

    /**
     * 9 gold → 1 Gold Plus
     */
    GOLD_PLUS(750, 5.5f, 8.0f, 20, Tiers.GOLD, "gold_plus", () -> Ingredient.of(Items.GOLD_INGOT)),

    /**
     * 9 diamond → 1 Diamond Plus
     */
    DIAMOND_PLUS(1200, 7.0f, 9.0f, 18, Tiers.DIAMOND, "diamond_plus", () -> Ingredient.of(Items.DIAMOND)),

    /**
     * 9 netherite → 1 Netherite Plus
     */
    NETHERITE_PLUS(1800, 9.0f, 10.0f, 22, Tiers.NETHERITE, "netherite_plus", () -> Ingredient.of(Items.NETHERITE_INGOT)),

    /**
     * 9 netherite blocks → 1 Ultimate
     */
    ULTIMATE(3000, 12.0f, 12.0f, 25, Tiers.NETHERITE, "ultimate", () -> Ingredient.of(Blocks.NETHERITE_BLOCK));

    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final Tiers repairLootTier;
    private final String name;
    private final java.util.function.Supplier<Ingredient> repairIngredient;

    SuperTier(int uses, float attackDamageBonus, float speed, int enchantmentValue,
              Tiers repairLootTier, String name, java.util.function.Supplier<Ingredient> repairIngredient) {
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairLootTier = repairLootTier;
        this.name = name;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamageBonus;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }

    @Override
    public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
        return switch (this) {
            case IRON_PLUS -> BlockTags.INCORRECT_FOR_IRON_TOOL;
            case GOLD_PLUS -> BlockTags.INCORRECT_FOR_GOLD_TOOL;
            case DIAMOND_PLUS -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
            case NETHERITE_PLUS, ULTIMATE -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        };
    }

    /**
     * The vanilla tier used for mining level determination.
     */
    public @NotNull Tiers getRepairLootTier() {
        return repairLootTier;
    }

    public @NotNull String getName() {
        return name;
    }
}
