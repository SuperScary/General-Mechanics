package general.mechanics.datagen.models;

import general.mechanics.GM;
import general.mechanics.api.item.ItemDefinition;
import general.mechanics.api.item.base.ElectricalComponent;
import general.mechanics.api.item.element.metallic.ElementItem;
import general.mechanics.api.item.plastic.*;
import general.mechanics.api.util.IDataProvider;
import general.mechanics.registries.CoreElements;
import general.mechanics.registries.CoreItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;

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

            if (item.asItem() instanceof ElectricalComponent) {
                electricalComponentItem(item);
                continue;
            }

            if (item.asItem() instanceof PlasticTypeItem p) {
                plasticItem(item, p.getPlasticType().getDisplayName().toLowerCase());
                continue;
            }

            if (item.asItem() instanceof ColoredPlasticItem p) {
                plasticItem(item, p.getParentPlastic().getPlasticType().getDisplayName().toLowerCase());
                continue;
            }

            handheldItem(item);
        }

        for (var item : CoreElements.getElements()) {
            if (item.asItem() instanceof ElementItem) {
                element((ItemDefinition<ElementItem>) item);
            }
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

    public ItemModelBuilder plasticItem (ItemDefinition<?> item, String parentName) {
        var resource = GM.getResource("item/plastic/" + parentName.toLowerCase().replace(' ', '_'));
        existingFileHelper.trackGenerated(resource, PackType.CLIENT_RESOURCES, ".png", "textures");

        return this.getBuilder(item.id().getPath()).parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", resource);
    }

    public ItemModelBuilder element (ItemDefinition<ElementItem> item) {
        var element = item.get();
        var nugget = element.getNuggetItem();
        var raw = element.getRawItem();
        var dust = element.getDustItem();
        var plate = element.getPlateItem();

        this.getBuilder(nugget.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", GM.getResource("item/ingot/nugget"));
        this.getBuilder(raw.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", GM.getResource("item/ingot/raw_ore"));
        this.getBuilder(dust.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", GM.getResource("item/ingot/dust"));
        this.getBuilder(plate.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", GM.getResource("item/ingot/plate"));

        return this.getBuilder(item.id().getPath()).parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", GM.getResource("item/ingot/ingot"));
    }

    public static boolean textureExists (ResourceLocation texture, ExistingFileHelper existingFileHelper) {
        return existingFileHelper.exists(texture, PackType.CLIENT_RESOURCES);
    }

}
