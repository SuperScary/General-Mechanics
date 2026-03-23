package general.mechanics.api.util;

import net.minecraft.core.Direction;

public enum LocalSide {
    UP,
    DOWN,
    FRONT,
    BACK,
    LEFT,
    RIGHT;

    public Direction toWorld(Direction facing) {
        if (facing == null || !facing.getAxis().isHorizontal()) {
            facing = Direction.NORTH;
        }

        return switch (this) {
            case UP -> Direction.UP;
            case DOWN -> Direction.DOWN;
            case FRONT -> facing;
            case BACK -> facing.getOpposite();
            case LEFT -> facing.getClockWise();
            case RIGHT -> facing.getCounterClockWise();
        };
    }
}
