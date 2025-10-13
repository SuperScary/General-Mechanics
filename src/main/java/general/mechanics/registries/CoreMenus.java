package general.mechanics.registries;

import general.mechanics.GM;
import general.mechanics.gui.menu.MatterFabricatorMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CoreMenus {

    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, GM.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<MatterFabricatorMenu>> MATTER_FABRICATOR_MENU = register("matter_fabricator_menu", MatterFabricatorMenu::new);

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register (String name, IContainerFactory<T> factory) {
        return REGISTRY.register(name, () -> IMenuTypeExtension.create(factory));
    }

}
