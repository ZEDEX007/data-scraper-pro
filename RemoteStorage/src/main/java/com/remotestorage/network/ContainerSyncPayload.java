package com.remotestorage.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ContainerSyncPayload(int slot, ItemStack stack, int containerId) implements CustomPayload {
    public static final Id<ContainerSyncPayload> PACKET_ID = new Id<>(Identifier.of("remotestorage", "container_sync"));
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
    
    public static final PacketCodec<RegistryByteBuf, ContainerSyncPayload> CODEC = PacketCodec.tuple(
            PacketCodec.VAR_INT, ContainerSyncPayload::containerId,
            PacketCodec.VAR_INT, ContainerSyncPayload::slot,
            ItemStack.PACKET_CODEC, ContainerSyncPayload::stack,
            ContainerSyncPayload::new
    );
}
