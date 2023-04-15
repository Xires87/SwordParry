package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.util.CanBlock;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.ParryItem;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity implements Attackable, CanBlock {
    @Shadow protected ItemStack activeItemStack;
    @Shadow protected int itemUseTimeLeft;

    private static final TrackedData<Boolean> BLOCKING_DATA;
    private static final TrackedData<Boolean> PARRY_DATA;

    public int parryDataTimer = 0;


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    //makes blockedByShield method return true only when attack was blocked with shield or parried
    @Inject(method = "blockedByShield(Lnet/minecraft/entity/damage/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    private void shieldBlocking(DamageSource source, CallbackInfoReturnable<Boolean> ret) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(this.activeItemStack.getItem() instanceof ToolItem tool){
            int predictionEnchantmentLevel = ModEnchantments.getPredictionEnchantment(dys);
            if(((ParryItem) tool).getUseParryAction(this.activeItemStack) == UseAction.BLOCK){
                ParryItem parryItem = (ParryItem) tool;
                if(this.activeItemStack.getMaxUseTime() - this.itemUseTimeLeft >= parryItem.getParryTicks() + predictionEnchantmentLevel || source.isIn(DamageTypeTags.IS_EXPLOSION)){
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
                    if(!(this.activeItemStack.getItem() instanceof ShieldItem && this.activeItemStack.getItem().getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= ((ParryItem) this.activeItemStack.getItem()).getParryTicks() + ModEnchantments.getPredictionEnchantment(dys))){

                        parryDataTimer = 10;
                        ((CanBlock) dys).setParryDataToTrue();

                        //parry enchantment
                        int parryEnchantmentLevel = ModEnchantments.getParryEnchantment(dys);

                        //variables for parry effects
                        double knockback = ((ParryItem) this.activeItemStack.getItem()).getKnockbackAfterParryAction();
                        int slowness = ((ParryItem) this.activeItemStack.getItem()).getSlownessAfterParryAction();
                        int slownessAmp = ((ParryItem) this.activeItemStack.getItem()).getSlownessAmplifierAfterParryAction();
                        int weakness = ((ParryItem) this.activeItemStack.getItem()).getWeaknessAfterParryAction();
                        int weaknessAmp = ((ParryItem) this.activeItemStack.getItem()).getWeaknessAmplifierAfterParryAction();
                        int disarmed = 0;

                        float[] modifiers = new float[3];
                        if(attacker instanceof PlayerEntity) {
                            modifiers[0] = 0.65F; modifiers[1] = 0.2F; modifiers[2] = 0.15F;
                            knockback -= FrycParry.config.parryKnockbackStrengthForPlayersModifier;
                            slowness -= FrycParry.config.slownessForPlayersAfterParryModifier;
                            slownessAmp -= FrycParry.config.slownessForPlayersAmplifierModifier;
                            weakness -= FrycParry.config.weaknessForPlayersAfterParryModifier;
                            weaknessAmp -= FrycParry.config.weaknessForPlayersAmplifierModifier;
                            disarmed = ((ParryItem) this.activeItemStack.getItem()).getDisarmedAfterParryAction();
                        }
                        else {
                            modifiers[0] = 0.95F; modifiers[1] = 0.3F; modifiers[2] = 0.22F;
                        }

                        //knockback
                        if(knockback > 0){
                            attacker.takeKnockback((knockback + parryEnchantmentLevel * modifiers[0])/10, dys.getX() - attacker.getX(), dys.getZ() - attacker.getZ());
                            attacker.velocityModified = true;
                        }

                        //slowness
                        if(slowness > 0){
                            slowness = (int) (slowness + slowness * (parryEnchantmentLevel * modifiers[1]));
                            slownessAmp--;
                            if(slownessAmp < 0) slownessAmp = 0;
                            if(attacker.hasStatusEffect(StatusEffects.SLOWNESS)){
                                if(attacker.getActiveStatusEffects().get(StatusEffects.SLOWNESS).getDuration() < slowness) attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, slowness, slownessAmp));
                            }
                            else attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, slowness, slownessAmp));
                        }

                        //weakness
                        if(weakness > 0){
                            weakness = (int) (weakness + weakness * (parryEnchantmentLevel * modifiers[2]));
                            weaknessAmp--;
                            if(weaknessAmp < 0) weaknessAmp = 0;
                            if(attacker.hasStatusEffect(StatusEffects.WEAKNESS)){
                                if(attacker.getActiveStatusEffects().get(StatusEffects.WEAKNESS).getDuration() < weakness) attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, weakness, weaknessAmp));
                            }
                            else attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, weakness, weaknessAmp));
                        }

                        //disarm
                        if(disarmed > 0){
                            disarmed = disarmed + parryEnchantmentLevel * 5;
                            if(attacker.hasStatusEffect(ModEffects.DISARMED)){
                                if(attacker.getActiveStatusEffects().get(ModEffects.DISARMED).getDuration() < disarmed) attacker.addStatusEffect(new StatusEffectInstance(ModEffects.DISARMED, disarmed, 0));
                            }
                            else attacker.addStatusEffect(new StatusEffectInstance(ModEffects.DISARMED, disarmed, 0));
                        }

                        //counterattack enchantment and disabling block
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
                    else if(this.activeItemStack.getItem() instanceof ShieldItem && dys instanceof PlayerEntity player){
                        if(((CanBlock) player).getParryDataValue()) ((CanBlock) player).setParryDataToFalse();
                        if(attacker.disablesShield()) player.disableShield(true);
                    }
                }
            }
        }
        if(!(this.activeItemStack.getItem() instanceof ShieldItem)){
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
                amount *= ((ParryItem) this.activeItemStack.getItem()).getProjectileDamageTakenAfterBlock();
                b =30;
            }
            else if(source.getAttacker() instanceof LivingEntity attacker){
                amount *= ((ParryItem) this.activeItemStack.getItem()).getMeleeDamageTakenAfterBlock();
                b = 30;
                if(attacker.disablesShield() && dys instanceof PlayerEntity player){
                    player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 160);
                    dys.stopUsingItem();
                }
            }
            dys.world.sendEntityStatus(this, b);

            if(!(this.activeItemStack.getItem() instanceof ShieldItem)){
                dys.stopUsingItem();
            }

            //damaging item
            if(!ParryHelper.hasShieldEquipped(dys)){
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
            if(item instanceof ToolItem && item.getUseAction(this.activeItemStack) != UseAction.BLOCK){
                ret.setReturnValue(((ParryItem) item).getUseParryAction(this.activeItemStack) == UseAction.BLOCK && dys.getDataTracker().get(BLOCKING_DATA)); //<--- BLOCKING_DATA is required to block with items that doesn't have UseAction.BLOCK
            }
            else {
                ret.setReturnValue(item.getUseAction(this.activeItemStack) == UseAction.BLOCK);
            }
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


    //plays SoundEvents.ITEM_SHIELD_BREAK sound when attack is blocked with tool
    @ModifyVariable(at = @At("HEAD"), method = "handleStatus(B)V")
    private byte init(byte status) {
        if(status == 29){
            LivingEntity entity = ((LivingEntity)(Object)this);
            if(ParryHelper.canParry(entity)){
                status = 30;
            }
        }
        return status;
    }

    //starts tracking BLOCKING_DATA and PARRY_DATA
    @Inject(method = "initDataTracker()V", at = @At("HEAD"))
    private void initBlockingData(CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        dys.getDataTracker().startTracking(BLOCKING_DATA, false);
        dys.getDataTracker().startTracking(PARRY_DATA, false);
    }


    //item wont be used (finishUsing()) when itemUseTimeLeft reaches 0 (when BLOCKING_DATA is true)
    @Inject(method = "consumeItem()V", at = @At("HEAD"), cancellable = true)
    private void dontConsumeItem(CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(dys.getDataTracker().get(BLOCKING_DATA)) info.cancel();
    }

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void decrementParryDataTimer(CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(parryDataTimer > 0) parryDataTimer--;
        else if(((CanBlock) dys).getParryDataValue()) ((CanBlock) dys).setParryDataToFalse();
    }




    //blocking data and parry data
    public void setBlockingDataToTrue(){
        ((LivingEntity)(Object)this).getDataTracker().set(BLOCKING_DATA, true);
    }

    public void setBlockingDataToFalse(){
        ((LivingEntity)(Object)this).getDataTracker().set(BLOCKING_DATA, false);
    }

    public boolean getBlockingDataValue(){
        return ((LivingEntity)(Object)this).getDataTracker().get(BLOCKING_DATA);
    }

    public void setParryDataToTrue(){
        ((LivingEntity)(Object)this).getDataTracker().set(PARRY_DATA, true);
    }

    public void setParryDataToFalse(){
        ((LivingEntity)(Object)this).getDataTracker().set(PARRY_DATA, false);
    }

    public boolean getParryDataValue(){
        return ((LivingEntity)(Object)this).getDataTracker().get(PARRY_DATA);
    }

    static{
        BLOCKING_DATA = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        PARRY_DATA = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }


}
