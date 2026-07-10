package com.remotestorage.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModPacketHandler {
    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(ContainerSyncPayload.PACKET_ID, ContainerSyncPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ChunkStatusPayload.PACKET_ID, ChunkStatusPayload.CODEC);
    }
    
    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(ItemTransferPayload.PACKET_ID, ItemTransferPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(CacheRequestPayload.PACKET_ID, CacheRequestPayload.CODEC);
    }
    
    public static void sendToClient(ServerPlayerEntity player, ContainerSyncPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }
    
    public static void sendToClient(ServerPlayerEntity player, ChunkStatusPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }
    
    public static void sendToServer(CacheRequestPayload payload) {
        ClientPlayNetworking.send(payload);
    }
    
    public static void sendToServer(ItemTransferPayload payload) {
        ClientPlayNetworking.send(payload);
    }
}
