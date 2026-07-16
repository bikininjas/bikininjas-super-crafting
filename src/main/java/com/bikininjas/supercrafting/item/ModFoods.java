package com.bikininjas.supercrafting.item;

import com.bikininjas.supercrafting.SuperCraftingMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registry for super food items with exotic crafting requirements.
 * Each food has unique effects tuned to its tier.
 */
public final class ModFoods {

    private ModFoods() {}

    public static final DeferredRegister<Item> FOODS =
            DeferredRegister.create(Registries.ITEM, SuperCraftingMod.MODID);

    // ── Super Apple (Iron tier) ──
    public static final DeferredHolder<Item, Item> SUPER_APPLE =
            FOODS.register("super_apple", () -> new Item(props(food(8, 1.2f, true)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0f)
            )) {
                @Override public boolean isFoil(ItemStack stack) { return true; }
            });

    // ── Super Carrot (Gold tier) ──
    public static final DeferredHolder<Item, Item> SUPER_CARROT =
            FOODS.register("super_carrot", () -> new Item(props(food(10, 1.6f, true)
                    .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 6000, 0), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0), 1.0f)
            )) {
                @Override public boolean isFoil(ItemStack stack) { return true; }
            });

    // ── Super Melon (Diamond tier) ──
    public static final DeferredHolder<Item, Item> SUPER_MELON =
            FOODS.register("super_melon", () -> new Item(props(food(12, 2.0f, true)
                    .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 3600, 2), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0), 1.0f)
            )) {
                @Override public boolean isFoil(ItemStack stack) { return true; }
            });

    // ── Super Chorus (Netherite tier) ──
    public static final DeferredHolder<Item, Item> SUPER_CHORUS =
            FOODS.register("super_chorus", () -> new Item(props(food(8, 1.0f, true)
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 3600, 1), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 300, 2), 1.0f)
            )) {
                @Override public boolean isFoil(ItemStack stack) { return true; }
            });

    // ── Ultimate Feast (Ultimate tier) ──
    public static final DeferredHolder<Item, Item> ULTIMATE_FEAST =
            FOODS.register("ultimate_feast", () -> new Item(props(food(20, 4.0f, true)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 4), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 2), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 600, 4), 1.0f)
            )) {
                @Override public boolean isFoil(ItemStack stack) { return true; }
            });

    // ── helpers ──
    private static FoodProperties.Builder food(int nutrition, float sat, boolean alwaysEat) {
        var b = new FoodProperties.Builder().nutrition(nutrition).saturationModifier(sat);
        if (alwaysEat) b.alwaysEdible();
        return b;
    }

    private static Item.Properties props(FoodProperties.Builder fp) {
        return new Item.Properties().food(fp.build());
    }
}
