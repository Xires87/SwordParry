package net.fryc.frycparry.mixin.items;

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
abstract class ShieldMixin extends Item implements ParryItem {
    public ShieldMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", at = @At("HEAD"))
    private void preventUsingWhenDisarmed(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> ret) {
        if(user.hasStatusEffect(ModEffects.DISARMED)) ret.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
    }


    //cooldown after using a shield
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        Item item = stack.getItem();

        if(user instanceof PlayerEntity player && !world.isClient()){
            float cooldown;
            if(((CanBlock) user).hasParriedRecently()){
                cooldown = ((ParryItem) item).getCooldownAfterParryAction();
            }
            else {
                cooldown = ((ParryItem) item).getCooldownAfterInterruptingBlockAction();
            }

            if(cooldown < 0){
                cooldown = ((int) player.getAttackCooldownProgressPerTick() - 1) * (cooldown * -1);
            }
            if(cooldown > 0){
                if(!player.getItemCooldownManager().isCoolingDown(item)) player.getItemCooldownManager().set(item, (int) cooldown);
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


    //makes shield enchantable
    public boolean isEnchantable(ItemStack stack) {
        return !stack.hasEnchantments();
    }

    public int getEnchantability() {
        return 12;
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
