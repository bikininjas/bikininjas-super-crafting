package com.bikininjas.supercrafting;

import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.corelib.entity.SpawnHelper;
import com.bikininjas.corelib.message.MessageHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.joml.Vector3f;

import java.util.List;
import java.util.Random;

/**
 * Adds funny ULTIMATE-tier behaviors to super-crafting items.
 * <p>
 * Uses the static-initializer module pattern (no {@code @EventBusSubscriber}),
 * matching {@link EnchantHandler}. ULTIMATE items are identified by their
 * registry name containing {@code "ultimate_"}.
 */
public final class SuperFunnyIntegration {

    private static final ModLogger LOGGER = LogManager.getLogger("super_crafting", SuperFunnyIntegration.class);
    private static final Random RANDOM = new Random();

    private SuperFunnyIntegration() {
    }

    // Static initializer — registers the event handler on the NeoForge bus.
    static {
        NeoForge.EVENT_BUS.register(EventHandler.class);
    }

    /** Force class loading so the static initializer runs. */
    public static void init() {
    }

    private static boolean isUltimateItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return path.contains("ultimate_");
    }

    private static boolean nameContains(ItemStack stack, String needle) {
        String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return path.contains(needle);
    }

    public static final class EventHandler {

        private EventHandler() {
        }

        // ULTIMATE SWORD — 30% chance lightning on hit
        @SubscribeEvent
        static void onUltimateSwordHit(LivingDamageEvent.Post event) {
            if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
                return;
            }
            ItemStack weapon = attacker.getMainHandItem();
            if (!isUltimateItem(weapon) || !nameContains(weapon, "sword")) {
                return;
            }
            if (RANDOM.nextFloat() > 0.3f) {
                return;
            }

            LivingEntity target = event.getEntity();
            if (target.level() instanceof ServerLevel serverLevel) {
                EntityType.LIGHTNING_BOLT.spawn(serverLevel, target.blockPosition(), MobSpawnType.TRIGGERED);
                LOGGER.info("ULTIMATE SWORD: Thunder struck {}", target.getName().getString());
            }
        }

        // ULTIMATE PICKAXE — bonus XP on break
        @SubscribeEvent
        static void onUltimatePickaxeBreak(BlockEvent.BreakEvent event) {
            if (event.getPlayer().isCreative()) {
                return;
            }
            ItemStack tool = event.getPlayer().getMainHandItem();
            if (!isUltimateItem(tool) || !nameContains(tool, "pickaxe")) {
                return;
            }

            if (event.getLevel() instanceof ServerLevel level) {
                if (RANDOM.nextFloat() > 0.5f) {
                    level.addFreshEntity(new ExperienceOrb(level,
                            event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                            3 + RANDOM.nextInt(5)));
                }
                LOGGER.debug("ULTIMATE PICKAXE: Bonus XP at {}", event.getPos());
            }
        }

        // ULTIMATE SHOVEL — 20% chance silverfish on dig
        @SubscribeEvent
        static void onUltimateShovelBreak(BlockEvent.BreakEvent event) {
            if (event.getPlayer().isCreative()) return;
            ItemStack tool = event.getPlayer().getMainHandItem();
            if (!isUltimateItem(tool) || !nameContains(tool, "shovel")) return;
            if (RANDOM.nextFloat() > 0.2f) return;
            if (event.getLevel() instanceof ServerLevel serverLevel) {
                SpawnHelper.spawnAt(serverLevel, EntityType.SILVERFISH,
                        event.getPos().getX() + 0.5, event.getPos().getY(), event.getPos().getZ() + 0.5);
                LOGGER.debug("ULTIMATE SHOVEL: Silverfish spawned at {}", event.getPos());
            }
        }

        // ULTIMATE AXE — launch hit entity
        @SubscribeEvent
        static void onUltimateAxeHit(LivingDamageEvent.Post event) {
            if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
                return;
            }
            ItemStack weapon = attacker.getMainHandItem();
            if (!isUltimateItem(weapon) || !nameContains(weapon, "axe")) {
                return;
            }

            LivingEntity target = event.getEntity();
            target.setDeltaMovement(target.getDeltaMovement().add(0, 1.2, 0));
            target.hurtMarked = true;
            LOGGER.info("ULTIMATE AXE: Yeeted {}", target.getName().getString());
        }

        // ULTIMATE HOE — chicken transform on right-click entity
        @SubscribeEvent
        static void onUltimateHoeRightClick(PlayerInteractEvent.EntityInteract event) {
            ItemStack item = event.getItemStack();
            if (!isUltimateItem(item) || !nameContains(item, "hoe")) {
                return;
            }
            if (!(event.getTarget() instanceof LivingEntity target)) {
                return;
            }
            if (target instanceof Player) {
                return;
            }

            if (target.level() instanceof ServerLevel serverLevel) {
                var chicken = EntityType.CHICKEN.create(serverLevel);
                if (chicken != null) {
                    chicken.moveTo(target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
                    target.discard();
                    serverLevel.addFreshEntity(chicken);
                    serverLevel.playSound(null, target.blockPosition(), SoundEvents.CHICKEN_AMBIENT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    LOGGER.info("ULTIMATE HOE: Transformed {} into chicken!", target.getName().getString());
                }
            }
        }

        // ULTIMATE HELMET — glowing effect on nearby mobs
        @SubscribeEvent
        static void onUltimateHelmetTick(PlayerTickEvent.Post event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) {
                return;
            }
            ItemStack helmet = player.getInventory().getArmor(3); // helmet slot
            if (helmet.isEmpty() || !isUltimateItem(helmet) || !nameContains(helmet, "helmet")) {
                return;
            }
            if (player.tickCount % 20 != 0) {
                return; // once per second
            }

            var nearby = player.level().getEntitiesOfClass(LivingEntity.class,
                    new AABB(player.blockPosition()).inflate(10),
                    e -> e != player && !(e instanceof Player));
            for (LivingEntity mob : nearby) {
                mob.setGlowingTag(true);
            }
        }

        // ULTIMATE CHESTPLATE — 25% damage reflect
        @SubscribeEvent
        static void onUltimateChestplateReflect(LivingIncomingDamageEvent event) {
            if (!(event.getEntity() instanceof LivingEntity victim)) {
                return;
            }
            ItemStack chest = victim.getItemBySlot(EquipmentSlot.CHEST);
            if (chest.isEmpty() || !isUltimateItem(chest) || !nameContains(chest, "chestplate")) {
                return;
            }
            if (RANDOM.nextFloat() > 0.25f) {
                return;
            }

            if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                float reflectDmg = event.getAmount() * 0.4f;
                attacker.hurt(attacker.damageSources().magic(), reflectDmg);
                victim.level().playSound(null, victim.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.5F);
                LOGGER.debug("ULTIMATE CHESTPLATE: Reflected {} dmg to {}", reflectDmg, attacker.getName().getString());
            }
        }

        // ULTIMATE LEGGINGS — Speed II while equipped
        @SubscribeEvent
        static void onUltimateLeggingsTick(PlayerTickEvent.Post event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            ItemStack leggings = player.getInventory().getArmor(1); // leggings slot
            if (leggings.isEmpty() || !isUltimateItem(leggings) || !nameContains(leggings, "leggings")) return;
            if (player.tickCount % 60 != 0) return; // refresh every 3s
            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 80, 1, true, false, false));
        }

        // ULTIMATE BOOTS — rainbow particle trail while sprinting
        @SubscribeEvent
        static void onUltimateBootsTrail(PlayerTickEvent.Post event) {
            var player = event.getEntity();
            ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
            if (boots.isEmpty() || !isUltimateItem(boots) || !nameContains(boots, "boots")) {
                return;
            }
            if (!player.isSprinting()) {
                return;
            }
            if (player.tickCount % 3 != 0) {
                return;
            }

            int rgb = RANDOM.nextInt(0xFFFFFF);
            float r = ((rgb >> 16) & 0xFF) / 255.0f;
            float g = ((rgb >> 8) & 0xFF) / 255.0f;
            float b = (rgb & 0xFF) / 255.0f;
            player.level().addParticle(
                    new DustParticleOptions(new Vector3f(r, g, b), 1.0f),
                    player.getX(), player.getY() + 0.1, player.getZ(), 0, 0, 0);
        }
        @SubscribeEvent
        static void onItemTooltip(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            if (stack.isEmpty()) return;
            String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();

            if (!path.startsWith("super_") && !path.startsWith("ultimate_")) {
                return;
            }
            List<Component> tooltip = event.getToolTip();

            boolean isFood = path.contains("apple") || path.contains("carrot") || path.contains("melon")
                    || path.contains("chorus") || path.contains("feast");
            boolean isArmor = path.contains("helmet") || path.contains("chestplate")
                    || path.contains("leggings") || path.contains("boots");
            boolean isTool = path.contains("sword") || path.contains("pickaxe")
                    || path.contains("axe") || path.contains("shovel") || path.contains("hoe");

            if (!isFood) {
                ChatFormatting tierColor;
                String tierName;
                int[] armorDefense = null;

                if (path.contains("ultimate_")) {
                    tierColor = ChatFormatting.LIGHT_PURPLE;
                    tierName = "V — ULTIMATE";
                    armorDefense = new int[]{6, 11, 9, 6};
                } else if (path.contains("netherite")) {
                    tierColor = ChatFormatting.DARK_PURPLE;
                    tierName = "IV — Netherite+";
                    armorDefense = new int[]{4, 9, 7, 4};
                } else if (path.contains("diamond")) {
                    tierColor = ChatFormatting.AQUA;
                    tierName = "III — Diamond+";
                    armorDefense = new int[]{4, 8, 7, 3};
                } else if (path.contains("gold")) {
                    tierColor = ChatFormatting.GOLD;
                    tierName = "II — Gold+";
                    armorDefense = new int[]{3, 6, 5, 2};
                } else {
                    tierColor = ChatFormatting.GRAY;
                    tierName = "I — Iron+";
                    armorDefense = new int[]{3, 7, 6, 2};
                }

                tooltip.add(MessageHelper.colored("◆ " + tierName, tierColor));

                if (isArmor) {
                    int def = -1;
                    if (path.contains("helmet")) def = armorDefense[0];
                    else if (path.contains("chestplate")) def = armorDefense[1];
                    else if (path.contains("leggings")) def = armorDefense[2];
                    else if (path.contains("boots")) def = armorDefense[3];
                    if (def >= 0) {
                        tooltip.add(MessageHelper.colored("Defense: +" + def, ChatFormatting.GRAY));
                    }
                }

            if (isArmor) {
                    tooltip.add(MessageHelper.colored("Auto-enchant: Protection IV, Unbreaking III, Mending", ChatFormatting.DARK_GRAY));
                } else if (isTool) {
                    tooltip.add(MessageHelper.colored("Auto-enchant: Efficiency V, Unbreaking III, Fortune III, Mending", ChatFormatting.DARK_GRAY));
                }
            }

            if (path.contains("super_apple")) {
                tooltip.add(MessageHelper.colored("◆ Special Food", ChatFormatting.GOLD));
                tooltip.add(MessageHelper.colored("Restores 10 Hunger", ChatFormatting.GREEN));
                tooltip.add(MessageHelper.colored("Regeneration II, Resistance I, Absorption I", ChatFormatting.GREEN));
            } else if (path.contains("super_carrot")) {
                tooltip.add(MessageHelper.colored("◆ Special Food", ChatFormatting.GOLD));
                tooltip.add(MessageHelper.colored("Restores 10 Hunger", ChatFormatting.GREEN));
                tooltip.add(MessageHelper.colored("Night Vision 3min, Speed II", ChatFormatting.GREEN));
            } else if (path.contains("super_melon")) {
                tooltip.add(MessageHelper.colored("◆ Special Food", ChatFormatting.GOLD));
                tooltip.add(MessageHelper.colored("Restores 12 Hunger", ChatFormatting.GREEN));
                tooltip.add(MessageHelper.colored("Absorption III, Resistance I", ChatFormatting.GREEN));
            } else if (path.contains("super_chorus")) {
                tooltip.add(MessageHelper.colored("◆ Special Food", ChatFormatting.GOLD));
                tooltip.add(MessageHelper.colored("Restores 12 Hunger", ChatFormatting.GREEN));
                tooltip.add(MessageHelper.colored("Slow Falling III, Regeneration III", ChatFormatting.GREEN));
                tooltip.add(MessageHelper.colored("30% chance: Levitation I (fun!)", ChatFormatting.LIGHT_PURPLE));
            } else if (path.contains("ultimate_feast")) {
                tooltip.add(MessageHelper.colored("◆ ULTIMATE Food", ChatFormatting.LIGHT_PURPLE));
                tooltip.add(MessageHelper.colored("Restores 20 Hunger", ChatFormatting.GREEN));
                tooltip.add(MessageHelper.colored("Regeneration V, Strength II, Absorption V", ChatFormatting.GREEN));
                tooltip.add(MessageHelper.colored("Resistance III, Fire Resistance", ChatFormatting.GREEN));
            }

            if (path.contains("ultimate_")) {
                if (path.contains("sword")) {
                    tooltip.add(MessageHelper.colored("⚡ 30% chance: lightning strike", ChatFormatting.AQUA));
                } else if (path.contains("pickaxe")) {
                    tooltip.add(MessageHelper.colored("💎 50% chance: bonus XP orbs", ChatFormatting.AQUA));
                } else if (path.contains("axe")) {
                    tooltip.add(MessageHelper.colored("🚀 Launches hit entity upward", ChatFormatting.AQUA));
                } else if (path.contains("shovel")) {
                    tooltip.add(MessageHelper.colored("🏔 Spawns silverfish on dig", ChatFormatting.AQUA));
                } else if (path.contains("hoe")) {
                    tooltip.add(MessageHelper.colored("🐔 Right-click mob → chicken", ChatFormatting.AQUA));
                } else if (path.contains("helmet")) {
                    tooltip.add(MessageHelper.colored("👁 Glowing on nearby mobs (10 blocks)", ChatFormatting.AQUA));
                } else if (path.contains("chestplate")) {
                    tooltip.add(MessageHelper.colored("🛡 25% chance: reflect 40% damage", ChatFormatting.AQUA));
                } else if (path.contains("leggings")) {
                    tooltip.add(MessageHelper.colored("🏃 Speed II while equipped", ChatFormatting.AQUA));
                } else if (path.contains("boots")) {
                    tooltip.add(MessageHelper.colored("🌈 Rainbow particle trail while sprinting", ChatFormatting.AQUA));
                }
            }
        }
    }
}
