package com.bikininjas.supercrafting.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Five armor materials matching {@link SuperTier} progression.
 * <p>
 * NeoForge 21.1.x models {@link ArmorMaterial} as an immutable record, so each
 * tier is a pre-built instance rather than an enum/interface implementation.
 */
public final class SuperArmorMaterial {

    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = new EnumMap<>(Map.of(
            ArmorItem.Type.BOOTS, 13,
            ArmorItem.Type.LEGGINGS, 15,
            ArmorItem.Type.CHESTPLATE, 16,
            ArmorItem.Type.HELMET, 11,
            ArmorItem.Type.BODY, 16
    ));

    private static ArmorMaterial build(SuperTier tier, int[] defensePerSlot, Supplier<Ingredient> repair) {
        var defense = new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class);
        defense.put(ArmorItem.Type.BOOTS, defensePerSlot[3]);
        defense.put(ArmorItem.Type.LEGGINGS, defensePerSlot[2]);
        defense.put(ArmorItem.Type.CHESTPLATE, defensePerSlot[1]);
        defense.put(ArmorItem.Type.HELMET, defensePerSlot[0]);
        var layers = List.of(new ArmorMaterial.Layer(
                ResourceLocation.fromNamespaceAndPath("minecraft", "netherite"), "", false));
        var toughness = switch (tier) {
            case IRON_PLUS, GOLD_PLUS -> 1.0f;
            case DIAMOND_PLUS -> 2.0f;
            case NETHERITE_PLUS -> 4.0f;
            case ULTIMATE -> 6.0f;
        };
        var knockback = switch (tier) {
            case NETHERITE_PLUS -> 0.1f;
            case ULTIMATE -> 0.25f;
            default -> 0.0f;
        };
        return new ArmorMaterial(defense, tier.getEnchantmentValue(),
                SoundEvents.ARMOR_EQUIP_NETHERITE, repair, layers, toughness, knockback);
    }

    public static final ArmorMaterial IRON_PLUS = build(SuperTier.IRON_PLUS, new int[]{3, 7, 6, 3}, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ArmorMaterial GOLD_PLUS = build(SuperTier.GOLD_PLUS, new int[]{3, 7, 6, 3}, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ArmorMaterial DIAMOND_PLUS = build(SuperTier.DIAMOND_PLUS, new int[]{4, 8, 7, 4}, () -> Ingredient.of(Items.DIAMOND));
    public static final ArmorMaterial NETHERITE_PLUS = build(SuperTier.NETHERITE_PLUS, new int[]{5, 9, 8, 5}, () -> Ingredient.of(Items.NETHERITE_INGOT));
    public static final ArmorMaterial ULTIMATE = build(SuperTier.ULTIMATE, new int[]{8, 12, 10, 8}, () -> Ingredient.of(Items.NETHERITE_INGOT));

    private SuperArmorMaterial() {
    }
}
