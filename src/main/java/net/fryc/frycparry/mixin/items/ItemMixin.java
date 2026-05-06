package net.fryc.frycparry.mixin.items;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.util.mixin_interfaces.ParryItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
abstract class ItemMixin implements ToggleableFeature, ItemConvertible, FabricItem, ParryItem {

    private ParryAttributes parryAttributes = ParryAttributes.DEFAULT;

    public void setParryAttributes(String parryAttributesId){
        this.parryAttributes = ParryAttributes.get(parryAttributesId);
    }

    public void setParryAttributes(ParryAttributes parryAttributes){
        this.parryAttributes = parryAttributes;
    }

    public ParryAttributes getParryAttributes(){
        return this.parryAttributes;
    }
}
