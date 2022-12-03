package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
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


    //makes blockedByShield method return true only when attack was blocked with shield
    @Inject(method = "blockedByShield(Lnet/minecraft/entity/damage/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    private void shieldBlocking(DamageSource source, CallbackInfoReturnable<Boolean> ret) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if((this.activeItemStack.getItem() instanceof SwordItem || this.activeItemStack.getItem() instanceof AxeItem) && dys.getOffHandStack().isEmpty()){
            if(this.activeItemStack.getItem() instanceof SwordItem){
                if(this.activeItemStack.getItem().getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= FrycParry.config.swordParryTicks || source.isExplosive()){
                    ret.setReturnValue(false);
                }
            }
            else if(this.activeItemStack.getItem() instanceof AxeItem){
                if(this.activeItemStack.getItem().getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= FrycParry.config.axeParryTicks || source.isExplosive()){
                    ret.setReturnValue(false);
                }
            }
        }
        else if(!(this.activeItemStack.getItem() instanceof ShieldItem) || (source.isExplosive() && this.activeItemStack.getItem().getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft < 5)){
            ret.setReturnValue(false);
        }
    }

    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V", shift = At.Shift.AFTER))
    private void parry(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ret) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(!source.isProjectile()){
            if(!source.isExplosive()){
                if(source.getAttacker() instanceof LivingEntity attacker){
                    if(!(this.activeItemStack.getItem() instanceof ShieldItem && this.activeItemStack.getItem().getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= FrycParry.config.shieldParryTicks)){
                        //knockback and slowness after parry
                        attacker.takeKnockback((double)(FrycParry.config.parryKnockbackStrength)/10, dys.getX() - attacker.getX(), dys.getZ() - attacker.getZ());
                        if(attacker.hasStatusEffect(StatusEffects.SLOWNESS)){
                            if(attacker.getActiveStatusEffects().get(StatusEffects.SLOWNESS).getDuration() < 100) attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 0));
                        }
                        else attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 0));

                        //weakness for players
                        if(FrycParry.config.weaknessForPlayersAfterParry && attacker instanceof PlayerEntity player){
                            if(player.hasStatusEffect(StatusEffects.WEAKNESS)){
                                if(player.getActiveStatusEffects().get(StatusEffects.WEAKNESS).getDuration() < 65) player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 65, 0));
                            }
                            else player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 65, 0));
                        }
                        //disabling block
                        if(attacker.disablesShield() && dys instanceof PlayerEntity player){
                            player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 100);
                            dys.stopUsingItem();
                            dys.swingHand(dys.getActiveHand(), true);
                        }
                    }

                }
            }
        }
        if((FrycParry.config.interruptAxeBlockActionAfterParryOrBlock && this.activeItemStack.getItem() instanceof AxeItem) || (FrycParry.config.interruptSwordBlockActionAfterParryOrBlock && this.activeItemStack.getItem() instanceof SwordItem)){
            dys.stopUsingItem();
            dys.swingHand(dys.getActiveHand(), true);
        }

        //damaging item
        if(!(dys.getMainHandStack().getItem() instanceof ShieldItem || dys.getOffHandStack().getItem() instanceof ShieldItem)){
            dys.getMainHandStack().setDamage(dys.getMainHandStack().getDamage() + 1);
        }
    }

    @ModifyVariable(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At("HEAD"), ordinal = 0)
    private float blocking(float amount, DamageSource source) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(amount > 0.0F && !dys.blockedByShield(source) && this.blockedByWeapon(source, dys)){
            byte b = 2;
            if(source.isExplosive()){
                if(this.activeItemStack.getItem() instanceof ShieldItem){
                    amount *= 0.8F;
                    b = 29;
                }
            }
            else if(source.isProjectile()){
                amount *= ((float)(FrycParry.config.swordBlockArrowDamageTaken)/100);
                b =30;
            }
            else if(source.getAttacker() instanceof LivingEntity attacker){
                amount *= ((float)(FrycParry.config.swordBlockMeleeDamageTaken)/100);
                b = 30;
                if(attacker.disablesShield() && dys instanceof PlayerEntity player){
                    player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 100);
                    dys.stopUsingItem();
                }
            }
            dys.world.sendEntityStatus(this, b);

            if((FrycParry.config.interruptAxeBlockActionAfterParryOrBlock && this.activeItemStack.getItem() instanceof AxeItem) || (FrycParry.config.interruptSwordBlockActionAfterParryOrBlock && this.activeItemStack.getItem() instanceof SwordItem)){
                dys.stopUsingItem();
            }

            //damaging item
            if(!(dys.getMainHandStack().getItem() instanceof  ShieldItem || dys.getOffHandStack().getItem() instanceof ShieldItem)){
                dys.getMainHandStack().setDamage(dys.getMainHandStack().getDamage() + 1);
            }
        }
        return amount;
    }


    //removes the 5 tick block delay
    @Inject(method = "isBlocking()Z", at = @At("HEAD"), cancellable = true)
    private void removeBlockDelay(CallbackInfoReturnable<Boolean> ret) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if (dys.isUsingItem() && !this.activeItemStack.isEmpty()) {
            Item item = this.activeItemStack.getItem();
            ret.setReturnValue(item.getUseAction(this.activeItemStack) == UseAction.BLOCK);
        }
    }

    //vanilla blockedByShield method with additional parameter
    public boolean blockedByWeapon(DamageSource source, LivingEntity user) {
        Entity entity = source.getSource();
        boolean bl = false;
        if (entity instanceof PersistentProjectileEntity persistentProjectileEntity) {
            if (persistentProjectileEntity.getPierceLevel() > 0) {
                bl = true;
            }
        }

        if (!source.bypassesArmor() && user.isBlocking() && !bl) {
            Vec3d vec3d = source.getPosition();
            if (vec3d != null) {
                Vec3d vec3d2 = user.getRotationVec(1.0F);
                Vec3d vec3d3 = vec3d.relativize(user.getPos()).normalize();
                vec3d3 = new Vec3d(vec3d3.x, 0.0, vec3d3.z);
                if (vec3d3.dotProduct(vec3d2) < 0.0) {
                    return true;
                }
            }
        }

        return false;
    }


    //plays SoundEvents.ITEM_SHIELD_BREAK sound when attack is blocked with axe or sword
    @ModifyVariable(at = @At("HEAD"), method = "handleStatus(B)V")
    private byte init(byte status) {
        if(status == 29){
            LivingEntity entity = ((LivingEntity)(Object)this);
            if(entity.getOffHandStack().isEmpty() && (entity.getMainHandStack().getItem() instanceof SwordItem || entity.getMainHandStack().getItem() instanceof AxeItem)){
                status = 30;
            }
        }
        return status;
    }

}
