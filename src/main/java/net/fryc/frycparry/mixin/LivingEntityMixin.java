package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity{
    @Shadow protected ItemStack activeItemStack;
    @Shadow protected int itemUseTimeLeft;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected void applyDamage(DamageSource source, float amount){
    }


    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V", shift = At.Shift.AFTER))
    private void parryAndDisableSwordBlocking(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ret) {
        LivingEntity entity = ((LivingEntity)(Object)this);
        Item item = this.activeItemStack.getItem();
        boolean parry = false;
        if(this.activeItemStack.getItem() instanceof ShieldItem){
            if(!(item.getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= FrycParry.config.shieldParryTicks)){
                parry = true;
                if(source.isExplosive() && !(item.getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= 5)){ //parrying explosives with shield reduces damage only by 20% (blocking protects from damage completely)
                    amount *= 0.8F;
                    this.applyDamage(source, amount);
                }
            }
        }
        else if(this.activeItemStack.getItem() instanceof SwordItem){
            if(item.getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= FrycParry.config.swordParryTicks || source.isExplosive()){ //parrying or blocking with sword doesn't reduce damage from explosives
                if(source.isExplosive()){
                    this.applyDamage(source, amount);
                }
                else if(source.isProjectile()){
                    if(source.getSource() != null) source.getSource().remove(RemovalReason.DISCARDED); //removes arrow
                    amount *= ((float)(FrycParry.config.swordBlockArrowDamageTaken)/100);
                    this.applyDamage(source, amount);
                }
                else if(source.getAttacker() instanceof LivingEntity){
                    amount *= ((float)(FrycParry.config.swordBlockMeleeDamageTaken)/100);
                    this.applyDamage(source, amount);
                }
            }
            else {
                parry = true;
            }
            if(FrycParry.config.interruptSwordBlockActionAfterParryOrBlock) entity.stopUsingItem();
            entity.getMainHandStack().setDamage(entity.getMainHandStack().getDamage() + 1);
        }
        else if(this.activeItemStack.getItem() instanceof AxeItem){
            if(item.getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= FrycParry.config.axeParryTicks || source.isExplosive()){ //parrying or blocking with axe doesn't reduce damage from explosives
                if(source.isExplosive()){
                    this.applyDamage(source, amount);
                }
                else if(source.isProjectile()){
                    if(source.getSource() != null) source.getSource().remove(RemovalReason.DISCARDED); //removes arrow
                    amount *= ((float)(FrycParry.config.axeBlockArrowDamageTaken)/100);
                    this.applyDamage(source, amount);
                }
                else if(source.getAttacker() instanceof LivingEntity){
                    amount *= ((float)(FrycParry.config.axeBlockMeleeDamageTaken)/100);
                    this.applyDamage(source, amount);
                }
            }
            else {
                parry = true;
            }
            if(FrycParry.config.interruptAxeBlockActionAfterParryOrBlock) entity.stopUsingItem();
            entity.getMainHandStack().setDamage(entity.getMainHandStack().getDamage() + 1);
        }
        if(parry && !source.isExplosive()){
            if(source.getSource() instanceof LivingEntity livingEntity){
                livingEntity.takeKnockback((float)(FrycParry.config.parryKnockbackStrength)/10, entity.getX() - livingEntity.getX(), entity.getZ() - livingEntity.getZ());
                if(livingEntity.hasStatusEffect(StatusEffects.SLOWNESS)){
                    if(livingEntity.getActiveStatusEffects().get(StatusEffects.SLOWNESS).getDuration() < 100) livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 0));
                }
                else livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 0));
            }
        }
    }

    /* old
        @ModifyConstant(method = "isBlocking()Z", constant = @Constant(intValue = 5))
        private int removeBlockDelay(int i) {
            return 0;
        }
     */


    //removes the 5 tick block delay
    @Inject(method = "isBlocking()Z", at = @At("HEAD"), cancellable = true)
    private void removeBlockDelay(CallbackInfoReturnable<Boolean> ret) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if (dys.isUsingItem() && !this.activeItemStack.isEmpty()) {
            Item item = this.activeItemStack.getItem();
            ret.setReturnValue(item.getUseAction(this.activeItemStack) == UseAction.BLOCK);
        }
    }


    @ModifyVariable(at = @At("HEAD"), method = "handleStatus(B)V")
    private byte init(byte status) {
        if(status == 29){
            LivingEntity entity = ((LivingEntity)(Object)this);
            if(entity.getOffHandStack().isEmpty() && (entity.getMainHandStack().getItem() instanceof SwordItem || entity.getMainHandStack().getItem() instanceof AxeItem)){
                //entity.playSound(ModSoundEvent.PARRY, 0.8F, 0.8F + this.world.random.nextFloat() * 0.4F);
                //status = 20;
                status = 30;
            }
        }
        return status;
    }
}
