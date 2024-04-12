package net.fryc.frycparry.mixin;

import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity {


    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }



    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V", shift = At.Shift.BEFORE))
    private void setBlockCooldownOnItemSwap(CallbackInfo info) {
        PlayerEntity dys = ((PlayerEntity)(Object)this);
        Item item = dys.getMainHandStack().getItem();
        if(dys.isUsingItem()){
            if(((CanBlock) dys).getBlockingDataValue()) ((CanBlock) dys).stopUsingItemParry();
            else dys.stopUsingItem();
        }

        //onStoppedUsingParry() is not used when player switches item while blocking
        ((CanBlock) dys).setBlockingDataToFalse();
        ((CanBlock) dys).setParryDataToFalse();

        if(!ParryHelper.isItemParryDisabledWithConfig(dys.getWorld(), dys.getMainHandStack())){
            int cooldown;
            if(ParryHelper.canParryWithoutShield(dys)){
                if(!dys.getItemCooldownManager().isCoolingDown(item)){
                    cooldown = ParryHelper.getParryCooldown(dys, item);
                    if(cooldown > 0){
                        dys.getItemCooldownManager().set(item, cooldown);
                    }
                }
            }
            else {
                if(dys.getMainHandStack().getItem() instanceof ShieldItem){
                    if(!dys.getItemCooldownManager().isCoolingDown(item)){
                        cooldown = ParryHelper.getParryCooldown(dys, item);
                        if(cooldown > 0){
                            dys.getItemCooldownManager().set(item, cooldown);
                        }
                    }
                }
                else if(dys.getOffHandStack().getItem() instanceof ShieldItem) {
                    item = dys.getOffHandStack().getItem();
                    if(!dys.getItemCooldownManager().isCoolingDown(item)){
                        cooldown = ParryHelper.getParryCooldown(dys, item);
                        if(cooldown > 0){
                            dys.getItemCooldownManager().set(item, cooldown);
                        }
                    }
                }
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

    @Inject(method = "resetLastAttackedTicks()V", at = @At("HEAD"))
    private void setCooldownForParry(CallbackInfo info) {
        PlayerEntity dys = ((PlayerEntity)(Object)this);
        if(!dys.handSwinging && !dys.getWorld().isClient()){
            int cooldownProgress = (int) dys.getAttackCooldownProgressPerTick() - 1; // <-- cooldown based on attack speed
            if(ParryHelper.hasShieldEquipped(dys)){
                if(dys.getMainHandStack().getUseAction() == UseAction.BLOCK){
                    if(!dys.getItemCooldownManager().isCoolingDown(dys.getMainHandStack().getItem())) dys.getItemCooldownManager().set(dys.getMainHandStack().getItem(), cooldownProgress);
                }
                else {
                    if(!dys.getItemCooldownManager().isCoolingDown(dys.getOffHandStack().getItem())) dys.getItemCooldownManager().set(dys.getOffHandStack().getItem(), cooldownProgress);
                }
            }
            else if(ParryHelper.canParryWithoutShield(dys) && !ParryHelper.isItemParryDisabledWithConfig(dys.getWorld(), dys.getMainHandStack())){
                if(!dys.getItemCooldownManager().isCoolingDown(dys.getMainHandStack().getItem())) dys.getItemCooldownManager().set(dys.getMainHandStack().getItem(), cooldownProgress);
            }
        }

    }

    @Inject(method = "Lnet/minecraft/entity/player/PlayerEntity;attack(Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    private void cancelAttackWhenDisarmed(Entity target, CallbackInfo info) {
        PlayerEntity dys = ((PlayerEntity)(Object)this);
        if(dys.hasStatusEffect(ModEffects.DISARMED)){
            info.cancel();
        }
    }

    @Inject(method = "Lnet/minecraft/entity/player/PlayerEntity;takeShieldHit(Lnet/minecraft/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
    private void dontDisableShield(LivingEntity attacker, CallbackInfo info) {
        super.takeShieldHit(attacker);
        info.cancel();
    }




}
