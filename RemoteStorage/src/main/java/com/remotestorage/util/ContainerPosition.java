package com.remotestorage.util;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class ContainerPosition {
    private final RegistryKey<World> worldKey;
    private final BlockPos pos;
    
    public ContainerPosition(RegistryKey<World> worldKey, BlockPos pos) {
        this.worldKey = worldKey;
        this.pos = pos.toImmutable();
    }
    
    public RegistryKey<World> getWorldKey() {
        return worldKey;
    }
    
    public BlockPos getPos() {
        return pos;
    }
    
    public int getX() {
        return pos.getX();
    }
    
    public int getY() {
        return pos.getY();
    }
    
    public int getZ() {
        return pos.getZ();
    }
    
    public String getDimensionName() {
        return worldKey.getValue().toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContainerPosition that = (ContainerPosition) o;
        return Objects.equals(worldKey, that.worldKey) && Objects.equals(pos, that.pos);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(worldKey, pos);
    }
    
    @Override
    public String toString() {
        return "ContainerPosition{" +
                "world=" + getDimensionName() +
                ", pos=" + pos.toShortString() +
                '}';
    }
}
