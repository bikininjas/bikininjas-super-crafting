package com.bikininjas.supercrafting.item;

import com.bikininjas.corelib.log.LogManager;
import com.bikininjas.corelib.log.ModLogger;
import com.bikininjas.supercrafting.SuperCraftingMod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Super bow with durability scaling and increased arrow damage per tier.
 */
public final class SuperBowItem extends BowItem {

    private static final ModLogger LOGGER = LogManager.getLogger(SuperCraftingMod.MODID, SuperBowItem.class);

    private final Tier tier;
    private final float damageMultiplier;

    public SuperBowItem(@NotNull Tier tier, @NotNull Properties properties, float damageMultiplier) {
        super(properties.durability(tier.getUses()));
        this.tier = tier;
        this.damageMultiplier = damageMultiplier;
    }

    @Override
    public int getEnchantmentValue() {
        return tier.getEnchantmentValue();
    }

    /** Higher-tier bows deal more damage per arrow. */
    public float getDamageMultiplier() {
        return damageMultiplier;
    }

    @Override
    protected @NotNull Projectile createProjectile(@NotNull Level level, @NotNull LivingEntity shooter,
                                                    @NotNull ItemStack weapon, @NotNull ItemStack ammo,
                                                    boolean isCrit) {
        Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
        if (projectile instanceof AbstractArrow arrow) {
            arrow.setBaseDamage(arrow.getBaseDamage() * damageMultiplier);
        }
        return projectile;
    }
}
