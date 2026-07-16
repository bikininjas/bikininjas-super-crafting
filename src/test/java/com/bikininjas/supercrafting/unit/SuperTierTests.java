package com.bikininjas.supercrafting.unit;

import com.bikininjas.supercrafting.SuperCraftingMod;
import com.bikininjas.supercrafting.item.SuperTier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pure JUnit5 unit tests for {@link SuperTier} — no Minecraft runtime required.
 */
class SuperTierTests {

    @Test
    void modIdIsValid() {
        assertEquals("super_crafting", SuperCraftingMod.MODID);
        assertTrue(SuperCraftingMod.MODID.matches("[a-z][a-z0-9_]{1,63}"),
                "MODID must match NeoForge convention [a-z][a-z0-9_]{1,63}");
    }

    @Test
    void allTiersAreDefined() {
        SuperTier[] tiers = SuperTier.values();
        assertEquals(5, tiers.length);
    }

    @Test
    void tierOrderIsCorrect() {
        var tiers = SuperTier.values();
        assertEquals(SuperTier.IRON_PLUS, tiers[0]);
        assertEquals(SuperTier.GOLD_PLUS, tiers[1]);
        assertEquals(SuperTier.DIAMOND_PLUS, tiers[2]);
        assertEquals(SuperTier.NETHERITE_PLUS, tiers[3]);
        assertEquals(SuperTier.ULTIMATE, tiers[4]);
    }

    @Test
    void nextProgression() {
        assertEquals(SuperTier.GOLD_PLUS, SuperTier.IRON_PLUS.next());
        assertEquals(SuperTier.DIAMOND_PLUS, SuperTier.GOLD_PLUS.next());
        assertEquals(SuperTier.NETHERITE_PLUS, SuperTier.DIAMOND_PLUS.next());
        assertEquals(SuperTier.ULTIMATE, SuperTier.NETHERITE_PLUS.next());
    }

    @Test
    void ultimateNextReturnsItself() {
        assertEquals(SuperTier.ULTIMATE, SuperTier.ULTIMATE.next());
    }

    @Test
    void previousDegression() {
        assertEquals(SuperTier.NETHERITE_PLUS, SuperTier.ULTIMATE.previous());
        assertEquals(SuperTier.DIAMOND_PLUS, SuperTier.NETHERITE_PLUS.previous());
        assertEquals(SuperTier.GOLD_PLUS, SuperTier.DIAMOND_PLUS.previous());
        assertEquals(SuperTier.IRON_PLUS, SuperTier.GOLD_PLUS.previous());
    }

    @Test
    void ironPlusPreviousReturnsItself() {
        assertEquals(SuperTier.IRON_PLUS, SuperTier.IRON_PLUS.previous());
    }

    @Test
    void ironPlusStats() {
        var tier = SuperTier.IRON_PLUS;
        assertEquals(500, tier.getUses());
        assertEquals(4.0f, tier.getAttackDamageBonus(), 0.001f);
        assertEquals(7.0f, tier.getSpeed(), 0.001f);
        assertEquals(15, tier.getEnchantmentValue());
    }

    @Test
    void goldPlusStats() {
        var tier = SuperTier.GOLD_PLUS;
        assertEquals(750, tier.getUses());
        assertEquals(5.5f, tier.getAttackDamageBonus(), 0.001f);
        assertEquals(8.0f, tier.getSpeed(), 0.001f);
        assertEquals(20, tier.getEnchantmentValue());
    }

    @Test
    void diamondPlusStats() {
        var tier = SuperTier.DIAMOND_PLUS;
        assertEquals(1200, tier.getUses());
        assertEquals(7.0f, tier.getAttackDamageBonus(), 0.001f);
        assertEquals(9.0f, tier.getSpeed(), 0.001f);
        assertEquals(18, tier.getEnchantmentValue());
    }

    @Test
    void netheritePlusStats() {
        var tier = SuperTier.NETHERITE_PLUS;
        assertEquals(1800, tier.getUses());
        assertEquals(9.0f, tier.getAttackDamageBonus(), 0.001f);
        assertEquals(10.0f, tier.getSpeed(), 0.001f);
        assertEquals(22, tier.getEnchantmentValue());
    }

    @Test
    void ultimateStats() {
        var tier = SuperTier.ULTIMATE;
        assertEquals(3000, tier.getUses());
        assertEquals(12.0f, tier.getAttackDamageBonus(), 0.001f);
        assertEquals(12.0f, tier.getSpeed(), 0.001f);
        assertEquals(25, tier.getEnchantmentValue());
    }

    @Test
    void statsIncreaseMonotonically() {
        var tiers = SuperTier.values();
        for (int i = 1; i < tiers.length; i++) {
            assertTrue(tiers[i].getUses() > tiers[i - 1].getUses(),
                    tiers[i] + " durability must be > " + tiers[i - 1]);
            assertTrue(tiers[i].getAttackDamageBonus() >= tiers[i - 1].getAttackDamageBonus(),
                    tiers[i] + " damage must be >= " + tiers[i - 1]);
        }
    }

    @Test
    void enchantabilityIsPositive() {
        for (var tier : SuperTier.values()) {
            assertTrue(tier.getEnchantmentValue() > 0);
        }
    }
}
