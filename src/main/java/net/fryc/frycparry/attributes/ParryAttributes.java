package net.fryc.frycparry.attributes;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.network.ModPackets;
import net.fryc.frycparry.util.ConfigHelper;
import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.*;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import oshi.util.tuples.Quartet;

import java.util.HashMap;
import java.util.Map;

public class ParryAttributes {

    private final String id;
    private final int parryTicks;
    private final float meleeDamageTakenAfterBlock;
    private final float projectileDamageTakenAfterBlock;
    private final float explosionDamageTakenAfterBlock;
    private final float cooldownAfterParryAction;
    private final float cooldownAfterInterruptingBlockAction;
    private final float cooldownAfterAttack;
    private final int maxUseTime;
    private final int blockDelay;
    private final int explosionBlockDelay;
    private final boolean shouldStopUsingItemAfterBlockOrParry;
    private final double knockbackAfterParryAction;

    private final Map<RegistryEntry<StatusEffect>, Quartet<Integer, Integer, Float, Float>> parryEffects;

    private static final HashMap<String, ParryAttributes> REGISTERED_ATTRIBUTES = new HashMap<>();

    private static final String DEFAULT_ID = "default";
    private static final String SWORD_DEFAULT_ID = "sword_default";
    private static final String SHIELD_DEFAULT_ID = "shield_default";
    private static final String AXE_DEFAULT_ID = "axe_default";
    private static final String PICKAXE_DEFAULT_ID = "pickaxe_default";
    private static final String HOE_DEFAULT_ID = "hoe_default";
    private static final String OTHER_DEFAULT_ID = "other_default";
    private static final String SHOVEL_DEFAULT_ID = "shovel_default";
    private static final String MACE_DEFAULT_ID = "mace_default";

    //public static final PacketByteBuf.PacketWriter<ParryAttributes> PACKET_WRITER;

    //public static final PacketByteBuf.PacketReader<ParryAttributes> PACKET_READER;

    public static final PacketCodec<RegistryByteBuf, ParryAttributes> PACKET_CODEC;


    public static final ParryAttributes DEFAULT = new ParryAttributes(
            DEFAULT_ID, 0, 0.80F, 0.95F, 1.0F,
            18, 28, -0.90F, 7200,
            0, -1, true, 4.0, new HashMap<>()
            ).addToMap(DEFAULT_ID);


    public static ParryAttributes get(String id){
        if(REGISTERED_ATTRIBUTES.containsKey(id)){
            return REGISTERED_ATTRIBUTES.get(id);
        }

        FrycParry.LOGGER.error("Unable to get Parry Attributes with following id: " + id);
        return REGISTERED_ATTRIBUTES.get("default");
    }

    public static HashMap<String, ParryAttributes> getRegisteredAttributesCopy(){
        return new HashMap<>(REGISTERED_ATTRIBUTES);
    }

    protected ParryAttributes(String id, int parryTicks, float meleeDamageTakenAfterBlock,
                              float projectileDamageTakenAfterBlock, float explosionDamageTakenAfterBlock,
                              float cooldownAfterParryAction, float cooldownAfterInterruptingBlockAction, float cooldownAfterAttack,
                              int maxUseTime, int blockDelay, int explosionBlockDelay, boolean shouldStopUsingItemAfterBlockOrParry,
                              double knockbackAfterParryAction, Map<RegistryEntry<StatusEffect>, Quartet<Integer, Integer, Float, Float>> parryEffects){

        this.id = id;
        this.parryTicks = parryTicks;
        this.meleeDamageTakenAfterBlock = meleeDamageTakenAfterBlock;
        this.projectileDamageTakenAfterBlock = projectileDamageTakenAfterBlock;
        this.explosionDamageTakenAfterBlock = explosionDamageTakenAfterBlock;
        this.cooldownAfterParryAction = cooldownAfterParryAction;
        this.cooldownAfterInterruptingBlockAction = cooldownAfterInterruptingBlockAction;
        this.cooldownAfterAttack = cooldownAfterAttack;
        this.maxUseTime = maxUseTime;
        this.blockDelay = blockDelay;
        this.explosionBlockDelay = explosionBlockDelay;
        this.shouldStopUsingItemAfterBlockOrParry = shouldStopUsingItemAfterBlockOrParry;
        this.knockbackAfterParryAction = knockbackAfterParryAction;
        this.parryEffects = parryEffects;
    }

    protected ParryAttributes addToMap(String id){
        REGISTERED_ATTRIBUTES.put(id, this);

        return this;
    }

    public static ParryAttributes create(String id, int parryTicks, float meleeDamageTakenAfterBlock, float projectileDamageTakenAfterBlock, float explosionDamageTakenAfterBlock,
                                         float cooldownAfterParryAction, float cooldownAfterInterruptingBlockAction, float cooldownAfterAttack,
                                         int maxUseTime, int blockDelay, int explosionBlockDelay, boolean shouldStopUsingItemAfterBlockOrParry,
                                         double knockbackAfterParryAction, Map<RegistryEntry<StatusEffect>, Quartet<Integer, Integer, Float, Float>> parryEffects){

        return create(id, new ParryAttributes(id, parryTicks, meleeDamageTakenAfterBlock, projectileDamageTakenAfterBlock, explosionDamageTakenAfterBlock,
                cooldownAfterParryAction, cooldownAfterInterruptingBlockAction, cooldownAfterAttack, maxUseTime, blockDelay,
                explosionBlockDelay, shouldStopUsingItemAfterBlockOrParry, knockbackAfterParryAction, parryEffects));
    }

    public static ParryAttributes create(String id, ParryAttributes parryAttributes){
        return parryAttributes.addToMap(id);
    }


    public static ParryAttributes getDefaultParryAttributes(Item item){
        if(item instanceof PickaxeItem) return new ParryAttributes(
                PICKAXE_DEFAULT_ID, FrycParry.config.pickaxe.pickaxeParryTicks, (float)FrycParry.config.pickaxe.pickaxeBlockMeleeDamageTaken/100,
                (float)FrycParry.config.pickaxe.pickaxeBlockArrowDamageTaken/100, (float)FrycParry.config.pickaxe.explosionBlockDamageTaken/100,
                FrycParry.config.pickaxe.cooldownAfterPickaxeParryAction, FrycParry.config.pickaxe.cooldownAfterInterruptingPickaxeBlockAction,
                FrycParry.config.pickaxe.cooldownAfterAttack, FrycParry.config.pickaxe.maxUseTime, FrycParry.config.pickaxe.blockDelay,
                FrycParry.config.pickaxe.explosionBlockDelay, FrycParry.config.pickaxe.shouldStopUsingPickaxeAfterBlockOrParry,
                FrycParry.config.pickaxe.pickaxeParryKnockbackStrength, new HashMap<>(ConfigHelper.pickaxeParryEffects)
        ).addToMap(PICKAXE_DEFAULT_ID);
        if(item instanceof AxeItem) return new ParryAttributes(
                AXE_DEFAULT_ID, FrycParry.config.axe.axeParryTicks, (float)FrycParry.config.axe.axeBlockMeleeDamageTaken/100,
                (float)FrycParry.config.axe.axeBlockArrowDamageTaken/100, (float)FrycParry.config.axe.explosionBlockDamageTaken/100,
                FrycParry.config.axe.cooldownAfterAxeParryAction, FrycParry.config.axe.cooldownAfterInterruptingAxeBlockAction,
                FrycParry.config.axe.cooldownAfterAttack, FrycParry.config.axe.maxUseTime, FrycParry.config.axe.blockDelay,
                FrycParry.config.axe.explosionBlockDelay, FrycParry.config.axe.shouldStopUsingAxeAfterBlockOrParry,
                FrycParry.config.axe.axeParryKnockbackStrength, new HashMap<>(ConfigHelper.axeParryEffects)
        ).addToMap(AXE_DEFAULT_ID);
        if(item instanceof SwordItem) return new ParryAttributes(
                SWORD_DEFAULT_ID, FrycParry.config.sword.swordParryTicks, (float)FrycParry.config.sword.swordBlockMeleeDamageTaken/100,
                (float)FrycParry.config.sword.swordBlockArrowDamageTaken/100, (float)FrycParry.config.sword.explosionBlockDamageTaken/100,
                FrycParry.config.sword.cooldownAfterSwordParryAction, FrycParry.config.sword.cooldownAfterInterruptingSwordBlockAction,
                FrycParry.config.sword.cooldownAfterAttack, FrycParry.config.sword.maxUseTime, FrycParry.config.sword.blockDelay,
                FrycParry.config.sword.explosionBlockDelay, FrycParry.config.sword.shouldStopUsingSwordAfterBlockOrParry,
                FrycParry.config.sword.swordParryKnockbackStrength, new HashMap<>(ConfigHelper.swordParryEffects)
        ).addToMap(SWORD_DEFAULT_ID);
        if(item instanceof ShovelItem) return new ParryAttributes(
                SHOVEL_DEFAULT_ID, FrycParry.config.shovel.shovelParryTicks, (float)FrycParry.config.shovel.shovelBlockMeleeDamageTaken/100,
                (float)FrycParry.config.shovel.shovelBlockArrowDamageTaken/100, (float)FrycParry.config.shovel.explosionBlockDamageTaken/100,
                FrycParry.config.shovel.cooldownAfterShovelParryAction, FrycParry.config.shovel.cooldownAfterInterruptingShovelBlockAction,
                FrycParry.config.shovel.cooldownAfterAttack, FrycParry.config.shovel.maxUseTime, FrycParry.config.shovel.blockDelay,
                FrycParry.config.shovel.explosionBlockDelay, FrycParry.config.shovel.shouldStopUsingShovelAfterBlockOrParry,
                FrycParry.config.shovel.shovelParryKnockbackStrength, new HashMap<>(ConfigHelper.shovelParryEffects)
        ).addToMap(SHOVEL_DEFAULT_ID);
        if(item instanceof HoeItem) return new ParryAttributes(
                HOE_DEFAULT_ID, FrycParry.config.hoe.hoeParryTicks, (float)FrycParry.config.hoe.hoeBlockMeleeDamageTaken/100,
                (float)FrycParry.config.hoe.hoeBlockArrowDamageTaken/100, (float)FrycParry.config.hoe.explosionBlockDamageTaken/100,
                FrycParry.config.hoe.cooldownAfterHoeParryAction, FrycParry.config.hoe.cooldownAfterInterruptingHoeBlockAction,
                FrycParry.config.hoe.cooldownAfterAttack, FrycParry.config.hoe.maxUseTime, FrycParry.config.hoe.blockDelay,
                FrycParry.config.hoe.explosionBlockDelay, FrycParry.config.hoe.shouldStopUsingHoeAfterBlockOrParry,
                FrycParry.config.hoe.hoeParryKnockbackStrength, new HashMap<>(ConfigHelper.hoeParryEffects)
        ).addToMap(HOE_DEFAULT_ID);
        if(item instanceof ShieldItem) return new ParryAttributes(
                SHIELD_DEFAULT_ID, FrycParry.config.shield.shieldParryTicks, (float)FrycParry.config.shield.shieldBlockMeleeDamageTaken/100,
                (float)FrycParry.config.shield.shieldBlockArrowDamageTaken/100, (float)FrycParry.config.shield.explosionBlockDamageTaken/100,
                FrycParry.config.shield.cooldownAfterShieldParryAction, FrycParry.config.shield.cooldownAfterInterruptingShieldBlockAction,
                FrycParry.config.shield.cooldownAfterAttack, FrycParry.config.shield.maxUseTime, FrycParry.config.shield.blockDelay,
                FrycParry.config.shield.explosionBlockDelay, FrycParry.config.shield.shouldStopUsingShieldAfterBlockOrParry,
                FrycParry.config.shield.shieldParryKnockbackStrength, new HashMap<>(ConfigHelper.shieldParryEffects)
        ).addToMap(SHIELD_DEFAULT_ID);
        if(item instanceof MaceItem) return new ParryAttributes(
                MACE_DEFAULT_ID, FrycParry.config.mace.maceParryTicks, (float)FrycParry.config.mace.maceBlockMeleeDamageTaken/100,
                (float)FrycParry.config.mace.maceBlockArrowDamageTaken/100, (float)FrycParry.config.mace.explosionBlockDamageTaken/100,
                FrycParry.config.mace.cooldownAfterMaceParryAction, FrycParry.config.mace.cooldownAfterInterruptingMaceBlockAction,
                FrycParry.config.mace.cooldownAfterAttack, FrycParry.config.mace.maxUseTime, FrycParry.config.mace.blockDelay,
                FrycParry.config.mace.explosionBlockDelay, FrycParry.config.mace.shouldStopUsingMaceAfterBlockOrParry,
                FrycParry.config.mace.maceParryKnockbackStrength, new HashMap<>(ConfigHelper.maceParryEffects)
        ).addToMap(MACE_DEFAULT_ID);
        if(ParryHelper.hasAttackSpeedAttribute(item.getDefaultStack())) return new ParryAttributes(
                OTHER_DEFAULT_ID, FrycParry.config.server.parryTicks, (float)FrycParry.config.server.blockMeleeDamageTaken/100,
                (float)FrycParry.config.server.blockArrowDamageTaken/100, (float)FrycParry.config.server.explosionBlockDamageTaken/100,
                FrycParry.config.server.cooldownAfterParryAction, FrycParry.config.server.cooldownAfterInterruptingBlockAction,
                FrycParry.config.server.cooldownAfterAttack, FrycParry.config.server.maxUseTime, FrycParry.config.server.blockDelay,
                FrycParry.config.server.explosionBlockDelay, FrycParry.config.server.shouldStopUsingAfterBlockOrParry,
                FrycParry.config.server.parryKnockbackStrength, new HashMap<>(ConfigHelper.parryEffects)
        ).addToMap(OTHER_DEFAULT_ID);
        return ParryAttributes.DEFAULT;
    }


    //------------------------------------------------------------------------------------------------------------------

    public String getId(){
        return this.id;
    }

    public int getMaxUseTimeParry(){
        return this.maxUseTime;
    }
    public int getBlockDelay(){
        return this.blockDelay;
    }
    public int getExplosionBlockDelay(){
        return this.explosionBlockDelay;
    }

    public int getParryTicks(){
        return this.parryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return this.meleeDamageTakenAfterBlock;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return this.projectileDamageTakenAfterBlock;
    }
    public float getExplosionDamageTakenAfterBlock(){
        return this.explosionDamageTakenAfterBlock;
    }

    public float getCooldownAfterParryAction(){
        return this.cooldownAfterParryAction;
    }

    public float getCooldownAfterInterruptingBlockAction(){
        return this.cooldownAfterInterruptingBlockAction;
    }
    public float getCooldownAfterAttack(){
        return this.cooldownAfterAttack;
    }

    public double getKnockbackAfterParryAction(){
        return this.knockbackAfterParryAction;
    }

    public boolean shouldStopUsingItemAfterBlockOrParry(){
        return this.shouldStopUsingItemAfterBlockOrParry;
    }

    public HashMap<RegistryEntry<StatusEffect>, Quartet<Integer, Integer, Float, Float>> getParryEffectsCopy(){
        return new HashMap<>(this.parryEffects);
    }

    static {
        PACKET_CODEC = new PacketCodec<>() {
            @Override
            public ParryAttributes decode(RegistryByteBuf buf) {
                String id = PacketCodecs.STRING.decode(buf);

                int parryTicks = PacketCodecs.INTEGER.decode(buf);
                int maxUseTime = PacketCodecs.INTEGER.decode(buf);
                int blockDelay = PacketCodecs.INTEGER.decode(buf);
                int explosionBlockDelay = PacketCodecs.INTEGER.decode(buf);

                float meleeDamageTakenAfterBlock = PacketCodecs.FLOAT.decode(buf);
                float projectileDamageTakenAfterBlock = PacketCodecs.FLOAT.decode(buf);
                float explosionDamageTakenAfterBlock = PacketCodecs.FLOAT.decode(buf);
                float cooldownAfterParryAction = PacketCodecs.FLOAT.decode(buf);
                float cooldownAfterInterruptingBlockAction = PacketCodecs.FLOAT.decode(buf);
                float cooldownAfterAttack = PacketCodecs.FLOAT.decode(buf);

                boolean shouldStopUsingItemAfterBlockOrParry = PacketCodecs.BOOL.decode(buf);

                double knockbackAfterParryAction = PacketCodecs.DOUBLE.decode(buf);

                Map<RegistryEntry<StatusEffect>, Quartet<Integer, Integer, Float, Float>> parryEffects = PacketCodecs.map(
                        HashMap::new,
                        StatusEffect.ENTRY_PACKET_CODEC,
                        ModPackets.QUARTET_PACKET_CODEC
                ).decode(buf);


                return new ParryAttributes(
                        id,
                        parryTicks,
                        meleeDamageTakenAfterBlock,
                        projectileDamageTakenAfterBlock,
                        explosionDamageTakenAfterBlock,
                        cooldownAfterParryAction,
                        cooldownAfterInterruptingBlockAction,
                        cooldownAfterAttack,
                        maxUseTime,
                        blockDelay,
                        explosionBlockDelay,
                        shouldStopUsingItemAfterBlockOrParry,
                        knockbackAfterParryAction,
                        parryEffects
                );
            }

            @Override
            public void encode(RegistryByteBuf buf, ParryAttributes value) {
                PacketCodecs.STRING.encode(buf, value.getId());

                PacketCodecs.INTEGER.encode(buf, value.getParryTicks());
                PacketCodecs.INTEGER.encode(buf, value.getMaxUseTimeParry());
                PacketCodecs.INTEGER.encode(buf, value.getBlockDelay());
                PacketCodecs.INTEGER.encode(buf, value.getExplosionBlockDelay());

                PacketCodecs.FLOAT.encode(buf, value.getMeleeDamageTakenAfterBlock());
                PacketCodecs.FLOAT.encode(buf, value.getProjectileDamageTakenAfterBlock());
                PacketCodecs.FLOAT.encode(buf, value.getExplosionDamageTakenAfterBlock());
                PacketCodecs.FLOAT.encode(buf, value.getCooldownAfterParryAction());
                PacketCodecs.FLOAT.encode(buf, value.getCooldownAfterInterruptingBlockAction());
                PacketCodecs.FLOAT.encode(buf, value.getCooldownAfterAttack());

                PacketCodecs.BOOL.encode(buf, value.shouldStopUsingItemAfterBlockOrParry());

                PacketCodecs.DOUBLE.encode(buf, value.getKnockbackAfterParryAction());

                PacketCodecs.map(
                        HashMap::new,
                        StatusEffect.ENTRY_PACKET_CODEC,
                        ModPackets.QUARTET_PACKET_CODEC
                ).encode(buf, value.getParryEffectsCopy());
            }
        };
/*
        PACKET_WRITER = (buf, attr) -> {
            buf.writeString(attr.getId());

            buf.writeIntArray(new int[]{
                    attr.getParryTicks(),
                    attr.getMaxUseTimeParry(),
                    attr.getBlockDelay(),
                    attr.getExplosionBlockDelay()
            });

            buf.writeFloat(attr.getMeleeDamageTakenAfterBlock());
            buf.writeFloat(attr.getProjectileDamageTakenAfterBlock());
            buf.writeFloat(attr.getExplosionDamageTakenAfterBlock());
            buf.writeFloat(attr.getCooldownAfterParryAction());
            buf.writeFloat(attr.getCooldownAfterInterruptingBlockAction());
            buf.writeFloat(attr.getCooldownAfterAttack());

            buf.writeBoolean(attr.shouldStopUsingItemAfterBlockOrParry());

            buf.writeDouble(attr.getKnockbackAfterParryAction());

            buf.writeMap(attr.getParryEffectsCopy(), (buf2, effect) -> {
                buf2.writeIdentifier(Registries.STATUS_EFFECT.getId(effect));
            }, (buf3, quartet) -> {
                buf3.writeInt(quartet.getA());
                buf3.writeInt(quartet.getB());

                buf3.writeFloat(quartet.getC());
                buf3.writeFloat(quartet.getD());
            });
        };


        PACKET_READER = (buf) -> {
            String id = buf.readString();

            int[] ints = buf.readIntArray();
            int parryTicks = ints[0];
            int maxUseTime = ints[1];
            int blockDelay = ints[2];
            int explosionBlockDelay = ints[3];

            float meleeDamageTakenAfterBlock = buf.readFloat();
            float projectileDamageTakenAfterBlock = buf.readFloat();
            float explosionDamageTakenAfterBlock = buf.readFloat();
            float cooldownAfterParryAction = buf.readFloat();
            float cooldownAfterInterruptingBlockAction = buf.readFloat();
            float cooldownAfterAttack = buf.readFloat();

            boolean shouldStopUsingItemAfterBlockOrParry = buf.readBoolean();

            double knockbackAfterParryAction = buf.readDouble();

            Map<StatusEffect, Quartet<Integer, Integer, Float, Float>> parryEffects = buf.readMap(buf2 -> {
                return Registries.STATUS_EFFECT.get(buf2.readIdentifier());
            }, buf3 -> {
                return new Quartet<>(buf3.readInt(), buf3.readInt(), buf3.readFloat(), buf3.readFloat());
            });

            return new ParryAttributes(
                    id,
                    parryTicks,
                    meleeDamageTakenAfterBlock,
                    projectileDamageTakenAfterBlock,
                    explosionDamageTakenAfterBlock,
                    cooldownAfterParryAction,
                    cooldownAfterInterruptingBlockAction,
                    cooldownAfterAttack,
                    maxUseTime,
                    blockDelay,
                    explosionBlockDelay,
                    shouldStopUsingItemAfterBlockOrParry,
                    knockbackAfterParryAction,
                    parryEffects
            );
        };

         */
    }
}
