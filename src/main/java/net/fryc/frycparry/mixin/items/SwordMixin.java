package net.fryc.frycparry.mixin.items;

import net.fryc.frycparry.FrycParry;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SwordItem.class)
abstract class SwordMixin {

    public int getParryTicks(){
        return FrycParry.config.swordParryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return (float) FrycParry.config.swordBlockMeleeDamageTaken/100;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return (float) FrycParry.config.swordBlockArrowDamageTaken/100;
    }

    public int getCooldownAfterParryAction(){
        return FrycParry.config.cooldownAfterSwordParryAction;
    }
    public int getCooldownAfterInterruptingBlockAction(){
        return FrycParry.config.cooldownAfterInterruptingSwordBlockAction;
    }
    public double getKnockbackAfterParryAction(){
        return FrycParry.config.swordParryKnockbackStrength;
    }
    public int getSlownessAfterParryAction(){
        return FrycParry.config.swordSlownessAfterParry;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return FrycParry.config.swordSlownessAfterParryAmplifier;
    }
    public int getWeaknessAfterParryAction(){
        return FrycParry.config.swordWeaknessAfterParry;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return FrycParry.config.swordWeaknessAfterParryAmplifier;
    }
    public int getDisarmedAfterParryAction(){
        return FrycParry.config.swordDisarmAfterParry;
    }

}
