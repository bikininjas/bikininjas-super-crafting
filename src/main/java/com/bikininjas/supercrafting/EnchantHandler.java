package com.bikininjas.supercrafting;

import com.bikininjas.corelib.enchantment.EnchantmentUtils;
import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.supercrafting.item.ModItems;
import com.bikininjas.supercrafting.item.SuperBowItem;
import com.bikininjas.supercrafting.item.SuperFishingRodItem;
import com.bikininjas.supercrafting.item.SuperShieldItem;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Applies super enchantments to crafted super items.
 * <p>
 * Subscribes to {@link PlayerEvent.ItemCraftedEvent} on the NeoForge event bus.
 * Uses the static-initializer module pattern (no {@code @EventBusSubscriber}).
 * <ul>
 *   <li>Armor pieces get Protection, Unbreaking and Mending.</li>
 *   <li>Tools/weapons get Efficiency, Unbreaking, Fortune and Mending.</li>
 * </ul>
 */
public final class EnchantHandler {

    private static final ModLogger LOGGER = LogManager.getLogger("super_crafting", EnchantHandler.class);

    // Lazy-initialized to avoid accessing unbound DeferredHolders at class init time
    private static volatile Set<Item> swordSet;
    private static volatile Set<Item> pickaxeSet;
    private static volatile Set<Item> axeSet;
    private static volatile Set<Item> shovelSet;
    private static volatile Set<Item> hoeSet;
    private static volatile Set<Item> armorSet;

    private static Set<Item> swordSet() {
        if (swordSet == null) {
            swordSet = Set.of(
                    ModItems.SUPER_IRON_SWORD.get(), ModItems.SUPER_GOLD_SWORD.get(),
                    ModItems.SUPER_DIAMOND_SWORD.get(), ModItems.SUPER_NETHERITE_SWORD.get(),
                    ModItems.ULTIMATE_SWORD.get()
            );
        }
        return swordSet;
    }

    private static Set<Item> pickaxeSet() {
        if (pickaxeSet == null) {
            pickaxeSet = Set.of(
                    ModItems.SUPER_IRON_PICKAXE.get(), ModItems.SUPER_GOLD_PICKAXE.get(),
                    ModItems.SUPER_DIAMOND_PICKAXE.get(), ModItems.SUPER_NETHERITE_PICKAXE.get(),
                    ModItems.ULTIMATE_PICKAXE.get()
            );
        }
        return pickaxeSet;
    }

    private static Set<Item> axeSet() {
        if (axeSet == null) {
            axeSet = Set.of(
                    ModItems.SUPER_IRON_AXE.get(), ModItems.SUPER_GOLD_AXE.get(),
                    ModItems.SUPER_DIAMOND_AXE.get(), ModItems.SUPER_NETHERITE_AXE.get(),
                    ModItems.ULTIMATE_AXE.get()
            );
        }
        return axeSet;
    }

    private static Set<Item> shovelSet() {
        if (shovelSet == null) {
            shovelSet = Set.of(
                    ModItems.SUPER_IRON_SHOVEL.get(), ModItems.SUPER_GOLD_SHOVEL.get(),
                    ModItems.SUPER_DIAMOND_SHOVEL.get(), ModItems.SUPER_NETHERITE_SHOVEL.get(),
                    ModItems.ULTIMATE_SHOVEL.get()
            );
        }
        return shovelSet;
    }

    private static Set<Item> hoeSet() {
        if (hoeSet == null) {
            hoeSet = Set.of(
                    ModItems.SUPER_IRON_HOE.get(), ModItems.SUPER_GOLD_HOE.get(),
                    ModItems.SUPER_DIAMOND_HOE.get(), ModItems.SUPER_NETHERITE_HOE.get(),
                    ModItems.ULTIMATE_HOE.get()
            );
        }
        return hoeSet;
    }

    private static Set<Item> armorSet() {
        if (armorSet == null) {
            armorSet = Set.of(
                    ModItems.SUPER_IRON_HELMET.get(), ModItems.SUPER_IRON_CHESTPLATE.get(),
                    ModItems.SUPER_IRON_LEGGINGS.get(), ModItems.SUPER_IRON_BOOTS.get(),
                    ModItems.SUPER_GOLD_HELMET.get(), ModItems.SUPER_GOLD_CHESTPLATE.get(),
                    ModItems.SUPER_GOLD_LEGGINGS.get(), ModItems.SUPER_GOLD_BOOTS.get(),
                    ModItems.SUPER_DIAMOND_HELMET.get(), ModItems.SUPER_DIAMOND_CHESTPLATE.get(),
                    ModItems.SUPER_DIAMOND_LEGGINGS.get(), ModItems.SUPER_DIAMOND_BOOTS.get(),
                    ModItems.SUPER_NETHERITE_HELMET.get(), ModItems.SUPER_NETHERITE_CHESTPLATE.get(),
                    ModItems.SUPER_NETHERITE_LEGGINGS.get(), ModItems.SUPER_NETHERITE_BOOTS.get(),
                    ModItems.ULTIMATE_HELMET.get(), ModItems.ULTIMATE_CHESTPLATE.get(),
                    ModItems.ULTIMATE_LEGGINGS.get(), ModItems.ULTIMATE_BOOTS.get()
            );
        }
        return armorSet;
    }

    private EnchantHandler() {
    }

    // Static initializer — registers the event handler on the NeoForge bus.
    static {
        NeoForge.EVENT_BUS.register(EventHandler.class);
    }

    /** Force class loading so the static initializer runs. */
    public static void init() {
    }

    private static final class EventHandler {

        private EventHandler() {
        }

        @SubscribeEvent
        static void onItemCrafted(@NotNull PlayerEvent.ItemCraftedEvent event) {
            var stack = event.getCrafting();
            if (stack.isEmpty()) {
                return;
            }

            var item = stack.getItem();
            var registryAccess = event.getEntity().level().registryAccess();
            var enchantments = registryAccess.lookupOrThrow(Registries.ENCHANTMENT);

            if (isArmor(item)) {
                applyArmorEnchants(stack, enchantments);
            } else if (isSword(item)) {
                applySwordEnchants(stack, enchantments);
            } else if (isPickaxe(item) || isShovel(item)) {
                applyMiningEnchants(stack, enchantments);
            } else if (isAxe(item)) {
                applyAxeEnchants(stack, enchantments);
            } else if (isHoe(item)) {
                applyHoeEnchants(stack, enchantments);
            } else if (item instanceof SuperBowItem) {
                applyBowEnchants(stack, enchantments);
            } else if (item instanceof SuperShieldItem) {
                applyShieldEnchants(stack, enchantments);
            } else if (item instanceof SuperFishingRodItem) {
                applyFishingRodEnchants(stack, enchantments);
            }
        }
    }

    private static boolean isSword(@NotNull Item item) {
        return swordSet().contains(item);
    }

    private static boolean isPickaxe(@NotNull Item item) {
        return pickaxeSet().contains(item);
    }

    private static boolean isAxe(@NotNull Item item) {
        return axeSet().contains(item);
    }

    private static boolean isShovel(@NotNull Item item) {
        return shovelSet().contains(item);
    }

    private static boolean isHoe(@NotNull Item item) {
        return hoeSet().contains(item);
    }

    private static boolean isArmor(@NotNull Item item) {
        return armorSet().contains(item);
    }

    private static void applySwordEnchants(@NotNull ItemStack stack,
                                           @NotNull HolderLookup.RegistryLookup<Enchantment> enchantments) {
        var map = new java.util.HashMap<Holder<Enchantment>, Integer>();
        map.put(enchantments.getOrThrow(Enchantments.SHARPNESS), 5);
        map.put(enchantments.getOrThrow(Enchantments.LOOTING), 3);
        map.put(enchantments.getOrThrow(Enchantments.UNBREAKING), 3);
        map.put(enchantments.getOrThrow(Enchantments.MENDING), 1);
        EnchantmentUtils.applyEnchantments(stack, map);
    }

    private static void applyMiningEnchants(@NotNull ItemStack stack,
                                            @NotNull HolderLookup.RegistryLookup<Enchantment> enchantments) {
        var map = new java.util.HashMap<Holder<Enchantment>, Integer>();
        map.put(enchantments.getOrThrow(Enchantments.EFFICIENCY), 5);
        map.put(enchantments.getOrThrow(Enchantments.FORTUNE), 3);
        map.put(enchantments.getOrThrow(Enchantments.UNBREAKING), 3);
        map.put(enchantments.getOrThrow(Enchantments.MENDING), 1);
        EnchantmentUtils.applyEnchantments(stack, map);
    }

    private static void applyAxeEnchants(@NotNull ItemStack stack,
                                         @NotNull HolderLookup.RegistryLookup<Enchantment> enchantments) {
        var map = new java.util.HashMap<Holder<Enchantment>, Integer>();
        map.put(enchantments.getOrThrow(Enchantments.SHARPNESS), 4);
        map.put(enchantments.getOrThrow(Enchantments.EFFICIENCY), 5);
        map.put(enchantments.getOrThrow(Enchantments.UNBREAKING), 3);
        map.put(enchantments.getOrThrow(Enchantments.MENDING), 1);
        EnchantmentUtils.applyEnchantments(stack, map);
    }

    private static void applyHoeEnchants(@NotNull ItemStack stack,
                                         @NotNull HolderLookup.RegistryLookup<Enchantment> enchantments) {
        var map = new java.util.HashMap<Holder<Enchantment>, Integer>();
        map.put(enchantments.getOrThrow(Enchantments.FORTUNE), 3);
        map.put(enchantments.getOrThrow(Enchantments.UNBREAKING), 3);
        map.put(enchantments.getOrThrow(Enchantments.MENDING), 1);
        EnchantmentUtils.applyEnchantments(stack, map);
    }

    private static void applyArmorEnchants(@NotNull ItemStack stack,
                                           @NotNull HolderLookup.RegistryLookup<Enchantment> enchantments) {
        var map = new java.util.HashMap<Holder<Enchantment>, Integer>();
        map.put(enchantments.getOrThrow(Enchantments.PROTECTION), 4);
        map.put(enchantments.getOrThrow(Enchantments.UNBREAKING), 3);
        map.put(enchantments.getOrThrow(Enchantments.MENDING), 1);
        EnchantmentUtils.applyEnchantments(stack, map);
    }

    private static void applyBowEnchants(@NotNull ItemStack stack,
                                         @NotNull HolderLookup.RegistryLookup<Enchantment> enchantments) {
        var map = new java.util.HashMap<Holder<Enchantment>, Integer>();
        map.put(enchantments.getOrThrow(Enchantments.POWER), 5);
        map.put(enchantments.getOrThrow(Enchantments.FLAME), 1);
        map.put(enchantments.getOrThrow(Enchantments.PUNCH), 2);
        map.put(enchantments.getOrThrow(Enchantments.INFINITY), 1);
        map.put(enchantments.getOrThrow(Enchantments.UNBREAKING), 3);
        EnchantmentUtils.applyEnchantments(stack, map);
    }

    private static void applyShieldEnchants(@NotNull ItemStack stack,
                                            @NotNull HolderLookup.RegistryLookup<Enchantment> enchantments) {
        var map = new java.util.HashMap<Holder<Enchantment>, Integer>();
        map.put(enchantments.getOrThrow(Enchantments.UNBREAKING), 3);
        map.put(enchantments.getOrThrow(Enchantments.MENDING), 1);
        EnchantmentUtils.applyEnchantments(stack, map);
    }

    private static void applyFishingRodEnchants(@NotNull ItemStack stack,
                                                @NotNull HolderLookup.RegistryLookup<Enchantment> enchantments) {
        var map = new java.util.HashMap<Holder<Enchantment>, Integer>();
        map.put(enchantments.getOrThrow(Enchantments.LUCK_OF_THE_SEA), 3);
        map.put(enchantments.getOrThrow(Enchantments.LURE), 3);
        map.put(enchantments.getOrThrow(Enchantments.UNBREAKING), 3);
        map.put(enchantments.getOrThrow(Enchantments.MENDING), 1);
        EnchantmentUtils.applyEnchantments(stack, map);
    }
}
