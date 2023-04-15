package net.fryc.frycparry.util;


public interface CanBlock {

    //for LivingEntity
    void setBlockingDataToTrue();

    void setBlockingDataToFalse();

    boolean getBlockingDataValue();

    void setParryDataToTrue();

    void setParryDataToFalse();

    boolean getParryDataValue();

}
