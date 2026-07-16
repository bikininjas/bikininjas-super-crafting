package com.bikininjas.supercrafting.item;

import com.bikininjas.supercrafting.SuperCraftingMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;

/**
 * Registry for all super-crafting items: weapons, tools, and armor.
 * <p>
 * Enchantments are defined as raw {@link ResourceKey} pairs in a static map
 * and resolved to {@link Holder holders} lazily at server-start time
 * (via {@link #createEnchantedStack(DeferredHolder)}).
 */
public final class ModItems {

    private ModItems() {}

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, SuperCraftingMod.MODID);

    // ──────────────────────────────────────────────
    //  Swords  (5 tiers)
    // ──────────────────────────────────────────────

    public static final DeferredHolder<Item, SwordItem> SUPER_IRON_SWORD =
            sword("super_iron_sword", SuperTier.IRON_PLUS);

    public static final DeferredHolder<Item, SwordItem> SUPER_GOLD_SWORD =
            sword("super_gold_sword", SuperTier.GOLD_PLUS);

    public static final DeferredHolder<Item, SwordItem> SUPER_DIAMOND_SWORD =
            sword("super_diamond_sword", SuperTier.DIAMOND_PLUS);

    public static final DeferredHolder<Item, SwordItem> SUPER_NETHERITE_SWORD =
            sword("super_netherite_sword", SuperTier.NETHERITE_PLUS);

    public static final DeferredHolder<Item, SwordItem> ULTIMATE_SWORD =
            sword("ultimate_sword", SuperTier.ULTIMATE);

    // ──────────────────────────────────────────────
    //  Pickaxes  (5 tiers)
    // ──────────────────────────────────────────────

    public static final DeferredHolder<Item, PickaxeItem> SUPER_IRON_PICKAXE =
            pickaxe("super_iron_pickaxe", SuperTier.IRON_PLUS);

    public static final DeferredHolder<Item, PickaxeItem> SUPER_GOLD_PICKAXE =
            pickaxe("super_gold_pickaxe", SuperTier.GOLD_PLUS);

    public static final DeferredHolder<Item, PickaxeItem> SUPER_DIAMOND_PICKAXE =
            pickaxe("super_diamond_pickaxe", SuperTier.DIAMOND_PLUS);

    public static final DeferredHolder<Item, PickaxeItem> SUPER_NETHERITE_PICKAXE =
            pickaxe("super_netherite_pickaxe", SuperTier.NETHERITE_PLUS);

    public static final DeferredHolder<Item, PickaxeItem> ULTIMATE_PICKAXE =
            pickaxe("ultimate_pickaxe", SuperTier.ULTIMATE);

    // ──────────────────────────────────────────────
    //  Axes  (5 tiers)
    // ──────────────────────────────────────────────

    public static final DeferredHolder<Item, AxeItem> SUPER_IRON_AXE =
            axe("super_iron_axe", SuperTier.IRON_PLUS);

    public static final DeferredHolder<Item, AxeItem> SUPER_GOLD_AXE =
            axe("super_gold_axe", SuperTier.GOLD_PLUS);

    public static final DeferredHolder<Item, AxeItem> SUPER_DIAMOND_AXE =
            axe("super_diamond_axe", SuperTier.DIAMOND_PLUS);

    public static final DeferredHolder<Item, AxeItem> SUPER_NETHERITE_AXE =
            axe("super_netherite_axe", SuperTier.NETHERITE_PLUS);

    public static final DeferredHolder<Item, AxeItem> ULTIMATE_AXE =
            axe("ultimate_axe", SuperTier.ULTIMATE);

    // ──────────────────────────────────────────────
    //  Shovels  (5 tiers)
    // ──────────────────────────────────────────────

    public static final DeferredHolder<Item, ShovelItem> SUPER_IRON_SHOVEL =
            shovel("super_iron_shovel", SuperTier.IRON_PLUS);

    public static final DeferredHolder<Item, ShovelItem> SUPER_GOLD_SHOVEL =
            shovel("super_gold_shovel", SuperTier.GOLD_PLUS);

    public static final DeferredHolder<Item, ShovelItem> SUPER_DIAMOND_SHOVEL =
            shovel("super_diamond_shovel", SuperTier.DIAMOND_PLUS);

    public static final DeferredHolder<Item, ShovelItem> SUPER_NETHERITE_SHOVEL =
            shovel("super_netherite_shovel", SuperTier.NETHERITE_PLUS);

    public static final DeferredHolder<Item, ShovelItem> ULTIMATE_SHOVEL =
            shovel("ultimate_shovel", SuperTier.ULTIMATE);

    // ──────────────────────────────────────────────
    //  Hoes  (5 tiers)
    // ──────────────────────────────────────────────

    public static final DeferredHolder<Item, HoeItem> SUPER_IRON_HOE =
            hoe("super_iron_hoe", SuperTier.IRON_PLUS);

    public static final DeferredHolder<Item, HoeItem> SUPER_GOLD_HOE =
            hoe("super_gold_hoe", SuperTier.GOLD_PLUS);

    public static final DeferredHolder<Item, HoeItem> SUPER_DIAMOND_HOE =
            hoe("super_diamond_hoe", SuperTier.DIAMOND_PLUS);

    public static final DeferredHolder<Item, HoeItem> SUPER_NETHERITE_HOE =
            hoe("super_netherite_hoe", SuperTier.NETHERITE_PLUS);

    public static final DeferredHolder<Item, HoeItem> ULTIMATE_HOE =
            hoe("ultimate_hoe", SuperTier.ULTIMATE);

    // ──────────────────────────────────────────────
    //  Armor  (5 tiers × 4 types)
    // ──────────────────────────────────────────────

    // -- Iron Plus --
    public static final DeferredHolder<Item, ArmorItem> SUPER_IRON_HELMET =
            armor("super_iron_helmet", SuperArmorMaterial.IRON_PLUS, ArmorItem.Type.HELMET);
    public static final DeferredHolder<Item, ArmorItem> SUPER_IRON_CHESTPLATE =
            armor("super_iron_chestplate", SuperArmorMaterial.IRON_PLUS, ArmorItem.Type.CHESTPLATE);
    public static final DeferredHolder<Item, ArmorItem> SUPER_IRON_LEGGINGS =
            armor("super_iron_leggings", SuperArmorMaterial.IRON_PLUS, ArmorItem.Type.LEGGINGS);
    public static final DeferredHolder<Item, ArmorItem> SUPER_IRON_BOOTS =
            armor("super_iron_boots", SuperArmorMaterial.IRON_PLUS, ArmorItem.Type.BOOTS);

    // -- Gold Plus --
    public static final DeferredHolder<Item, ArmorItem> SUPER_GOLD_HELMET =
            armor("super_gold_helmet", SuperArmorMaterial.GOLD_PLUS, ArmorItem.Type.HELMET);
    public static final DeferredHolder<Item, ArmorItem> SUPER_GOLD_CHESTPLATE =
            armor("super_gold_chestplate", SuperArmorMaterial.GOLD_PLUS, ArmorItem.Type.CHESTPLATE);
    public static final DeferredHolder<Item, ArmorItem> SUPER_GOLD_LEGGINGS =
            armor("super_gold_leggings", SuperArmorMaterial.GOLD_PLUS, ArmorItem.Type.LEGGINGS);
    public static final DeferredHolder<Item, ArmorItem> SUPER_GOLD_BOOTS =
            armor("super_gold_boots", SuperArmorMaterial.GOLD_PLUS, ArmorItem.Type.BOOTS);

    // -- Diamond Plus --
    public static final DeferredHolder<Item, ArmorItem> SUPER_DIAMOND_HELMET =
            armor("super_diamond_helmet", SuperArmorMaterial.DIAMOND_PLUS, ArmorItem.Type.HELMET);
    public static final DeferredHolder<Item, ArmorItem> SUPER_DIAMOND_CHESTPLATE =
            armor("super_diamond_chestplate", SuperArmorMaterial.DIAMOND_PLUS, ArmorItem.Type.CHESTPLATE);
    public static final DeferredHolder<Item, ArmorItem> SUPER_DIAMOND_LEGGINGS =
            armor("super_diamond_leggings", SuperArmorMaterial.DIAMOND_PLUS, ArmorItem.Type.LEGGINGS);
    public static final DeferredHolder<Item, ArmorItem> SUPER_DIAMOND_BOOTS =
            armor("super_diamond_boots", SuperArmorMaterial.DIAMOND_PLUS, ArmorItem.Type.BOOTS);

    // -- Netherite Plus --
    public static final DeferredHolder<Item, ArmorItem> SUPER_NETHERITE_HELMET =
            armor("super_netherite_helmet", SuperArmorMaterial.NETHERITE_PLUS, ArmorItem.Type.HELMET);
    public static final DeferredHolder<Item, ArmorItem> SUPER_NETHERITE_CHESTPLATE =
            armor("super_netherite_chestplate", SuperArmorMaterial.NETHERITE_PLUS, ArmorItem.Type.CHESTPLATE);
    public static final DeferredHolder<Item, ArmorItem> SUPER_NETHERITE_LEGGINGS =
            armor("super_netherite_leggings", SuperArmorMaterial.NETHERITE_PLUS, ArmorItem.Type.LEGGINGS);
    public static final DeferredHolder<Item, ArmorItem> SUPER_NETHERITE_BOOTS =
            armor("super_netherite_boots", SuperArmorMaterial.NETHERITE_PLUS, ArmorItem.Type.BOOTS);

    // -- Ultimate --
    public static final DeferredHolder<Item, ArmorItem> ULTIMATE_HELMET =
            armor("ultimate_helmet", SuperArmorMaterial.ULTIMATE, ArmorItem.Type.HELMET);
    public static final DeferredHolder<Item, ArmorItem> ULTIMATE_CHESTPLATE =
            armor("ultimate_chestplate", SuperArmorMaterial.ULTIMATE, ArmorItem.Type.CHESTPLATE);
    public static final DeferredHolder<Item, ArmorItem> ULTIMATE_LEGGINGS =
            armor("ultimate_leggings", SuperArmorMaterial.ULTIMATE, ArmorItem.Type.LEGGINGS);
    public static final DeferredHolder<Item, ArmorItem> ULTIMATE_BOOTS =
            armor("ultimate_boots", SuperArmorMaterial.ULTIMATE, ArmorItem.Type.BOOTS);

    // ──────────────────────────────────────────────
    //  Enchantment map (raw ResourceKey, resolved late)
    // ──────────────────────────────────────────────

    /** Pairs an enchantment key with its desired level. */
    public record EnchEntry(ResourceKey<Enchantment> key, int level) {}

    @SuppressWarnings("unchecked")
    private static Registry<Enchantment> enchantmentRegistry() {
        return (Registry<Enchantment>) BuiltInRegistries.REGISTRY
                .get(Registries.ENCHANTMENT.location());
    }

    /**
     * Map every super item to its list of enchantment entries.
     * Only populated once; safe to access after server start.
     */
    private static volatile Map<Item, List<EnchEntry>> enchantMap;

    /**
     * Look up (or build) the enchantment map for all super items.
     * <p>
     * Uses double-checked locking; the map is built lazily so that
     * {@link BuiltInRegistries} is fully available.
     */
    public static Map<Item, List<EnchEntry>> getEnchantMap() {
        Map<Item, List<EnchEntry>> result = enchantMap;
        if (result == null) {
            synchronized (ModItems.class) {
                result = enchantMap;
                if (result == null) {
                    enchantMap = result = buildEnchantMap();
                }
            }
        }
        return result;
    }

    private static Map<Item, List<EnchEntry>> buildEnchantMap() {
        Map<Item, List<EnchEntry>> map = new LinkedHashMap<>();

        // Swords
        map.put(SUPER_IRON_SWORD.get(),        List.of(entry(Enchantments.SHARPNESS, 2)));
        map.put(SUPER_GOLD_SWORD.get(),        List.of(entry(Enchantments.SHARPNESS, 3),    entry(Enchantments.FIRE_ASPECT, 1)));
        map.put(SUPER_DIAMOND_SWORD.get(),     List.of(entry(Enchantments.SHARPNESS, 4),    entry(Enchantments.FIRE_ASPECT, 2)));
        map.put(SUPER_NETHERITE_SWORD.get(),   List.of(entry(Enchantments.SHARPNESS, 5),    entry(Enchantments.FIRE_ASPECT, 2),
                                                         entry(Enchantments.LOOTING, 2)));
        map.put(ULTIMATE_SWORD.get(),          List.of(entry(Enchantments.SHARPNESS, 7),    entry(Enchantments.FIRE_ASPECT, 3),
                                                         entry(Enchantments.LOOTING, 3),     entry(Enchantments.SWEEPING_EDGE, 3)));

        // Pickaxes
        map.put(SUPER_IRON_PICKAXE.get(),      List.of(entry(Enchantments.EFFICIENCY, 2)));
        map.put(SUPER_GOLD_PICKAXE.get(),      List.of(entry(Enchantments.EFFICIENCY, 3),   entry(Enchantments.FORTUNE, 1)));
        map.put(SUPER_DIAMOND_PICKAXE.get(),   List.of(entry(Enchantments.EFFICIENCY, 4),   entry(Enchantments.FORTUNE, 2)));
        map.put(SUPER_NETHERITE_PICKAXE.get(), List.of(entry(Enchantments.EFFICIENCY, 5),   entry(Enchantments.FORTUNE, 3)));
        map.put(ULTIMATE_PICKAXE.get(),        List.of(entry(Enchantments.EFFICIENCY, 7),   entry(Enchantments.FORTUNE, 4),
                                                         entry(Enchantments.UNBREAKING, 4)));

        // Axes
        map.put(SUPER_IRON_AXE.get(),          List.of(entry(Enchantments.EFFICIENCY, 2)));
        map.put(SUPER_GOLD_AXE.get(),          List.of(entry(Enchantments.EFFICIENCY, 3),   entry(Enchantments.FORTUNE, 1)));
        map.put(SUPER_DIAMOND_AXE.get(),       List.of(entry(Enchantments.EFFICIENCY, 4),   entry(Enchantments.FORTUNE, 2)));
        map.put(SUPER_NETHERITE_AXE.get(),     List.of(entry(Enchantments.EFFICIENCY, 5),   entry(Enchantments.FORTUNE, 3)));
        map.put(ULTIMATE_AXE.get(),            List.of(entry(Enchantments.EFFICIENCY, 7),   entry(Enchantments.FORTUNE, 4),
                                                         entry(Enchantments.UNBREAKING, 4)));

        // Shovels
        map.put(SUPER_IRON_SHOVEL.get(),       List.of(entry(Enchantments.EFFICIENCY, 2)));
        map.put(SUPER_GOLD_SHOVEL.get(),       List.of(entry(Enchantments.EFFICIENCY, 3)));
        map.put(SUPER_DIAMOND_SHOVEL.get(),    List.of(entry(Enchantments.EFFICIENCY, 4)));
        map.put(SUPER_NETHERITE_SHOVEL.get(),  List.of(entry(Enchantments.EFFICIENCY, 5)));
        map.put(ULTIMATE_SHOVEL.get(),         List.of(entry(Enchantments.EFFICIENCY, 7),   entry(Enchantments.UNBREAKING, 4)));

        // Hoes
        map.put(SUPER_IRON_HOE.get(),          List.of(entry(Enchantments.EFFICIENCY, 2)));
        map.put(SUPER_GOLD_HOE.get(),          List.of(entry(Enchantments.EFFICIENCY, 3)));
        map.put(SUPER_DIAMOND_HOE.get(),       List.of(entry(Enchantments.EFFICIENCY, 4)));
        map.put(SUPER_NETHERITE_HOE.get(),     List.of(entry(Enchantments.EFFICIENCY, 5)));
        map.put(ULTIMATE_HOE.get(),            List.of(entry(Enchantments.EFFICIENCY, 7),   entry(Enchantments.UNBREAKING, 4)));

        // Armor — Iron Plus
        map.put(SUPER_IRON_HELMET.get(),       List.of(entry(Enchantments.PROTECTION, 2)));
        map.put(SUPER_IRON_CHESTPLATE.get(),   List.of(entry(Enchantments.PROTECTION, 2)));
        map.put(SUPER_IRON_LEGGINGS.get(),     List.of(entry(Enchantments.PROTECTION, 2)));
        map.put(SUPER_IRON_BOOTS.get(),        List.of(entry(Enchantments.PROTECTION, 2)));

        // Armor — Gold Plus
        map.put(SUPER_GOLD_HELMET.get(),       List.of(entry(Enchantments.PROTECTION, 3),    entry(Enchantments.FIRE_PROTECTION, 1)));
        map.put(SUPER_GOLD_CHESTPLATE.get(),   List.of(entry(Enchantments.PROTECTION, 3),    entry(Enchantments.FIRE_PROTECTION, 1)));
        map.put(SUPER_GOLD_LEGGINGS.get(),     List.of(entry(Enchantments.PROTECTION, 3),    entry(Enchantments.FIRE_PROTECTION, 1)));
        map.put(SUPER_GOLD_BOOTS.get(),        List.of(entry(Enchantments.PROTECTION, 3),    entry(Enchantments.FIRE_PROTECTION, 1)));

        // Armor — Diamond Plus
        map.put(SUPER_DIAMOND_HELMET.get(),    List.of(entry(Enchantments.PROTECTION, 4),    entry(Enchantments.PROJECTILE_PROTECTION, 2)));
        map.put(SUPER_DIAMOND_CHESTPLATE.get(),List.of(entry(Enchantments.PROTECTION, 4),    entry(Enchantments.PROJECTILE_PROTECTION, 2)));
        map.put(SUPER_DIAMOND_LEGGINGS.get(),  List.of(entry(Enchantments.PROTECTION, 4),    entry(Enchantments.PROJECTILE_PROTECTION, 2)));
        map.put(SUPER_DIAMOND_BOOTS.get(),     List.of(entry(Enchantments.PROTECTION, 4),    entry(Enchantments.PROJECTILE_PROTECTION, 2)));

        // Armor — Netherite Plus
        map.put(SUPER_NETHERITE_HELMET.get(),     List.of(entry(Enchantments.PROTECTION, 5),  entry(Enchantments.BLAST_PROTECTION, 2)));
        map.put(SUPER_NETHERITE_CHESTPLATE.get(), List.of(entry(Enchantments.PROTECTION, 5),  entry(Enchantments.BLAST_PROTECTION, 2)));
        map.put(SUPER_NETHERITE_LEGGINGS.get(),   List.of(entry(Enchantments.PROTECTION, 5),  entry(Enchantments.BLAST_PROTECTION, 2)));
        map.put(SUPER_NETHERITE_BOOTS.get(),      List.of(entry(Enchantments.PROTECTION, 5),  entry(Enchantments.BLAST_PROTECTION, 2)));

        // Armor — Ultimate
        map.put(ULTIMATE_HELMET.get(),      List.of(entry(Enchantments.PROTECTION, 6),
                                                     entry(Enchantments.FIRE_PROTECTION, 3),
                                                     entry(Enchantments.BLAST_PROTECTION, 3),
                                                     entry(Enchantments.PROJECTILE_PROTECTION, 3)));
        map.put(ULTIMATE_CHESTPLATE.get(),  List.of(entry(Enchantments.PROTECTION, 6),
                                                     entry(Enchantments.FIRE_PROTECTION, 3),
                                                     entry(Enchantments.BLAST_PROTECTION, 3),
                                                     entry(Enchantments.PROJECTILE_PROTECTION, 3)));
        map.put(ULTIMATE_LEGGINGS.get(),    List.of(entry(Enchantments.PROTECTION, 6),
                                                     entry(Enchantments.FIRE_PROTECTION, 3),
                                                     entry(Enchantments.BLAST_PROTECTION, 3),
                                                     entry(Enchantments.PROJECTILE_PROTECTION, 3)));
        map.put(ULTIMATE_BOOTS.get(),       List.of(entry(Enchantments.PROTECTION, 6),
                                                     entry(Enchantments.FIRE_PROTECTION, 3),
                                                     entry(Enchantments.BLAST_PROTECTION, 3),
                                                     entry(Enchantments.PROJECTILE_PROTECTION, 3)));

        return Collections.unmodifiableMap(map);
    }

    private static EnchEntry entry(ResourceKey<Enchantment> key, int level) {
        return new EnchEntry(key, level);
    }

    /**
     * Create an {@link ItemStack} for the given holder with all its
     * default enchantments applied.
     * Safe to call only after registries are available (server-start or later).
     */
    public static ItemStack createEnchantedStack(DeferredHolder<Item, ?> holder) {
        return createEnchantedStack(holder.get());
    }

    /**
     * Create an {@link ItemStack} for the given item with all its
     * default enchantments applied.
     * Safe to call only after registries are available (server-start or later).
     */
    public static ItemStack createEnchantedStack(Item item) {
        ItemStack stack = new ItemStack(item);
        List<EnchEntry> entries = getEnchantMap().get(item);
        if (entries == null || entries.isEmpty()) return stack;

        Registry<Enchantment> registry = enchantmentRegistry();
        var mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        for (EnchEntry e : entries) {
            registry.getHolder(e.key()).ifPresent(h -> mutable.set(h, e.level()));
        }
        stack.set(DataComponents.ENCHANTMENTS, mutable.toImmutable());
        return stack;
    }

    // ──────────────────────────────────────────────
    //  Registration helpers
    // ──────────────────────────────────────────────

    private static DeferredHolder<Item, SwordItem> sword(String name, SuperTier tier) {
        return ITEMS.register(name, () -> new SwordItem(tier, properties()) {
            @Override public boolean isFoil(ItemStack stack) { return true; }
        });
    }

    private static DeferredHolder<Item, PickaxeItem> pickaxe(String name, SuperTier tier) {
        return ITEMS.register(name, () -> new PickaxeItem(tier, properties()) {
            @Override public boolean isFoil(ItemStack stack) { return true; }
        });
    }

    private static DeferredHolder<Item, AxeItem> axe(String name, SuperTier tier) {
        return ITEMS.register(name, () -> new AxeItem(tier, properties()) {
            @Override public boolean isFoil(ItemStack stack) { return true; }
        });
    }

    private static DeferredHolder<Item, ShovelItem> shovel(String name, SuperTier tier) {
        return ITEMS.register(name, () -> new ShovelItem(tier, properties()) {
            @Override public boolean isFoil(ItemStack stack) { return true; }
        });
    }

    private static DeferredHolder<Item, HoeItem> hoe(String name, SuperTier tier) {
        return ITEMS.register(name, () -> new HoeItem(tier, properties()) {
            @Override public boolean isFoil(ItemStack stack) { return true; }
        });
    }

    private static DeferredHolder<Item, ArmorItem> armor(String name, Holder<ArmorMaterial> material,
                                                           ArmorItem.Type type) {
        return ITEMS.register(name, () -> new ArmorItem(material, type, properties()) {
            @Override public boolean isFoil(ItemStack stack) { return true; }
        });
    }

    private static Item.Properties properties() {
        return new Item.Properties().fireResistant();
    }
}
