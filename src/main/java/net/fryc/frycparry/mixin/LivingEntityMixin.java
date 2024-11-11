package net.fryc.frycparry.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.network.ModPackets;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
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

    public int parryTimer = 0;

    @Shadow
    protected ItemStack activeItemStack;
    @Shadow
    protected int itemUseTimeLeft;


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
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
                            //player.getWorld().sendEntityStatus(player, EntityStatuses.BREAK_SHIELD);
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
            if(((ParryItem) dys.getActiveItem().getItem()).shouldStopUsingItemAfterBlockOrParry()){
                ((CanBlock) dys).stopUsingItemParry();
                if(shouldSwingHand) dys.swingHand(dys.getActiveHand(), true);
            }

            //damaging item that is not shield
            if(!ParryHelper.hasShieldEquipped(dys)){
                if(dys.getMainHandStack().isDamageable()){
                    dys.getMainHandStack().damage(1, dys, (player) -> {
                        player.sendToolBreakStatus(player.getActiveHand());
                    });
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
                    // when shield is not held for 5 ticks, explosion will deal 80% of its damage
                    if(dys.getActiveItem().getItem() instanceof ShieldItem){
                        amount *= 0.8F;
                    }
                }
                else if(source.isIn(DamageTypeTags.IS_PROJECTILE)){
                    amount *= ((ParryItem) dys.getActiveItem().getItem()).getProjectileDamageTakenAfterBlock();
                }
                else if(source.getAttacker() instanceof LivingEntity attacker){
                    amount *= ((ParryItem) dys.getActiveItem().getItem()).getMeleeDamageTakenAfterBlock();
                    if(attacker.disablesShield() && dys instanceof PlayerEntity player){
                        ParryHelper.disableParryItem(player, dys.getActiveItem().getItem());
                        ParryHelper.playGuardBreakSound(player);
                        //b = 30;
                        playSound = false;
                    }
                }
                else {
                    playSound = false;
                }

                if(playSound){
                    ParryHelper.playBlockSound(dys);
                }
                //dys.getWorld().sendEntityStatus(this, b);


                // interrupting block action after BLOCKING attack with tool
                if(((ParryItem) dys.getActiveItem().getItem()).shouldStopUsingItemAfterBlockOrParry()){
                    ((CanBlock) dys).stopUsingItemParry();
                }

                //damaging item that is not shield
                if(!ParryHelper.hasShieldEquipped(dys)){
                    if(dys.getMainHandStack().isDamageable()){
                        dys.getMainHandStack().damage(1, dys, (player) -> {
                            player.sendToolBreakStatus(player.getActiveHand());
                        });
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


    //removes the 5 tick block delay
    @Inject(method = "isBlocking()Z", at = @At("HEAD"), cancellable = true)
    private void removeBlockDelay(CallbackInfoReturnable<Boolean> ret) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if (dys.isUsingItem() && !dys.getActiveItem().isEmpty()) {
            Item item = dys.getActiveItem().getItem();
            if(ParryHelper.isItemParryEnabled(dys.getActiveItem()) && item.getUseAction(dys.getActiveItem()) != UseAction.BLOCK){
                ret.setReturnValue(((ParryItem) item).getUseParryAction(dys.getActiveItem()) == UseAction.BLOCK && dys.getDataTracker().get(BLOCKING_DATA)); //<--- BLOCKING_DATA is required to block with items that doesn't have UseAction.BLOCK
            }
            else {
                ret.setReturnValue(item.getUseAction(dys.getActiveItem()) == UseAction.BLOCK);
            }
        }
    }


    //executed only on CLIENT
    /* it didnt work properly on multiplayer
    @Inject(at = @At("HEAD"), method = "handleStatus(B)V", cancellable = true)
    private void modifyBlockAndParrySoundsClient(byte status, CallbackInfo info) {
        LivingEntity dys = ((LivingEntity) (Object)this);
        if(status == EntityStatuses.BLOCK_WITH_SHIELD){
            boolean shield = !ParryHelper.canParryWithoutShield(dys);
            if(((CanBlock) dys).hasParriedRecently()){
                if(shield){
                    dys.playSound(ModSounds.SHIELD_PARRY, 1.1f, 0.8f + this.getWorld().random.nextFloat() * 0.4f);
                }
                else {
                    dys.playSound(ModSounds.TOOL_PARRY, 1.1f, 0.8f + this.getWorld().random.nextFloat() * 0.4f);
                }
                info.cancel();
            }
            else {
                if(!shield){
                    dys.playSound(ModSounds.TOOL_BLOCK, 0.9f, 0.8f + this.getWorld().random.nextFloat() * 0.4f);
                    info.cancel();
                }
                // no cancel here to play vanilla shield block sound
            }
        }
        else if(status == EntityStatuses.BREAK_SHIELD){
            boolean tool = ParryHelper.canParryWithoutShield(dys);
            if(tool){
                dys.playSound(ModSounds.TOOL_GUARD_BREAK, 0.8f, 0.8f + this.getWorld().random.nextFloat() * 0.4f);
            }
            else {
                dys.playSound(ModSounds.SHIELD_GUARD_BREAK, 0.8f, 0.8f + this.getWorld().random.nextFloat() * 0.4f);
            }
            info.cancel();
        }
    }
     */

    @Inject(at = @At("HEAD"), method = "handleStatus(B)V", cancellable = true)
    private void cancelShieldBlockAndBreakStatuses(byte status, CallbackInfo info) {
        if(status == EntityStatuses.BLOCK_WITH_SHIELD || status == EntityStatuses.BREAK_SHIELD){
            info.cancel();
        }
    }

    //starts tracking BLOCKING_DATA and PARRY_DATA
    @Inject(method = "initDataTracker()V", at = @At("HEAD"))
    private void initBlockingData(CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        dys.getDataTracker().startTracking(BLOCKING_DATA, false);
        dys.getDataTracker().startTracking(PARRY_DATA, false);
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

    // cancels stopUsingItem() method when BLOCKING_DATA is true
    @Inject(method = "stopUsingItem()V", at = @At("HEAD"), cancellable = true)
    private void cancelStopUsingItem(CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(((CanBlock) dys).getBlockingDataValue()) info.cancel();
    }

    // method to stop blocking with parry key
    public void stopUsingItemParry() {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if (!dys.getActiveItem().isEmpty() && ParryHelper.isItemParryEnabled(dys.getActiveItem()) && !(dys.getActiveItem().getItem() instanceof ShieldItem)) {
            ((ParryItem) dys.getActiveItem().getItem()).onStoppedUsingParry(dys.getActiveItem(), dys.getWorld(), dys, dys.getItemUseTimeLeft());
        }
        else{
            dys.stopUsingItem();
            return;
        }

        dys.clearActiveItem();
    }



    @WrapOperation(
            method = "consumeItem()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;finishUsing(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;")
    )
    private ItemStack finishBlockingWhenRemainingUseTicksReachZero(ItemStack instance, World world, LivingEntity user, Operation<ItemStack> original) {
        if (((CanBlock) user).getBlockingDataValue()) {
            return ((ParryItem) instance.getItem()).finishUsingParry(instance, world, user);
        } else {
            return original.call(instance, world, user);
        }
    }

    @Shadow
    protected void setLivingFlag(int mask, boolean value) {
    }


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

    public void setParryTimer(World world, int ticks){
        parryTimer = ticks;
        if(!world.isClient()){
            if(((LivingEntity)(Object)this) instanceof ServerPlayerEntity player){
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeInt(ticks);
                ServerPlayNetworking.send(player, ModPackets.INFORM_CLIENT_ABOUT_PARRY_ID, buf);
            }
        }
    }
    public boolean hasParriedRecently(){
        return parryTimer > 0;
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
