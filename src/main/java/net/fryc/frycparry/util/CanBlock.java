package net.fryc.frycparry.util;

import net.minecraft.entity.data.TrackedData;

public interface CanBlock {

    //for living entity
    void setBlockingDataToTrue();

    void setBlockingDataToFalse();

    boolean getBlockingDataValue();

    void setParryDataToTrue();

    void setParryDataToFalse();

    boolean getParryDataValue();

}
