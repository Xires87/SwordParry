package net.fryc.frycparry.mixin;

import net.fryc.frycparry.sound.ModSounds;
import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Separate mixin for sound alternation to avoid conflicts with upstream changes
@Mixin(LivingEntity.class)
abstract class LivingEntitySoundAlternationMixin extends Entity {

    public LivingEntitySoundAlternationMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    // Plays ModSounds.PARRYING_SOUND_EVENT sound when attack is blocked with tool
    @Inject(method = "handleStatus(B)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"), cancellable = true)
    private void playParriedSound(byte status, CallbackInfo info) {
        LivingEntity entity = ((LivingEntity)(Object)this);
        if (ParryHelper.canParryWithoutShield(entity)) {
            this.playSound(ModSounds.PARRYING_SOUND_EVENT, 1.0F, 1.0F);
            info.cancel();
        }
    }
}
