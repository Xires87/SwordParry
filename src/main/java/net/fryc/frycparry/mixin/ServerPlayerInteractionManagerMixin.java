package net.fryc.frycparry.mixin;

import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.fryc.frycparry.util.interfaces.ServerParryInteraction;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayerInteractionManager.class)
abstract class ServerPlayerInteractionManagerMixin implements ServerParryInteraction {

    @Shadow private GameMode gameMode;

    public boolean isCreative(){
        return this.gameMode.isCreative();
    }


    public ActionResult interactItemParry(ServerPlayerEntity player, World world, ItemStack stack, Hand hand) {
        if (this.gameMode == GameMode.SPECTATOR) {
            return ActionResult.PASS;
        } else if (player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
            return ActionResult.PASS;
        } else {
            int i = stack.getCount();
            int j = stack.getDamage();

            TypedActionResult<ItemStack> typedActionResult;
            if(ParryHelper.isItemParryEnabled(stack)){
                typedActionResult = ((ParryItem) stack.getItem()).useParry(world, player, hand);
            }
            else {
                typedActionResult = TypedActionResult.fail(stack);
            }

            ItemStack itemStack = (ItemStack)typedActionResult.getValue();
            if (itemStack == stack && itemStack.getCount() == i && itemStack.getMaxUseTime(player) <= 0 && itemStack.getDamage() == j) {
                return typedActionResult.getResult();
            } else if (typedActionResult.getResult() == ActionResult.FAIL && itemStack.getMaxUseTime(player) > 0 && !player.isUsingItem()) {
                return typedActionResult.getResult();
            } else {
                if (stack != itemStack) {
                    player.setStackInHand(hand, itemStack);
                }

                if (this.isCreative()) {
                    itemStack.setCount(i);
                    if (itemStack.isDamageable() && itemStack.getDamage() != j) {
                        itemStack.setDamage(j);
                    }
                }

                if (itemStack.isEmpty()) {
                    player.setStackInHand(hand, ItemStack.EMPTY);
                }

                if (!player.isUsingItem()) {
                    player.playerScreenHandler.syncState();
                }

                return typedActionResult.getResult();
            }
        }
    }
}
