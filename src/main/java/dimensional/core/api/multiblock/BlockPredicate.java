package dimensional.core.api.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * A predicate used to validate whether a block in the world matches a specific requirement.
 * Implementations may check block, tag, state properties, etc.
 */
@FunctionalInterface
public interface BlockPredicate {

    /**
     * Tests whether the given world/block position/state matches this predicate.
     */
    boolean test(Level level, BlockPos pos, BlockState state);

    /**
     * Returns a predicate that matches anything (always true).
     */
    static BlockPredicate any() {
        return (level, pos, state) -> true;
    }

    /**
     * Returns a predicate that matches exactly the given block.
     */
    static BlockPredicate of(Block block) {
        Objects.requireNonNull(block, "block");
        return (level, pos, state) -> state.is(block);
    }

    /**
     * Returns a predicate that matches blocks contained in the given tag.
     */
    static BlockPredicate of(TagKey<Block> tag) {
        Objects.requireNonNull(tag, "tag");
        return (level, pos, state) -> state.is(tag);
    }

    /**
     * Returns a predicate backed by a BlockState predicate.
     */
    static BlockPredicate of(Predicate<BlockState> statePredicate) {
        Objects.requireNonNull(statePredicate, "statePredicate");
        return (level, pos, state) -> statePredicate.test(state);
    }

    /**
     * Logical AND composition.
     */
    default BlockPredicate and(BlockPredicate other) {
        Objects.requireNonNull(other, "other");
        return (l, p, s) -> this.test(l, p, s) && other.test(l, p, s);
    }

    /**
     * Logical OR composition.
     */
    default BlockPredicate or(BlockPredicate other) {
        Objects.requireNonNull(other, "other");
        return (l, p, s) -> this.test(l, p, s) || other.test(l, p, s);
    }

    /**
     * Logical NOT.
     */
    default BlockPredicate negate() {
        return (l, p, s) -> !this.test(l, p, s);
    }
}
