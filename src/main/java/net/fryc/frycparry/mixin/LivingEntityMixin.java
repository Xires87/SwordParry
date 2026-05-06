package net.fryc.frycparry.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.network.payloads.InformClientAboutParryPayload;
import net.fryc.frycparry.tag.ModEntityTypeTags;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.mixin_interfaces.CanBlock;
import net.fryc.frycparry.util.mixin_interfaces.ParryItem;
import net.fryc.frycparry.util.mixin_interfaces.TargetingMob;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity implements Attackable, CanBlock {

    private static final TrackedData<Boolean> PARRY_DATA;

    public int parryTimer = 0;

    @Shadow
    protected ItemStack activeItemStack;
    @Shadow
    protected int itemUseTimeLeft;


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onStatusEffectRemoved(Lnet/minecraft/entity/effect/StatusEffectInstance;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffect;onRemoved(Lnet/minecraft/entity/attribute/AttributeContainer;)V", shift = At.Shift.BEFORE))
    protected void onDisarmedRemoved(StatusEffectInstance effect, CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(effect.getEffectType() == ModEffects.DISARMED){
            if(dys instanceof MobEntity mob){
                mob.setTarget(((TargetingMob) mob).getLastTarget());
                ((TargetingMob) mob).setLastTarget(null);
                mob.setAttacking(true);
            }
        }
    }


    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V", shift = At.Shift.AFTER))
    private void parryOrFullyBlock(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ret) { // <----- executed only on server
        LivingEntity dys = ((LivingEntity)(Object)this);
        boolean shouldSwingHand = false;
        boolean playSound = true;
        if(ParryHelper.isItemParryEnabled(dys.getActiveItem())){
            if(((CanBlock) dys).getParryDataValue()){ // <--- checks if attack was parried
                ((CanBlock) dys).setParryDataToFalse();
                ((CanBlock) dys).setParryTimer(dys.getWorld(), 10);
                shouldSwingHand = true;

                if(source.getAttacker() instanceof LivingEntity attacker){
                    if(!source.isIn(DamageTypeTags.IS_PROJECTILE)){
                        //applying parry effects
                        ParryHelper.applyParryEffects(dys, attacker);

                        //counterattack enchantment and disabling block
                        if(dys instanceof  PlayerEntity player){
                            //counterattack enchantment
                            ParryHelper.applyCounterattackEffects(player, attacker);

                            //disabling block after parrying axe attack (when config allows it)
                            if(attacker.disablesShield() && FrycParry.config.server.disableBlockAfterParryingAxeAttack){
                                ParryHelper.disableParryItem(player, dys.getActiveItem().getItem());
                                dys.swingHand(dys.getActiveHand(), true);
                                playSound = false;
                            }
                        }
                    }
                }

                if(playSound){
                    ParryHelper.playParrySound(dys);
                }
            }
            else {
                ((CanBlock) dys).setParryDataToFalse(); // <--- redundant
                if(dys instanceof PlayerEntity player){
                    if(source.getAttacker() instanceof LivingEntity attacker){
                        if(attacker.disablesShield()){
                            ParryHelper.disableParryItem(player, dys.getActiveItem().getItem());
                            ParryHelper.playGuardBreakSound(player);
                            playSound = false;
                        }
                    }
                }

                if(playSound){
                    ParryHelper.playBlockSound(dys);
                }
            }

            // interrupting block action after PARRYING or FULLY BLOCKING (no dmg) attack with tool
            if(((ParryItem) dys.getActiveItem().getItem()).getParryAttributes().shouldStopUsingItemAfterBlockOrParry()){
                dys.stopUsingItem();
                if(shouldSwingHand) dys.swingHand(dys.getActiveHand(), true);
            }

            //damaging item that is not shield
            if(!ParryHelper.hasShieldEquipped(dys)){
                if(dys.getMainHandStack().isDamageable()){
                    dys.getMainHandStack().damage(1, dys, EquipmentSlot.MAINHAND);
                }
            }
        }
    }

    @ModifyVariable(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At("HEAD"), ordinal = 0)
    private float blocking(float amount, DamageSource source) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(dys.getActiveItem().isEmpty() || !ParryHelper.isItemParryEnabled(dys.getActiveItem()) || dys.getWorld().isClient()) return amount;
        if(ParryHelper.attackWasBlocked(source, dys)){
            if(ParryHelper.attackWasParried(source, dys.getActiveItem(), dys)){
                ((CanBlock) dys).setParryDataToTrue();
                return amount;
            }
            if(amount > 0.0F && !dys.blockedByShield(source)){
                boolean playSound = true;
                float originalDamage = amount;

                if(source.isIn(DamageTypeTags.IS_EXPLOSION)){
                    ParryItem parryItem = (ParryItem) dys.getActiveItem().getItem();
                    if(ParryHelper.explosionCanBeBlocked(parryItem)){
                        float multiplier;
                        if(ParryHelper.explosionWasSuccessfullyBlocked(parryItem, dys)){
                            multiplier = parryItem.getParryAttributes().getExplosionDamageTakenAfterBlock();
                        }
                        else {
                            multiplier = 1.0F - (1.0F - parryItem.getParryAttributes().getExplosionDamageTakenAfterBlock())/5;
                        }
                        amount *= multiplier;
                    }
                    else {
                        playSound = false;
                    }
                }
                else if(source.isIn(DamageTypeTags.IS_PROJECTILE)){
                    amount *= ((ParryItem) dys.getActiveItem().getItem()).getParryAttributes().getProjectileDamageTakenAfterBlock();
                }
                else if(source.getAttacker() instanceof LivingEntity attacker){
                    amount *= ((ParryItem) dys.getActiveItem().getItem()).getParryAttributes().getMeleeDamageTakenAfterBlock();
                    if(attacker.disablesShield() && dys instanceof PlayerEntity player){
                        ParryHelper.disableParryItem(player, dys.getActiveItem().getItem());
                        ParryHelper.playGuardBreakSound(player);
                        playSound = false;
                    }
                }
                else {
                    playSound = false;
                }

                if(playSound){
                    ParryHelper.playBlockSound(dys);
                }

                // interrupting block action after BLOCKING attack with tool
                if(((ParryItem) dys.getActiveItem().getItem()).getParryAttributes().shouldStopUsingItemAfterBlockOrParry()){
                    dys.stopUsingItem();
                }

                //damaging item that is not shield
                if(!ParryHelper.hasShieldEquipped(dys)){
                    if(dys.getMainHandStack().isDamageable()){
                        dys.getMainHandStack().damage(1, dys, EquipmentSlot.MAINHAND);
                    }
                }
                else {
                    dys.damageShield(originalDamage);
                    if(dys.getActiveItem().isEmpty()) dys.stopUsingItem();
                }


            }
        }
        return amount;
    }


    @ModifyExpressionValue(
            method = "isBlocking()Z",
            at = @At(value = "CONSTANT", args = "intValue=5")
    )
    private int modifyBlockDelay(int original) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        int blockDelay = ((ParryItem) dys.getActiveItem().getItem()).getParryAttributes().getBlockDelay() - ModEnchantments.getReflexEnchantment(dys);
        if(blockDelay < 0){
            blockDelay = 0;
        }
        return original - 5 + blockDelay;
    }

    @Inject(at = @At("HEAD"), method = "handleStatus(B)V", cancellable = true)
    private void cancelShieldBlockAndBreakStatuses(byte status, CallbackInfo info) {
        if(status == EntityStatuses.BLOCK_WITH_SHIELD || status == EntityStatuses.BREAK_SHIELD){
            info.cancel();
        }
    }

    //starts tracking BLOCKING_DATA and PARRY_DATA
    @Inject(method = "initDataTracker(Lnet/minecraft/entity/data/DataTracker$Builder;)V", at = @At("HEAD"))
    private void initBlockingData(DataTracker.Builder builder, CallbackInfo info) {
        builder.add(PARRY_DATA, false);
    }


    // decrements parryTimer and makes PARRY_DATA false when parryTimer is 0
    @Inject(method = "tick()V", at = @At("TAIL"))
    private void decrementParryDataTimer(CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(parryTimer > 0){
            parryTimer--;
        }
        else if(!dys.getWorld().isClient()){ // <---- parry data is always false on client
            if(((CanBlock) dys).getParryDataValue()) ((CanBlock) dys).setParryDataToFalse();
        }
    }

    @Inject(method = "canHaveStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z", at = @At("HEAD"), cancellable = true)
    private void makeSomeMobsResistantToDisarm(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> ret) {
        if(effect.getEffectType() == ModEffects.DISARMED && this.getType().isIn(ModEntityTypeTags.DISARM_RESISTANT_MOBS)){
            ret.setReturnValue(false);
        }
    }


    public void setParryTimer(World world, int ticks){
        parryTimer = ticks;
        if(!world.isClient()){
            if(((LivingEntity)(Object)this) instanceof ServerPlayerEntity player){
                ServerPlayNetworking.send(player, new InformClientAboutParryPayload(ticks));
            }
        }
    }

    public boolean hasParriedRecently(){
        return parryTimer > 0;
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
        PARRY_DATA = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }


}
