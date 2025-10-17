package general.mechanics.api.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * Validates a {@link Layout} against blocks in the world starting from an anchor position.
 * Assumes the base (unrotated) layout faces SOUTH and supports horizontal rotation and optional mirroring.
 */
public final class MultiblockValidator {

    private MultiblockValidator() {}

    /**
     * Tries all 8 combinations (4 horizontal facings x mirrored/not) and returns the first success.
     */
    public static Optional<ValidationResult> findMatch(Layout layout, Level level, BlockPos anchorPos) {
        return findMatch(layout, level, anchorPos, EnumSet.of(Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST), true);
    }

    /**
     * Tries all 8 combinations while also enforcing block count requirements.
     */
    public static Optional<ValidationResult> findMatch(Layout layout,
                                                       Level level,
                                                       BlockPos anchorPos,
                                                       Map<Block, Integer> requiredExact,
                                                       Map<Block, Integer> requiredMinimum) {
        return findMatch(layout, level, anchorPos,
                EnumSet.of(Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST),
                true,
                requiredExact,
                requiredMinimum);
    }

    /**
     * Tries specified facings and optionally mirrored/not.
     */
    public static Optional<ValidationResult> findMatch(Layout layout,
                                                       Level level,
                                                       BlockPos anchorPos,
                                                       EnumSet<Direction> facings,
                                                       boolean includeMirror) {
        Objects.requireNonNull(layout, "layout");
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(anchorPos, "anchorPos");
        for (Direction facing : facings) {
            if (!facing.getAxis().isHorizontal()) continue;
            for (boolean mirror : includeMirror ? new boolean[]{false, true} : new boolean[]{false}) {
                ValidationResult result = validateAt(layout, level, anchorPos, facing, mirror);
                if (result.success()) {
                    return Optional.of(result);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Tries specified facings and optionally mirrored/not, enforcing block count requirements.
     */
    public static Optional<ValidationResult> findMatch(Layout layout,
                                                       Level level,
                                                       BlockPos anchorPos,
                                                       EnumSet<Direction> facings,
                                                       boolean includeMirror,
                                                       Map<Block, Integer> requiredExact,
                                                       Map<Block, Integer> requiredMinimum) {
        Objects.requireNonNull(layout, "layout");
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(anchorPos, "anchorPos");
        for (Direction facing : facings) {
            if (!facing.getAxis().isHorizontal()) continue;
            for (boolean mirror : includeMirror ? new boolean[]{false, true} : new boolean[]{false}) {
                ValidationResult result = validateAt(layout, level, anchorPos, facing, mirror, requiredExact, requiredMinimum);
                if (result.success()) {
                    return Optional.of(result);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Validates the layout at the given anchor position with a fixed orientation.
     * Returns a result containing success/failure and the first mismatch (if any).
     */
    public static ValidationResult validateAt(Layout layout,
                                              Level level,
                                              BlockPos anchorPos,
                                              Direction facing,
                                              boolean mirrorX) {
        Objects.requireNonNull(layout, "layout");
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(anchorPos, "anchorPos");
        if (!facing.getAxis().isHorizontal()) {
            throw new IllegalArgumentException("Facing must be horizontal");
        }

        final var keys = layout.keys();
        final var ignoredAnchor = layout.anchorChar().orElse(null);
        final var anchorOffset = layout.anchorOffset();

        final BlockPos origin = anchorPos.subtract(anchorOffset);

        final var mismatchRef = new Object() {
            BlockPos worldPos = null;
        };

        layout.forEachVoxel((lx, ly, lz, c) -> {
            if (mismatchRef.worldPos != null) return; // already failed

            if (c == ' ' || (ignoredAnchor != null && c == ignoredAnchor)) {
                return; // skip checks
            }

            var predicate = keys.get(c);
            if (predicate == null) {
                throw new IllegalStateException("No predicate mapped for character '" + c + "'");
            }

            // Relative from origin in layout-space
            BlockPos rel = RotationUtil.rotateAndMirror(lx, ly, lz, facing, mirrorX);
            BlockPos worldPos = origin.offset(rel);

            BlockState state = level.getBlockState(worldPos);
            if (!predicate.test(level, worldPos, state)) {
                mismatchRef.worldPos = worldPos.immutable();
            }
        });

        boolean success = mismatchRef.worldPos == null;
        return new ValidationResult(success, success ? null : mismatchRef.worldPos, facing, mirrorX);
    }

    /**
     * Validates the layout and enforces global block count requirements within the structure volume.
     */
    public static ValidationResult validateAt(Layout layout,
                                              Level level,
                                              BlockPos anchorPos,
                                              Direction facing,
                                              boolean mirrorX,
                                              Map<Block, Integer> requiredExact,
                                              Map<Block, Integer> requiredMinimum) {
        ValidationResult base = validateAt(layout, level, anchorPos, facing, mirrorX);
        if (!base.success()) return base;

        // Count blocks within the transformed structure bounding box
        final int width = layout.width();
        final int depth = layout.depth();
        final int height = layout.height();
        final var anchorOffset = layout.anchorOffset();
        final BlockPos origin = anchorPos.subtract(anchorOffset);

        Map<Block, Integer> counts = new HashMap<>();
        for (int y = 0; y < height; y++) {
            for (int z = 0; z < depth; z++) {
                for (int x = 0; x < width; x++) {
                    BlockPos rel = RotationUtil.rotateAndMirror(x, y, z, facing, mirrorX);
                    BlockPos worldPos = origin.offset(rel);
                    Block block = level.getBlockState(worldPos).getBlock();
                    counts.merge(block, 1, Integer::sum);
                }
            }
        }

        if (requiredExact != null) {
            for (Map.Entry<Block, Integer> e : requiredExact.entrySet()) {
                int actual = counts.getOrDefault(e.getKey(), 0);
                if (actual != e.getValue()) {
                    return new ValidationResult(false, anchorPos, facing, mirrorX);
                }
            }
        }

        if (requiredMinimum != null) {
            for (Map.Entry<Block, Integer> e : requiredMinimum.entrySet()) {
                int actual = counts.getOrDefault(e.getKey(), 0);
                if (actual < e.getValue()) {
                    return new ValidationResult(false, anchorPos, facing, mirrorX);
                }
            }
        }

        return new ValidationResult(true, null, facing, mirrorX);
    }

    /**
     * Result of a validation attempt.
     * - success: true if all required positions matched.
     * - firstMismatch: world position of the first mismatch (null on success).
     * - facing: the facing used for validation.
     * - mirrored: whether mirroring was used.
     */
    public record ValidationResult(boolean success,
                                   BlockPos firstMismatch,
                                   Direction facing,
                                   boolean mirrored) { }
}
