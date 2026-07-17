package com.bikininjas.supercrafting;

import com.bikininjas.corelib.enchantment.EnchantmentUtils;
import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.supercrafting.item.ModItems;
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

import java.util.ArrayList;
import java.util.List;
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

    private static final Set<Item> armorSet;
    private static final Set<Item> toolSet;

    static {
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
        toolSet = Set.of(
                ModItems.SUPER_IRON_SWORD.get(), ModItems.SUPER_GOLD_SWORD.get(),
                ModItems.SUPER_DIAMOND_SWORD.get(), ModItems.SUPER_NETHERITE_SWORD.get(),
                ModItems.ULTIMATE_SWORD.get(),
                ModItems.SUPER_IRON_PICKAXE.get(), ModItems.SUPER_GOLD_PICKAXE.get(),
                ModItems.SUPER_DIAMOND_PICKAXE.get(), ModItems.SUPER_NETHERITE_PICKAXE.get(),
                ModItems.ULTIMATE_PICKAXE.get(),
                ModItems.SUPER_IRON_AXE.get(), ModItems.SUPER_GOLD_AXE.get(),
                ModItems.SUPER_DIAMOND_AXE.get(), ModItems.SUPER_NETHERITE_AXE.get(),
                ModItems.ULTIMATE_AXE.get(),
                ModItems.SUPER_IRON_SHOVEL.get(), ModItems.SUPER_GOLD_SHOVEL.get(),
                ModItems.SUPER_DIAMOND_SHOVEL.get(), ModItems.SUPER_NETHERITE_SHOVEL.get(),
                ModItems.ULTIMATE_SHOVEL.get(),
                ModItems.SUPER_IRON_HOE.get(), ModItems.SUPER_GOLD_HOE.get(),
                ModItems.SUPER_DIAMOND_HOE.get(), ModItems.SUPER_NETHERITE_HOE.get(),
                ModItems.ULTIMATE_HOE.get()
        );
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
            } else if (isTool(item)) {
                applyToolEnchants(stack, enchantments);
            }
        }
    }

    private static boolean isArmor(@NotNull Item item) {
        return armorSet.contains(item);
    }

    private static boolean isTool(@NotNull Item item) {
        return toolSet.contains(item);
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
