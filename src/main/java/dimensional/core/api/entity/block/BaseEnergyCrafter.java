package dimensional.core.api.entity.block;

import dimensional.core.api.attributes.Attribute;
import dimensional.core.api.energy.EnergizedCrafter;
import dimensional.core.api.recipe.CoreRecipe;
import dimensional.core.api.util.Range;
import dimensional.core.api.util.data.Keys;
import dimensional.core.api.util.data.PropertyHelper;
import dimensional.core.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class BaseEnergyCrafter<T extends CoreRecipe<?>> extends BasePoweredBlockEntity implements EnergizedCrafter<T> {

    private final Attribute.FloatValue progress;
    private final Attribute.BooleanValue isCrafting = Attribute.Builder.of(Keys.CRAFTING, false);

    public BaseEnergyCrafter(BlockEntityType<?> type, BlockPos pos, BlockState blockState, Attribute.IntValue capacity, Attribute.IntValue maxReceive) {
        this(type, pos, blockState, capacity, maxReceive, Attribute.Builder.of(Keys.POWER, 0));
    }

    public BaseEnergyCrafter(BlockEntityType<?> type, BlockPos pos, BlockState blockState, Attribute.IntValue capacity, Attribute.IntValue maxReceive, Attribute.IntValue current) {
        super(type, pos, blockState, capacity, maxReceive, current);
        progress = Attribute.Builder.of(Keys.PROGRESS, 0f);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public @NotNull Optional<RecipeHolder<T>> getCurrentRecipe () {
        if (level == null) return Optional.empty();
        
        var recipeManager = level.getRecipeManager();
        var recipeType = getRecipeType();
        var inputSlots = getInputSlots();

        // Use raw types to bypass generic type checking since CoreRecipe extends Recipe
        for (var recipe : recipeManager.getAllRecipesFor((RecipeType) recipeType)) {
            RecipeHolder<?> recipeHolder = (RecipeHolder<?>) recipe;
            var recipeValue = recipeHolder.value();
            
            // Create a recipe input from our input slots
            var recipeInput = createRecipeInput(inputSlots.toArray());
            
            // Check if this recipe matches our current inputs
            // Use raw types to bypass generic type checking
            try {
                if (((Recipe) recipeValue).matches((RecipeInput) recipeInput, level)) {
                    return Optional.of((RecipeHolder<T>) recipe);
                }
            } catch (ClassCastException e) {
                // If the recipe input type doesn't match, skip this recipe
                continue;
            }
        }

        return Optional.empty();
    }

    /**
     * Creates a recipe input from the specified input slots.
     * This method should be overridden by subclasses to create the appropriate RecipeInput type.
     */
    protected Object createRecipeInput(int[] inputSlots) {
        // Default implementation creates a simple list of ItemStacks
        List<ItemStack> inputs = new ArrayList<>();
        for (int slot : inputSlots) {
            inputs.add(getInventory().getStackInSlot(slot));
        }
        return inputs;
    }

    @Override
    public void increaseCraftingProgress () {
        progress.increase(1f);
    }

    @Override
    public float getProgress () {
        return progress.get();
    }

    @Override
    public boolean hasEnoughEnergy (int required) {
        var hasEnergy = this.getEnergyStorage().getEnergyStored() >= required;
        if (hasEnergy) this.getEnergyStorage().extractEnergy(required, false);
        return hasEnergy;
    }

    @Override
    public void tick (Level level, BlockPos pos, BlockState state) {
        if (Utils.isDevEnvironment()) getEnergyStorage().receiveEnergy(10_000, false); // TODO: Dev only environment

        // Check if any input slots have items
        boolean hasInputs = Arrays.stream(getInputSlots().toArray())
                .anyMatch(slot -> getInventory().getStackInSlot(slot) != ItemStack.EMPTY);

        if (!hasInputs) {
            progress.set(0f);
            updateBlockState(state.setValue(BlockStateProperties.CRAFTING, false));
        }

        if (hasRecipe(state)) {
            isCrafting.set(true);
            increaseCraftingProgress();
            getEnergyStorage().extractEnergy(getEnergyAmount(), false);

            if (hasFinished()) {
                craftItem(Objects.requireNonNull(getRecipeResultItem(level)));
                progress.set(0f);
                isCrafting.set(false);
            }
        }
    }

    @Override
    public boolean hasRecipe (BlockState state) {
        var recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            updateBlockState(state.setValue(BlockStateProperties.CRAFTING, false));
            return false;
        }
        var result = getRecipeResultItem(Objects.requireNonNull(getLevel()));
        isCrafting.set(result != null);
        
        // Check if we can insert the result into any output slot
        boolean canInsert = canInsertResult(result);
        boolean hasEnergy = hasEnoughEnergy(getEnergyAmount());
        
        var hasRecipe = canInsert && hasEnergy;
        updateBlockState(state.setValue(BlockStateProperties.CRAFTING, hasRecipe));
        PropertyHelper.setValues(state, isCrafting(), BlockStateProperties.POWERED);
        return hasRecipe;
    }

    /**
     * Checks if the result can be inserted into any of the output slots.
     */
    protected boolean canInsertResult(ItemStack result) {
        var outputSlots = getOutputSlots();
        for (int outputSlot : outputSlots.toArray()) {
            if (canInsertAmount(result.getCount(), 0, outputSlot, getInventory()) && 
                canInsertItem(getInventory(), result.getItem(), outputSlot)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void craftItem (ItemStack result) {
        consumeInputs();
        insertResult(result);
        
        isCrafting.set(false);
        progress.set(0f);
        PropertyHelper.setValues(getBlockState(), isCrafting(), BlockStateProperties.POWERED);
    }

    /**
     * Consumes items from input slots based on the current recipe.
     * This method should be overridden by subclasses for custom consumption logic.
     */
    protected void consumeInputs() {
        var recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;
        
        var inputSlots = getInputSlots();
        for (int slot : inputSlots.toArray()) {
            var stack = getInventory().getStackInSlot(slot);
            if (stack != ItemStack.EMPTY) {
                getInventory().extractItem(slot, 1, false);
                break;
            }
        }
    }

    /**
     * Inserts the result into the first available output slot.
     */
    protected void insertResult(ItemStack result) {
        var outputSlots = getOutputSlots();
        for (int outputSlot : outputSlots.toArray()) {
            var currentStack = getInventory().getStackInSlot(outputSlot);
            if (currentStack == ItemStack.EMPTY) {
                getInventory().setStackInSlot(outputSlot, result.copy());
                return;
            } else if (currentStack.is(result.getItem()) && 
                      currentStack.getCount() + result.getCount() <= currentStack.getMaxStackSize()) {
                getInventory().setStackInSlot(outputSlot, new ItemStack(result.getItem(), 
                    currentStack.getCount() + result.getCount()));
                return;
            }
        }
    }

    @Override
    public void saveClientData (CompoundTag tag, HolderLookup.Provider registries) {
        super.saveClientData(tag, registries);
        tag.putFloat(Keys.PROGRESS, getProgress());
        tag.putBoolean(Keys.CRAFTING, isCrafting());
    }

    @Override
    public void loadClientData (CompoundTag tag, HolderLookup.Provider registries) {
        super.loadClientData(tag, registries);
        progress.set(tag.getFloat(Keys.PROGRESS));
        isCrafting.set(tag.getBoolean(Keys.CRAFTING));
    }

    @Override
    public void setLevel(@NotNull Level level) {
        super.setLevel(level);
    }

    @Override
    public boolean isCrafting () {
        return isCrafting.getAsBoolean();
    }

    @Override
    public int getScaledProgress() {
        var progress = (int) getProgress();
        var max = getMaxProgress();
        var arrowSize = 26;
        return max != 0 && progress != 0 ? progress * arrowSize / max : 0;
    }

    /**
     * Returns the input slots for this crafter.
     * By default, returns a single slot for backward compatibility.
     * Override this method to support multiple input slots.
     */
    public abstract Range getInputSlots();

    /**
     * Returns the output slots for this crafter.
     * By default, returns a single slot for backward compatibility.
     * Override this method to support multiple output slots.
     */
    public abstract Range getOutputSlots();

}
