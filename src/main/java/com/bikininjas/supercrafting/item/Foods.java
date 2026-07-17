package com.bikininjas.supercrafting.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

/**
 * Five exotic food definitions for super crafting recipes.
 */
public final class Foods {

    private Foods() {}

    public static final FoodProperties SUPER_APPLE = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(1.4f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 2), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 1), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 0), 1.0f)
            .alwaysEdible().build();

    public static final FoodProperties SUPER_CARROT = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(1.6f)
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 3600, 0), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1800, 1), 1.0f)
            .alwaysEdible().build();

    public static final FoodProperties SUPER_MELON = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(2.0f)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 3600, 2), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 900, 0), 1.0f)
            .alwaysEdible().build();

    public static final FoodProperties SUPER_CHORUS = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(1.4f)
            .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 100, 0), 0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 2400, 2), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 2), 1.0f)
            .alwaysEdible().build();

    public static final FoodProperties ULTIMATE_FEAST = new FoodProperties.Builder()
            .nutrition(20).saturationModifier(4.0f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 4), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1800, 2), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1800, 0), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 3600, 4), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1800, 1), 1.0f)
            .alwaysEdible().build();
}
