package com.bikininjas.supercrafting.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Custom tiers for the super-crafting fusion system.
 * <p>
 * Each tier provides escalating stats: durability, mining speed,
 * attack damage bonus, enchantment value, and repair ingredient.
 */
public enum SuperTier implements Tier {

    IRON_PLUS(500, 7.0f, 4.0f, 15, () -> Ingredient.of(Items.IRON_INGOT)),
    GOLD_PLUS(750, 8.0f, 5.5f, 20, () -> Ingredient.of(Items.GOLD_INGOT)),
    DIAMOND_PLUS(1200, 9.0f, 7.0f, 18, () -> Ingredient.of(Items.DIAMOND)),
    NETHERITE_PLUS(1800, 10.0f, 9.0f, 22, () -> Ingredient.of(Items.NETHERITE_INGOT)),
    ULTIMATE(3000, 12.0f, 12.0f, 25, () -> Ingredient.of(Items.NETHERITE_BLOCK));

    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    SuperTier(int uses, float speed, float attackDamageBonus, int enchantmentValue,
              Supplier<Ingredient> repairIngredient) {
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
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
    public @Nullable TagKey<Block> getIncorrectBlocksForDrops() {
        return null;
    }

    /**
     * @return the next tier in the progression, or itself if this is the highest
     */
    public SuperTier next() {
        var values = values();
        int ord = ordinal();
        return ord < values.length - 1 ? values[ord + 1] : this;
    }

    /**
     * @return the previous tier in the progression, or itself if this is the lowest
     */
    public SuperTier previous() {
        int ord = ordinal();
        return ord > 0 ? values()[ord - 1] : this;
    }
}
