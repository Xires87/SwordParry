package net.fryc.frycparry.util.interfaces;

import net.fryc.frycparry.attributes.ParryAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public interface ParryItem {

    void setParryAttributes(String parryAttributesId);
    void setParryAttributes(ParryAttributes parryAttributes);
    ParryAttributes getParryAttributes();
    UseAction getUseParryAction(ItemStack stack);


    TypedActionResult<ItemStack> useParry(World world, PlayerEntity user, Hand hand);

    void onStoppedUsingParry(ItemStack stack, World world, LivingEntity user, int remainingUseTicks);

    ItemStack finishUsingParry(ItemStack stack, World world, LivingEntity user);

}
