package net.fryc.frycparry.mixin.items;

import net.fryc.frycparry.FrycParry;
import net.minecraft.item.PickaxeItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PickaxeItem.class)
abstract class PickaxeMixin {

    public int getParryTicks(){
        return FrycParry.config.pickaxe.pickaxeParryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return (float) FrycParry.config.pickaxe.pickaxeBlockMeleeDamageTaken/100;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return (float) FrycParry.config.pickaxe.pickaxeBlockArrowDamageTaken/100;
    }

    public int getCooldownAfterParryAction(){
        return FrycParry.config.pickaxe.cooldownAfterPickaxeParryAction;
    }
    public int getCooldownAfterInterruptingBlockAction(){
        return FrycParry.config.pickaxe.cooldownAfterInterruptingPickaxeBlockAction;
    }
    public double getKnockbackAfterParryAction(){
        return FrycParry.config.pickaxe.pickaxeParryKnockbackStrength;
    }
    public int getSlownessAfterParryAction(){
        return FrycParry.config.pickaxe.pickaxeSlownessAfterParry;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return FrycParry.config.pickaxe.pickaxeSlownessAfterParryAmplifier;
    }
    public int getWeaknessAfterParryAction(){
        return FrycParry.config.pickaxe.pickaxeWeaknessAfterParry;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return FrycParry.config.pickaxe.pickaxeWeaknessAfterParryAmplifier;
    }
    public int getDisarmedAfterParryAction(){
        return FrycParry.config.pickaxe.pickaxeDisarmAfterParry;
    }

    public boolean shouldStopUsingItemAfterBlockOrParry(){
        return FrycParry.config.pickaxe.shouldStopUsingPickaxeAfterBlockOrParry;
    }
}
