package fluxmachines.core.api.attributes;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.*;

public class Attribute {

    private final List<String> key;
    private final AttributeValue<?> value;

    public <T> Attribute (List<String> key, Supplier<T> defaultSupplier, T defaultValue) {
        this.key = key;
        this.value = new AttributeValue<>(key, defaultSupplier, defaultValue);
    }

    public List<String> getKey () {
        return key;
    }

    public AttributeValue<?> getValue () {
        return value;
    }

    public static class Builder {

        public static BooleanValue of (String key, Boolean value) {
            return new BooleanValue(List.of(key), () -> value, value);
        }

        public static IntValue of (String key, Integer value) {
            return new IntValue(List.of(key), () -> value, value);
        }

        public static LongValue of (String key, Long value) {
            return new LongValue(List.of(key), () -> value, value);
        }

        public static DoubleValue of (String key, Double value) {
            return new DoubleValue(List.of(key), () -> value, value);
        }

        public static FloatValue of (String key, Float value) {
            return new FloatValue(List.of(key), () -> value, value);
        }

    }

    public static class AttributeValue<T> implements Supplier<T> {

        private final List<String> path;
        private final Supplier<T> defaultSupplier;
        private final T defaultValue;
        private @Nullable T cachedValue;

        AttributeValue (List<String> path, Supplier<T> defaultSupplier, @Nullable T defaultValue) {
            this.path = path;
            this.defaultSupplier = defaultSupplier;
            this.defaultValue = defaultValue;
            this.cachedValue = defaultValue;
        }

        public List<String> getPath() {
            return Lists.newArrayList(this.path);
        }

        @Override
        public T get () {
            if (cachedValue == null) {
                return defaultValue;
            }
            return cachedValue;
        }

        @Override
        public String toString () {
            return this.get().toString();
        }

        public T getDefault () {
            return this.defaultSupplier.get();
        }

        public void set (T value) {
            this.cachedValue = value;
        }

        public void clearCache () {
            this.cachedValue = null;
        }
    }

    public static class BooleanValue extends AttributeValue<Boolean> implements BooleanSupplier {
        BooleanValue (List<String> path, Supplier<Boolean> defaultSupplier, @Nullable Boolean defaultValue) {
            super(path, defaultSupplier, defaultValue);
        }

        public boolean getAsBoolean() {
            return this.get();
        }

        public boolean isTrue() {
            return this.getAsBoolean();
        }

        public boolean isFalse() {
            return !this.getAsBoolean();
        }
    }

    public static class IntValue extends AttributeValue<Integer> implements IntSupplier, ValueModifier<Integer> {
        IntValue (List<String> path, Supplier<Integer> defaultSupplier, @Nullable Integer defaultValue) {
            super(path, defaultSupplier, defaultValue);
        }

        public int getAsInt() {
            return this.get();
        }

        @Override
        public void increase (Integer amount) {
            this.set(this.get() + amount);
        }

        @Override
        public void decrease (Integer amount) {
            this.set(this.get() - amount);
        }
    }

    public static class LongValue extends AttributeValue<Long> implements LongSupplier, ValueModifier<Long> {
        LongValue (List<String> path, Supplier<Long> defaultSupplier, @Nullable Long defaultValue) {
            super(path, defaultSupplier, defaultValue);
        }

        public long getAsLong() {
            return this.get();
        }

        @Override
        public void increase (Long amount) {
            this.set(this.get() + amount);
        }

        @Override
        public void decrease (Long amount) {
            this.set(this.get() - amount);
        }
    }

    public static class DoubleValue extends AttributeValue<Double> implements DoubleSupplier, ValueModifier<Double> {
        DoubleValue (List<String> path, Supplier<Double> defaultSupplier, @Nullable Double defaultValue) {
            super(path, defaultSupplier, defaultValue);
        }

        public double getAsDouble() {
            return this.get();
        }

        @Override
        public void increase (Double amount) {
            this.set(this.get() + amount);
        }

        @Override
        public void decrease (Double amount) {
            this.set(this.get() - amount);
        }
    }

    public static class FloatValue extends AttributeValue<Float> implements Supplier<Float>, ValueModifier<Float> {

        FloatValue (List<String> path, Supplier<Float> defaultSupplier, @Nullable Float defaultValue) {
            super(path, defaultSupplier, defaultValue);
        }

        public float getAsFloat () {
            return this.get();
        }

        @Override
        public void increase (Float amount) {
            this.set(this.get() + amount);
        }

        @Override
        public void decrease (Float amount) {
            this.set(this.get() - amount);
        }
    }

    public interface ValueModifier<T extends Number> {

        void increase (T amount);

        void decrease (T amount);

    }

}
