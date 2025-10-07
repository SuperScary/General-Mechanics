package dimensional.core.api.entity.block;

import dimensional.core.api.attributes.Attribute;
import dimensional.core.api.energy.EnergizedCrafter;
import dimensional.core.api.recipe.CoreRecipe;
import dimensional.core.api.util.Range;
import dimensional.core.api.util.data.Keys;
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

        for (var recipe : recipeManager.getAllRecipesFor((RecipeType) recipeType)) {
            RecipeHolder<?> recipeHolder = (RecipeHolder<?>) recipe;
            var recipeValue = recipeHolder.value();

            var recipeInput = createRecipeInput(inputSlots.toArray());
            try {
                if (((Recipe) recipeValue).matches((RecipeInput) recipeInput, level)) {
                    return Optional.of((RecipeHolder<T>) recipe);
                }
            } catch (ClassCastException e) {
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
        return this.getEnergyStorage().getEnergyStored() >= required;
    }

    @Override
    public void tick (Level level, BlockPos pos, BlockState state) {
        if (Utils.isDevEnvironment()) getEnergyStorage().receiveEnergy(10_000, false); // TODO: Dev only environment
        if (level.isClientSide()) {
            return;
        }

        // Check if any input slots have items
        boolean hasInputs = Arrays.stream(getInputSlots().toArray())
                .anyMatch(slot -> getInventory().getStackInSlot(slot) != ItemStack.EMPTY);

        boolean canCraft = hasRecipe(state) && hasInputs && hasEnoughEnergy(getEnergyAmount());
        
        if (canCraft) {
            isCrafting.set(true);
            increaseCraftingProgress();
            getEnergyStorage().extractEnergy(getEnergyAmount(), false);
            setChanged();

            if (hasFinished()) {
                craftItem(Objects.requireNonNull(getRecipeResultItem(level)));
                progress.set(0f);
                isCrafting.set(false);
                setChanged();
            }
        }

        if (!canCraft && isCrafting.getAsBoolean()) {
            isCrafting.set(false);
            progress.set(0f);
            setChanged();
        }

        if (isCrafting.getAsBoolean() && getProgress() > 0) {
            setChanged();
        }

        updateCraftingState(state, canCraft);
    }

    @Override
    public boolean hasRecipe (BlockState state) {
        var recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }
        var result = getRecipeResultItem(Objects.requireNonNull(getLevel()));
        if (result == null) {
            return false;
        }

        boolean canInsert = canInsertResult(result);
        boolean hasEnergy = this.getEnergyStorage().getEnergyStored() >= getEnergyAmount();
        
        return canInsert && hasEnergy;
    }

    /**
     * Updates the block state based on the current crafting status.
     * This method handles both the CRAFTING and POWERED properties.
     */
    protected void updateCraftingState(BlockState state, boolean canCraft) {
        boolean currentlyCrafting = isCrafting.getAsBoolean();
        boolean shouldBePowered = canCraft || currentlyCrafting;

        if (state.getValue(BlockStateProperties.CRAFTING) != canCraft) {
            setChanged();
        }

        if (state.getValue(BlockStateProperties.POWERED) != shouldBePowered) {
            updateBlockState(state.setValue(BlockStateProperties.POWERED, shouldBePowered));
            setChanged();
        }
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
        float currentProgress = getProgress();
        boolean currentCrafting = isCrafting();
        
        tag.putFloat(Keys.PROGRESS, currentProgress);
        tag.putBoolean(Keys.CRAFTING, currentCrafting);
    }

    @Override
    public void loadClientData (CompoundTag tag, HolderLookup.Provider registries) {
        super.loadClientData(tag, registries);
        float newProgress = tag.getFloat(Keys.PROGRESS);
        boolean newCrafting = tag.getBoolean(Keys.CRAFTING);
        
        progress.set(newProgress);
        isCrafting.set(newCrafting);
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
