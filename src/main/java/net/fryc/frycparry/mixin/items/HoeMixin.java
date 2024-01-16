package net.fryc.frycparry.mixin.items;

import net.fryc.frycparry.FrycParry;
import net.minecraft.item.HoeItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HoeItem.class)
abstract class HoeMixin {

    public int getParryTicks(){
        return FrycParry.config.hoe.hoeParryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return (float) FrycParry.config.hoe.hoeBlockMeleeDamageTaken/100;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return (float) FrycParry.config.hoe.hoeBlockArrowDamageTaken/100;
    }

    public int getCooldownAfterParryAction(){
        return FrycParry.config.hoe.cooldownAfterHoeParryAction;
    }
    public int getCooldownAfterInterruptingBlockAction(){
        return FrycParry.config.hoe.cooldownAfterInterruptingHoeBlockAction;
    }
    public double getKnockbackAfterParryAction(){
        return FrycParry.config.hoe.hoeParryKnockbackStrength;
    }
    public int getSlownessAfterParryAction(){
        return FrycParry.config.hoe.hoeSlownessAfterParry;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return FrycParry.config.hoe.hoeSlownessAfterParryAmplifier;
    }
    public int getWeaknessAfterParryAction(){
        return FrycParry.config.hoe.hoeWeaknessAfterParry;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return FrycParry.config.hoe.hoeWeaknessAfterParryAmplifier;
    }
    public int getDisarmedAfterParryAction(){
        return FrycParry.config.hoe.hoeDisarmAfterParry;
    }
}
