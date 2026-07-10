package com.remotestorage.client.gui;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemListWidget implements Element, Selectable {
    private final RemoteStorageScreen screen;
    private final int x, y, width, height;
    private final List<ItemSlot> slots = new ArrayList<>();
    
    public ItemListWidget(RemoteStorageScreen screen, int x, int y, int width, int height) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        // Create slots for items (5 columns)
        int slotSize = 50;
        int cols = width / slotSize;
        int rows = height / slotSize;
        
        for (int i = 0; i < cols * rows; i++) {
            slots.add(new ItemSlot(
                    x + (i % cols) * slotSize,
                    y + (i / cols) * slotSize,
                    slotSize - 2
            ));
        }
    }
    
    public void render(net.minecraft.client.gui.DrawContext context, int mouseX, int mouseY, float delta) {
        var filteredItems = screen.getFilteredItems();
        int offset = screen.getScrollbarOffset();
        
        for (int i = 0; i < slots.size() && i + offset < filteredItems.size(); i++) {
            var slot = slots.get(i);
            var entry = filteredItems.get(i + offset);
            slot.render(context, entry.stack(), entry.count());
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int offset = screen.getScrollbarOffset();
        var filteredItems = screen.getFilteredItems();
        
        for (int i = 0; i < slots.size() && i + offset < filteredItems.size(); i++) {
            var slot = slots.get(i);
            if (slot.isMouseOver(mouseX, mouseY)) {
                // Handle item click - withdraw/deposit logic would go here
                return true;
            }
        }
        return false;
    }
    
    @Override
    public SelectionType getType() {
        return SelectionType.NONE;
    }
    
    @Override
    public Optional<Element> hoveredElement(double mouseX, double mouseY) {
        return Optional.empty();
    }
    
    @Override
    public boolean changeFocus(boolean lookForwards) {
        return false;
    }
    
    @Override
    public List<? extends Element> children() {
        return List.of();
    }
    
    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {}
    
    private static class ItemSlot {
        private final int x, y, size;
        
        public ItemSlot(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }
        
        public void render(net.minecraft.client.gui.DrawContext context, net.minecraft.item.ItemStack stack, int count) {
            context.drawItem(stack, x + 16, y + 16);
            
            // Render count
            if (count > 1) {
                context.drawTextWithShadow(
                        net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                        String.valueOf(count),
                        x + size - 5,
                        y + size - 8,
                        0xFFFFFF
                );
            }
        }
        
        public boolean isMouseOver(double mouseX, double mouseY) {
            return mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
        }
    }
}
