package com.remotestorage.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ChunkStatusPayload(int containerId, boolean loaded) implements CustomPayload {
    public static final Id<ChunkStatusPayload> PACKET_ID = new Id<>(Identifier.of("remotestorage", "chunk_status"));
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
    
    public static final PacketCodec<RegistryByteBuf, ChunkStatusPayload> CODEC = PacketCodec.tuple(
            PacketCodec.VAR_INT, ChunkStatusPayload::containerId,
            PacketCodec.BOOL, ChunkStatusPayload::loaded,
            ChunkStatusPayload::new
    );
}
