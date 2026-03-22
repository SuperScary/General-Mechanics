package general.mechanics.api.gui;

import general.mechanics.api.entity.block.BasePoweredBlockEntity;

import net.minecraft.core.Direction;

public final class MachineUiState {

    private final boolean hasPower;
    private final int energyStored;
    private final int energyCapacity;

    private final boolean enabled;
    private final boolean exportEnabled;
    private final boolean importEnabled;
    private final BasePoweredBlockEntity.RedstoneMode redstoneMode;

    private final boolean hasCrafting;
    private final int progress;
    private final int maxProgress;
    private final boolean crafting;

    private final int packedSideModes;

    public MachineUiState(
            boolean hasPower,
            int energyStored,
            int energyCapacity,
            boolean enabled,
            boolean exportEnabled,
            boolean importEnabled,
            BasePoweredBlockEntity.RedstoneMode redstoneMode,
            boolean hasCrafting,
            int progress,
            int maxProgress,
            boolean crafting,
            int packedSideModes
    ) {
        this.hasPower = hasPower;
        this.energyStored = energyStored;
        this.energyCapacity = energyCapacity;
        this.enabled = enabled;
        this.exportEnabled = exportEnabled;
        this.importEnabled = importEnabled;
        this.redstoneMode = redstoneMode;
        this.hasCrafting = hasCrafting;
        this.progress = progress;
        this.maxProgress = maxProgress;
        this.crafting = crafting;
        this.packedSideModes = packedSideModes;
    }

    public static MachineUiState empty() {
        return new MachineUiState(false, 0, 0, true, true, true, BasePoweredBlockEntity.RedstoneMode.IGNORED, false, 0, 0, false, 0);
    }

    public boolean hasPower() {
        return hasPower;
    }

    public int energyStored() {
        return energyStored;
    }

    public int energyCapacity() {
        return energyCapacity;
    }

    public boolean enabled() {
        return enabled;
    }

    public boolean exportEnabled() {
        return exportEnabled;
    }

    public boolean importEnabled() {
        return importEnabled;
    }

    public BasePoweredBlockEntity.RedstoneMode redstoneMode() {
        return redstoneMode;
    }

    public boolean hasCrafting() {
        return hasCrafting;
    }

    public int progress() {
        return progress;
    }

    public int maxProgress() {
        return maxProgress;
    }

    public boolean crafting() {
        return crafting;
    }

    public int packedSideModes() {
        return packedSideModes;
    }

    public BasePoweredBlockEntity.SideMode sideMode(Direction direction) {
        int shift = direction.ordinal() * 2;
        int id = (packedSideModes >> shift) & 0b11;
        return BasePoweredBlockEntity.SideMode.fromId(id);
    }

    public float progress01() {
        if (!hasCrafting || maxProgress <= 0) return 0.0f;
        return Math.min(1.0f, Math.max(0.0f, progress / (float) maxProgress));
    }
}
