package fluxmachines.core.gui.renderers;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import fluxmachines.core.api.item.ItemDefinition;
import fluxmachines.core.api.upgrade.UpgradeBase;
import fluxmachines.core.registries.CoreUpgrades;
import fluxmachines.core.util.MouseUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UpgradeSlotTooltipArea {

    private final int startX, startY, xSize, ySize, count, space;
    private final Block key;

    public UpgradeSlotTooltipArea (int startX, int startY, int xSize, int ySize, int count, int space, Block key) {
        this.startX = startX;
        this.startY = startY;
        this.xSize = xSize;
        this.ySize = ySize;
        this.count = count;
        this.space = space;
        this.key = key;
    }

    private ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> getUpgrades (Block key) {
        return CoreUpgrades.getCompatibleUpgrades(key);
    }

    private List<Component> getTooltips () {
        var tooltip = new ArrayList<Component>();
        var upgrades = getUpgrades(key);
        tooltip.add(Component.translatable("gui.fluxmachines.upgrade_tooltip"));

        for (var upgrade : upgrades) {
            tooltip.add(Component.translatable("gui.fluxmachines.upgrade_tooltip.item", Component.translatable(upgrade.getFirst().asItem().getDescriptionId()), upgrade.getSecond()));
        }

        return tooltip;
    }

    public void render (Font font, GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        if (isMouseWithinArea(mouseX, mouseY, x, y)) {
            graphics.renderTooltip(font, getTooltips(), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    private boolean areaIsValid () {
        return xSize > 0 && ySize > 0 && count > 0 && !getUpgrades(key).isEmpty();
    }

    private boolean isMouseWithinArea (int mouseX, int mouseY, int guiX, int guiY) {
        if (!areaIsValid()) return false;

        for (int i = 0; i < count; i++) {
            int offsetY = startY + i * (ySize + space);

            if (isMouseAboveArea(mouseX, mouseY, guiX, guiY, startX, offsetY, xSize, ySize)) {
                return true;
            }
        }

        return false;
    }

    private boolean isMouseAboveArea (int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

}
