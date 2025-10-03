package dimensional.core.datagen.models;

import com.google.gson.JsonPrimitive;
import dimensional.core.DimensionalCore;
import dimensional.core.api.block.BlockDefinition;
import dimensional.core.api.util.IDataProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public abstract class CoreBlockStateProvider extends BlockStateProvider implements IDataProvider {

    private static final VariantProperty<VariantProperties.Rotation> Z_ROT = new VariantProperty<>(DimensionalCore.MODID + ":z", r -> new JsonPrimitive(r.ordinal() * 90));
    public ExistingFileHelper existingFileHelper;

    public CoreBlockStateProvider(PackOutput packOutput, String modid, ExistingFileHelper existingFileHelper) {
        super(packOutput, modid, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    protected static PropertyDispatch createFacingDispatch (int baseRotX, int baseRotY) {
        return PropertyDispatch.property(BlockStateProperties.FACING)
                .select(Direction.DOWN, applyRotation(Variant.variant(), baseRotX + 90, baseRotY, 0))
                .select(Direction.UP, applyRotation(Variant.variant(), baseRotX + 270, baseRotY, 0))
                .select(Direction.NORTH, applyRotation(Variant.variant(), baseRotX, baseRotY, 0))
                .select(Direction.SOUTH, applyRotation(Variant.variant(), baseRotX, baseRotY + 180, 0))
                .select(Direction.WEST, applyRotation(Variant.variant(), baseRotX, baseRotY + 270, 0))
                .select(Direction.EAST, applyRotation(Variant.variant(), baseRotX, baseRotY + 90, 0));
    }

    protected static Variant applyRotation (Variant variant, int angleX, int angleY, int angleZ) {
        angleX = normalizeAngle(angleX);
        angleY = normalizeAngle(angleY);
        angleZ = normalizeAngle(angleZ);

        if (angleX != 0) {
            variant = variant.with(VariantProperties.X_ROT, rotationByAngle(angleX));
        }
        if (angleY != 0) {
            variant = variant.with(VariantProperties.Y_ROT, rotationByAngle(angleY));
        }
        if (angleZ != 0) {
            variant = variant.with(Z_ROT, rotationByAngle(angleZ));
        }
        return variant;
    }

    private static int normalizeAngle (int angle) {
        return angle - (angle / 360) * 360;
    }

    private static VariantProperties.Rotation rotationByAngle (int angle) {
        return switch (angle) {
            case 0 -> VariantProperties.Rotation.R0;
            case 90 -> VariantProperties.Rotation.R90;
            case 180 -> VariantProperties.Rotation.R180;
            case 270 -> VariantProperties.Rotation.R270;
            default -> throw new IllegalArgumentException("Invalid angle: " + angle);
        };
    }

    private static <T extends Comparable<T>> Condition addConditionTerm (Condition.TerminalCondition condition,
                                                                         BlockState blockState,
                                                                         Property<T> property) {
        return condition.term(property, blockState.getValue(property));
    }

    protected void simpleBlockAndItem (BlockDefinition<?> block) {
        var model = cubeAll(block.block());
        simpleBlock(block.block(), model);
        simpleBlockItem(block.block(), model);
    }

    protected void simpleBlockAndItem (BlockDefinition<?> block, ModelFile model) {
        simpleBlock(block.block(), model);
        simpleBlockItem(block.block(), model);
    }

    protected void simpleBlockAndItem (BlockDefinition<?> block, String textureName) {
        var model = models().cubeAll(block.id().getPath(), DimensionalCore.getResource(textureName));
        simpleBlock(block.block(), model);
        simpleBlockItem(block.block(), model);
    }

    @Override
    public @NotNull String getName() {
        return super.getName() + " " + getClass().getName();
    }
}
