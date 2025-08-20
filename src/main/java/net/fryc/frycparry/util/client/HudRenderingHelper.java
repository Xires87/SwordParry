package net.fryc.frycparry.util.client;

import net.fryc.frycparry.FrycParry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class HudRenderingHelper {

    private static final Function<DrawContext, Integer> ICON_X_POS = (context) -> {
        return Math.round((float) context.getScaledWindowWidth() /2) - 6 + FrycParry.config.client.blockCooldownIconX;
    };

    private static final Function<DrawContext, Integer> ICON_Y_POS = (context) -> {
        return Math.round((float) context.getScaledWindowHeight()/2) + 17 + FrycParry.config.client.blockCooldownIconY;
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
