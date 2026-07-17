package com.bikininjas.supercrafting.item;

import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.supercrafting.SuperCraftingMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Right-click abilities for super tools.
 * <ul>
 *   <li>Super pickaxe (all tiers): veinmine — break ores in a 3×3 area</li>
 *   <li>Super sword (all tiers): dash — launch player forward 2 blocks</li>
 * </ul>
 */
public final class RightClickAbilities {

    private static final ModLogger LOGGER = LogManager.getLogger(SuperCraftingMod.MODID, RightClickAbilities.class);

    static {
        NeoForge.EVENT_BUS.register(EventHandler.class);
    }

    private RightClickAbilities() {
    }

    /** Force class loading so the static initializer runs. */
    public static void init() {
    }

    // -- Event handler -----------------------------------------------------

    private static final class EventHandler {
        private EventHandler() {
        }

        @SubscribeEvent
        static void onRightClickItem(@NotNull PlayerInteractEvent.RightClickItem event) {
            Player player = event.getEntity();
            if (player.level().isClientSide()) {
                return;
            }
            var stack = event.getItemStack();

            // Pickaxe → veinmine
            if (stack.getItem() instanceof PickaxeItem && isSuperItem(stack)) {
                veinmine((ServerLevel) player.level(), (ServerPlayer) player, event.getPos().relative(event.getFace()));
                event.setCanceled(true);
                return;
            }

            // Sword → dash
            if (stack.getItem() instanceof SwordItem && isSuperItem(stack)) {
                dash(player);
                event.setCanceled(true);
            }
        }
    }

    /**
     * Check if an item stack is a super-crafting item by checking if its ID
     * is in the super_crafting namespace.
     */
    private static boolean isSuperItem(@NotNull net.minecraft.world.item.ItemStack stack) {
        var key = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem());
        return key != null && SuperCraftingMod.MODID.equals(key.getNamespace());
    }

    // -- Veinmine ----------------------------------------------------------

    private static void veinmine(@NotNull ServerLevel level,
                                  @NotNull ServerPlayer player,
                                  @NotNull BlockPos center) {
        BlockPos targetPos = center;
        int broken = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos pos = targetPos.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);
                    if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
                        var mainHandItem = player.getMainHandItem();
                        if (mainHandItem.isEmpty()) {
                            break;
                        }
                        // Simulate tool usage
                        boolean canHarvest = mainHandItem.isCorrectToolForDrops(state);
                        mainHandItem.hurtAndBreak(1, level, player, item -> {
                        });
                        level.destroyBlock(pos, true, player);
                        broken++;
                    }
                }
            }
        }
        if (broken > 0) {
            LOGGER.debug("Veinmine broke {} blocks at {}", broken, center);
        }
    }

    // -- Dash --------------------------------------------------------------

    private static void dash(@NotNull Player player) {
        var look = player.getLookAngle();
        double dashStrength = 2.0;
        player.setDeltaMovement(player.getDeltaMovement()
                .add(look.x * dashStrength, 0.3, look.z * dashStrength));
        player.hurtMarked = true;
        LOGGER.debug("Dash activated for {}", player.getName().getString());
    }
}
