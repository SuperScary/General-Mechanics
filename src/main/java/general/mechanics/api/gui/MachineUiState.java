package general.mechanics.api.gui;

import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import general.mechanics.api.component.io.IoType;

import net.minecraft.core.Direction;

public record MachineUiState(boolean hasPower, int energyStored, int energyCapacity, boolean enabled,
                             boolean exportEnabled, boolean importEnabled,
                             BasePoweredBlockEntity.RedstoneMode redstoneMode, boolean hasCrafting, int progress,
                             int maxProgress, boolean crafting, int packedSideModesItems, int packedSideModesEnergy,
                             int packedSideModesFluids) {

    public static MachineUiState empty() {
        return new MachineUiState(false, 0, 0, true, true, true, BasePoweredBlockEntity.RedstoneMode.IGNORED, false, 0, 0, false, 0, 0, 0);
    }

    public BasePoweredBlockEntity.SideMode sideMode(IoType type, Direction direction) {
        int packed = switch (type) {
            case ITEMS -> packedSideModesItems;
            case ENERGY -> packedSideModesEnergy;
            case FLUIDS -> packedSideModesFluids;
        };
        int shift = direction.ordinal() * 2;
        int id = (packed >> shift) & 0b11;
        return BasePoweredBlockEntity.SideMode.fromId(id);
    }

    public float progress01() {
        if (!hasCrafting || maxProgress <= 0) return 0.0f;
        return Math.min(1.0f, Math.max(0.0f, progress / (float) maxProgress));
    }
}
