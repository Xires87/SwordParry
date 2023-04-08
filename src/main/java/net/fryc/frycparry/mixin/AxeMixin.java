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
abstract class AxeMixin extends MiningToolItem {
    protected AxeMixin(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    //cooldown for block after attacking
    /*
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(FrycParry.config.cooldownForBlockAfterAxeAttack > 0 && attacker instanceof PlayerEntity player){
            if(FrycParry.config.enableBlockingWithAxe && !player.getItemCooldownManager().isCoolingDown(stack.getItem())) player.getItemCooldownManager().set(stack.getItem(), FrycParry.config.cooldownForBlockAfterAxeAttack);
        }
        super.postHit(stack, target, attacker);
        return true;
    }

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


    //cooldown after using block
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if(FrycParry.config.cooldownAfterBlockAction > 0 && user instanceof PlayerEntity player){
            if(!player.getItemCooldownManager().isCoolingDown(stack.getItem())) player.getItemCooldownManager().set(stack.getItem(), FrycParry.config.cooldownAfterBlockAction);
        }
    }


     */

    //public UseAction getUseAction(ItemStack stack) {
    //    return UseAction.BOW;
    //}

//todo zrobic siekierke i inne narzedzia
    public int getParryTicks(){
        return 4;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return 0.55F;
    }
    public float getProjectileDamageTakenAfterBlock(){
        return 0.10F;
    }
}
