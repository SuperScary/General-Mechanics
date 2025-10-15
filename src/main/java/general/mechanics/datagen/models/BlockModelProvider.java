package general.mechanics.datagen.models;

import general.mechanics.GM;
import general.mechanics.api.block.BlockDefinition;
import general.mechanics.api.block.base.DecorativeBlock;
import general.mechanics.api.block.base.OreBlock;
import general.mechanics.api.block.ice.IceBlock;
import general.mechanics.api.block.plastic.PlasticBlock;
import general.mechanics.registries.CoreBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.function.BiConsumer;

public class BlockModelProvider extends CoreBlockStateProvider {

    public static final ResourceLocation MACHINE_BOTTOM = GM.getResource("block/machine/machine_bottom");
    public static final ResourceLocation MACHINE_TOP = GM.getResource("block/machine/machine_top");
    public static final ResourceLocation MACHINE_SIDE = GM.getResource("block/machine/machine_side");

    public BlockModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, GM.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (var block : CoreBlocks.getBlocks()) {
            if (block.block() instanceof OreBlock || block.block() instanceof DecorativeBlock) {
                blockWithItem(block);
            } else if (block.block() instanceof IceBlock) {
                iceBlockWithItem(block);
            } else if (block.block() instanceof PlasticBlock) {
                plasticBlockWithItem(block);
            }
        }

        machine(CoreBlocks.MATTER_FABRICATOR);
    }

    private void blockWithItem(BlockDefinition<?> block) {
        err(List.of(block.id()));
        simpleBlockWithItem(block.block(), cubeAll(block.block()));
    }

    private void iceBlockWithItem(BlockDefinition<?> block) {
        err(List.of(block.id()));
        var model = models().cubeAll(block.id().getPath(), GM.getResource("block/" + block.id().getPath())).renderType("translucent").texture("particle", GM.getResource("block/" + block.id().getPath()));

        simpleBlockWithItem(block.block(), model);
    }

    private void plasticBlockWithItem(BlockDefinition<?> block) {
        err(List.of(block.id()));
        var model = models().cubeAll(block.id().getPath(), GM.getResource("block/plastic_block")).texture("particle", GM.getResource("block/plastic_block"))
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .face(Direction.NORTH).texture("#all").tintindex(0).cullface(Direction.NORTH).end()
                .face(Direction.EAST).texture("#all").tintindex(0).cullface(Direction.EAST).end()
                .face(Direction.SOUTH).texture("#all").tintindex(0).cullface(Direction.SOUTH).end()
                .face(Direction.WEST).texture("#all").tintindex(0).cullface(Direction.WEST).end()
                .face(Direction.UP).texture("#all").tintindex(0).cullface(Direction.UP).end()
                .face(Direction.DOWN).texture("#all").tintindex(0).cullface(Direction.DOWN).end()
                .end();
        simpleBlockWithItem(block.block(), model);
    }

    private void machine(BlockDefinition<?> block) {
        var on = modLoc("block/machine/" + block.id().getPath() + "/on");
        var off = modLoc("block/machine/" + block.id().getPath() + "/off");

        err(List.of(on, off));

        BlockModelBuilder modelOn = models().cube("block/machine/" + block.id().getPath() + "/" + block.id().getPath() + "_on", MACHINE_BOTTOM, MACHINE_TOP, on, MACHINE_SIDE, MACHINE_SIDE, MACHINE_SIDE).texture("particle", MACHINE_SIDE);
        BlockModelBuilder modelOff = models().cube("block/machine/" + block.id().getPath() + "/" + block.id().getPath() + "_off", MACHINE_BOTTOM, MACHINE_TOP, off, MACHINE_SIDE, MACHINE_SIDE, MACHINE_SIDE).texture("particle", MACHINE_SIDE);
        directionBlock(block.block(), (state, builder) -> builder.modelFile(state.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff));

        simpleBlockItem(block.block(), modelOn);
    }

    private VariantBlockStateBuilder directionBlock(Block block, BiConsumer<BlockState, ConfiguredModel.Builder<?>> model) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.forAllStates(state -> {
            ConfiguredModel.Builder<?> bld = ConfiguredModel.builder();
            model.accept(state, bld);
            applyRotationBld(bld, state.getValue(BlockStateProperties.FACING));
            return bld.build();
        });
        return builder;
    }

    private void applyRotationBld(ConfiguredModel.Builder<?> builder, Direction direction) {
        switch (direction) {
            case DOWN -> builder.rotationX(90);
            case UP -> builder.rotationX(-90);
            case NORTH -> {
            }
            case SOUTH -> builder.rotationY(180);
            case WEST -> builder.rotationY(270);
            case EAST -> builder.rotationY(90);
        }
    }

    /**
     * Ignores missing textures so we can still build data without the texture present.
     *
     * @param list a list of ResourceLocations that we know will exist but currently don't.
     */
    private void err(List<ResourceLocation> list) {
        for (var res : list) {
            existingFileHelper.trackGenerated(res, PackType.CLIENT_RESOURCES, ".png", "textures");
        }
    }

}
