package general.mechanics.datagen.models;

import general.mechanics.GM;
import general.mechanics.api.item.ItemDefinition;
import general.mechanics.api.item.base.ElectricalComponent;
import general.mechanics.api.item.plastic.PlasticItem;
import general.mechanics.api.item.plastic.RawPlasticItem;
import general.mechanics.api.util.IDataProvider;
import general.mechanics.registries.CoreItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CoreItemModelProvider extends ItemModelProvider implements IDataProvider {

    public CoreItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, GM.MODID, existingFileHelper);
    }

    private static ResourceLocation makeId (String id) {
        return id.contains(":") ? ResourceLocation.parse(id) : GM.getResource(id);
    }

    @Override
    protected void registerModels () {
        for (var item : CoreItems.getItems()) {
            if (item.asItem() instanceof RawPlasticItem) {
                rawPlasticItem(item);
                continue;
            }

            if (item.asItem() instanceof PlasticItem) {
                plasticItem(item);
                continue;
            }

            if (item.asItem() instanceof ElectricalComponent) {
                electricalComponentItem(item);
                continue;
            }

            handheldItem(item);
        }
    }

    public ItemModelBuilder handheldItem (ItemDefinition<?> item) {
        return handheldItem(item.asItem());
    }

    public ItemModelBuilder rawPlasticItem (ItemDefinition<?> item) {
        return this.getBuilder(item.id().getPath()).parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", GM.getResource("item/raw_plastic"));
    }

    public ItemModelBuilder electricalComponentItem (ItemDefinition<?> item) {
        return this.getBuilder(item.id().getPath()).parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", GM.getResource("item/components/" + item.id().getPath()));
    }

    public ItemModelBuilder plasticItem (ItemDefinition<?> item) {
        return this.getBuilder(item.id().getPath()).parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", GM.getResource("item/plastic"));
    }

}
