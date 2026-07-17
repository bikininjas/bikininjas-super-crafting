package com.bikininjas.supercrafting.item;

import net.minecraft.world.item.Tiers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SuperTier} — verifies the exact stat values of all five
 * fusion tiers (IRON_PLUS, GOLD_PLUS, DIAMOND_PLUS, NETHERITE_PLUS, ULTIMATE).
 * <p>
 * Constructor signature: {@code SuperTier(uses, attackDamageBonus, speed,
 * enchantmentValue, repairLootTier, name, repairIngredient)}.
 */
class SuperTierTest {

    @Test
    void ironPlus_hasExpectedValues() {
        assertEquals(800, SuperTier.IRON_PLUS.getUses(), "IRON_PLUS uses");
        assertEquals(7.0f, SuperTier.IRON_PLUS.getAttackDamageBonus(), 0.0001f, "IRON_PLUS attack damage bonus");
        assertEquals(8.0f, SuperTier.IRON_PLUS.getSpeed(), 0.0001f, "IRON_PLUS speed");
        assertEquals(14, SuperTier.IRON_PLUS.getEnchantmentValue(), "IRON_PLUS enchantment value");
        assertEquals(Tiers.IRON, SuperTier.IRON_PLUS.getRepairLootTier(), "IRON_PLUS repair loot tier");
        assertEquals("iron_plus", SuperTier.IRON_PLUS.getName(), "IRON_PLUS name");
    }

    @Test
    void goldPlus_hasExpectedValues() {
        assertEquals(600, SuperTier.GOLD_PLUS.getUses(), "GOLD_PLUS uses");
        assertEquals(6.0f, SuperTier.GOLD_PLUS.getAttackDamageBonus(), 0.0001f, "GOLD_PLUS attack damage bonus");
        assertEquals(14.0f, SuperTier.GOLD_PLUS.getSpeed(), 0.0001f, "GOLD_PLUS speed");
        assertEquals(25, SuperTier.GOLD_PLUS.getEnchantmentValue(), "GOLD_PLUS enchantment value");
        assertEquals(Tiers.GOLD, SuperTier.GOLD_PLUS.getRepairLootTier(), "GOLD_PLUS repair loot tier");
        assertEquals("gold_plus", SuperTier.GOLD_PLUS.getName(), "GOLD_PLUS name");
    }

    @Test
    void diamondPlus_hasExpectedValues() {
        assertEquals(1800, SuperTier.DIAMOND_PLUS.getUses(), "DIAMOND_PLUS uses");
        assertEquals(11.0f, SuperTier.DIAMOND_PLUS.getAttackDamageBonus(), 0.0001f, "DIAMOND_PLUS attack damage bonus");
        assertEquals(10.0f, SuperTier.DIAMOND_PLUS.getSpeed(), 0.0001f, "DIAMOND_PLUS speed");
        assertEquals(16, SuperTier.DIAMOND_PLUS.getEnchantmentValue(), "DIAMOND_PLUS enchantment value");
        assertEquals(Tiers.DIAMOND, SuperTier.DIAMOND_PLUS.getRepairLootTier(), "DIAMOND_PLUS repair loot tier");
        assertEquals("diamond_plus", SuperTier.DIAMOND_PLUS.getName(), "DIAMOND_PLUS name");
    }

    @Test
    void netheritePlus_hasExpectedValues() {
        assertEquals(2800, SuperTier.NETHERITE_PLUS.getUses(), "NETHERITE_PLUS uses");
        assertEquals(16.0f, SuperTier.NETHERITE_PLUS.getAttackDamageBonus(), 0.0001f, "NETHERITE_PLUS attack damage bonus");
        assertEquals(12.0f, SuperTier.NETHERITE_PLUS.getSpeed(), 0.0001f, "NETHERITE_PLUS speed");
        assertEquals(18, SuperTier.NETHERITE_PLUS.getEnchantmentValue(), "NETHERITE_PLUS enchantment value");
        assertEquals(Tiers.NETHERITE, SuperTier.NETHERITE_PLUS.getRepairLootTier(), "NETHERITE_PLUS repair loot tier");
        assertEquals("netherite_plus", SuperTier.NETHERITE_PLUS.getName(), "NETHERITE_PLUS name");
    }

    @Test
    void ultimate_hasExpectedValues() {
        assertEquals(5000, SuperTier.ULTIMATE.getUses(), "ULTIMATE uses");
        assertEquals(35.0f, SuperTier.ULTIMATE.getAttackDamageBonus(), 0.0001f, "ULTIMATE attack damage bonus");
        assertEquals(18.0f, SuperTier.ULTIMATE.getSpeed(), 0.0001f, "ULTIMATE speed");
        assertEquals(25, SuperTier.ULTIMATE.getEnchantmentValue(), "ULTIMATE enchantment value");
        assertEquals(Tiers.NETHERITE, SuperTier.ULTIMATE.getRepairLootTier(), "ULTIMATE repair loot tier");
        assertEquals("ultimate", SuperTier.ULTIMATE.getName(), "ULTIMATE name");
    }

    @Test
    void allTiers_progressionIsCorrect() {
        // Gold trades durability for speed — intentionally lower uses than Iron
        assertTrue(SuperTier.IRON_PLUS.getUses() < SuperTier.DIAMOND_PLUS.getUses());
        assertTrue(SuperTier.DIAMOND_PLUS.getUses() < SuperTier.NETHERITE_PLUS.getUses());
        assertTrue(SuperTier.NETHERITE_PLUS.getUses() < SuperTier.ULTIMATE.getUses());
        assertTrue(SuperTier.IRON_PLUS.getAttackDamageBonus() < SuperTier.ULTIMATE.getAttackDamageBonus());
    }
}
