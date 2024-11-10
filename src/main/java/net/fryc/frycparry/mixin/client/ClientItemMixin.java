package net.fryc.frycparry.mixin.client;

import net.fryc.frycparry.client.SyncParryItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Item.class, priority = 2000)
public abstract class ClientItemMixin implements SyncParryItem {

    @Unique
    public int syncMaxUseTime = -1;

    @Override
    public void setSyncMaxUseTime(int syncMaxUseTime) {
        this.syncMaxUseTime = syncMaxUseTime;
    }

    @Inject(method = "getMaxUseTime", at = @At("HEAD"), cancellable = true)
    public void getMaxUseTimeMixin(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if(syncMaxUseTime >= 0){
            cir.setReturnValue(syncMaxUseTime);
        }
    }
}
