package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
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
        if(FrycParry.config.cooldownAfterBlockAction > 0 && user instanceof PlayerEntity player){
            if(!player.getItemCooldownManager().isCoolingDown(stack.getItem())) player.getItemCooldownManager().set(stack.getItem(), FrycParry.config.cooldownAfterBlockAction);
        }
    }

    public boolean isEnchantable(ItemStack stack) {
        return !stack.hasEnchantments();
    }

    public int getEnchantability() {
        return 10;
    }

}
