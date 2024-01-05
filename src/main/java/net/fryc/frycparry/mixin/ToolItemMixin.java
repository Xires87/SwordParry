package net.fryc.frycparry.mixin;

import net.fryc.frycparry.util.CanBlock;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.ParryItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ToolItem.class)
abstract class ToolItemMixin extends Item implements ParryItem {

    public ToolItemMixin(Settings settings) {
        super(settings);
    }


    public TypedActionResult<ItemStack> useParry(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(ParryHelper.isItemParryDisabled(user.getWorld(), itemStack.getItem()) || hand == Hand.OFF_HAND) return TypedActionResult.fail(user.getStackInHand(hand));// <-- disables offhand parrying and parrying with disabled items
        if(ParryHelper.canParry(user)){
            ((CanBlock) user).setCurrentHandParry(hand);
            ((CanBlock) user).setBlockingDataToTrue();
            return TypedActionResult.success(itemStack);
        }
        else return TypedActionResult.fail(user.getStackInHand(hand));
    }

    //cooldown after using block
    public void onStoppedUsingParry(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        ((CanBlock) user).setBlockingDataToFalse();
        Item item = stack.getItem();

        //if player stops blocking after parry, cooldown is shorter (and depends on item used)
        if(user instanceof PlayerEntity player){
            if(((CanBlock) user).getParryDataValue()){
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

        ((CanBlock) user).setParryDataToFalse();
    }


    public UseAction getUseParryAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    public int getMaxUseTimeParry(){
        return 72000;
    }


    public int getParryTicks(){
        return 0;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return 0.80F;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return 0.95F;
    }

    public int getCooldownAfterParryAction(){
        return 18;
    }

    public int getCooldownAfterInterruptingBlockAction(){
        return 28;
    }

    public double getKnockbackAfterParryAction(){
        return 4;
    }
    public int getSlownessAfterParryAction(){
        return 60;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return 1;
    }
    public int getWeaknessAfterParryAction(){
        return 0;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return 1;
    }
    public int getDisarmedAfterParryAction(){
        return 20;
    }
}
