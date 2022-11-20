package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.tag.TagKey;
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
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker instanceof PlayerEntity player){
            player.getItemCooldownManager().set(stack.getItem(), 15);
        }
        super.postHit(stack, target, attacker);
        return true;
    }

    //lets you block with sword only if your off hand is empty
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(!FrycParry.config.enableBlockingWithAxe) return TypedActionResult.fail(user.getStackInHand(hand));
        if(user.getOffHandStack().isEmpty() && user.getMainHandStack().getItem() instanceof AxeItem){
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
