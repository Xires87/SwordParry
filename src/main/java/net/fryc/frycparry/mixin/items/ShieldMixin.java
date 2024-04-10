package net.fryc.frycparry.mixin.items;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShieldItem.class)
abstract class ShieldMixin extends Item {
    public ShieldMixin(Settings settings) {
        super(settings);
    }

// todo rozwiazac jakos z tarcza
    @Inject(method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", at = @At("HEAD"))
    private void preventUsingWhenDisarmed(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> ret) {
        if(user.hasStatusEffect(ModEffects.DISARMED)) ret.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
    }


    //cooldown after using a shield
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        Item item = stack.getItem();

        if(user instanceof PlayerEntity player && !world.isClient()){
            if(((CanBlock) user).hasParriedRecently()){
                //player.sendMessage(Text.of("sparowano" + (world.isClient() ? " klient" : " serwer")));
                if(((ParryItem) item).getCooldownAfterParryAction() > 0){
                    if(!player.getItemCooldownManager().isCoolingDown(item)) player.getItemCooldownManager().set(item, ((ParryItem) item).getCooldownAfterParryAction());
                }
            }
            else {
                if(((ParryItem) item).getCooldownAfterInterruptingBlockAction() > 0){
                    if(!player.getItemCooldownManager().isCoolingDown(item)) player.getItemCooldownManager().set(item, ((ParryItem) item).getCooldownAfterInterruptingBlockAction());
                }
            }
        }
    }

    public int getMaxUseTimeParry(){
        return 72000;
    }



    //makes shield enchantable
    public boolean isEnchantable(ItemStack stack) {
        return !stack.hasEnchantments();
    }

    public int getEnchantability() {
        return 12;
    }

    // zabezpieczenie na wypadek gdyby sie chcialy odpalic te metody
    public UseAction getUseParryAction(ItemStack stack){
        return UseAction.NONE;
    }

    public TypedActionResult<ItemStack> useParry(World world, PlayerEntity user, Hand hand){
        return ((ShieldItem)(Object)this).use(world, user, hand);
    }

    public void onStoppedUsingParry(ItemStack stack, World world, LivingEntity user, int remainingUseTicks){
        onStoppedUsing(stack, world, user, remainingUseTicks);
    }
    //----------------------------------------------------------------------

    public int getParryTicks(){
        return FrycParry.config.shield.shieldParryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return (float) FrycParry.config.shield.shieldBlockMeleeDamageTaken/100;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return (float) FrycParry.config.shield.shieldBlockArrowDamageTaken/100;
    }

    public int getCooldownAfterParryAction(){
        return FrycParry.config.shield.cooldownAfterShieldParryAction;
    }
    public int getCooldownAfterInterruptingBlockAction(){
        return FrycParry.config.shield.cooldownAfterInterruptingShieldBlockAction;
    }
    public double getKnockbackAfterParryAction(){
        return FrycParry.config.shield.shieldParryKnockbackStrength;
    }
    public int getSlownessAfterParryAction(){
        return FrycParry.config.shield.shieldSlownessAfterParry;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return FrycParry.config.shield.shieldSlownessAfterParryAmplifier;
    }
    public int getWeaknessAfterParryAction(){
        return FrycParry.config.shield.shieldWeaknessAfterParry;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return FrycParry.config.shield.shieldWeaknessAfterParryAmplifier;
    }
    public int getDisarmedAfterParryAction(){
        return FrycParry.config.shield.shieldDisarmAfterParry;
    }

    public boolean shouldStopUsingItemAfterBlockOrParry(){
        return FrycParry.config.shield.shouldStopUsingShieldAfterBlockOrParry;
    }

}
