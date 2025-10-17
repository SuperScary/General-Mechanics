package general.mechanics.api.multiblock;

import general.mechanics.api.multiblock.base.Multiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public final class MultiblockDefinition {
    private final ResourceLocation id;
    private final Layout layout;
    private final Supplier<Multiblock> objectFactory;

    public MultiblockDefinition(ResourceLocation id, Layout layout) {
        this(id, layout, null);
    }

    public MultiblockDefinition(ResourceLocation id, Layout layout, Supplier<Multiblock> objectFactory) {
        this.id = Objects.requireNonNull(id, "id");
        this.layout = Objects.requireNonNull(layout, "layout");
        this.objectFactory = objectFactory;
    }

    public ResourceLocation id() {
        return id;
    }

    public Layout layout() {
        return layout;
    }

    public Optional<MultiblockValidator.ValidationResult> findMatch(Level level, BlockPos anchorPos) {
        if (objectFactory != null) {
            Multiblock obj = objectFactory.get();
            Map<net.minecraft.world.level.block.Block, Integer> exact = obj.getRequiredExactBlocks();
            Map<net.minecraft.world.level.block.Block, Integer> minimum = obj.getRequiredMinimumBlocks();
            if (!exact.isEmpty() || !minimum.isEmpty()) {
                return MultiblockValidator.findMatch(layout, level, anchorPos, exact, minimum);
            }
        }
        return MultiblockValidator.findMatch(layout, level, anchorPos);
    }

    public Optional<MultiblockValidator.ValidationResult> findMatch(Level level,
                                                                    BlockPos anchorPos,
                                                                    EnumSet<Direction> facings,
                                                                    boolean includeMirror) {
        if (objectFactory != null) {
            Multiblock obj = objectFactory.get();
            Map<net.minecraft.world.level.block.Block, Integer> exact = obj.getRequiredExactBlocks();
            Map<net.minecraft.world.level.block.Block, Integer> minimum = obj.getRequiredMinimumBlocks();
            if (!exact.isEmpty() || !minimum.isEmpty()) {
                return MultiblockValidator.findMatch(layout, level, anchorPos, facings, includeMirror, exact, minimum);
            }
        }
        return MultiblockValidator.findMatch(layout, level, anchorPos, facings, includeMirror);
    }

    public MultiblockValidator.ValidationResult validateAt(Level level, BlockPos anchorPos, Direction facing, boolean mirrored) {
        if (objectFactory != null) {
            Multiblock obj = objectFactory.get();
            Map<net.minecraft.world.level.block.Block, Integer> exact = obj.getRequiredExactBlocks();
            Map<net.minecraft.world.level.block.Block, Integer> minimum = obj.getRequiredMinimumBlocks();
            if (!exact.isEmpty() || !minimum.isEmpty()) {
                return MultiblockValidator.validateAt(layout, level, anchorPos, facing, mirrored, exact, minimum);
            }
        }
        return MultiblockValidator.validateAt(layout, level, anchorPos, facing, mirrored);
    }

    /**
     * Creates a multiblock object for this definition.
     * 
     * @return A new multiblock object, or null if this definition doesn't create objects
     */
    public Multiblock createObject() {
        return objectFactory != null ? objectFactory.get() : null;
    }

    /**
     * Checks if this definition creates multiblock objects.
     * 
     * @return true if this definition creates objects, false otherwise
     */
    public boolean hasObject() {
        return objectFactory != null;
    }

    @Override
    public String toString() {
        return "MultiblockDefinition{" +
            "id=" + id +
            ", layout=" + layout +
            ", hasObject=" + hasObject() +
            '}';
    }
}
