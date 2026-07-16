package com.bikininjas.supercrafting.item;

import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Central registry for all super-crafting items.
 * One {@link DeferredRegister.Items} + one {@link DeferredRegister<CreativeModeTab>}.
 */
public final class ModItems {

    private static final ModLogger LOGGER = LogManager.getLogger("super_crafting", ModItems.class);

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("super_crafting");
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "super_crafting");

    private ModItems() {
    }

    // ================================================================
    // Tools — 5 tiers × 6 types = 30 items
    // ================================================================

    // Swords
    public static final DeferredItem<SwordItem> SUPER_IRON_SWORD = tool("super_iron_sword", SuperTier.IRON_PLUS, SwordItem::new);
    public static final DeferredItem<SwordItem> SUPER_GOLD_SWORD = tool("super_gold_sword", SuperTier.GOLD_PLUS, SwordItem::new);
    public static final DeferredItem<SwordItem> SUPER_DIAMOND_SWORD = tool("super_diamond_sword", SuperTier.DIAMOND_PLUS, SwordItem::new);
    public static final DeferredItem<SwordItem> SUPER_NETHERITE_SWORD = tool("super_netherite_sword", SuperTier.NETHERITE_PLUS, SwordItem::new);
    public static final DeferredItem<SwordItem> ULTIMATE_SWORD = tool("ultimate_sword", SuperTier.ULTIMATE, SwordItem::new);

    // Pickaxes
    public static final DeferredItem<PickaxeItem> SUPER_IRON_PICKAXE = tool("super_iron_pickaxe", SuperTier.IRON_PLUS, PickaxeItem::new);
    public static final DeferredItem<PickaxeItem> SUPER_GOLD_PICKAXE = tool("super_gold_pickaxe", SuperTier.GOLD_PLUS, PickaxeItem::new);
    public static final DeferredItem<PickaxeItem> SUPER_DIAMOND_PICKAXE = tool("super_diamond_pickaxe", SuperTier.DIAMOND_PLUS, PickaxeItem::new);
    public static final DeferredItem<PickaxeItem> SUPER_NETHERITE_PICKAXE = tool("super_netherite_pickaxe", SuperTier.NETHERITE_PLUS, PickaxeItem::new);
    public static final DeferredItem<PickaxeItem> ULTIMATE_PICKAXE = tool("ultimate_pickaxe", SuperTier.ULTIMATE, PickaxeItem::new);

    // Axes
    public static final DeferredItem<AxeItem> SUPER_IRON_AXE = tool("super_iron_axe", SuperTier.IRON_PLUS, AxeItem::new);
    public static final DeferredItem<AxeItem> SUPER_GOLD_AXE = tool("super_gold_axe", SuperTier.GOLD_PLUS, AxeItem::new);
    public static final DeferredItem<AxeItem> SUPER_DIAMOND_AXE = tool("super_diamond_axe", SuperTier.DIAMOND_PLUS, AxeItem::new);
    public static final DeferredItem<AxeItem> SUPER_NETHERITE_AXE = tool("super_netherite_axe", SuperTier.NETHERITE_PLUS, AxeItem::new);
    public static final DeferredItem<AxeItem> ULTIMATE_AXE = tool("ultimate_axe", SuperTier.ULTIMATE, AxeItem::new);

    // Shovels
    public static final DeferredItem<ShovelItem> SUPER_IRON_SHOVEL = tool("super_iron_shovel", SuperTier.IRON_PLUS, ShovelItem::new);
    public static final DeferredItem<ShovelItem> SUPER_GOLD_SHOVEL = tool("super_gold_shovel", SuperTier.GOLD_PLUS, ShovelItem::new);
    public static final DeferredItem<ShovelItem> SUPER_DIAMOND_SHOVEL = tool("super_diamond_shovel", SuperTier.DIAMOND_PLUS, ShovelItem::new);
    public static final DeferredItem<ShovelItem> SUPER_NETHERITE_SHOVEL = tool("super_netherite_shovel", SuperTier.NETHERITE_PLUS, ShovelItem::new);
    public static final DeferredItem<ShovelItem> ULTIMATE_SHOVEL = tool("ultimate_shovel", SuperTier.ULTIMATE, ShovelItem::new);

    // Hoes
    public static final DeferredItem<HoeItem> SUPER_IRON_HOE = tool("super_iron_hoe", SuperTier.IRON_PLUS, HoeItem::new);
    public static final DeferredItem<HoeItem> SUPER_GOLD_HOE = tool("super_gold_hoe", SuperTier.GOLD_PLUS, HoeItem::new);
    public static final DeferredItem<HoeItem> SUPER_DIAMOND_HOE = tool("super_diamond_hoe", SuperTier.DIAMOND_PLUS, HoeItem::new);
    public static final DeferredItem<HoeItem> SUPER_NETHERITE_HOE = tool("super_netherite_hoe", SuperTier.NETHERITE_PLUS, HoeItem::new);
    public static final DeferredItem<HoeItem> ULTIMATE_HOE = tool("ultimate_hoe", SuperTier.ULTIMATE, HoeItem::new);

    // ================================================================
    // Armor — 5 tiers × 4 types = 20 items
    // ================================================================

    // Iron Plus
    public static final DeferredItem<ArmorItem> SUPER_IRON_HELMET = armor("super_iron_helmet", SuperArmorMaterial.IRON_PLUS, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> SUPER_IRON_CHESTPLATE = armor("super_iron_chestplate", SuperArmorMaterial.IRON_PLUS, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> SUPER_IRON_LEGGINGS = armor("super_iron_leggings", SuperArmorMaterial.IRON_PLUS, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> SUPER_IRON_BOOTS = armor("super_iron_boots", SuperArmorMaterial.IRON_PLUS, ArmorItem.Type.BOOTS);

    // Gold Plus
    public static final DeferredItem<ArmorItem> SUPER_GOLD_HELMET = armor("super_gold_helmet", SuperArmorMaterial.GOLD_PLUS, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> SUPER_GOLD_CHESTPLATE = armor("super_gold_chestplate", SuperArmorMaterial.GOLD_PLUS, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> SUPER_GOLD_LEGGINGS = armor("super_gold_leggings", SuperArmorMaterial.GOLD_PLUS, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> SUPER_GOLD_BOOTS = armor("super_gold_boots", SuperArmorMaterial.GOLD_PLUS, ArmorItem.Type.BOOTS);

    // Diamond Plus
    public static final DeferredItem<ArmorItem> SUPER_DIAMOND_HELMET = armor("super_diamond_helmet", SuperArmorMaterial.DIAMOND_PLUS, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> SUPER_DIAMOND_CHESTPLATE = armor("super_diamond_chestplate", SuperArmorMaterial.DIAMOND_PLUS, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> SUPER_DIAMOND_LEGGINGS = armor("super_diamond_leggings", SuperArmorMaterial.DIAMOND_PLUS, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> SUPER_DIAMOND_BOOTS = armor("super_diamond_boots", SuperArmorMaterial.DIAMOND_PLUS, ArmorItem.Type.BOOTS);

    // Netherite Plus
    public static final DeferredItem<ArmorItem> SUPER_NETHERITE_HELMET = armor("super_netherite_helmet", SuperArmorMaterial.NETHERITE_PLUS, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> SUPER_NETHERITE_CHESTPLATE = armor("super_netherite_chestplate", SuperArmorMaterial.NETHERITE_PLUS, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> SUPER_NETHERITE_LEGGINGS = armor("super_netherite_leggings", SuperArmorMaterial.NETHERITE_PLUS, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> SUPER_NETHERITE_BOOTS = armor("super_netherite_boots", SuperArmorMaterial.NETHERITE_PLUS, ArmorItem.Type.BOOTS);

    // Ultimate
    public static final DeferredItem<ArmorItem> ULTIMATE_HELMET = armor("ultimate_helmet", SuperArmorMaterial.ULTIMATE, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> ULTIMATE_CHESTPLATE = armor("ultimate_chestplate", SuperArmorMaterial.ULTIMATE, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> ULTIMATE_LEGGINGS = armor("ultimate_leggings", SuperArmorMaterial.ULTIMATE, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> ULTIMATE_BOOTS = armor("ultimate_boots", SuperArmorMaterial.ULTIMATE, ArmorItem.Type.BOOTS);

    // ================================================================
    // Foods — 5 items
    // ================================================================

    public static final DeferredItem<Item> SUPER_APPLE = ITEMS.registerItem("super_apple",
            props -> new Item(props.food(Foods.SUPER_APPLE)));

    public static final DeferredItem<Item> SUPER_CARROT = ITEMS.registerItem("super_carrot",
            props -> new Item(props.food(Foods.SUPER_CARROT)));

    public static final DeferredItem<Item> SUPER_MELON = ITEMS.registerItem("super_melon",
            props -> new Item(props.food(Foods.SUPER_MELON)));

    public static final DeferredItem<Item> SUPER_CHORUS = ITEMS.registerItem("super_chorus",
            props -> new Item(props.food(Foods.SUPER_CHORUS)));

    public static final DeferredItem<Item> ULTIMATE_FEAST = ITEMS.registerItem("ultimate_feast",
            props -> new Item(props.food(Foods.ULTIMATE_FEAST)));

    // ================================================================
    // Creative tab
    // ================================================================

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SUPER_CRAFTING_TAB =
            CREATIVE_TABS.register("super_crafting", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.super_crafting"))
                    .icon(() -> new ItemStack(SUPER_IRON_SWORD.get()))
                    .displayItems((params, output) -> ITEMS.getEntries().forEach(e -> output.accept(e.get())))
                    .build());

    // ================================================================
    // Helpers
    // ================================================================

    @SuppressWarnings("SameParameterValue")
    private static <T extends TieredItem> DeferredItem<T> tool(String name, SuperTier tier, ToolFactory<T> factory) {
        return ITEMS.registerItem(name, props -> factory.create(tier, props.fireResistant()));
    }

    @SuppressWarnings("SameParameterValue")
    private static DeferredItem<ArmorItem> armor(String name, ArmorMaterial mat, ArmorItem.Type type) {
        return ITEMS.registerItem(name, props -> new ArmorItem(Holder.direct(mat), type, props.fireResistant()));
    }

    @FunctionalInterface
    private interface ToolFactory<T extends TieredItem> {
        T create(Tier tier, Item.Properties properties);
    }
}
