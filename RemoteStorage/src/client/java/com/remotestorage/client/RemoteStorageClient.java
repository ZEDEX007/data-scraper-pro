package com.remotestorage.client;

import com.remotestorage.RemoteStorageMod;
import com.remotestorage.client.gui.RemoteStorageScreen;
import com.remotestorage.network.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteStorageClient implements ClientModInitializer {
    public static final String MOD_ID = "remotestorage";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private static KeyBinding openKeybind;
    private static RemoteStorageScreen currentScreen;
    
    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Remote Storage client");
        
        openKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.remotestorage.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.remotestorage"
        ));
        
        registerPackets();
        
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
        
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ModPacketHandler.sendToServer(new CacheRequestPayload());
        });
        
        LOGGER.info("Remote Storage client initialized");
    }
    
    private void registerPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ContainerSyncPayload.PACKET_ID, this::handleContainerSync);
        ClientPlayNetworking.registerGlobalReceiver(ChunkStatusPayload.PACKET_ID, this::handleChunkStatus);
    }
    
    private void handleContainerSync(ClientPlayNetworking.Context context, ContainerSyncPayload payload) {
        // Handle container sync from server
        MinecraftClient.getInstance().execute(() -> {
            if (currentScreen != null) {
                currentScreen.handleContainerUpdate(payload);
            }
        });
    }
    
    private void handleChunkStatus(ClientPlayNetworking.Context context, ChunkStatusPayload payload) {
        // Handle chunk status update
        MinecraftClient.getInstance().execute(() -> {
            if (currentScreen != null) {
                currentScreen.handleChunkStatusUpdate(payload.containerId(), payload.loaded());
            }
        });
    }
    
    private void onClientTick(MinecraftClient client) {
        while (openKeybind.wasPressed()) {
            if (client.player != null && client.currentScreen == null) {
                client.setScreen(new RemoteStorageScreen());
            }
        }
    }
    
    public static KeyBinding getOpenKeybind() {
        return openKeybind;
    }
}
