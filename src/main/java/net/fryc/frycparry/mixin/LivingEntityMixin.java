package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.DamageTypeTags;
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
        if((this.activeItemStack.getItem() instanceof SwordItem || this.activeItemStack.getItem() instanceof AxeItem)){
            int predictionEnchantmentLevel = ModEnchantments.getPredictionEnchantment(dys);
            if(this.activeItemStack.getItem() instanceof SwordItem){
                if(this.activeItemStack.getItem().getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= FrycParry.config.swordParryTicks + predictionEnchantmentLevel || source.isIn(DamageTypeTags.IS_EXPLOSION)){
                    ret.setReturnValue(false);
                }
            }
            else if(this.activeItemStack.getItem() instanceof AxeItem){
                if(this.activeItemStack.getItem().getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= FrycParry.config.axeParryTicks + predictionEnchantmentLevel || source.isIn(DamageTypeTags.IS_EXPLOSION)){
                    ret.setReturnValue(false);
                }
            }
        }
        else if(!(this.activeItemStack.getItem() instanceof ShieldItem) || (source.isIn(DamageTypeTags.IS_EXPLOSION) && this.activeItemStack.getItem().getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft < 5)){
            ret.setReturnValue(false);
        }
    }

    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V", shift = At.Shift.AFTER))
    private void parry(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ret) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(!source.isIn(DamageTypeTags.IS_PROJECTILE)){
            if(!source.isIn(DamageTypeTags.IS_EXPLOSION)){
                if(source.getAttacker() instanceof LivingEntity attacker){
                    if(!(this.activeItemStack.getItem() instanceof ShieldItem && this.activeItemStack.getItem().getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= FrycParry.config.shieldParryTicks + ModEnchantments.getPredictionEnchantment(dys))){

                        //parry enchantment
                        int parryEnchantmentLevel = ModEnchantments.getParryEnchantment(dys);

                        //parry effects for players
                        if(attacker instanceof PlayerEntity player){
                            //knockback
                            player.takeKnockback((double)(FrycParry.config.parryKnockbackStrengthForPlayers + parryEnchantmentLevel * 0.65)/10, dys.getX() - attacker.getX(), dys.getZ() - attacker.getZ());
                            player.velocityModified = true;

                            int disarm = FrycParry.config.disarmForPlayersAfterParry;
                            if(disarm > 0){
                                disarm = disarm + parryEnchantmentLevel * 5;
                                if(player.hasStatusEffect(ModEffects.DISARMED)){
                                    if(player.getActiveStatusEffects().get(ModEffects.DISARMED).getDuration() < disarm) player.addStatusEffect(new StatusEffectInstance(ModEffects.DISARMED, disarm, 0));
                                }
                                else player.addStatusEffect(new StatusEffectInstance(ModEffects.DISARMED, disarm, 0));
                            }

                            int weak = FrycParry.config.weaknessForPlayersAfterParry;
                            int weakAmpl = FrycParry.config.weaknessForPlayersAmplifier;
                            if(weak > 0 && weakAmpl > 0){
                                weak = (int) (weak + weak * (parryEnchantmentLevel * 0.15));
                                if(player.hasStatusEffect(StatusEffects.WEAKNESS)){
                                    if(player.getActiveStatusEffects().get(StatusEffects.WEAKNESS).getDuration() < weak) player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, weak, weakAmpl - 1));
                                }
                                else player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, weak, weakAmpl - 1));
                            }

                            int slow = FrycParry.config.slownessForPlayersAfterParry;
                            int slowAmp = FrycParry.config.slownessForPlayersAmplifier;
                            if(slow > 0 && slowAmp > 0){
                                slow = (int) (slow + slow * (parryEnchantmentLevel * 0.2));
                                if(player.hasStatusEffect(StatusEffects.SLOWNESS)){
                                    if(player.getActiveStatusEffects().get(StatusEffects.SLOWNESS).getDuration() < slow) player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, slow, slowAmp - 1));
                                }
                                else player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, slow, slowAmp - 1));
                            }

                        }
                        //parry effects for mobs
                        else {
                            //knockback
                            attacker.takeKnockback((double)(FrycParry.config.parryKnockbackStrength + parryEnchantmentLevel * 0.95)/10, dys.getX() - attacker.getX(), dys.getZ() - attacker.getZ());
                            attacker.velocityModified = true;

                            int weak = FrycParry.config.weaknessAfterParry;
                            int weakAmpl = FrycParry.config.weaknessAmplifier;
                            if(weak > 0 && weakAmpl > 0){
                                weak = (int) (weak + weak * (parryEnchantmentLevel * 0.22));
                                if(attacker.hasStatusEffect(StatusEffects.WEAKNESS)){
                                    if(attacker.getActiveStatusEffects().get(StatusEffects.WEAKNESS).getDuration() < weak) attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, weak, weakAmpl - 1));
                                }
                                else attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, weak, weakAmpl - 1));
                            }

                            int slow = FrycParry.config.slownessAfterParry;
                            int slowAmp = FrycParry.config.slownessAmplifier;
                            if(slow > 0 && slowAmp > 0){
                                slow = (int) (slow + slow * (parryEnchantmentLevel * 0.3));
                                if(attacker.hasStatusEffect(StatusEffects.SLOWNESS)){
                                    if(attacker.getActiveStatusEffects().get(StatusEffects.SLOWNESS).getDuration() < slow) attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, slow, slowAmp - 1));
                                }
                                else attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, slow, slowAmp - 1));
                            }
                        }

                        //counterattack enchantment
                        if(dys instanceof  PlayerEntity player){

                            //counterattack enchantment
                            int counterattackEnchantmentLevel = ModEnchantments.getCounterattackEnchantment(player);
                            if(counterattackEnchantmentLevel > 0){
                                double ctrattack_damage = player.getAttributes().getValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                                if(player.getOffHandStack().isEmpty()) ctrattack_damage *= (counterattackEnchantmentLevel * 0.2) + 0.1;
                                else ctrattack_damage *= (counterattackEnchantmentLevel * 0.1) + 0.1;
                                attacker.damage(world.getDamageSources().playerAttack(player),(float) ctrattack_damage);
                            }

                            //disabling block
                            if(attacker.disablesShield() && FrycParry.config.disableBlockAfterParryingAxeAttack){
                                if(this.activeItemStack.getItem() instanceof ShieldItem) player.disableShield(true);
                                else {
                                    player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 100);
                                    if(ParryHelper.checkDualWieldingWeapons(player)) player.getItemCooldownManager().set(player.getOffHandStack().getItem(), 100);
                                }
                                dys.stopUsingItem();
                                dys.swingHand(dys.getActiveHand(), true);
                            }
                        }


                    }
                    else if(this.activeItemStack.getItem() instanceof ShieldItem && attacker.disablesShield() && dys instanceof PlayerEntity player){
                        player.disableShield(true);
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
            dys.getMainHandStack().damage(1, dys, (player) -> {
                player.sendToolBreakStatus(player.getActiveHand());
            });
        }
    }

    @ModifyVariable(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At("HEAD"), ordinal = 0)
    private float blocking(float amount, DamageSource source) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(amount > 0.0F && !dys.blockedByShield(source) && this.blockedByWeapon(source, dys)){
            byte b = 2;
            if(source.isIn(DamageTypeTags.IS_EXPLOSION)){
                if(this.activeItemStack.getItem() instanceof ShieldItem){
                    amount *= 0.8F;
                    b = 29;
                }
            }
            else if(source.isIn(DamageTypeTags.IS_PROJECTILE)){
                amount *= ((float)(FrycParry.config.swordBlockArrowDamageTaken)/100);
                b =30;
            }
            else if(source.getAttacker() instanceof LivingEntity attacker){
                amount *= ((float)(FrycParry.config.swordBlockMeleeDamageTaken)/100);
                b = 30;
                if(attacker.disablesShield() && dys instanceof PlayerEntity player){
                    player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 160);
                    if(ParryHelper.checkDualWieldingWeapons(player)) player.getItemCooldownManager().set(player.getOffHandStack().getItem(), 160);
                    dys.stopUsingItem();
                }
            }
            dys.world.sendEntityStatus(this, b);

            if((FrycParry.config.interruptAxeBlockActionAfterParryOrBlock && this.activeItemStack.getItem() instanceof AxeItem) || (FrycParry.config.interruptSwordBlockActionAfterParryOrBlock && this.activeItemStack.getItem() instanceof SwordItem)){
                dys.stopUsingItem();
            }

            //damaging item
            if(!(dys.getMainHandStack().getItem() instanceof  ShieldItem || dys.getOffHandStack().getItem() instanceof ShieldItem)){
                dys.getMainHandStack().damage(1, dys, (player) -> {
                    player.sendToolBreakStatus(player.getActiveHand());
                });
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

        if (!source.isIn(DamageTypeTags.BYPASSES_ARMOR) && user.isBlocking() && !bl) {
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
            if((entity.getOffHandStack().isEmpty() || ParryHelper.checkDualWieldingWeapons((PlayerEntity) entity) || ParryHelper.checkDualWieldingItems((PlayerEntity) entity)) && (entity.getMainHandStack().getItem() instanceof SwordItem || entity.getMainHandStack().getItem() instanceof AxeItem)){
                status = 30;
            }
        }
        return status;
    }

}
