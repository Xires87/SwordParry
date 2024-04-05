package net.fryc.frycparry.mixin;

import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, priority = 1100) // higher priority to make my mod compatible with frost shield from Wizards
abstract class LivingEntityWithPriorityMixin extends Entity implements Attackable, CanBlock {

    public LivingEntityWithPriorityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    //makes blockedByShield method return true only when attack was fully blocked (no dmg) or parried
    @Inject(method = "blockedByShield(Lnet/minecraft/entity/damage/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    private void shieldBlocking(DamageSource source, CallbackInfoReturnable<Boolean> ret) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(!ParryHelper.canParryWithoutShield(dys) && !ParryHelper.hasShieldEquipped(dys)){ // <---- hasShieldEquipped is not redundant DONT TOUCH
            ret.setReturnValue(false);
        }
        else{
            if(!((CanBlock) dys).getParryDataValue() && !ParryHelper.blockingFullyNegatesDamage(source, dys.getActiveItem(), dys)){
                ret.setReturnValue(false);
            }
        }
    }
}
