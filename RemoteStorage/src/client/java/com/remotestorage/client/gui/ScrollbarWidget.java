package com.remotestorage.client.gui;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;

import java.util.List;
import java.util.Optional;

public class ScrollbarWidget implements Element, Selectable {
    private final int x, y, width, height;
    private int maxScroll = 0;
    private int currentOffset = 0;
    private boolean dragging = false;
    
    public ScrollbarWidget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void setMaxScroll(int maxScroll) {
        this.maxScroll = maxScroll;
        if (currentOffset > maxScroll) {
            currentOffset = maxScroll;
        }
    }
    
    public int getOffset() {
        return currentOffset;
    }
    
    public void render(net.minecraft.client.gui.DrawContext context, int mouseX, int mouseY, float delta) {
        // Draw scrollbar background
        context.fill(x, y, x + width, y + height, 0x40000000);
        
        // Calculate thumb position and size
        int thumbHeight = Math.max(20, height / Math.max(1, maxScroll + 10));
        int thumbY = y + (maxScroll > 0 ? (currentOffset * (height - thumbHeight)) / maxScroll : 0);
        
        // Draw thumb
        int thumbColor = dragging ? 0xFFAAAAAA : 0x80808080;
        context.fill(x + 2, thumbY, x + width - 2, thumbY + thumbHeight, thumbColor);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && isMouseOver(mouseX, mouseY)) {
            dragging = true;
            updateScroll(mouseY);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = false;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging) {
            updateScroll(mouseY);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (isMouseOver(mouseX, mouseY)) {
            currentOffset = Math.max(0, Math.min(maxScroll, currentOffset - (int)verticalAmount));
            return true;
        }
        return false;
    }
    
    private void updateScroll(double mouseY) {
        int thumbHeight = Math.max(20, height / Math.max(1, maxScroll + 10));
        int availableHeight = height - thumbHeight;
        if (availableHeight > 0) {
            double ratio = (mouseY - y - thumbHeight / 2.0) / availableHeight;
            currentOffset = (int)(Math.max(0, Math.min(1, ratio)) * maxScroll);
        }
    }
    
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
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
}
