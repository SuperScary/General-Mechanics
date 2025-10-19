package general.mechanics.api.item.element;

import lombok.Getter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable definition of an alloy: a named material made from a composition of {@link ElementType} parts.
 * Parts are relative (e.g., brass 2:1 Cu:Zn). Percentages can be derived from parts.
 */
public final class AlloyType {

    @Getter
    private final String name;

    @Getter
    private final String symbol;

    /** Unmodifiable map of element -> integer parts (relative proportion). */
    @Getter
    private final Map<ElementType, Integer> elementParts;

    private final int totalParts;

    public AlloyType(String name, String symbol, Map<ElementType, Integer> elementParts) {
        this.name = Objects.requireNonNull(name, "name").trim();
        this.symbol = Objects.requireNonNull(symbol, "symbol").trim();

        if (this.name.isEmpty()) throw new IllegalArgumentException("AlloyType name cannot be blank");
        if (this.symbol.isEmpty()) throw new IllegalArgumentException("AlloyType symbol cannot be blank");
        Objects.requireNonNull(elementParts, "elementParts");
        if (elementParts.isEmpty()) throw new IllegalArgumentException("AlloyType must have at least one element");

        // Normalize into a LinkedHashMap to preserve insertion order and validate values
        LinkedHashMap<ElementType, Integer> normalized = new LinkedHashMap<>();
        int runningTotal = 0;
        for (Map.Entry<ElementType, Integer> entry : elementParts.entrySet()) {
            ElementType element = Objects.requireNonNull(entry.getKey(), "element");
            Integer parts = Objects.requireNonNull(entry.getValue(), "parts");
            if (parts <= 0) throw new IllegalArgumentException("Parts must be positive for element: " + element.name());
            Integer previous = normalized.put(element, parts);
            if (previous != null) throw new IllegalArgumentException("Duplicate element in composition: " + element.name());
            runningTotal += parts;
        }
        this.totalParts = runningTotal;
        this.elementParts = Collections.unmodifiableMap(normalized);
    }

    /** Total number of relative parts across all elements. */
    public int getTotalParts() {
        return totalParts;
    }

    /** Fraction [0,1] of the alloy made from the given element (by parts). */
    public double getFraction(ElementType element) {
        Integer parts = elementParts.get(element);
        if (parts == null) return 0.0d;
        return (double) parts / (double) totalParts;
    }

    /** Percentage [0,100] of the alloy made from the given element (by parts). */
    public double getPercentage(ElementType element) {
        return getFraction(element) * 100.0d;
    }

    /** Returns an empirical formula like Cu2Zn (omits 1 by default). */
    public String getEmpiricalFormula() {
        return getEmpiricalFormula(false);
    }

    /** Returns an empirical formula; when includeOnes is true, shows count 1 (e.g., Cu1Zn2). */
    public String getEmpiricalFormula(boolean includeOnes) {
        int divisor = gcdOfValues(elementParts.values());
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<ElementType, Integer> entry : elementParts.entrySet()) {
            int simplified = entry.getValue() / Math.max(divisor, 1);
            builder.append(entry.getKey().getSymbol());
            if (simplified != 1 || includeOnes) builder.append(simplified);
        }
        return builder.toString();
    }

    /** Returns a percentage-based formula like Cu66.7Zn33.3, rounded to the given decimals. */
    public String getPercentageFormula(int decimals) {
        StringBuilder builder = new StringBuilder();
        double scale = Math.pow(10, Math.max(decimals, 0));
        boolean first = true;
        for (Map.Entry<ElementType, Integer> entry : elementParts.entrySet()) {
            if (!first) {
                // no separator to keep compact, adjust if you want "·" or "/"
            }
            first = false;
            double pct = (entry.getValue() * 100.0d) / (double) totalParts;
            double rounded = Math.round(pct * scale) / scale;
            builder.append(entry.getKey().getSymbol()).append(rounded);
        }
        return builder.toString();
    }

    private static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a == 0 ? 1 : a;
    }

    private static int gcdOfValues(Iterable<Integer> values) {
        int result = 0;
        for (Integer v : values) {
            result = gcd(result, v);
            if (result == 1) return 1; // early exit
        }
        return result == 0 ? 1 : result;
    }

    /**
     * Convenience builder for constructing an {@link AlloyType} with fluent addition of element parts.
     */
    public static Builder builder(String name, String symbol) {
        return new Builder(name, symbol);
    }

    public static final class Builder {
        private final String name;
        private final String symbol;
        private final LinkedHashMap<ElementType, Integer> parts = new LinkedHashMap<>();

        private Builder(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
        }

        public Builder add(ElementType element, int partCount) {
            if (partCount <= 0) throw new IllegalArgumentException("partCount must be positive");
            if (parts.putIfAbsent(element, partCount) != null) {
                throw new IllegalArgumentException("Element already added: " + element.name());
            }
            return this;
        }

        public AlloyType build() {
            return new AlloyType(name, symbol, parts);
        }
    }
}
