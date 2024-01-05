package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.util.CanBlock;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.ParryItem;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.ToolItem;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity implements Attackable, CanBlock {

    private static final TrackedData<Boolean> BLOCKING_DATA;
    private static final TrackedData<Boolean> PARRY_DATA;

    public int parryDataTimer = 0;

    @Shadow
    protected ItemStack activeItemStack;
    @Shadow
    protected int itemUseTimeLeft;


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    //makes blockedByShield method return true only when attack was blocked with shield or parried
    @Inject(method = "blockedByShield(Lnet/minecraft/entity/damage/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    private void shieldBlocking(DamageSource source, CallbackInfoReturnable<Boolean> ret) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(dys.getActiveItem().getItem() instanceof ToolItem tool && dys.getActiveItem().getUseAction() != UseAction.BLOCK){
            int predictionEnchantmentLevel = ModEnchantments.getPredictionEnchantment(dys);
            if(((ParryItem) tool).getUseParryAction(dys.getActiveItem()) == UseAction.BLOCK){
                ParryItem parryItem = (ParryItem) tool;
                if(parryItem.getMaxUseTimeParry() - dys.getItemUseTimeLeft() >= parryItem.getParryTicks() + predictionEnchantmentLevel || source.isIn(DamageTypeTags.IS_EXPLOSION)){
                    ret.setReturnValue(false);
                }
            }
        }
        else if(!(dys.getActiveItem().getUseAction() == UseAction.BLOCK) || (source.isIn(DamageTypeTags.IS_EXPLOSION) && dys.getActiveItem().getItem().getMaxUseTime(dys.getActiveItem()) - dys.getItemUseTimeLeft() < 5)){
            ret.setReturnValue(false);
        }
    }
// todo przetestowac z simply swordami
    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V", shift = At.Shift.AFTER))
    private void parry(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ret) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(dys.getActiveItem().isEmpty() || !(dys.getActiveItem().getItem() instanceof ToolItem || dys.getActiveItem().getItem() instanceof ShieldItem)) return;
        if(!source.isIn(DamageTypeTags.IS_EXPLOSION)){
            if(!(dys.getActiveItem().getUseAction() == UseAction.BLOCK && ((ParryItem) dys.getActiveItem().getItem()).getMaxUseTimeParry() - dys.getItemUseTimeLeft() >= ((ParryItem) dys.getActiveItem().getItem()).getParryTicks() + ModEnchantments.getPredictionEnchantment(dys))){

                parryDataTimer = 10;
                ((CanBlock) dys).setParryDataToTrue();

                if(source.getAttacker() instanceof LivingEntity attacker){
                    if(!source.isIn(DamageTypeTags.IS_PROJECTILE)){
                        //applying parry effects
                        ParryHelper.applyParryEffects(dys, attacker);

                        //counterattack enchantment and disabling block
                        if(dys instanceof  PlayerEntity player){
                            //counterattack enchantment
                            ParryHelper.applyCounterattackEffects(player, attacker);

                            //disabling block after parrying axe attack (when config allows it)
                            if(attacker.disablesShield() && FrycParry.config.disableBlockAfterParryingAxeAttack){
                                if(dys.getActiveItem().getItem() instanceof ShieldItem) player.disableShield(true);
                                else {
                                    player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 100);
                                    if(ParryHelper.checkDualWieldingWeapons(player)) player.getItemCooldownManager().set(player.getOffHandStack().getItem(), 100);
                                }
                                ((CanBlock) dys).stopUsingItemParry();
                                dys.swingHand(dys.getActiveHand(), true);
                            }
                        }
                    }
                }
            }
            else if(dys.getActiveItem().getItem() instanceof ShieldItem && dys instanceof PlayerEntity player){ // <-- triggered when player blocks attack with shield
                if(((CanBlock) player).getParryDataValue()) ((CanBlock) player).setParryDataToFalse();
                if(source.getAttacker() instanceof LivingEntity attacker){
                    if(attacker.disablesShield()) player.disableShield(true);
                }
            }
        }

        // interrupting block action after PARRYING attack with weapon or tool
        if(dys.getActiveItem().getUseAction() != UseAction.BLOCK){
            ((CanBlock) dys).stopUsingItemParry();
            dys.swingHand(dys.getActiveHand(), true);
        }

        //damaging item that is not shield
        if(!(dys.getMainHandStack().getItem() instanceof ShieldItem || dys.getOffHandStack().getItem() instanceof ShieldItem)){
            dys.getMainHandStack().damage(1, dys, (player) -> {
                player.sendToolBreakStatus(player.getActiveHand());
            });
        }
    }

    @ModifyVariable(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At("HEAD"), ordinal = 0)
    private float blocking(float amount, DamageSource source) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(dys.getActiveItem().isEmpty() || !(dys.getActiveItem().getItem() instanceof ToolItem || dys.getActiveItem().getItem() instanceof ShieldItem)) return amount;
        if(amount > 0.0F && !dys.blockedByShield(source) && this.blockedByWeapon(source, dys)){
            byte b = 2;
            if(source.isIn(DamageTypeTags.IS_EXPLOSION)){
                // when shield is not held for 5 ticks, explosion will deal 80% of its damage
                if(dys.getActiveItem().getUseAction() == UseAction.BLOCK){
                    amount *= 0.8F;
                    b = 29;
                }
            }
            else if(source.isIn(DamageTypeTags.IS_PROJECTILE)){
                amount *= ((ParryItem) dys.getActiveItem().getItem()).getProjectileDamageTakenAfterBlock();
                b =30;
            }
            else if(source.getAttacker() instanceof LivingEntity attacker){
                amount *= ((ParryItem) dys.getActiveItem().getItem()).getMeleeDamageTakenAfterBlock();
                b = 30;
                if(attacker.disablesShield() && dys instanceof PlayerEntity player){
                    player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 160);
                    ((CanBlock) dys).stopUsingItemParry();
                }
            }
            dys.getWorld().sendEntityStatus(this, b);

            // interrupting block action after BLOCKING attack with weapon or tool
            if(dys.getActiveItem().getUseAction() != UseAction.BLOCK){
                ((CanBlock) dys).stopUsingItemParry();
            }

            //damaging item that is not shield
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
        if (dys.isUsingItem() && !dys.getActiveItem().isEmpty()) {
            Item item = dys.getActiveItem().getItem();
            if(item instanceof ToolItem && item.getUseAction(dys.getActiveItem()) != UseAction.BLOCK){
                ret.setReturnValue(((ParryItem) item).getUseParryAction(dys.getActiveItem()) == UseAction.BLOCK && dys.getDataTracker().get(BLOCKING_DATA)); //<--- BLOCKING_DATA is required to block with items that doesn't have UseAction.BLOCK
            }
            else {
                ret.setReturnValue(item.getUseAction(dys.getActiveItem()) == UseAction.BLOCK);
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


    /* item wont be used (finishUsing()) when itemUseTimeLeft reaches 0 (when BLOCKING_DATA is true)
    @Inject(method = "consumeItem()V", at = @At("HEAD"), cancellable = true)
    private void dontConsumeItem(CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(dys.getDataTracker().get(BLOCKING_DATA)) info.cancel();
    }

     */

    // makes PARRY_DATA false after 10 ticks
    @Inject(method = "tick()V", at = @At("TAIL"))
    private void decrementParryDataTimer(CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(parryDataTimer > 0) parryDataTimer--;
        else if(((CanBlock) dys).getParryDataValue()) ((CanBlock) dys).setParryDataToFalse();
    }

    // cancels stopUsingItem() method when BLOCKING_DATA is true
    @Inject(method = "stopUsingItem()V", at = @At("HEAD"), cancellable = true)
    private void removeBlockDelay(CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(((CanBlock) dys).getBlockingDataValue()) info.cancel();
    }

    // method to stop blocking with parry key
    public void stopUsingItemParry() {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if (!dys.getActiveItem().isEmpty() && dys.getActiveItem().getItem() instanceof ToolItem tool && dys.getActiveItem().getUseAction() != UseAction.BLOCK) {
            ((ParryItem) tool).onStoppedUsingParry(dys.getActiveItem(), dys.getWorld(), dys, dys.getItemUseTimeLeft());
        }
        else{
            dys.stopUsingItem();
            return;
        }

        dys.clearActiveItem();
    }

    @Shadow
    protected void setLivingFlag(int mask, boolean value) {
    }

    // todo wyczyscic kod (narazie tylko osobny maxUseTime zrobilem)
    public void setCurrentHandParry(Hand hand) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        ItemStack itemStack = dys.getStackInHand(hand);
        if (!itemStack.isEmpty() && !dys.isUsingItem()) {
            this.activeItemStack = itemStack;
            this.itemUseTimeLeft = ((ParryItem) itemStack.getItem()).getMaxUseTimeParry();
            if (!this.getWorld().isClient) {
                this.setLivingFlag(1, true);
                this.setLivingFlag(2, hand == Hand.OFF_HAND);
                dys.emitGameEvent(GameEvent.ITEM_INTERACT_START);
            }

        }
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
