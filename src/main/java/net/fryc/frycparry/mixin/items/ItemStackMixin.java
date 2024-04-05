package net.fryc.frycparry.mixin.items;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
abstract class ItemStackMixin implements FabricItemStack {


    // its probably useless but I prefer to keep it
    @Inject(method = "onStoppedUsing(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V", at = @At("HEAD"), cancellable = true)
    private void stopUsingItemParry(World world, LivingEntity user, int remainingUseTicks, CallbackInfo info) {
        ItemStack dys = ((ItemStack)(Object)this);
        if(((CanBlock) user).getBlockingDataValue() && ParryHelper.isItemParryEnabled(dys)){
            ((ParryItem) dys.getItem()).onStoppedUsingParry(dys, world, user, remainingUseTicks);
            info.cancel();
        }
    }
}
