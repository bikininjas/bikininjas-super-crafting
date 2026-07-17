package com.bikininjas.supercrafting.item;

import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.supercrafting.SuperCraftingMod;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Super shield with durability scaling per tier.
 */
public final class SuperShieldItem extends ShieldItem {

    @SuppressWarnings("unused")
    private static final ModLogger LOGGER = LogManager.getLogger(SuperCraftingMod.MODID, SuperShieldItem.class);

    private final Tier tier;

    public SuperShieldItem(@NotNull Tier tier, @NotNull Properties properties) {
        super(properties.durability(tier.getUses() * 2));
        this.tier = tier;
    }

    @Override
    public int getEnchantmentValue() {
        return tier.getEnchantmentValue();
    }
}
