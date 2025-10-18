package general.mechanics;

import com.mojang.blaze3d.systems.RenderSystem;
import general.mechanics.api.block.plastic.ColoredPlasticBlock;
import general.mechanics.api.block.plastic.PlasticTypeBlock;
import general.mechanics.api.item.element.metallic.*;
import general.mechanics.api.item.plastic.ColoredPlasticItem;
import general.mechanics.api.item.plastic.PlasticTypeItem;
import general.mechanics.api.item.tools.ToolItem;
import general.mechanics.gui.screen.MatterFabricatorScreen;
import general.mechanics.registries.CoreBlocks;
import general.mechanics.registries.CoreElements;
import general.mechanics.registries.CoreItems;
import general.mechanics.registries.CoreMenus;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.IItemDecorator;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class CoreClient extends CoreBase {

    private static final IItemDecorator DURABILITY_GRADIENT;

    public CoreClient(ModContainer container, IEventBus modEventBus) {
        super(container, modEventBus);
        modEventBus.addListener(this::registerItemColors);
        modEventBus.addListener(this::registerBlockColors);
        modEventBus.addListener(this::registerElementColors);
        modEventBus.addListener(this::registerMenuScreens);
        modEventBus.addListener(this::registerDecorators);
    }

    public void registerItemColors(RegisterColorHandlersEvent.Item event) {
        for (var item : CoreItems.getItems()) {
            if (item.get() instanceof ColoredPlasticItem coloredPlastic) {
                event.register(ColoredPlasticItem::getColor, coloredPlastic);
            } else if (item.get() instanceof PlasticTypeItem plasticTypeItem) {
                event.register(PlasticTypeItem::getColor, plasticTypeItem);
            }
        }

        for (var block : CoreBlocks.getAllColoredPlasticBlocks()) {
            event.register(ColoredPlasticBlock::getColorForItemStack, block);
        }

        for (var block : CoreBlocks.getAllPlasticTypeBlocks()) {
            event.register(PlasticTypeBlock::getColorForItemStack, block);
        }
    }

    public void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        for (var block : CoreBlocks.getAllColoredPlasticBlocks()) {
            event.register(ColoredPlasticBlock::getColor, block);
        }

        for (var block : CoreBlocks.getAllPlasticTypeBlocks()) {
            event.register(PlasticTypeBlock::getColor, block);
        }
    }

    public void registerElementColors(RegisterColorHandlersEvent.Item event) {
        for (var itemDef : CoreElements.getElements()) {
            var item = itemDef.get();
            if (item instanceof ElementItem) {
                event.register(ElementItem::getColor, item);
            } else if (item instanceof ElementRawItem) {
                event.register(ElementRawItem::getColor, item);
            } else if (item instanceof ElementNuggetItem) {
                event.register(ElementNuggetItem::getColor, item);
            } else if (item instanceof ElementDustItem) {
                event.register(ElementDustItem::getColor, item);
            } else if (item instanceof ElementPlateItem) {
                event.register(ElementPlateItem::getColor, item);
            } else if (item instanceof ElementPileItem) {
                event.register(ElementPileItem::getColor, item);
            } else if (item instanceof ElementRodItem) {
                event.register(ElementRodItem::getColor, item);
            }
        }
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(CoreMenus.MATTER_FABRICATOR_MENU.get(), MatterFabricatorScreen::new);
    }

    public void registerDecorators(RegisterItemDecorationsEvent event) {
        for (var item : CoreItems.getItems()) {
            if (item.get() instanceof ToolItem toolItem) {
                event.register(toolItem, DURABILITY_GRADIENT);
            }
        }
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

    static {
        DURABILITY_GRADIENT = (g, font, stack, x, y) -> {
            if (!stack.isDamageableItem()) return false;

            final int BAR_W = 13, BAR_H = 1;
            final int barX = x + 2, barY = y + 13;

            final int max = stack.getMaxDamage();
            final int used = stack.getDamageValue();
            final float pct = max > 0 ? (float) (max - used) / max : 0f;
            final int filled = Mth.clamp(Math.round(pct * BAR_W), 0, BAR_W);

            // ---- Draw ABOVE the item sprite ----
            var pose = g.pose();
            pose.pushPose();
            // Push it “toward the camera” so it renders on top of the item
            pose.translate(0, 0, 200);

            // You can also bypass depth just to be safe
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            // Outline + background
            /*g.fill(barX - 1, barY - 1, barX + BAR_W + 1, barY + BAR_H + 1, 0xFF000000);*/
            g.fill(barX, barY, barX + BAR_W, barY + BAR_H, 0xFF1A1A1A);

            // Gradient fill (red -> green)
            for (int i = 0; i < filled; i++) {
                float t = i / (float) (BAR_W - 1);
                int rgb = Mth.hsvToRgb(t * (1f / 3f), 1f, 1f);
                g.fill(barX + i, barY, barX + i + 1, barY + BAR_H, 0xFF000000 | rgb);
            }

            if (filled < BAR_W) {
                g.fill(barX + filled, barY, barX + BAR_W, barY + BAR_H, 0xFF2B2B2B);
            }

            // Restore state
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
            pose.popPose();

            return false;
        };
    }
}
