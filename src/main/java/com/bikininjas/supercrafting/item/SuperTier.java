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
     * Tier 1 — Workhorse: 800 uses, attack 7.0, speed 8.0 (3.5 hearts/hit).
     */
    IRON_PLUS(800, 7.0f, 8.0f, 14, Tiers.IRON, "iron_plus", () -> Ingredient.of(Items.IRON_INGOT)),

    /**
     * Tier 2 — Swift Striker: 600 uses, attack 6.0, speed 14.0, best enchant 25.
     */
    GOLD_PLUS(600, 6.0f, 14.0f, 25, Tiers.GOLD, "gold_plus", () -> Ingredient.of(Items.GOLD_INGOT)),

    /**
     * Tier 3 — Heavy Hitter: 1800 uses, attack 11.0, speed 10.0 (5.5 hearts/hit).
     */
    DIAMOND_PLUS(1800, 11.0f, 10.0f, 16, Tiers.DIAMOND, "diamond_plus", () -> Ingredient.of(Items.DIAMOND)),

    /**
     * Tier 4 — Endgame Standard: 2800 uses, attack 16.0, speed 12.0 (8 hearts/hit).
     */
    NETHERITE_PLUS(2800, 16.0f, 12.0f, 18, Tiers.NETHERITE, "netherite_plus", () -> Ingredient.of(Items.NETHERITE_INGOT)),

    /**
     * Tier 5 — Transcendent: 5000 uses, attack 35.0, speed 18.0 (17.5 hearts/hit — one-shots most mobs).
     */
    ULTIMATE(5000, 35.0f, 18.0f, 25, Tiers.NETHERITE, "ultimate", () -> Ingredient.of(Blocks.NETHERITE_BLOCK));

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
