package net.fryc.frycparry.mixin.items;

import net.fryc.frycparry.FrycParry;
import net.minecraft.item.PickaxeItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PickaxeItem.class)
abstract class PickaxeMixin {

    public int getParryTicks(){
        return FrycParry.config.pickaxeParryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return (float) FrycParry.config.pickaxeBlockMeleeDamageTaken/100;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return (float) FrycParry.config.pickaxeBlockArrowDamageTaken/100;
    }

    public int getCooldownAfterParryAction(){
        return FrycParry.config.cooldownAfterPickaxeParryAction;
    }
    public int getCooldownAfterInterruptingBlockAction(){
        return FrycParry.config.cooldownAfterInterruptingPickaxeBlockAction;
    }
    public double getKnockbackAfterParryAction(){
        return FrycParry.config.pickaxeParryKnockbackStrength;
    }
    public int getSlownessAfterParryAction(){
        return FrycParry.config.pickaxeSlownessAfterParry;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return FrycParry.config.pickaxeSlownessAfterParryAmplifier;
    }
    public int getWeaknessAfterParryAction(){
        return FrycParry.config.pickaxeWeaknessAfterParry;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return FrycParry.config.pickaxeWeaknessAfterParryAmplifier;
    }
    public int getDisarmedAfterParryAction(){
        return FrycParry.config.pickaxeDisarmAfterParry;
    }
}
