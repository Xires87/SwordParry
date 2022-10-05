package net.fryc.frycparry.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {
    @Shadow protected ItemStack activeItemStack;
    @Shadow protected int itemUseTimeLeft;
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected void applyDamage(DamageSource source, float amount){
    }



    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V", shift = At.Shift.AFTER))
    private void disableSwordBlocking(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ret) {
        LivingEntity entity = ((LivingEntity)(Object)this);
        Item item = this.activeItemStack.getItem();
        boolean parry = false; //first 5 ticks of blocking count as parry
        if(this.activeItemStack.getItem() instanceof ShieldItem){
            if(!(item.getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= 5)){
                parry = true;
            }
        }
        else if(this.activeItemStack.getItem() instanceof SwordItem){
            if(item.getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= 5){
                if(source.getAttacker() instanceof LivingEntity){
                    amount *= 0.5F;
                    this.applyDamage(source, amount);
                }
                else if(source.isProjectile()){
                    if(source.getSource() != null) source.getSource().remove(RemovalReason.DISCARDED); //removes arrow
                    this.applyDamage(source, amount);
                }
            }
            else {
                parry = true;
            }
            entity.stopUsingItem();
            entity.getMainHandStack().setDamage(entity.getMainHandStack().getDamage() + 1);
        }
        if(parry){
            if(source.getSource() instanceof LivingEntity livingEntity){
                livingEntity.takeKnockback(0.9, entity.getX() - livingEntity.getX(), entity.getZ() - livingEntity.getZ());
                if(livingEntity.hasStatusEffect(StatusEffects.SLOWNESS)){
                    if(livingEntity.getActiveStatusEffects().get(StatusEffects.SLOWNESS).getDuration() < 100) livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 0));
                }
                else livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 0));
            }
        }
    }

    //removes the 5 tick block delay
    @ModifyConstant(method = "isBlocking()Z", constant = @Constant(intValue = 5))
    private int removeBlockDelay(int i) {
        return 0;
    }
}
