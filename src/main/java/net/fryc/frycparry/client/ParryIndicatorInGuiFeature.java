package net.fryc.frycparry.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fryc.frycparry.FrycParry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class ParryIndicatorInGuiFeature {

    public static final Identifier ICONS_TEXTURE = Identifier.of(FrycParry.MOD_ID, "textures/gui/icons.png");

    public static void render(DrawContext context, int screenWidth, int screenHeight, float progress) {
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        int posX = screenWidth / 2 - 8;
        int posY = screenHeight / 2 - 7 + 55;
        int textureHeight = (int) (progress * 15);
        context.drawTexture(ICONS_TEXTURE, posX, posY, 54, 0, 16, 14, 256, 256);
        context.drawTexture(ICONS_TEXTURE, posX, posY + 14 - textureHeight, 70, 14 - textureHeight, 16, textureHeight, 256, 256);
        RenderSystem.defaultBlendFunc();
    }
}
