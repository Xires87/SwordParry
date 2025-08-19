package net.fryc.frycparry.mixin.items;

import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.util.ConfigHelper;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.interfaces.HasParryCooldownManager;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShieldItem.class)
abstract class ShieldMixin extends Item implements ParryItem {

    public ShieldMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", at = @At("HEAD"), cancellable = true)
    private void preventUsingWhenDisarmed(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> ret) {
        if(parryingIsNotPossible(user.getStackInHand(hand), user, hand)) ret.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
    }
    @Inject(method = "getMaxUseTime(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;)I", at = @At("RETURN"), cancellable = true)
    private void modifyShieldMaxUseTime(ItemStack stack, LivingEntity user, CallbackInfoReturnable<Integer> ret) {
        ret.setReturnValue(((ParryItem) stack.getItem()).getParryAttributes().getMaxUseTimeParry());
    }


    //cooldown after using a shield
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if(user instanceof ServerPlayerEntity player){
            ((HasParryCooldownManager) player).getParryCooldownManager().addCooldown(player, ParryHelper.getParryCooldown(player, stack.getItem()));
        }
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.stopUsingItem();
        return stack;
    }

    public ItemStack finishUsingParry(ItemStack stack, World world, LivingEntity user) {
        return finishUsing(stack, world, user);
    }

    public boolean isEnchantable(ItemStack stack) {
        if(this.getEnchantability() < 1){
            return super.isEnchantable(stack);
        }

        return !stack.hasEnchantments();
    }

    //makes shield enchantable
    public int getEnchantability() {
        return ConfigHelper.shieldEnchantability;
    }

    public UseAction getUseParryAction(ItemStack stack){
        return UseAction.NONE;
    }

    public TypedActionResult<ItemStack> useParry(World world, PlayerEntity user, Hand hand){
        return ((ShieldItem)(Object)this).use(world, user, hand);
    }

    public void onStoppedUsingParry(ItemStack stack, World world, LivingEntity user, int remainingUseTicks){
        onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    private static boolean parryingIsNotPossible(ItemStack stack, PlayerEntity user, Hand hand){
        return user.hasStatusEffect(ModEffects.DISARMED) || !ParryHelper.isReadyToBlock(user);
    }

}
