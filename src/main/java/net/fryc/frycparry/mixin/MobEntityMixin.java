package net.fryc.frycparry.mixin;

import net.fryc.frycparry.effects.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Targeter;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
abstract class MobEntityMixin extends LivingEntity implements Targeter {

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


}
