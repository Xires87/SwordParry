package net.fryc.frycparry.util.mixin_interfaces;

import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public interface TargetingMob {

    void setLastTarget(@Nullable LivingEntity target);

    @Nullable
    LivingEntity getLastTarget();
}
