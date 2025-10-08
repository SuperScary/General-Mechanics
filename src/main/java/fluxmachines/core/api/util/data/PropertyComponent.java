package fluxmachines.core.api.util.data;

import com.google.common.base.Preconditions;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PropertyComponent<T extends Comparable<T>> implements StringRepresentable {

    private final Property<T> property;
    private T value;

    public PropertyComponent (Property<T> property, T value) {
        this.property = property;
        this.value = value;
    }

    public PropertyComponent (Property<T> property) {
        this.property = property;
        this.value = null;
    }

    public BlockState withProperty (BlockState state) {
        Preconditions.checkArgument(getProperty() != null && getValue() != null, "Property and/or value cannot be null");
        return state.setValue(property, value);
    }

    public T setValue (T value) {
        this.value = value;
        return this.value;
    }

    public Property<T> getProperty () {
        return property;
    }

    public T getValue () {
        return value;
    }

    @Override
    public @NotNull String getSerializedName () {
        return getValue().toString();
    }

    public static List<PropertyComponent<?>> createEmptyList () {
        return new ArrayList<>();
    }

}
