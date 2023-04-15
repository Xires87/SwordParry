package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AxeItem.class)
abstract class AxeMixin {

    /*

    //lets you block with sword only if your off hand is empty
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(user.getMainHandStack().getItem() instanceof AxeItem){
            if(!world.isClient()){
                EntityType.FIREBALL.spawn(((ServerWorld) world), user.getBlockPos(), SpawnReason.TRIGGERED);
            }
            user.setCurrentHand(hand);
            return TypedActionResult.success(itemStack);
        }
        else return TypedActionResult.fail(user.getStackInHand(hand));
    }



     */

    //public UseAction getUseAction(ItemStack stack) {
    //    return UseAction.BOW;
    //}

    public int getParryTicks(){
        return FrycParry.config.axeParryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return (float) FrycParry.config.axeBlockMeleeDamageTaken/100;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return (float) FrycParry.config.axeBlockArrowDamageTaken/100;
    }

    public int getCooldownAfterParryAction(){
        return FrycParry.config.cooldownAfterAxeParryAction;
    }
    public int getCooldownAfterInterruptingBlockAction(){
        return FrycParry.config.cooldownAfterInterruptingAxeBlockAction;
    }
    public double getKnockbackAfterParryAction(){
        return FrycParry.config.axeParryKnockbackStrength;
    }
    public int getSlownessAfterParryAction(){
        return FrycParry.config.axeSlownessAfterParry;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return FrycParry.config.axeSlownessAfterParryAmplifier;
    }
    public int getWeaknessAfterParryAction(){
        return FrycParry.config.axeWeaknessAfterParry;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return FrycParry.config.axeWeaknessAfterParryAmplifier;
    }
    public int getDisarmedAfterParryAction(){
        return FrycParry.config.axeDisarmAfterParry;
    }
}
