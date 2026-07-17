package com.bikininjas.supercrafting.item;

import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.supercrafting.SuperCraftingMod;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Super Repair Kit — repairs held super-crafting tools/armor by 50%.
 */
public class SuperRepairKitItem extends Item {

    private static final ModLogger LOGGER = LogManager.getLogger(SuperCraftingMod.MODID, SuperRepairKitItem.class);

    public SuperRepairKitItem(@NotNull Properties properties) {
        super(Objects.requireNonNull(properties, "properties must not be null"));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level,
                                                            @NotNull Player player,
                                                            @NotNull InteractionHand usedHand) {
        ItemStack kit = player.getItemInHand(usedHand);
        if (level.isClientSide()) {
            return InteractionResultHolder.success(kit);
        }

        InteractionHand otherHand = (usedHand == InteractionHand.MAIN_HAND)
                ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack toRepair = player.getItemInHand(otherHand);

        if (toRepair.isEmpty() || !toRepair.isDamaged()) {
            return InteractionResultHolder.fail(kit);
        }

        int newDamage = toRepair.getDamageValue() - toRepair.getMaxDamage() / 2;
        toRepair.setDamageValue(Math.max(0, newDamage));

        if (!player.getAbilities().instabuild) {
            kit.shrink(1);
        }

        return InteractionResultHolder.consume(kit);
    }
}
