package net.fryc.frycparry.util.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycparry.FrycParryClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class ItemRendererHelper {

    public static boolean shouldApplyParryTransform = false;


    public static void applyParryTransform(MatrixStack matrices){
        float x = FrycParryClient.config.itemRotationX;
        float y = FrycParryClient.config.itemRotationY;
        float z = FrycParryClient.config.itemRotationZ;

        Vector3f vector3f = new Vector3f(FrycParryClient.config.itemTranslationX, FrycParryClient.config.itemTranslationY, FrycParryClient.config.itemTranslationZ);
        vector3f.mul(0.0625F);
        vector3f.set(MathHelper.clamp(vector3f.x, -5.0F, 5.0F), MathHelper.clamp(vector3f.y, -5.0F, 5.0F), MathHelper.clamp(vector3f.z, -5.0F, 5.0F));

        Vector3f vector3f2 = new Vector3f(FrycParryClient.config.itemScaleX, FrycParryClient.config.itemScaleY, FrycParryClient.config.itemScaleZ);
        vector3f2.set(MathHelper.clamp(vector3f2.x, -4.0F, 4.0F), MathHelper.clamp(vector3f2.y, -4.0F, 4.0F), MathHelper.clamp(vector3f2.z, -4.0F, 4.0F));

        matrices.translate(vector3f.x(), vector3f.y(), vector3f.z());
        matrices.multiply((new Quaternionf()).rotationXYZ(x * 0.017453292F, y * 0.017453292F, z * 0.017453292F));
        matrices.scale(vector3f2.x(), vector3f2.y(), vector3f2.z());
    }
}
