package fluxmachines.core.gui.screen;

import fluxmachines.core.FluxMachines;
import fluxmachines.core.gui.menu.MatterFabricatorMenu;
import fluxmachines.core.gui.screen.base.BaseScreen;
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
        return FluxMachines.getResource("textures/gui/matter_fabricator.png");
    }

    @Override
    public void addAdditionalTabElements(GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {

    }
}
