package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.util.CanBlock;
import net.fryc.frycparry.util.ParryItem;
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
        Item item = stack.getItem();

        if(user instanceof PlayerEntity player){
            if(((CanBlock) user).getParryDataValue()){
                if(FrycParry.config.cooldownAfterShieldParryAction > 0){
                    if(!player.getItemCooldownManager().isCoolingDown(item)) player.getItemCooldownManager().set(item, FrycParry.config.cooldownAfterShieldParryAction);
                }
            }
            else {
                if(FrycParry.config.cooldownAfterInterruptingBlockAction > 0){
                    if(!player.getItemCooldownManager().isCoolingDown(item)) player.getItemCooldownManager().set(item, FrycParry.config.cooldownAfterInterruptingBlockAction);
                }
            }
        }

        // todo zrobic zeby PARRY_DATA ustawialo sie na false po jakims czasie i jak sie zablokuje atak tarcza
        ((CanBlock) user).setParryDataToFalse();
    }


    // todo zrobic zeby nie mogl blokowac kiedy ma disarma

    //makes shield enchantable
    public boolean isEnchantable(ItemStack stack) {
        return !stack.hasEnchantments();
    }

    public int getEnchantability() {
        return 12;
    }

}
