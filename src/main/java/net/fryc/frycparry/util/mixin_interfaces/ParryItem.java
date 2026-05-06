package net.fryc.frycparry.util.mixin_interfaces;

import net.fryc.frycparry.attributes.ParryAttributes;

public interface ParryItem {

    void setParryAttributes(String parryAttributesId);
    void setParryAttributes(ParryAttributes parryAttributes);
    ParryAttributes getParryAttributes();

}
