package net.fryc.frycparry.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShieldItem.class)
abstract class ShieldMixin extends Item {
    public ShieldMixin(Settings settings) {
        super(settings);
    }

    //cooldown after using a shield
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if(user instanceof PlayerEntity player){
            player.getItemCooldownManager().set(stack.getItem(), 20);
        }
    }

}
