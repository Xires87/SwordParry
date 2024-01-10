package net.fryc.frycparry.util.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public interface ParryItem {

    //for ToolItem
    UseAction getUseParryAction(ItemStack stack);


    TypedActionResult<ItemStack> useParry(World world, PlayerEntity user, Hand hand);

    void onStoppedUsingParry(ItemStack stack, World world, LivingEntity user, int remainingUseTicks);

    int getParryTicks();

    float getMeleeDamageTakenAfterBlock();

    float getProjectileDamageTakenAfterBlock();

    int getCooldownAfterParryAction();
    int getCooldownAfterInterruptingBlockAction();
    double getKnockbackAfterParryAction();
    int getSlownessAfterParryAction();
    int getSlownessAmplifierAfterParryAction();
    int getWeaknessAfterParryAction();
    int getWeaknessAmplifierAfterParryAction();
    int getDisarmedAfterParryAction();

    int getMaxUseTimeParry();
}
