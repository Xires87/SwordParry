package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.minecraft.item.HoeItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HoeItem.class)
abstract class HoeMixin {

    public int getParryTicks(){
        return FrycParry.config.hoeParryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return (float) FrycParry.config.hoeBlockMeleeDamageTaken/100;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return (float) FrycParry.config.hoeBlockArrowDamageTaken/100;
    }

    public int getCooldownAfterParryAction(){
        return FrycParry.config.cooldownAfterHoeParryAction;
    }
    public int getCooldownAfterInterruptingBlockAction(){
        return FrycParry.config.cooldownAfterInterruptingHoeBlockAction;
    }
    public double getKnockbackAfterParryAction(){
        return FrycParry.config.hoeParryKnockbackStrength;
    }
    public int getSlownessAfterParryAction(){
        return FrycParry.config.hoeSlownessAfterParry;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return FrycParry.config.hoeSlownessAfterParryAmplifier;
    }
    public int getWeaknessAfterParryAction(){
        return FrycParry.config.hoeWeaknessAfterParry;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return FrycParry.config.hoeWeaknessAfterParryAmplifier;
    }
    public int getDisarmedAfterParryAction(){
        return FrycParry.config.hoeDisarmAfterParry;
    }
}
