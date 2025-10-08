package fluxmachines.core.api.multiblock;

import fluxmachines.core.api.block.BlockDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

import java.util.*;

/**
 * A 3D pattern of characters with a mapping from character -> BlockPredicate.
 * Layout coordinates:
 * - X increases to the east, comes from String column index.
 * - Z increases to the south, comes from row index within a layer.
 * - Y increases upward, comes from layer index (0 = bottom layer).
 *
 * Special characters:
 * - ' ' (space) is ignored (no check).
 * - Anchor char (if provided) marks the origin within the layout and is ignored during validation.
 */
public final class Layout {

    private final char[][][] pattern; // [y][z][x]
    private final int width;  // x
    private final int depth;  // z
    private final int height; // y
    private final Map<Character, BlockPredicate> keys;
    private final Character anchorChar; // nullable
    private final BlockPos anchorOffset; // layout-space coordinates of anchor

    private Layout(char[][][] pattern,
                   int width,
                   int depth,
                   int height,
                   Map<Character, BlockPredicate> keys,
                   Character anchorChar,
                   BlockPos anchorOffset) {
        this.pattern = pattern;
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.keys = Collections.unmodifiableMap(new HashMap<>(keys));
        this.anchorChar = anchorChar;
        this.anchorOffset = anchorOffset;
    }

    public int width() {
        return width;
    }

    public int depth() {
        return depth;
    }

    public int height() {
        return height;
    }

    public Optional<Character> anchorChar() {
        return Optional.ofNullable(anchorChar);
    }

    public BlockPos anchorOffset() {
        return anchorOffset;
    }

    public Map<Character, BlockPredicate> keys() {
        return keys;
    }
    
    /**
     * Gets the character at the specified layout coordinates.
     * 
     * @param x X coordinate (0 to width-1)
     * @param y Y coordinate (0 to height-1) 
     * @param z Z coordinate (0 to depth-1)
     * @return The character at the specified position
     * @throws IndexOutOfBoundsException if coordinates are out of bounds
     */
    public char getCharAt(int x, int y, int z) {
        if (x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= depth) {
            throw new IndexOutOfBoundsException("Coordinates (" + x + ", " + y + ", " + z + ") out of bounds for layout " + width + "x" + height + "x" + depth);
        }
        return pattern[y][z][x];
    }

    /**
     * Iterates all voxels of the pattern, passing layout-space coordinates and character.
     * Coordinates are relative to the min corner (0,0,0), not adjusted by anchor.
     */
    public void forEachVoxel(VoxelConsumer consumer) {
        for (int y = 0; y < height; y++) {
            for (int z = 0; z < depth; z++) {
                for (int x = 0; x < width; x++) {
                    char c = pattern[y][z][x];
                    consumer.accept(x, y, z, c);
                }
            }
        }
    }

    @FunctionalInterface
    public interface VoxelConsumer {
        void accept(int x, int y, int z, char c);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<List<String>> layers = new ArrayList<>();
        private final Map<Character, BlockPredicate> keys = new HashMap<>();
        private Character anchorChar;
        private int width = -1;
        private int depth = -1;

        /**
         * Defines a layer from north-most row to south-most row.
         * Each row is a string of equal width. All layers must share identical width and depth.
         */
        public Builder layer(String... rows) {
            Objects.requireNonNull(rows, "rows");
            if (rows.length == 0) throw new IllegalArgumentException("Layer must have at least one row");
            List<String> normalized = new ArrayList<>(rows.length);
            int localWidth = -1;
            for (String row : rows) {
                Objects.requireNonNull(row, "row");
                if (localWidth == -1) {
                    localWidth = row.length();
                    if (localWidth == 0) throw new IllegalArgumentException("Row width must be > 0");
                } else if (localWidth != row.length()) {
                    throw new IllegalArgumentException("All rows in a layer must have the same width");
                }
                normalized.add(row);
            }
            int localDepth = normalized.size();

            if (width == -1) {
                width = localWidth;
                depth = localDepth;
            } else {
                if (width != localWidth || depth != localDepth) {
                    throw new IllegalArgumentException("All layers must share the same width and depth");
                }
            }

            layers.add(normalized);
            return this;
        }

        /**
         * Maps a character used in the layout to a predicate.
         * The space character ' ' is implicit and ignored; no need to map it.
         */
        public Builder key(char symbol, BlockPredicate predicate) {
            if (symbol == ' ') throw new IllegalArgumentException("Space ' ' is implicitly ignored; do not map it");
            keys.put(symbol, Objects.requireNonNull(predicate, "predicate"));
            return this;
        }

        public Builder key(char symbol, Block block) {
            return key(symbol, BlockPredicate.of(block));
        }

        public Builder key(char symbol, BlockDefinition<?> block) {
            return key(symbol, BlockPredicate.of(block.block()));
        }

        /**
         * Marks a character as the anchor. This character is ignored in validation and denotes the layout origin.
         * The anchor character must appear exactly once in the layout.
         */
        public Builder anchor(char symbol) {
            this.anchorChar = symbol;
            return this;
        }

        public Layout build() {
            if (layers.isEmpty()) throw new IllegalStateException("No layers defined");
            int height = layers.size();
            char[][][] pattern = new char[height][depth][width];

            // Copy into 3D array and locate anchor
            BlockPos foundAnchor = null;
            for (int y = 0; y < height; y++) {
                List<String> layer = layers.get(y);
                for (int z = 0; z < depth; z++) {
                    String row = layer.get(z);
                    for (int x = 0; x < width; x++) {
                        char c = row.charAt(x);
                        pattern[y][z][x] = c;
                        if (anchorChar != null && c == anchorChar) {
                            if (foundAnchor != null) {
                                throw new IllegalStateException("Anchor character '" + anchorChar + "' appears more than once");
                            }
                            foundAnchor = new BlockPos(x, y, z);
                        }
                        if (c != ' ' && (anchorChar == null || c != anchorChar)) {
                            if (!keys.containsKey(c)) {
                                throw new IllegalStateException("No key mapping for character '" + c + "'");
                            }
                        }
                    }
                }
            }

            BlockPos anchorOffset = foundAnchor != null ? foundAnchor : BlockPos.ZERO;

            return new Layout(pattern, width, depth, height, keys, anchorChar, anchorOffset);
        }
    }
}
