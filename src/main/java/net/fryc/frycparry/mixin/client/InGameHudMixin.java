package net.fryc.frycparry.mixin.client;

import net.fryc.frycparry.client.ParryIndicatorInGuiFeature;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

    // Adds parry indicator to the HUD
    @Inject(method = "render", at = @At("HEAD"))
    public void onRender(DrawContext context, float tickDelta, CallbackInfo ci) {
        PlayerEntity player = getCameraPlayer();
        if (player.isUsingItem() && ParryHelper.isItemParryEnabled(player.getActiveItem())) {
            ParryItem parryItem = ((ParryItem) player.getActiveItem().getItem());
            int maxUseTimeParry = parryItem.getMaxUseTimeParry();
            int itemUseTimeLeft = player.getItemUseTimeLeft() < 0 ? maxUseTimeParry + player.getItemUseTimeLeft() : player.getItemUseTimeLeft();
            float progress = (float) itemUseTimeLeft / (float) maxUseTimeParry;
            Window window = client.getWindow();
            ParryIndicatorInGuiFeature.render(context, window.getScaledWidth(), window.getScaledHeight(), progress);
        }
    }
}
