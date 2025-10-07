package dimensional.core.gui.screen;

import dimensional.core.DimensionalCore;
import dimensional.core.gui.menu.MatterFabricatorMenu;
import dimensional.core.gui.screen.base.BaseScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MatterFabricatorScreen extends BaseScreen<MatterFabricatorMenu> {

    public MatterFabricatorScreen(MatterFabricatorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public ResourceLocation getGuiTexture () {
        return DimensionalCore.getResource("textures/gui/matter_fabricator.png");
    }

    @Override
    public void addAdditionalTabElements(GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {

    }
}
