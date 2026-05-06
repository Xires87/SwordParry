package net.fryc.frycparry.util.mixin_interfaces;


import net.minecraft.world.World;

public interface CanBlock {

    //for LivingEntity
    void setParryDataToTrue();

    void setParryDataToFalse();

    boolean getParryDataValue();

    boolean hasParriedRecently();

    void setParryTimer(World world, int ticks);

}
