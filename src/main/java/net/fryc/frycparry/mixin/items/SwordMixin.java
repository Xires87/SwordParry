package net.fryc.frycparry.mixin.items;

import net.fryc.frycparry.FrycParry;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SwordItem.class)
abstract class SwordMixin {

    public int getParryTicks(){
        return FrycParry.config.sword.swordParryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return (float) FrycParry.config.sword.swordBlockMeleeDamageTaken/100;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return (float) FrycParry.config.sword.swordBlockArrowDamageTaken/100;
    }

    public int getCooldownAfterParryAction(){
        return FrycParry.config.sword.cooldownAfterSwordParryAction;
    }
    public int getCooldownAfterInterruptingBlockAction(){
        return FrycParry.config.sword.cooldownAfterInterruptingSwordBlockAction;
    }
    public double getKnockbackAfterParryAction(){
        return FrycParry.config.sword.swordParryKnockbackStrength;
    }
    public int getSlownessAfterParryAction(){
        return FrycParry.config.sword.swordSlownessAfterParry;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return FrycParry.config.sword.swordSlownessAfterParryAmplifier;
    }
    public int getWeaknessAfterParryAction(){
        return FrycParry.config.sword.swordWeaknessAfterParry;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return FrycParry.config.sword.swordWeaknessAfterParryAmplifier;
    }
    public int getDisarmedAfterParryAction(){
        return FrycParry.config.sword.swordDisarmAfterParry;
    }

    public boolean shouldStopUsingItemAfterBlockOrParry(){
        return FrycParry.config.sword.shouldStopUsingSwordAfterBlockOrParry;
    }

}
