package com.bikininjas.supercrafting.item;

import com.bikininjas.supercrafting.SuperCraftingMod;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Holds the {@link ArmorMaterial} definitions for all super-crafting armor tiers.
 * <p>
 * Materials are registered via {@link DeferredRegister} to the {@link Registries#ARMOR_MATERIAL} registry.
 */
public final class SuperArmorMaterial {

    private SuperArmorMaterial() {}

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, SuperCraftingMod.MODID);

    // ──────────────────────────────────────────────
    //  Armor material holders
    // ──────────────────────────────────────────────

    public static final Holder<ArmorMaterial> IRON_PLUS =
            ARMOR_MATERIALS.register("super_iron", () -> material(
                    Map.of(ArmorItem.Type.HELMET, 2, ArmorItem.Type.CHESTPLATE, 5,
                            ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 2),
                    15, 1.0f, 0.0f, () -> Ingredient.of(Items.IRON_INGOT),
                    ResourceLocation.withDefaultNamespace("iron")));

    public static final Holder<ArmorMaterial> GOLD_PLUS =
            ARMOR_MATERIALS.register("super_gold", () -> material(
                    Map.of(ArmorItem.Type.HELMET, 2, ArmorItem.Type.CHESTPLATE, 6,
                            ArmorItem.Type.LEGGINGS, 7, ArmorItem.Type.BOOTS, 3),
                    20, 1.5f, 0.0f, () -> Ingredient.of(Items.GOLD_INGOT),
                    ResourceLocation.withDefaultNamespace("gold")));

    public static final Holder<ArmorMaterial> DIAMOND_PLUS =
            ARMOR_MATERIALS.register("super_diamond", () -> material(
                    Map.of(ArmorItem.Type.HELMET, 3, ArmorItem.Type.CHESTPLATE, 7,
                            ArmorItem.Type.LEGGINGS, 8, ArmorItem.Type.BOOTS, 3),
                    18, 2.0f, 0.0f, () -> Ingredient.of(Items.DIAMOND),
                    ResourceLocation.withDefaultNamespace("diamond")));

    public static final Holder<ArmorMaterial> NETHERITE_PLUS =
            ARMOR_MATERIALS.register("super_netherite", () -> material(
                    Map.of(ArmorItem.Type.HELMET, 4, ArmorItem.Type.CHESTPLATE, 8,
                            ArmorItem.Type.LEGGINGS, 9, ArmorItem.Type.BOOTS, 4),
                    22, 3.0f, 0.1f, () -> Ingredient.of(Items.NETHERITE_INGOT),
                    ResourceLocation.withDefaultNamespace("netherite")));

    public static final Holder<ArmorMaterial> ULTIMATE =
            ARMOR_MATERIALS.register("super_ultimate", () -> material(
                    Map.of(ArmorItem.Type.HELMET, 5, ArmorItem.Type.CHESTPLATE, 10,
                            ArmorItem.Type.LEGGINGS, 11, ArmorItem.Type.BOOTS, 5),
                    25, 4.0f, 0.2f, () -> Ingredient.of(Items.NETHERITE_BLOCK),
                    ResourceLocation.withDefaultNamespace("netherite")));

    // ──────────────────────────────────────────────
    //  Helper
    // ──────────────────────────────────────────────

    private static ArmorMaterial material(Map<ArmorItem.Type, Integer> defense,
                                           int enchantValue,
                                           float toughness,
                                           float knockbackResistance,
                                           Supplier<Ingredient> repairIngredient,
                                           ResourceLocation texture) {
        EnumMap<ArmorItem.Type, Integer> fullDefense = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            fullDefense.put(type, defense.getOrDefault(type, 0));
        }
        return new ArmorMaterial(
                fullDefense,
                enchantValue,
                SoundEvents.ARMOR_EQUIP_NETHERITE,
                repairIngredient,
                List.of(new ArmorMaterial.Layer(texture)),
                toughness,
                knockbackResistance
        );
    }
}
