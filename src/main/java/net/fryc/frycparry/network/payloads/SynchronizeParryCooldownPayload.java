package net.fryc.frycparry.network.payloads;

import net.fryc.frycparry.network.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record SynchronizeParryCooldownPayload(int cooldown) implements CustomPayload {

    public static final Id<SynchronizeParryCooldownPayload> ID = new Id<>(ModPackets.SYNCHRONIZE_PARRY_COOLDOWN_ID);

    public static final PacketCodec<RegistryByteBuf, SynchronizeParryCooldownPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, SynchronizeParryCooldownPayload::cooldown,
            SynchronizeParryCooldownPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
