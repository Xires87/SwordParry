package net.fryc.frycparry.mixin;

import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.util.interfaces.TargetingMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Targeter;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
abstract class MobEntityMixin extends LivingEntity implements Targeter, TargetingMob {

    @Nullable
    LivingEntity lastTarget = null;

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    //mobs are still attacking, but they don't do damage
    @Inject(method = "tryAttack(Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void mobsDontDealDamageWhenDisarmed(Entity target, CallbackInfoReturnable<Boolean> ret) {
        if(this.hasStatusEffect(ModEffects.DISARMED)){
            ret.setReturnValue(false);
        }
    }

    @Inject(method = "canTarget(Lnet/minecraft/entity/EntityType;)Z", at = @At("HEAD"), cancellable = true)
    private void mobsDontDealDamageWhenDisarmed(EntityType<?> type, CallbackInfoReturnable<Boolean> ret) {
        if(this.hasStatusEffect(ModEffects.DISARMED)){
            ret.setReturnValue(false);
        }
    }


    public void setLastTarget(@Nullable LivingEntity target){
        this.lastTarget = target;
    }

    @Nullable
    public LivingEntity getLastTarget(){
        return this.lastTarget;
    }


}
