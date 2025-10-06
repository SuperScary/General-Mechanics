package dimensional.core.api.util.data;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.List;

public class PropertyHelper {

    public static <T extends Comparable<T>> PropertyComponent<T> of (Property<T> property, T value) {
        return new PropertyComponent<>(property, value);
    }

    public static <T extends Comparable<T>> PropertyComponent<T> of (Property<T> property, BlockState state) {
        return of(property, state.getValue(property));
    }

    @SafeVarargs
    public static <T extends Comparable<T>> boolean sameValue (BlockState state, Property<T>... properties) {
        return sameValue(state, List.of(properties));
    }

    public static <T extends Comparable<T>> boolean sameValue (BlockState state, List<Property<T>> properties) {
        if (properties == null || properties.isEmpty()) {
            return true; // Empty list is considered to have all elements equal.
        }

        for (int i = 0; i < properties.size(); i++) {
            if (!state.getValue(properties.get(i)).equals(state.getValue(properties.getFirst()))) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    public static <T extends Comparable<T>, V extends T> V setValues (BlockState state, V value, Property<T>... properties) {
        for (Property<T> prop : properties) {
            state.setValue(prop, value);
        }
        return value;
    }

    public static List<PropertyComponent<?>> list (PropertyComponent<?>... components) {
        return List.of(components);
    }

}
