package net.fryc.frycparry.mixin.items;

import net.fryc.frycparry.FrycParry;
import net.minecraft.item.ShovelItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShovelItem.class)
abstract class ShovelMixin {

    public int getParryTicks(){
        return FrycParry.config.shovel.shovelParryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return (float) FrycParry.config.shovel.shovelBlockMeleeDamageTaken/100;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return (float) FrycParry.config.shovel.shovelBlockArrowDamageTaken/100;
    }

    public int getCooldownAfterParryAction(){
        return FrycParry.config.shovel.cooldownAfterShovelParryAction;
    }
    public int getCooldownAfterInterruptingBlockAction(){
        return FrycParry.config.shovel.cooldownAfterInterruptingShovelBlockAction;
    }
    public double getKnockbackAfterParryAction(){
        return FrycParry.config.shovel.shovelParryKnockbackStrength;
    }
    public int getSlownessAfterParryAction(){
        return FrycParry.config.shovel.shovelSlownessAfterParry;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return FrycParry.config.shovel.shovelSlownessAfterParryAmplifier;
    }
    public int getWeaknessAfterParryAction(){
        return FrycParry.config.shovel.shovelWeaknessAfterParry;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return FrycParry.config.shovel.shovelWeaknessAfterParryAmplifier;
    }
    public int getDisarmedAfterParryAction(){
        return FrycParry.config.shovel.shovelDisarmAfterParry;
    }

    public boolean shouldStopUsingItemAfterBlockOrParry(){
        return FrycParry.config.shovel.shouldStopUsingShovelAfterBlockOrParry;
    }
}
