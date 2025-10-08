package fluxmachines.core.datagen.models;

import fluxmachines.core.FluxMachines;
import fluxmachines.core.api.item.ItemDefinition;
import fluxmachines.core.api.item.base.ElectricalComponent;
import fluxmachines.core.api.item.plastic.PlasticItem;
import fluxmachines.core.api.item.plastic.RawPlasticItem;
import fluxmachines.core.api.util.IDataProvider;
import fluxmachines.core.registries.CoreItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CoreItemModelProvider extends ItemModelProvider implements IDataProvider {

    public CoreItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, FluxMachines.MODID, existingFileHelper);
    }

    private static ResourceLocation makeId (String id) {
        return id.contains(":") ? ResourceLocation.parse(id) : FluxMachines.getResource(id);
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
        return this.getBuilder(item.id().getPath()).parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", FluxMachines.getResource("item/raw_plastic"));
    }

    public ItemModelBuilder electricalComponentItem (ItemDefinition<?> item) {
        return this.getBuilder(item.id().getPath()).parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", FluxMachines.getResource("item/components/" + item.id().getPath()));
    }

    public ItemModelBuilder plasticItem (ItemDefinition<?> item) {
        return this.getBuilder(item.id().getPath()).parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", FluxMachines.getResource("item/plastic"));
    }

}
