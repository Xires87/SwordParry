package net.fryc.frycparry.compat;

import net.fabricmc.loader.api.FabricLoader;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.util.CompatHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public class ImmersiveMcCompat {

    private static final boolean IS_IMMERSIVE_MC_LOADED = FabricLoader.getInstance().isModLoaded("immersivemc");

    private static final Class<?> SHIELD_PROXY = IS_IMMERSIVE_MC_LOADED ? CompatHelper.getClassForName("com.hammy275.immersivemc.common.vr.mixin_proxy.ShieldProxy") : null;
    private static final Method IS_DAMAGE_SOURCE_BLOCKED = IS_IMMERSIVE_MC_LOADED ?
            CompatHelper.getMethodForName(SHIELD_PROXY, "isDamageSourceBlocked", LivingEntity.class, DamageSource.class) :
            null;

    private static final Method GET_A_BLOCKING_SHIELD = IS_IMMERSIVE_MC_LOADED ?
            CompatHelper.getMethodForName(SHIELD_PROXY, "getABlockingShield", LivingEntity.class) :
            null;


    public static boolean isDamageSourceBlocked(LivingEntity living, DamageSource damageSource){
        if(!IS_IMMERSIVE_MC_LOADED || IS_DAMAGE_SOURCE_BLOCKED == null){
            return false;
        }

        try{
            boolean blocked = (boolean) IS_DAMAGE_SOURCE_BLOCKED.invoke(SHIELD_PROXY, living, damageSource);

            return blocked;
        } catch (Exception e) {
            FrycParry.LOGGER.error("ImmersiveMc compat failed. Unable to use ShieldProxy.isDamageSourceBlocked([...]) method.", e);

            return false;
        }
    }

    @Nullable
    public static ItemStack getImmersiveBlockingItem(LivingEntity living){
        if(!IS_IMMERSIVE_MC_LOADED || GET_A_BLOCKING_SHIELD == null){
            return null;
        }

        try{
            ItemStack stack = (ItemStack) GET_A_BLOCKING_SHIELD.invoke(SHIELD_PROXY, living);

            return stack;
        } catch (Exception e) {
            FrycParry.LOGGER.error("ImmersiveMc compat failed. Unable to use ShieldProxy.getABlockingShield([...]) method.", e);

            return null;
        }
    }

    public static int getImmersiveBlockingTime(){
        return -1;
    }

    //Probably need to check if player is in VR
    public static boolean shouldUseImmersiveBlockingItem(LivingEntity living){
        return IS_IMMERSIVE_MC_LOADED && living.getActiveItem().isEmpty();
    }
}
