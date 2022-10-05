package net.fryc.frycparry.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
abstract class SwordMixin extends ToolItem implements Vanishable {
    public SwordMixin(ToolMaterial material, Settings settings) {
        super(material, settings);
    }


    //cooldown for block after attacking
    @Inject(method = "postHit(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"))
    private void blockCooldownAfterAttacking(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> ret) {
        if(attacker instanceof PlayerEntity player){
            player.getItemCooldownManager().set(stack.getItem(), 12);
        }
    }

    //lets you block with sword only if your off hand is empty
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(user.getOffHandStack().isEmpty() && user.getMainHandStack().getItem() instanceof SwordItem){
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
        else return TypedActionResult.pass(user.getStackInHand(hand));
    }

    //cooldown after using block
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if(user instanceof PlayerEntity player){
            player.getItemCooldownManager().set(stack.getItem(), 20);
        }
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }
}
