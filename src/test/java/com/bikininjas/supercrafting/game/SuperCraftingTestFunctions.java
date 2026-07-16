package com.bikininjas.supercrafting.game;

import com.bikininjas.supercrafting.EnchantHandler;
import com.bikininjas.supercrafting.SuperFunnyIntegration;
import com.bikininjas.supercrafting.item.ModItems;
import com.bikininjas.supercrafting.recipe.ExoticRecipeManager;
import com.bikininjas.supercrafting.recipe.FusionRecipeManager;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.SimpleContainer;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.gametest.EmptyTemplate;
import net.neoforged.testframework.gametest.ExtendedGameTestHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * GameTest functions for the super-crafting mod, migrated to the NeoForge Test
 * Framework (mirrors {@code core-lib}'s {@code CoreLibTestFunctions}).
 */
@ForEachTest(groups = "super_crafting")
public final class SuperCraftingTestFunctions {

    private SuperCraftingTestFunctions() {
    }

    // ========================================================================
    // Item registration
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void superCraftingMod_itemsRegistered(@NotNull ExtendedGameTestHelper helper) {
        // Force module class loading so DeferredRegisters are populated.
        EnchantHandler.init();
        SuperFunnyIntegration.init();

        int itemCount = ModItems.ITEMS.getEntries().size();
        helper.assertTrue(itemCount == 55,
                "Expected 55 registered items, found " + itemCount);

        helper.assertTrue(ModItems.SUPER_CRAFTING_TAB.isBound(),
                "Creative tab 'super_crafting' should be registered");

        helper.succeed();
    }

    // ========================================================================
    // EnchantHandler
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void superCraftingMod_enchantHandler_appliesOnCraft(@NotNull ExtendedGameTestHelper helper) {
        EnchantHandler.init();

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var stack = new ItemStack(ModItems.ULTIMATE_SWORD.get());
        var container = new SimpleContainer(ItemStack.EMPTY);

        var event = new PlayerEvent.ItemCraftedEvent(player, stack, container);
        NeoForge.EVENT_BUS.post(event);

        helper.assertTrue(stack.isEnchanted(),
                "Crafting an ULTIMATE item should apply enchantments via EnchantHandler");
        helper.assertTrue(stack.getEnchantmentLevel(
                helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(net.minecraft.world.item.enchantment.Enchantments.UNBREAKING)) > 0,
                "ULTIMATE tool should carry Unbreaking from EnchantHandler");

        helper.succeed();
    }

    // ========================================================================
    // SuperFunnyIntegration
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void superFunnyIntegration_swordLightning(@NotNull ExtendedGameTestHelper helper) {
        SuperFunnyIntegration.init();

        var level = helper.getLevel();
        var player = helper.makeMockServerPlayerInLevel();
        player.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.ULTIMATE_SWORD.get()));

        var target = helper.spawn(EntityType.ZOMBIE, new net.minecraft.core.BlockPos(1, 1, 1));
        var source = player.damageSources().playerAttack(player);

        // Lightning triggers on 30% of hits — retry until a bolt is summoned.
        boolean struck = false;
        for (int i = 0; i < 50 && !struck; i++) {
            target.hurt(source, 1.0f);
            if (level.getEntitiesOfClass(Entity.class,
                    new net.minecraft.world.phys.AABB(target.blockPosition()).inflate(3))
                    .stream().anyMatch(e -> e.getType() == EntityType.LIGHTNING_BOLT)) {
                struck = true;
            }
            if (target.isDeadOrDying()) {
                target = helper.spawn(EntityType.ZOMBIE, new net.minecraft.core.BlockPos(1, 1, 1));
            }
        }

        helper.assertTrue(struck,
                "Hitting with the ULTIMATE sword should eventually summon a lightning bolt");
        helper.succeed();
    }

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void superFunnyIntegration_hoeChicken(@NotNull ExtendedGameTestHelper helper) {
        SuperFunnyIntegration.init();

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var hoe = new ItemStack(ModItems.ULTIMATE_HOE.get());

        var cow = helper.spawn(EntityType.COW, new net.minecraft.core.BlockPos(1, 1, 1));
        player.setItemSlot(EquipmentSlot.MAINHAND, hoe);
        var event = new PlayerInteractEvent.EntityInteract(player, InteractionHand.MAIN_HAND, cow);
        NeoForge.EVENT_BUS.post(event);

        boolean chickenPresent = helper.getLevel().getEntitiesOfClass(
                        net.minecraft.world.entity.animal.Chicken.class,
                        new net.minecraft.world.phys.AABB(cow.blockPosition()).inflate(3))
                .stream().anyMatch(c -> c.distanceToSqr(cow.position()) < 25.0);

        helper.assertTrue(chickenPresent,
                "Right-clicking an entity with the ULTIMATE hoe should transform it into a chicken");
        helper.succeed();
    }

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void superFunnyIntegration_bootsTrail(@NotNull ExtendedGameTestHelper helper) {
        SuperFunnyIntegration.init();

        var player = helper.makeMockServerPlayerInLevel();
        player.setItemSlot(EquipmentSlot.FEET, new ItemStack(ModItems.ULTIMATE_BOOTS.get()));
        player.setSprinting(true);

        helper.assertTrue(!player.getItemBySlot(EquipmentSlot.FEET).isEmpty(),
                "Player should be wearing the ULTIMATE boots");
        helper.assertTrue(player.isSprinting(), "Player should be sprinting");

        // Fire the tick event that drives the rainbow particle trail.
        NeoForge.EVENT_BUS.post(new PlayerTickEvent.Post(player));

        helper.succeed();
    }

    // ========================================================================
    // Recipes
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void fusionRecipe_shapelessCrafts(@NotNull ExtendedGameTestHelper helper) {
        var recipes = FusionRecipeManager.createAll();
        helper.assertTrue(recipes.size() == 45,
                "FusionRecipeManager.createAll() should return 45 recipes, found " + recipes.size());
        helper.succeed();
    }

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void exoticRecipe_shapedCrafts(@NotNull ExtendedGameTestHelper helper) {
        var recipes = ExoticRecipeManager.createAll();
        helper.assertTrue(recipes.size() == 5,
                "ExoticRecipeManager.createAll() should return 5 recipes, found " + recipes.size());
        helper.succeed();
    }

    // ========================================================================
    // Foods
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void foods_effectsApplied(@NotNull ExtendedGameTestHelper helper) {
        var player = helper.makeMockServerPlayerInLevel();
        var level = helper.getLevel();

        eatAndAssert(helper, player, level, new ItemStack(ModItems.SUPER_APPLE.get()),
                MobEffects.REGENERATION, "super_apple");
        eatAndAssert(helper, player, level, new ItemStack(ModItems.SUPER_CARROT.get()),
                MobEffects.NIGHT_VISION, "super_carrot");
        eatAndAssert(helper, player, level, new ItemStack(ModItems.SUPER_MELON.get()),
                MobEffects.ABSORPTION, "super_melon");
        eatAndAssert(helper, player, level, new ItemStack(ModItems.SUPER_CHORUS.get()),
                MobEffects.SLOW_FALLING, "super_chorus");
        eatAndAssert(helper, player, level, new ItemStack(ModItems.ULTIMATE_FEAST.get()),
                MobEffects.REGENERATION, "ultimate_feast");

        helper.succeed();
    }

    private static void eatAndAssert(@NotNull ExtendedGameTestHelper helper,
                                    @NotNull ServerPlayer player,
                                    @NotNull ServerLevel level,
                                    @NotNull ItemStack food,
                                    @NotNull Holder<MobEffect> expected,
                                    @NotNull String label) {
        var props = food.getItem().getFoodProperties(food, player);
        player.eat(level, food, props);
        helper.assertMobEffectPresent(player, expected,
                "Eating " + label + " should apply " + BuiltInRegistries.MOB_EFFECT.getKey(expected.value()));
    }
}
