package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
