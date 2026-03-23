package general.mechanics.api.capability;

import net.minecraft.core.Direction;

public final class AutomationAccess {

    private static AutomationAccessPolicy POLICY = AutomationAccessPolicy.DENY_IF_NO_SIDE;

    private AutomationAccess() {
    }

    public static AutomationAccessPolicy getPolicy() {
        return POLICY;
    }

    public static void setPolicy(AutomationAccessPolicy policy) {
        if (policy == null) throw new IllegalArgumentException("policy");
        POLICY = policy;
    }

    public static boolean allow(Direction side) {
        return POLICY.allow(side);
    }
}
