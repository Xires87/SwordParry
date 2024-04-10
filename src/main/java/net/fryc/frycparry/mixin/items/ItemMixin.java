package net.fryc.frycparry.mixin.items;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
abstract class ItemMixin implements ToggleableFeature, ItemConvertible, FabricItem, ParryItem {

    private ParryAttributes parryAttributes = ParryAttributes.DEFAULT;

    public void setParryAttributes(String parryAttributesId){
        this.parryAttributes = ParryAttributes.get(parryAttributesId);
    }
    public void setParryAttributes(ParryAttributes parryAttributes){
        this.parryAttributes = parryAttributes;
    }
    public ParryAttributes getParryAttributes(){
        return this.parryAttributes;
    }

    public TypedActionResult<ItemStack> useParry(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(ParryHelper.isItemParryDisabled(user.getWorld(), itemStack.getItem()) || hand == Hand.OFF_HAND) return TypedActionResult.fail(user.getStackInHand(hand));// <-- disables offhand parrying and parrying with disabled items
        if(ParryHelper.canParryWithoutShield(user)){
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
        if(user instanceof PlayerEntity player && !world.isClient()){
            if(((CanBlock) user).hasParriedRecently()){
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


    public UseAction getUseParryAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    public int getMaxUseTimeParry(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().getMaxUseTimeParry();
    }


    public int getParryTicks(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().getParryTicks();
    }

    public float getMeleeDamageTakenAfterBlock(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().getMeleeDamageTakenAfterBlock();
    }

    public float getProjectileDamageTakenAfterBlock(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().getProjectileDamageTakenAfterBlock();
    }

    public int getCooldownAfterParryAction(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().getCooldownAfterParryAction();
    }

    public int getCooldownAfterInterruptingBlockAction(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().getCooldownAfterInterruptingBlockAction();
    }

    public double getKnockbackAfterParryAction(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().getKnockbackAfterParryAction();
    }
    public int getSlownessAfterParryAction(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().getSlownessAfterParryAction();
    }
    public int getSlownessAmplifierAfterParryAction(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().getSlownessAmplifierAfterParryAction();
    }
    public int getWeaknessAfterParryAction(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().getWeaknessAfterParryAction();
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().getWeaknessAmplifierAfterParryAction();
    }
    public int getDisarmedAfterParryAction(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().getDisarmedAfterParryAction();
    }

    public boolean shouldStopUsingItemAfterBlockOrParry(){
        return ((ParryItem)((Item)(Object)this)).getParryAttributes().shouldStopUsingItemAfterBlockOrParry();
    }

}
