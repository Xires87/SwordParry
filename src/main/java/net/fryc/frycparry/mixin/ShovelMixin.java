package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.minecraft.item.ShovelItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShovelItem.class)
abstract class ShovelMixin {

    public int getParryTicks(){
        return FrycParry.config.shovelParryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return (float) FrycParry.config.shovelBlockMeleeDamageTaken/100;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return (float) FrycParry.config.shovelBlockArrowDamageTaken/100;
    }

    public int getCooldownAfterParryAction(){
        return FrycParry.config.cooldownAfterShovelParryAction;
    }
    public int getCooldownAfterInterruptingBlockAction(){
        return FrycParry.config.cooldownAfterInterruptingShovelBlockAction;
    }
    public double getKnockbackAfterParryAction(){
        return FrycParry.config.shovelParryKnockbackStrength;
    }
    public int getSlownessAfterParryAction(){
        return FrycParry.config.shovelSlownessAfterParry;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return FrycParry.config.shovelSlownessAfterParryAmplifier;
    }
    public int getWeaknessAfterParryAction(){
        return FrycParry.config.shovelWeaknessAfterParry;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return FrycParry.config.shovelWeaknessAfterParryAmplifier;
    }
    public int getDisarmedAfterParryAction(){
        return FrycParry.config.shovelDisarmAfterParry;
    }
}
