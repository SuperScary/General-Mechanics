package general.mechanics.api.capability;

import net.minecraft.core.Direction;

public enum AutomationAccessPolicy {
    DENY_IF_NO_SIDE,
    ALLOW_IF_NO_SIDE;

    public boolean allow(Direction side) {
        if (side == null) {
            return this == ALLOW_IF_NO_SIDE;
        }
        return true;
    }
}
