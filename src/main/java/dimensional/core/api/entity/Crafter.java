package dimensional.core.api.entity;

import dimensional.core.api.inventory.CoreInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * An interface for block entities that can craft items.
 * @param <T>
 */
public interface Crafter<T extends Recipe<?>> extends CoreInventory {

    /**
     * Crafts the item.
     */
    void craftItem (ItemStack result);

    /**
     * Checks if the block entity has a recipe.
     * @return true if the block entity has a recipe, false otherwise
     */
    boolean hasRecipe (BlockState state);

    /**
     * Gets the recipe type.
     * @return the recipe type
     */
    RecipeType<T> getRecipeType ();

    /**
     * Gets the current recipe.
     * @return the current recipe
     */
    @NotNull
    Optional<RecipeHolder<T>> getCurrentRecipe ();

    /**
     * Increases the crafting progress.
     */
    void increaseCraftingProgress ();

    /**
     * Gets the crafting progress.
     * @return the crafting progress
     */
    float getProgress ();

    /**
     * Gets the maximum progress.
     * @return the maximum progress
     */
    default int getMaxProgress () {
        return 176;
    }

    /**
     * Checks if the crafting has finished.
     * @return true if the crafting has finished, false otherwise
     */
    default boolean hasFinished () {
        return getProgress() >= getMaxProgress();
    }

    /**
     * Checks if the block entity is crafting.
     * @return true if the block entity is crafting, false otherwise
     */
    boolean isCrafting();

    /**
     * Gets the scaled progress.
     * @return the scaled progress
     */
    int getScaledProgress ();

    /**
     * Gets the result item of the recipe. Can be null.
     * @return {@link ItemStack}
     */
    @Nullable
    default ItemStack getRecipeResultItem (@NotNull Level level) {
        if (getCurrentRecipe().isPresent()) return getCurrentRecipe().get().value().getResultItem(Objects.requireNonNull(level).registryAccess());
        return ItemStack.EMPTY;
    }

}
