package com.remotestorage;

import com.remotestorage.config.ModConfig;
import com.remotestorage.storage.ContainerRegistry;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteStorageMod implements ModInitializer {
    public static final String MOD_ID = "remotestorage";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private static ModConfig config;
    private static ContainerRegistry containerRegistry;
    
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Remote Storage mod");
        
        config = ModConfig.load();
        containerRegistry = new ContainerRegistry();
        
        LOGGER.info("Remote Storage initialized successfully");
    }
    
    public static ModConfig getConfig() {
        return config;
    }
    
    public static ContainerRegistry getContainerRegistry() {
        return containerRegistry;
    }
}
