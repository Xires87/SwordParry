package net.fryc.frycparry.util.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycparry.FrycParry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.*;

@Environment(EnvType.CLIENT)
public class ClientParryHelper {


    public static boolean canParry(ClientPlayerEntity player) {
        return !isItemParryDisabled(player.getMainHandStack().getItem());
    }

    public static boolean isItemParryDisabled(Item item){
        if(item instanceof SwordItem) return !FrycParry.config.client.enableBlockingWithSword;
        if(item instanceof AxeItem) return !FrycParry.config.client.enableBlockingWithAxe;
        if(item instanceof PickaxeItem) return !FrycParry.config.client.enableBlockingWithPickaxe;
        if(item instanceof ShovelItem) return !FrycParry.config.client.enableBlockingWithShovel;
        if(item instanceof HoeItem) return !FrycParry.config.client.enableBlockingWithHoe;
        return !FrycParry.config.client.enableBlockingWithOtherTools;
    }
}
