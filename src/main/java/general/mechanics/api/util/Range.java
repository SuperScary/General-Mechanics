package general.mechanics.api.util;

import lombok.Getter;

public class Range {

    @Getter
    private final int from;

    @Getter
    private final int to;

    @Getter
    private final boolean inclusive;

    @Getter
    private int current;

    public Range(int from, int to, boolean inclusive) {
        this.from = from;
        this.to = to;
        this.inclusive = inclusive;
        this.current = from;
    }

    public Range(int from, int to) {
        this(from, to, false);
    }

    public Range(int value) {
        this(value, value, true);
    }

    /**
     * Returns the next number in the range.
     * If the range is inclusive, and we've reached `to`, return `to` again.
     * If the range is exclusive, and we've reached or passed `to`, throw an exception or wrap.
     */
    public int getNext() {
        if (from < to) { // ascending
            if (inclusive) {
                if (current > to) throw new IllegalStateException("Already past the end of the range.");
            } else {
                if (current >= to) throw new IllegalStateException("Already past the end of the range.");
            }
            return current++;
        } else if (from > to) { // descending
            if (inclusive) {
                if (current < to) throw new IllegalStateException("Already past the end of the range.");
            } else {
                if (current <= to) throw new IllegalStateException("Already past the end of the range.");
            }
            return current--;
        } else {
            // from == to
            if (!inclusive) throw new IllegalStateException("Empty range.");
            return from;
        }
    }

    /**
     * Resets the internal cursor to the beginning.
     */
    public void reset() {
        this.current = from;
    }

    /**
     * Returns whether there is another element in the range.
     */
    public boolean hasNext() {
        if (from < to)
            return inclusive ? current <= to : current < to;
        else if (from > to)
            return inclusive ? current >= to : current > to;
        else
            return inclusive && current == from;
    }

    public int[] toArray() {
        if (to == -1) {
            return new int[]{from};
        }

        int size = Math.abs(to - from) + (inclusive ? 1 : 0);
        int[] arr = new int[size];
        int step = (to >= from) ? 1 : -1;
        int value = from;
        for (int i = 0; i < size; i++) {
            arr[i] = value;
            value += step;
        }
        return arr;
    }

    @Override
    public String toString() {
        return String.format("Range(%d %s %d)", from, inclusive ? "<=" : "<", to);
    }
}

