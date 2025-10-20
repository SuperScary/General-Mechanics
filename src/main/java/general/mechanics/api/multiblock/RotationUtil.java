package general.mechanics.api.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

/**
 * Utility for rotating and mirroring relative positions in the horizontal plane.
 * We assume the base (unrotated) layout faces SOUTH.
 */
public final class RotationUtil {

    private RotationUtil() {}

    /**
     * Rotates and optionally mirrors a relative layout-space position (x,z) to world-space relative based on facing.
     * Y is unchanged.
     *
     * Rotation mapping (base facing SOUTH):
     * - SOUTH: (x, z)
     * - WEST:  (z, -x)
     * - NORTH: (-x, -z)
     * - EAST:  (-z, x)
     *
     * Mirroring reflects across the Z axis first: (x, z) -> (-x, z) before rotation.
     */
    public static BlockPos rotateAndMirror(int x, int y, int z, Direction facing, boolean mirrorX) {
        int rx = x;
        int rz = z;
        if (mirrorX) {
            rx = -rx;
        }

        return switch (facing) {
            case SOUTH -> new BlockPos(rx, y, rz);
            case WEST -> new BlockPos(rz, y, -rx);
            case NORTH -> new BlockPos(-rx, y, -rz);
            case EAST -> new BlockPos(-rz, y, rx);
            default -> throw new IllegalArgumentException("Unsupported facing for horizontal rotation: " + facing);
        };
    }
    
    /**
     * Performs the inverse transformation of rotateAndMirror.
     * Converts world-space relative position back to layout-space coordinates.
     */
    public static BlockPos rotateAndMirrorInverse(int x, int y, int z, Direction facing, boolean mirrorX) {
        // First, apply inverse rotation
        int lx, lz;
        switch (facing) {
            case SOUTH -> { lx = x; lz = z; }
            case WEST -> { lx = -z; lz = x; }
            case NORTH -> { lx = -x; lz = -z; }
            case EAST -> { lx = z; lz = -x; }
            default -> throw new IllegalArgumentException("Unsupported facing for horizontal rotation: " + facing);
        }
        
        // Then apply inverse mirroring
        if (mirrorX) {
            lx = -lx;
        }
        
        return new BlockPos(lx, y, lz);
    }
}
