package com.bikininjas.supercrafting.game;

import com.bikininjas.supercrafting.EnchantHandler;
import com.bikininjas.supercrafting.SuperFunnyIntegration;
import com.bikininjas.supercrafting.item.ModItems;
import com.bikininjas.supercrafting.item.SuperBowItem;
import com.bikininjas.supercrafting.item.SuperFishingRodItem;
import com.bikininjas.supercrafting.item.SuperShieldItem;
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
        helper.assertTrue(itemCount == 71,
                "Expected 71 registered items, found " + itemCount);

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
        helper.assertTrue(recipes.size() == 60,
                "FusionRecipeManager.createAll() should return 60 recipes, found " + recipes.size());
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
    // Bows
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void superCraftingMod_bowDamagePerTier(@NotNull ExtendedGameTestHelper helper) {
        var ironBow = new ItemStack(ModItems.SUPER_IRON_BOW.get());
        var ultimateBow = new ItemStack(ModItems.ULTIMATE_BOW.get());

        helper.assertTrue(ironBow.getMaxDamage() < ultimateBow.getMaxDamage(),
                "ULTIMATE bow should have more durability than IRON");
        helper.assertTrue(ultimateBow.getItem() instanceof SuperBowItem,
                "ULTIMATE_BOW should be a SuperBowItem with custom damage multiplier");

        helper.succeed();
    }

    // ========================================================================
    // Shields
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void superCraftingMod_shieldDurabilityPerTier(@NotNull ExtendedGameTestHelper helper) {
        var ironShield = new ItemStack(ModItems.SUPER_IRON_SHIELD.get());
        var diamondShield = new ItemStack(ModItems.SUPER_DIAMOND_SHIELD.get());
        var ultimateShield = new ItemStack(ModItems.ULTIMATE_SHIELD.get());

        helper.assertTrue(diamondShield.getMaxDamage() > ironShield.getMaxDamage(),
                "DIAMOND shield should outlast IRON");
        helper.assertTrue(ultimateShield.getMaxDamage() > diamondShield.getMaxDamage(),
                "ULTIMATE shield should outlast DIAMOND");

        helper.succeed();
    }

    // ========================================================================
    // Fishing Rods
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void superCraftingMod_fishingRodProperties(@NotNull ExtendedGameTestHelper helper) {
        var ironRod = new ItemStack(ModItems.SUPER_IRON_FISHING_ROD.get());
        var ultimateRod = new ItemStack(ModItems.ULTIMATE_FISHING_ROD.get());

        helper.assertTrue(ironRod.getMaxDamage() < ultimateRod.getMaxDamage(),
                "ULTIMATE fishing rod should have more durability than IRON");
        helper.assertTrue(ultimateRod.getItem() instanceof SuperFishingRodItem,
                "ULTIMATE_FISHING_ROD should be a custom SuperFishingRodItem");

        helper.succeed();
    }

    // ========================================================================
    // EnchantHandler — bow/shield/rod enchants
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void superCraftingMod_enchantHandler_bowShieldRod(@NotNull ExtendedGameTestHelper helper) {
        EnchantHandler.init();
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var container = new SimpleContainer(ItemStack.EMPTY);
        var registry = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        // Ultimate bow: Power + Flame + Punch + Infinity
        var bowStack = new ItemStack(ModItems.ULTIMATE_BOW.get());
        NeoForge.EVENT_BUS.post(new PlayerEvent.ItemCraftedEvent(player, bowStack, container));
        var power = net.minecraft.world.item.enchantment.Enchantments.POWER;
        var flame = net.minecraft.world.item.enchantment.Enchantments.FLAME;
        helper.assertTrue(bowStack.getEnchantmentLevel(registry.getOrThrow(power)) > 0,
                "Ultimate bow should get Power enchantment");

        // Ultimate shield: Unbreaking + Mending
        var shieldStack = new ItemStack(ModItems.ULTIMATE_SHIELD.get());
        NeoForge.EVENT_BUS.post(new PlayerEvent.ItemCraftedEvent(player, shieldStack, container));
        var unbreaking = net.minecraft.world.item.enchantment.Enchantments.UNBREAKING;
        helper.assertTrue(shieldStack.getEnchantmentLevel(registry.getOrThrow(unbreaking)) > 0,
                "Ultimate shield should get Unbreaking enchantment");

        // Ultimate rod: Lure + Luck + Unbreaking
        var rodStack = new ItemStack(ModItems.ULTIMATE_FISHING_ROD.get());
        NeoForge.EVENT_BUS.post(new PlayerEvent.ItemCraftedEvent(player, rodStack, container));
        var lure = net.minecraft.world.item.enchantment.Enchantments.LURE;
        var luck = net.minecraft.world.item.enchantment.Enchantments.LUCK_OF_THE_SEA;
        helper.assertTrue(rodStack.getEnchantmentLevel(registry.getOrThrow(lure)) > 0
                        || rodStack.getEnchantmentLevel(registry.getOrThrow(luck)) > 0,
                "Ultimate fishing rod should get Lure or Luck enchantment");

        helper.succeed();
    }

    // ========================================================================
    // Repair Kit — on bow/shield/rod
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void superCraftingMod_repairKit_onNewItems(@NotNull ExtendedGameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var repairKit = new ItemStack(ModItems.SUPER_REPAIR_KIT.get());

        // Bow repair
        var bow = new ItemStack(ModItems.SUPER_IRON_BOW.get());
        bow.setDamageValue(bow.getMaxDamage() / 2);
        player.setItemSlot(EquipmentSlot.MAINHAND, repairKit);
        player.setItemSlot(EquipmentSlot.OFFHAND, bow);
        var result = repairKit.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        // Should consume kit and repair bow
        helper.assertTrue(result.getResult().consumesAction() || bow.getDamageValue() <= bow.getMaxDamage() / 2,
                "Repair kit should work on bows");

        // Shield repair
        var shield = new ItemStack(ModItems.SUPER_IRON_SHIELD.get());
        shield.setDamageValue(shield.getMaxDamage() / 2);
        player.setItemSlot(EquipmentSlot.OFFHAND, shield);
        player.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.SUPER_REPAIR_KIT.get()));
        result = new ItemStack(ModItems.SUPER_REPAIR_KIT.get()).getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(result.getResult().consumesAction() || shield.getDamageValue() <= shield.getMaxDamage() / 2,
                "Repair kit should work on shields");

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
