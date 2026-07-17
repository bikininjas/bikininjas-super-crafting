package com.bikininjas.supercrafting.armor;

import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.supercrafting.SuperCraftingMod;
import com.bikininjas.supercrafting.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Applies set bonuses when wearing a full armor set of the same super tier.
 * <p>
 * Bonuses per tier:
 * <ul>
 *   <li>Iron Plus → Haste I</li>
 *   <li>Gold Plus → Luck I</li>
 *   <li>Diamond Plus → Strength I</li>
 *   <li>Netherite Plus → Resistance I</li>
 *   <li>Ultimate → temporary flight (5s every 30s)</li>
 * </ul>
 */
public final class SetBonuses {

    private static final ModLogger LOGGER = LogManager.getLogger(SuperCraftingMod.MODID, SetBonuses.class);

    static {
        NeoForge.EVENT_BUS.register(EventHandler.class);
    }

    private SetBonuses() {
    }

    /** Force class loading so the static initializer runs. */
    public static void init() {
    }

    // -- Tier lookup (lazy to avoid DeferredHolder.get() in static context) --

    private enum BonusTier {
        IRON(ModItems.SUPER_IRON_HELMET, ModItems.SUPER_IRON_CHESTPLATE,
             ModItems.SUPER_IRON_LEGGINGS, ModItems.SUPER_IRON_BOOTS,
             MobEffects.DIG_SPEED, "Iron+"),
        GOLD(ModItems.SUPER_GOLD_HELMET, ModItems.SUPER_GOLD_CHESTPLATE,
             ModItems.SUPER_GOLD_LEGGINGS, ModItems.SUPER_GOLD_BOOTS,
             MobEffects.LUCK, "Gold+"),
        DIAMOND(ModItems.SUPER_DIAMOND_HELMET, ModItems.SUPER_DIAMOND_CHESTPLATE,
                ModItems.SUPER_DIAMOND_LEGGINGS, ModItems.SUPER_DIAMOND_BOOTS,
                MobEffects.DAMAGE_BOOST, "Diamond+"),
        NETHERITE(ModItems.SUPER_NETHERITE_HELMET, ModItems.SUPER_NETHERITE_CHESTPLATE,
                  ModItems.SUPER_NETHERITE_LEGGINGS, ModItems.SUPER_NETHERITE_BOOTS,
                  MobEffects.DAMAGE_RESISTANCE, "Netherite+"),
        ULTIMATE(ModItems.ULTIMATE_HELMET, ModItems.ULTIMATE_CHESTPLATE,
                 ModItems.ULTIMATE_LEGGINGS, ModItems.ULTIMATE_BOOTS,
                 null, "Ultimate");

        private final DeferredItem<?> helmet, chestplate, leggings, boots;
        final Holder<MobEffect> effect;
        final String label;

        // Lazy-cached resolved items (safe: populated only after registration)
        private Item cachedHelmet, cachedChestplate, cachedLeggings, cachedBoots;

        BonusTier(DeferredItem<?> h, DeferredItem<?> c, DeferredItem<?> l, DeferredItem<?> b,
                  Holder<MobEffect> fx, String label) {
            this.helmet = h;
            this.chestplate = c;
            this.leggings = l;
            this.boots = b;
            this.effect = fx;
            this.label = label;
        }

        boolean isWearing(@NotNull Player player) {
            if (cachedHelmet == null) {
                cachedHelmet = helmet.get();
                cachedChestplate = chestplate.get();
                cachedLeggings = leggings.get();
                cachedBoots = boots.get();
            }
            return player.getItemBySlot(EquipmentSlot.HEAD).is(cachedHelmet)
                && player.getItemBySlot(EquipmentSlot.CHEST).is(cachedChestplate)
                && player.getItemBySlot(EquipmentSlot.LEGS).is(cachedLeggings)
                && player.getItemBySlot(EquipmentSlot.FEET).is(cachedBoots);
        }
    }

    // -- Event handler -----------------------------------------------------

    private static final class EventHandler {
        private EventHandler() {
        }

        @SubscribeEvent
        static void onPlayerTick(@NotNull PlayerTickEvent.Post event) {
            Player player = event.getEntity();
            if (player.level().isClientSide()) {
                return;
            }

            for (BonusTier tier : BonusTier.values()) {
                if (tier.isWearing(player)) {
                    if (tier == BonusTier.ULTIMATE) {
                        applyUltimateFlight(player);
                    } else if (tier.effect != null) {
                        player.addEffect(new MobEffectInstance(tier.effect, 200, 0, true, false));
                    }
                    return; // only one tier applies at a time
                }
            }
        }
    }

    // -- Ultimate flight (per-player) ---------------------------------------

    private static final Map<UUID, Integer> ultimateFlightTicks = new ConcurrentHashMap<>();

    private static void applyUltimateFlight(@NotNull Player player) {
        UUID id = player.getUUID();
        int ticks = ultimateFlightTicks.getOrDefault(id, 0) + 1;
        if (ticks >= 600) { // 30s at 20 TPS
            player.getAbilities().mayfly = true;
            ticks = 0;
        }
        // Allow flight for next 5 seconds, then revoke
        if (ticks >= 100) { // 5s
            if (!player.isCreative() && !player.isSpectator()) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
            }
        }
        ultimateFlightTicks.put(id, ticks);
    }
}
