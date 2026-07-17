package com.bikininjas.supercrafting.game;

import com.bikininjas.supercrafting.armor.SetBonuses;
import com.bikininjas.supercrafting.item.ModItems;
import com.bikininjas.supercrafting.item.RightClickAbilities;
import com.bikininjas.supercrafting.item.SuperRepairKitItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.gametest.EmptyTemplate;
import net.neoforged.testframework.gametest.ExtendedGameTestHelper;
import org.jetbrains.annotations.NotNull;

/**
 * GameTest functions for new super-crafting features:
 * SetBonuses, RightClickAbilities, SuperRepairKitItem.
 */
@ForEachTest(groups = "super_crafting")
public final class SuperCraftingNewFeaturesTest {

    private SuperCraftingNewFeaturesTest() {
    }

    private static @NotNull ServerPlayer makePlayer(@NotNull ExtendedGameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.moveTo(
                helper.absolutePos(player.blockPosition()).getX() + 1.5,
                helper.absolutePos(player.blockPosition()).getY() + 2,
                helper.absolutePos(player.blockPosition()).getZ() + 1.5
        );
        return (ServerPlayer) player;
    }

    // ========================================================================
    // SetBonuses — Belt armor set gives Haste
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void setBonuses_ironGivesHaste(@NotNull ExtendedGameTestHelper helper) {
        SetBonuses.init();
        var player = makePlayer(helper);
        player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.SUPER_IRON_HELMET.get()));
        player.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ModItems.SUPER_IRON_CHESTPLATE.get()));
        player.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ModItems.SUPER_IRON_LEGGINGS.get()));
        player.setItemSlot(EquipmentSlot.FEET, new ItemStack(ModItems.SUPER_IRON_BOOTS.get()));

        NeoForge.EVENT_BUS.post(new PlayerTickEvent.Post(player));
        helper.assertTrue(player.hasEffect(MobEffects.DIG_SPEED),
                "Iron set should grant Haste (DIG_SPEED)");
        helper.succeed();
    }

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void setBonuses_incompleteSetNoBuff(@NotNull ExtendedGameTestHelper helper) {
        SetBonuses.init();
        var player = makePlayer(helper);
        player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.SUPER_IRON_HELMET.get()));

        NeoForge.EVENT_BUS.post(new PlayerTickEvent.Post(player));
        helper.assertTrue(!player.hasEffect(MobEffects.DIG_SPEED),
                "Incomplete set should NOT grant Haste");
        helper.succeed();
    }

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void setBonuses_noArmorDoesNothing(@NotNull ExtendedGameTestHelper helper) {
        SetBonuses.init();
        var player = makePlayer(helper);
        NeoForge.EVENT_BUS.post(new PlayerTickEvent.Post(player));
        helper.succeed();
    }

    // ========================================================================
    // RightClickAbilities — veinmine
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void rightClick_pickaxeDoesNotCrash(@NotNull ExtendedGameTestHelper helper) {
        RightClickAbilities.init();
        var player = makePlayer(helper);
        player.setItemSlot(EquipmentSlot.MAINHAND,
                new ItemStack(ModItems.SUPER_IRON_PICKAXE.get()));
        helper.succeed();
    }

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void rightClick_swordDoesNotCrash(@NotNull ExtendedGameTestHelper helper) {
        RightClickAbilities.init();
        var player = makePlayer(helper);
        player.setItemSlot(EquipmentSlot.MAINHAND,
                new ItemStack(ModItems.SUPER_IRON_SWORD.get()));
        helper.succeed();
    }

    // ========================================================================
    // SuperRepairKitItem
    // ========================================================================

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void repairKit_repairsDamagedSuperItem(@NotNull ExtendedGameTestHelper helper) {
        var player = makePlayer(helper);
        var sword = new ItemStack(ModItems.SUPER_IRON_SWORD.get());
        sword.setDamageValue(sword.getMaxDamage() / 2);

        var kit = new ItemStack(ModItems.SUPER_REPAIR_KIT.get());
        player.setItemSlot(EquipmentSlot.MAINHAND, kit);
        player.setItemSlot(EquipmentSlot.OFFHAND, sword);
        kit.getItem().use(player.level(), player, InteractionHand.MAIN_HAND).getResult();

        helper.assertTrue(sword.getDamageValue() < sword.getMaxDamage() / 2,
                "Repair kit should reduce damage below 50%");
        helper.succeed();
    }

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void repairKit_fullDurabilityItemNotConsumed(@NotNull ExtendedGameTestHelper helper) {
        var player = makePlayer(helper);
        var sword = new ItemStack(ModItems.SUPER_IRON_SWORD.get());

        var kit = new ItemStack(ModItems.SUPER_REPAIR_KIT.get());
        player.setItemSlot(EquipmentSlot.MAINHAND, kit);
        player.setItemSlot(EquipmentSlot.OFFHAND, sword);
        kit.getItem().use(player.level(), player, InteractionHand.MAIN_HAND).getResult();

        helper.assertTrue(sword.getDamageValue() == 0,
                "Full-durability item should not be damaged");
        helper.succeed();
    }

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void repairKit_nonSuperItemDoesNotRepair(@NotNull ExtendedGameTestHelper helper) {
        var player = makePlayer(helper);
        var diamond = new ItemStack(net.minecraft.world.item.Items.DIAMOND_SWORD);
        diamond.setDamageValue(diamond.getMaxDamage() / 2);

        var kit = new ItemStack(ModItems.SUPER_REPAIR_KIT.get());
        player.setItemSlot(EquipmentSlot.MAINHAND, kit);
        player.setItemSlot(EquipmentSlot.OFFHAND, diamond);
        kit.getItem().use(player.level(), player, InteractionHand.MAIN_HAND).getResult();

        helper.assertTrue(diamond.getDamageValue() >= diamond.getMaxDamage() / 2,
                "Non-super item should not be repaired by repair kit");
        helper.succeed();
    }

    @EmptyTemplate(value = "3x3x3", floor = true)
    public static void repairKit_offhandItemRepairs(@NotNull ExtendedGameTestHelper helper) {
        var player = makePlayer(helper);
        var sword = new ItemStack(ModItems.SUPER_IRON_SWORD.get());
        sword.setDamageValue(sword.getMaxDamage() / 2);

        var kit = new ItemStack(ModItems.SUPER_REPAIR_KIT.get());
        player.setItemSlot(EquipmentSlot.MAINHAND, kit);
        player.setItemSlot(EquipmentSlot.OFFHAND, sword);
        kit.getItem().use(player.level(), player, InteractionHand.MAIN_HAND).getResult();

        helper.assertTrue(sword.getDamageValue() < sword.getMaxDamage() / 2,
                "Repair kit should repair item in offhand");
        helper.succeed();
    }
}
