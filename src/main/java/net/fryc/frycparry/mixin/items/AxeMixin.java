package net.fryc.frycparry.mixin.items;

import net.fryc.frycparry.FrycParry;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AxeItem.class)
abstract class AxeMixin {

    public int getParryTicks(){
        return FrycParry.config.axe.axeParryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return (float) FrycParry.config.axe.axeBlockMeleeDamageTaken/100;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return (float) FrycParry.config.axe.axeBlockArrowDamageTaken/100;
    }

    public int getCooldownAfterParryAction(){
        return FrycParry.config.axe.cooldownAfterAxeParryAction;
    }
    public int getCooldownAfterInterruptingBlockAction(){
        return FrycParry.config.axe.cooldownAfterInterruptingAxeBlockAction;
    }
    public double getKnockbackAfterParryAction(){
        return FrycParry.config.axe.axeParryKnockbackStrength;
    }
    public int getSlownessAfterParryAction(){
        return FrycParry.config.axe.axeSlownessAfterParry;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return FrycParry.config.axe.axeSlownessAfterParryAmplifier;
    }
    public int getWeaknessAfterParryAction(){
        return FrycParry.config.axe.axeWeaknessAfterParry;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return FrycParry.config.axe.axeWeaknessAfterParryAmplifier;
    }
    public int getDisarmedAfterParryAction(){
        return FrycParry.config.axe.axeDisarmAfterParry;
    }
}
