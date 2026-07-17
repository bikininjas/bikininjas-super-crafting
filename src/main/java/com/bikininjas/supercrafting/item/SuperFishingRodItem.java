package com.bikininjas.supercrafting.item;

import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.supercrafting.SuperCraftingMod;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;

/**
 * Super fishing rod with durability scaling and faster catch time per tier.
 */
public final class SuperFishingRodItem extends FishingRodItem {

    @SuppressWarnings("unused")
    private static final ModLogger LOGGER = LogManager.getLogger(SuperCraftingMod.MODID, SuperFishingRodItem.class);

    private final Tier tier;

    public SuperFishingRodItem(@NotNull Tier tier, @NotNull Properties properties) {
        super(properties.durability(tier.getUses()));
        this.tier = tier;
    }

    @Override
    public int getEnchantmentValue() {
        return tier.getEnchantmentValue();
    }
}
