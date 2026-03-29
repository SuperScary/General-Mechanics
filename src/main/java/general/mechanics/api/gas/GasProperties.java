package general.mechanics.api.gas;

import lombok.Getter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.SoundAction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class GasProperties {

    @Getter
    private String descriptionId;

    @Getter
    private double motionScale = 0.014;

    @Getter
    private boolean canPushEntity = false;

    @Getter
    private boolean canSwim = true;

    @Getter
    private boolean canDrown = true;

    @Getter
    private float fallDistanceModifier = 0.5F;

    @Getter
    private boolean canExtinguish = false;

    @Getter
    private boolean canConvertToSource = false;

    @Getter
    private boolean supportsBoating = false;

    @Getter
    private @Nullable PathType pathType;

    @Getter
    private @Nullable PathType adjacentPathType;

    @Getter
    private final Map<SoundAction, SoundEvent> sounds;

    @Getter
    private boolean canHydrate;

    @Getter
    private int lightLevel;

    @Getter
    private int density;

    @Getter
    private int temperature;

    @Getter
    private int viscosity;

    @Getter
    private Rarity rarity;

    private GasProperties() {
        this.pathType = PathType.WATER;
        this.adjacentPathType = PathType.WATER_BORDER;
        this.sounds = new HashMap();
        this.canHydrate = false;
        this.lightLevel = 0;
        this.density = 1000;
        this.temperature = 300;
        this.viscosity = 1000;
        this.rarity = Rarity.COMMON;
    }

    public static GasProperties create() {
        return new GasProperties();
    }

    public GasProperties descriptionId(String descriptionId) {
        this.descriptionId = descriptionId;
        return this;
    }

    public GasProperties motionScale(double motionScale) {
        this.motionScale = motionScale;
        return this;
    }

    public GasProperties canPushEntity(boolean canPushEntity) {
        this.canPushEntity = canPushEntity;
        return this;
    }

    public GasProperties canSwim(boolean canSwim) {
        this.canSwim = canSwim;
        return this;
    }

    public GasProperties canDrown(boolean canDrown) {
        this.canDrown = canDrown;
        return this;
    }

    public GasProperties fallDistanceModifier(float fallDistanceModifier) {
        this.fallDistanceModifier = fallDistanceModifier;
        return this;
    }

    public GasProperties canExtinguish(boolean canExtinguish) {
        this.canExtinguish = canExtinguish;
        return this;
    }

    public GasProperties canConvertToSource(boolean canConvertToSource) {
        this.canConvertToSource = canConvertToSource;
        return this;
    }

    public GasProperties supportsBoating(boolean supportsBoating) {
        this.supportsBoating = supportsBoating;
        return this;
    }

    public GasProperties pathType(@Nullable PathType pathType) {
        this.pathType = pathType;
        return this;
    }

    public GasProperties adjacentPathType(@Nullable PathType adjacentPathType) {
        this.adjacentPathType = adjacentPathType;
        return this;
    }

    public GasProperties sound(SoundAction action, SoundEvent sound) {
        this.sounds.put(action, sound);
        return this;
    }

    public GasProperties canHydrate(boolean canHydrate) {
        this.canHydrate = canHydrate;
        return this;
    }

    public GasProperties lightLevel(int lightLevel) {
        if (lightLevel >= 0 && lightLevel <= 15) {
            this.lightLevel = lightLevel;
            return this;
        } else {
            throw new IllegalArgumentException("The light level should be between [0,15].");
        }
    }

    public GasProperties density(int density) {
        this.density = density;
        return this;
    }

    public GasProperties temperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public GasProperties viscosity(int viscosity) {
        if (viscosity < 0) {
            throw new IllegalArgumentException("The viscosity should never be negative.");
        } else {
            this.viscosity = viscosity;
            return this;
        }
    }

    public GasProperties rarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

}
