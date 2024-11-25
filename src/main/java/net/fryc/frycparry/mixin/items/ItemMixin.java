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
        if(ParryHelper.isItemParryDisabledWithConfig(user.getWorld(), itemStack) || hand == Hand.OFF_HAND) return TypedActionResult.fail(user.getStackInHand(hand));// <-- disables offhand parrying and parrying with disabled items
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
            if(!player.getItemCooldownManager().isCoolingDown(item)){
                int cooldown = ParryHelper.getParryCooldown(player, item);
                if(cooldown > 0){
                    player.getItemCooldownManager().set(item, cooldown);
                }
            }
        }
    }

    public ItemStack finishUsingParry(ItemStack stack, World world, LivingEntity user) {
        ((CanBlock) user).stopUsingItemParry();
        return stack;
    }


    public UseAction getUseParryAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

}
