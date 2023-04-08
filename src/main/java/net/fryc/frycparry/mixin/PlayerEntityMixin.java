package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity {


    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V", shift = At.Shift.AFTER))
    private void setBlockCooldownOnItemSwap(CallbackInfo info) {
        PlayerEntity dys = ((PlayerEntity)(Object)this);
        Item item = dys.getMainHandStack().getItem();
        if(dys.isUsingItem()) dys.stopUsingItem();
        if(ParryHelper.canParry(dys) && !ParryHelper.isItemParryDisabled(dys.getMainHandStack().getItem())){
            if(!dys.getItemCooldownManager().isCoolingDown(dys.getMainHandStack().getItem())) dys.getItemCooldownManager().set(dys.getMainHandStack().getItem(), FrycParry.config.cooldownAfterInterruptingBlockAction);
        }
        else if(ParryHelper.hasShieldEquipped(dys)){
            if(dys.getMainHandStack().getItem() instanceof ShieldItem){
                if(!dys.getItemCooldownManager().isCoolingDown(dys.getMainHandStack().getItem())) dys.getItemCooldownManager().set(dys.getMainHandStack().getItem(), FrycParry.config.cooldownAfterInterruptingBlockAction);
            }
            else {
                if(!dys.getItemCooldownManager().isCoolingDown(dys.getOffHandStack().getItem())) dys.getItemCooldownManager().set(dys.getOffHandStack().getItem(), FrycParry.config.cooldownAfterInterruptingBlockAction);
            }
        }
    }



    @Inject(method = "tick()V", at = @At("TAIL"))
    private void disarm(CallbackInfo info) {
        PlayerEntity dys = ((PlayerEntity)(Object)this);
        if(dys.hasStatusEffect(ModEffects.DISARMED)){
            --this.lastAttackedTicks;
        }
    }

    /* todo wykombinowac sposob zeby przy zmissowaniu ataku tez sie cooldown odpalal
    @Inject(method = "resetLastAttackedTicks()V", at = @At("TAIL"))
    private void setCooldownForParry(CallbackInfo info) {
        PlayerEntity dys = ((PlayerEntity)(Object)this);
        if(ParryHelper.canParry(dys)){
            if(!dys.getItemCooldownManager().isCoolingDown(dys.getMainHandStack().getItem())) dys.getItemCooldownManager().set(dys.getMainHandStack().getItem(), 10);
        }
        else if(ParryHelper.hasShieldEquipped(dys)){
            if(dys.getMainHandStack().getItem() instanceof ShieldItem){
                if(!dys.getItemCooldownManager().isCoolingDown(dys.getMainHandStack().getItem())) dys.getItemCooldownManager().set(dys.getMainHandStack().getItem(), 10);
            }
            else {
                if(!dys.getItemCooldownManager().isCoolingDown(dys.getOffHandStack().getItem())) dys.getItemCooldownManager().set(dys.getOffHandStack().getItem(), 10);
            }
        }
    }

     */

    @Redirect(method = "Lnet/minecraft/entity/player/PlayerEntity;attack(Lnet/minecraft/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isAttackable()Z"))
    private boolean unAttackable(Entity target) {
        PlayerEntity dys = ((PlayerEntity)(Object)this);
        return target.isAttackable() && !dys.hasStatusEffect(ModEffects.DISARMED);
    }

    @Redirect(method = "Lnet/minecraft/entity/player/PlayerEntity;takeShieldHit(Lnet/minecraft/entity/LivingEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;disablesShield()Z"))
    private boolean dontDisableShield(LivingEntity attacker) {
        return false;
    }


}
