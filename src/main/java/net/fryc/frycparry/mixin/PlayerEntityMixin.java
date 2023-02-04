package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
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
        if(((item instanceof SwordItem && FrycParry.config.enableBlockingWithSword) ||
                (item instanceof AxeItem && FrycParry.config.enableBlockingWithAxe)) && (dys.getOffHandStack().isEmpty() || ParryHelper.checkDualWieldingWeapons(dys) || ParryHelper.checkDualWieldingItems(dys))){
            if(dys.isUsingItem()) dys.stopUsingItem();
            if(FrycParry.config.cooldownAfterBlockAction > 0){
                if(!dys.getItemCooldownManager().isCoolingDown(item)) dys.getItemCooldownManager().set(item, FrycParry.config.cooldownAfterBlockAction);
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
