package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V", shift = At.Shift.AFTER))
    private void setBlockCooldownOnItemSwap(CallbackInfo info) {
        PlayerEntity dys = ((PlayerEntity)(Object)this);
        Item item = dys.getMainHandStack().getItem();
        if(((item instanceof SwordItem && FrycParry.config.enableBlockingWithSword) ||
                (item instanceof AxeItem && FrycParry.config.enableBlockingWithAxe)) && dys.getOffHandStack().isEmpty()){
            if(dys.isUsingItem()) dys.stopUsingItem();
            if(!dys.getItemCooldownManager().isCoolingDown(item)) dys.getItemCooldownManager().set(item, 20);

        }
    }
}
