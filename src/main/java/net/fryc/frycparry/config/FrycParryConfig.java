package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fryc.frycparry.FrycParry;

@Config(name = FrycParry.MOD_ID)
public class FrycParryConfig extends PartitioningSerializer.GlobalData {

    @ConfigEntry.Category("shield")
    @ConfigEntry.Gui.TransitiveObject
    public ShieldConfig shield = new ShieldConfig();


    @ConfigEntry.Category("sword")
    @ConfigEntry.Gui.TransitiveObject
    public SwordConfig sword = new SwordConfig();

    @ConfigEntry.Category("axe")
    @ConfigEntry.Gui.TransitiveObject
    public AxeConfig axe = new AxeConfig();

    @ConfigEntry.Category("shovel")
    @ConfigEntry.Gui.TransitiveObject
    public ShovelConfig shovel = new ShovelConfig();

    @ConfigEntry.Category("pickaxe")
    @ConfigEntry.Gui.TransitiveObject
    public PickaxeConfig pickaxe = new PickaxeConfig();

    @ConfigEntry.Category("hoe")
    @ConfigEntry.Gui.TransitiveObject
    public HoeConfig hoe = new HoeConfig();

    @ConfigEntry.Category("multiplayerModifiers")
    @ConfigEntry.Gui.TransitiveObject
    public ModifiersConfig modifiers = new ModifiersConfig();

    @ConfigEntry.Category("enchantments")
    @ConfigEntry.Gui.TransitiveObject
    public EnchantmentsConfig enchantments = new EnchantmentsConfig();

    @ConfigEntry.Category("other")
    @ConfigEntry.Gui.TransitiveObject
    public OtherConfig server = new OtherConfig();

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public FrycparryClientConfig client = new FrycparryClientConfig();

}
