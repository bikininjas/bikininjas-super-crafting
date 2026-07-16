package com.bikininjas.supercrafting;

import com.bikininjas.corelib.enchantment.EnchantmentUtils;
import com.bikininjas.supercrafting.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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

    private static final Logger LOGGER = LoggerFactory.getLogger("super_crafting");

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
            } else if (isTool(item)) {
                applyToolEnchants(stack, enchantments);
            }
        }
    }

    private static boolean isArmor(@NotNull Item item) {
        return item == ModItems.SUPER_IRON_HELMET.get()
                || item == ModItems.SUPER_IRON_CHESTPLATE.get()
                || item == ModItems.SUPER_IRON_LEGGINGS.get()
                || item == ModItems.SUPER_IRON_BOOTS.get()
                || item == ModItems.SUPER_GOLD_HELMET.get()
                || item == ModItems.SUPER_GOLD_CHESTPLATE.get()
                || item == ModItems.SUPER_GOLD_LEGGINGS.get()
                || item == ModItems.SUPER_GOLD_BOOTS.get()
                || item == ModItems.SUPER_DIAMOND_HELMET.get()
                || item == ModItems.SUPER_DIAMOND_CHESTPLATE.get()
                || item == ModItems.SUPER_DIAMOND_LEGGINGS.get()
                || item == ModItems.SUPER_DIAMOND_BOOTS.get()
                || item == ModItems.SUPER_NETHERITE_HELMET.get()
                || item == ModItems.SUPER_NETHERITE_CHESTPLATE.get()
                || item == ModItems.SUPER_NETHERITE_LEGGINGS.get()
                || item == ModItems.SUPER_NETHERITE_BOOTS.get()
                || item == ModItems.ULTIMATE_HELMET.get()
                || item == ModItems.ULTIMATE_CHESTPLATE.get()
                || item == ModItems.ULTIMATE_LEGGINGS.get()
                || item == ModItems.ULTIMATE_BOOTS.get();
    }

    private static boolean isTool(@NotNull Item item) {
        return item == ModItems.SUPER_IRON_SWORD.get()
                || item == ModItems.SUPER_GOLD_SWORD.get()
                || item == ModItems.SUPER_DIAMOND_SWORD.get()
                || item == ModItems.SUPER_NETHERITE_SWORD.get()
                || item == ModItems.ULTIMATE_SWORD.get()
                || item == ModItems.SUPER_IRON_PICKAXE.get()
                || item == ModItems.SUPER_GOLD_PICKAXE.get()
                || item == ModItems.SUPER_DIAMOND_PICKAXE.get()
                || item == ModItems.SUPER_NETHERITE_PICKAXE.get()
                || item == ModItems.ULTIMATE_PICKAXE.get()
                || item == ModItems.SUPER_IRON_AXE.get()
                || item == ModItems.SUPER_GOLD_AXE.get()
                || item == ModItems.SUPER_DIAMOND_AXE.get()
                || item == ModItems.SUPER_NETHERITE_AXE.get()
                || item == ModItems.ULTIMATE_AXE.get()
                || item == ModItems.SUPER_IRON_SHOVEL.get()
                || item == ModItems.SUPER_GOLD_SHOVEL.get()
                || item == ModItems.SUPER_DIAMOND_SHOVEL.get()
                || item == ModItems.SUPER_NETHERITE_SHOVEL.get()
                || item == ModItems.ULTIMATE_SHOVEL.get()
                || item == ModItems.SUPER_IRON_HOE.get()
                || item == ModItems.SUPER_GOLD_HOE.get()
                || item == ModItems.SUPER_DIAMOND_HOE.get()
                || item == ModItems.SUPER_NETHERITE_HOE.get()
                || item == ModItems.ULTIMATE_HOE.get();
    }

    private static void applyArmorEnchants(@NotNull ItemStack stack,
                                           @NotNull HolderLookup.RegistryLookup<Enchantment> enchantments) {
        var map = new java.util.HashMap<Holder<Enchantment>, Integer>();
        map.put(enchantments.getOrThrow(Enchantments.PROTECTION), 4);
        map.put(enchantments.getOrThrow(Enchantments.UNBREAKING), 3);
        map.put(enchantments.getOrThrow(Enchantments.MENDING), 1);
        EnchantmentUtils.applyEnchantments(stack, map);
    }

    private static void applyToolEnchants(@NotNull ItemStack stack,
                                          @NotNull HolderLookup.RegistryLookup<Enchantment> enchantments) {
        var map = new java.util.HashMap<Holder<Enchantment>, Integer>();
        map.put(enchantments.getOrThrow(Enchantments.EFFICIENCY), 5);
        map.put(enchantments.getOrThrow(Enchantments.UNBREAKING), 3);
        map.put(enchantments.getOrThrow(Enchantments.FORTUNE), 3);
        map.put(enchantments.getOrThrow(Enchantments.MENDING), 1);
        EnchantmentUtils.applyEnchantments(stack, map);
    }
}
