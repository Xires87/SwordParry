package net.fryc.frycparry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fryc.frycparry.keybind.ModKeyBinds;
import net.fryc.frycparry.network.ModPackets;
import net.fryc.frycparry.util.interfaces.HasParryCooldownManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;

public class FrycParryClient implements ClientModInitializer {

    public static final Identifier EMPTY_SHIELD_TEXTURE = Identifier.of(FrycParry.MOD_ID, "textures/gui/crosshair/shield_empty.png");
    public static final Identifier FULL_SHIELD_TEXTURE = Identifier.of(FrycParry.MOD_ID, "textures/gui/crosshair/shield_full.png");

    @Override
    public void onInitializeClient() {

        ModPackets.registerS2CPackets();
        ModKeyBinds.register();

        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity player = client.player;
            if(player != null){
                int l = (int) (((HasParryCooldownManager) player).getParryCooldownManager().getCooldownProgress() * 13);
                if(l < 13){
                    context.drawTexture(
                            EMPTY_SHIELD_TEXTURE,
                            context.getScaledWindowWidth()/2 - 6 + FrycParry.config.client.blockCooldownIconX,
                            context.getScaledWindowHeight()/2 + 17 + FrycParry.config.client.blockCooldownIconY,
                            0,
                            0,
                            0,
                            12,
                            12,
                            12,
                            12
                    );

                    context.drawTexture(
                            FULL_SHIELD_TEXTURE,
                            context.getScaledWindowWidth()/2 - 6 + FrycParry.config.client.blockCooldownIconX,
                            context.getScaledWindowHeight()/2 + 17 + FrycParry.config.client.blockCooldownIconY,
                            0,
                            0,
                            0,
                            l,
                            12,
                            12,
                            12
                    );
                }
            }
        });

    }
}
