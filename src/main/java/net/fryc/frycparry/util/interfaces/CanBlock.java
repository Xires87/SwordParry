package net.fryc.frycparry.util.interfaces;


import net.minecraft.util.Hand;

public interface CanBlock {

    //for LivingEntity
    void setBlockingDataToTrue();

    void setBlockingDataToFalse();

    boolean getBlockingDataValue();

    void setParryDataToTrue();

    void setParryDataToFalse();

    boolean getParryDataValue();

    void stopUsingItemParry();

    void setCurrentHandParry(Hand hand);

    boolean hasParriedRecently();

}
