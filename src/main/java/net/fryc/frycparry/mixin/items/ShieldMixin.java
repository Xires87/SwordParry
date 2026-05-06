package net.fryc.frycparry.mixin.items;

import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.util.ConfigHelper;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.mixin_interfaces.HasParryCooldownManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
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


    @Inject(method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", at = @At("HEAD"), cancellable = true)
    private void preventUsingWhenDisarmed(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> ret) {
        if(parryingIsNotPossible(user.getStackInHand(hand), user, hand)) ret.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
    }

    //cooldown after using a shield
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if(user instanceof ServerPlayerEntity player){
            ((HasParryCooldownManager) player).getParryCooldownManager().addCooldown(player, ParryHelper.getParryCooldown(player, stack.getItem()));
        }
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.stopUsingItem();
        if(user instanceof ServerPlayerEntity player){
            ((HasParryCooldownManager) player).getParryCooldownManager().addCooldown(player, ParryHelper.getParryCooldown(player, stack.getItem()));
        }

        return stack;
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

    private static boolean parryingIsNotPossible(ItemStack stack, PlayerEntity user, Hand hand){
        return user.hasStatusEffect(ModEffects.DISARMED) || !ParryHelper.isReadyToBlock(user);
    }
}
