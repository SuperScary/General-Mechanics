package dimensional.core.datagen.models;

import dimensional.core.DimensionalCore;
import dimensional.core.api.item.ItemDefinition;
import dimensional.core.api.model.ICustomModel;
import dimensional.core.api.util.IDataProvider;
import dimensional.core.registries.CoreItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider implements IDataProvider {

    public ModItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, DimensionalCore.MODID, existingFileHelper);
    }

    private static ResourceLocation makeId (String id) {
        return id.contains(":") ? ResourceLocation.parse(id) : DimensionalCore.getResource(id);
    }

    @Override
    protected void registerModels () {
        for (var item : CoreItems.getItems()) {
            if (!(item instanceof ICustomModel)) {
                handheldItem(item);
            }
        }
    }

    public ItemModelBuilder handheldItem (ItemDefinition<?> item) {
        return handheldItem(item.asItem());
    }

}
