package fluxmachines.core.entity.block;

import fluxmachines.core.api.attributes.Attribute;
import fluxmachines.core.api.entity.block.BaseEnergyCrafter;
import fluxmachines.core.api.inventory.CoreItemStackHandler;
import fluxmachines.core.api.util.Range;
import fluxmachines.core.api.util.data.Keys;
import fluxmachines.core.gui.menu.MatterFabricatorMenu;
import fluxmachines.core.recipes.FabricationRecipe;
import fluxmachines.core.registries.CoreRecipes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MatterFabricatorBlockEntity extends BaseEnergyCrafter<FabricationRecipe> {

    public MatterFabricatorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        this(type, pos, blockState, 0);
    }

    public MatterFabricatorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int current) {
        super(type, pos, blockState, Attribute.Builder.of(Keys.MAX_POWER, 100_000), Attribute.Builder.of(Keys.MAX_DRAIN, 256), Attribute.Builder.of(Keys.POWER, current));
    }

    @Override
    public CoreItemStackHandler createInventory () {
        return new CoreItemStackHandler(4);
    }

    @Override
    public AbstractContainerMenu menu (int id, Inventory playerInventory, Player player) {
        return new MatterFabricatorMenu(id, playerInventory, this);
    }

    @Override
    public @NotNull Component getDisplayName () {
        return Component.translatable("block.fluxmachines.refabricator");
    }

    @Override
    public int getEnergyAmount () {
        return 8;
    }

    @Override
    public Range getInputSlots() {
        // Slots 0, 1, 2 are input slots for the refabricator
        return new Range(0, 2);
    }

    @Override
    public Range getOutputSlots() {
        // Slot 3 is the output slot
        return new Range(3, 0);
    }

    @Override
    protected Object createRecipeInput(int[] inputSlots) {
        // Create a RefabricationRecipeInput from the input slots
        ItemStack[] stacks = new ItemStack[inputSlots.length];
        for (int i = 0; i < inputSlots.length; i++) {
            stacks[i] = getInventory().getStackInSlot(inputSlots[i]);
        }
        return new FabricationRecipe.FabricationRecipeInput(stacks);
    }

    @Override
    protected void consumeInputs() {
        var recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;
        
        var recipeValue = recipe.get().value();
        var inputSlots = getInputSlots();
        
        // Get all non-empty input stacks
        List<ItemStack> inputStacks = new ArrayList<>();
        for (int slot : inputSlots.toArray()) {
            var stack = getInventory().getStackInSlot(slot);
            if (stack != ItemStack.EMPTY) {
                inputStacks.add(stack);
            }
        }
        
        // For each ingredient in the recipe, consume one matching item
        for (var ingredient : recipeValue.getIngredients()) {
            if (ingredient.isEmpty()) continue;
            
            // Find and consume one item that matches this ingredient
            for (int slot : inputSlots.toArray()) {
                var stack = getInventory().getStackInSlot(slot);
                if (stack != ItemStack.EMPTY && ingredient.test(stack)) {
                    getInventory().extractItem(slot, 1, false);
                    break;
                }
            }
        }
    }

    @Override
    public RecipeType<FabricationRecipe> getRecipeType() {
        return CoreRecipes.FABRICATION_RECIPE_TYPE.get();
    }

}
