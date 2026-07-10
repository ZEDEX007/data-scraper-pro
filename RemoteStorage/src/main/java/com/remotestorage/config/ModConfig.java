package com.remotestorage.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("remote_storage.json");
    
    @SerializedName("hotkey")
    private String hotkey = "R";
    
    @SerializedName("search_distance")
    private int searchDistance = 64;
    
    @SerializedName("cache_enabled")
    private boolean cacheEnabled = true;
    
    @SerializedName("gui_animations")
    private boolean guiAnimations = true;
    
    @SerializedName("dark_theme")
    private boolean darkTheme = true;
    
    public String getHotkey() {
        return hotkey;
    }
    
    public void setHotkey(String hotkey) {
        this.hotkey = hotkey;
    }
    
    public int getSearchDistance() {
        return searchDistance;
    }
    
    public void setSearchDistance(int searchDistance) {
        this.searchDistance = searchDistance;
    }
    
    public boolean isCacheEnabled() {
        return cacheEnabled;
    }
    
    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }
    
    public boolean isGuiAnimations() {
        return guiAnimations;
    }
    
    public void setGuiAnimations(boolean guiAnimations) {
        this.guiAnimations = guiAnimations;
    }
    
    public boolean isDarkTheme() {
        return darkTheme;
    }
    
    public void setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
    }
    
    public static ModConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                return GSON.fromJson(reader, ModConfig.class);
            } catch (IOException e) {
                ModConfig.LOGGER.error("Failed to load config", e);
            }
        }
        
        ModConfig config = new ModConfig();
        config.save();
        return config;
    }
    
    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            ModConfig.LOGGER.error("Failed to save config", e);
        }
    }
    
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ModConfig.class);
}
