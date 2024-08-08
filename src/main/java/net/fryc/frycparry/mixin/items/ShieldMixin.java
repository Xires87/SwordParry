package net.fryc.frycparry.mixin.items;

import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.util.EnchantmentsConfigHelper;
import net.fryc.frycparry.util.ParryHelper;
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
abstract class ShieldMixin extends Item implements ParryItem {
    public ShieldMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", at = @At("HEAD"), cancellable = true)
    private void preventUsingWhenDisarmed(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> ret) {
        if(user.hasStatusEffect(ModEffects.DISARMED)) ret.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
    }


    //cooldown after using a shield
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        Item item = stack.getItem();

        if(user instanceof PlayerEntity player && !world.isClient()){
            if(!player.getItemCooldownManager().isCoolingDown(item)){
                int cooldown = ParryHelper.getParryCooldown(player, item);
                if(cooldown > 0){
                    player.getItemCooldownManager().set(item, cooldown);
                }
            }
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
        return EnchantmentsConfigHelper.shieldEnchantability;
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

}
