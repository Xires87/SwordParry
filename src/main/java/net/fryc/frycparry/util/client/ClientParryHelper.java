package net.fryc.frycparry.util.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycparry.FrycParryClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.*;

@Environment(EnvType.CLIENT)
public class ClientParryHelper {


    public static boolean canParry(ClientPlayerEntity player) {
        return !isItemParryDisabled(player.getMainHandStack().getItem());
    }

    public static boolean isItemParryDisabled(Item item){
        if(item instanceof SwordItem) return !FrycParryClient.config.enableBlockingWithSword;
        if(item instanceof AxeItem) return !FrycParryClient.config.enableBlockingWithAxe;
        if(item instanceof PickaxeItem) return !FrycParryClient.config.enableBlockingWithPickaxe;
        if(item instanceof ShovelItem) return !FrycParryClient.config.enableBlockingWithShovel;
        if(item instanceof HoeItem) return !FrycParryClient.config.enableBlockingWithHoe;
        return !FrycParryClient.config.enableBlockingWithOtherTools;
    }
}
