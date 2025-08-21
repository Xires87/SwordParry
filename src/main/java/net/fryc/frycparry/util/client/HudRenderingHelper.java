package net.fryc.frycparry.util.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycparry.FrycParry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class HudRenderingHelper {

    private static final Function<DrawContext, Integer> ICON_X_POS = (context) -> {
        int value = Math.round((float) context.getScaledWindowWidth() * FrycParry.config.client.blockCooldownIconStartingPosition.getStartingX()) - 6 + FrycParry.config.client.blockCooldownIconX;
        if(value < 0) return 0;
        return Math.min(value, context.getScaledWindowWidth() - 12);
    };

    private static final Function<DrawContext, Integer> ICON_Y_POS = (context) -> {
        int value = Math.round((float) context.getScaledWindowHeight() * FrycParry.config.client.blockCooldownIconStartingPosition.getStartingY()) - 6 + FrycParry.config.client.blockCooldownIconY;
        if(value < 0) return 0;
        return Math.min(value, context.getScaledWindowHeight() - 12);
    };


    public static void drawFullIcon(DrawContext context, Identifier iconPath){
        drawPartialIcon(context, iconPath, 12, 12);
    }

    public static void drawFullIcon(DrawContext context, Identifier iconPath, int x, int y){
        drawPartialIcon(context, iconPath, x, y, 12, 12);
    }

    public static void drawPartialIcon(DrawContext context, Identifier iconPath, int width, int height){
        context.drawTexture(iconPath, ICON_X_POS.apply(context), ICON_Y_POS.apply(context), 0, 0, 0, width, height, 12, 12);
    }

    public static void drawPartialIcon(DrawContext context, Identifier iconPath, int x, int y, int width, int height){
        context.drawTexture(iconPath, x, y, 0, 0, 0, width, height, 12, 12);
    }
}
