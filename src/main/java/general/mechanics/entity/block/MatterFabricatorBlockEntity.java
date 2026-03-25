package general.mechanics.entity.block;

import general.mechanics.api.attributes.Attribute;
import general.mechanics.api.entity.block.BaseEnergyCrafter;
import general.mechanics.api.inventory.CoreItemStackHandler;
import general.mechanics.api.util.Range;
import general.mechanics.api.util.data.Keys;
import general.mechanics.gui.menu.MatterFabricatorMenu;
import general.mechanics.recipes.FabricationRecipe;
import general.mechanics.registries.CoreRecipes;
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
        return Component.translatable("block.gm.matter_fabricator");
    }

    @Override
    public int getEnergyAmount () {
        var recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            float fePerTick = recipe.get().value().powerIngredient().fePerTick();
            return Math.max(0, (int) Math.ceil(fePerTick));
        }
        return Math.max(0, (int) Math.ceil(FabricationRecipe.DEFAULT_POWER.fePerTick()));
    }

    @Override
    public int getMaxProgress() {
        var recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            float ticks = recipe.get().value().craftingTime().ticks();
            return Math.max(1, (int) Math.ceil(ticks));
        }
        return Math.max(1, (int) Math.ceil(FabricationRecipe.DEFAULT_CRAFTING_TIME.ticks()));
    }

    @Override
    public Range getInputSlots() {
        return Range.inclusive(0, 2);
    }

    @Override
    public Range getOutputSlots() {
        // Slot 3 is the output slot
        return Range.single(3);
    }

    @Override
    protected Object createRecipeInput(int[] inputSlots) {
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
        
        boolean[] used = new boolean[inputSlots.toArray().length];
        var required = recipeValue.inputItems();

        for (var req : required) {
            if (req.ingredient().isEmpty()) continue;

            boolean matched = false;
            int[] slotArr = inputSlots.toArray();
            for (int i = 0; i < slotArr.length; i++) {
                if (used[i]) continue;

                int slot = slotArr[i];
                var stack = getInventory().getStackInSlot(slot);
                if (stack == ItemStack.EMPTY) continue;
                if (stack.getCount() < req.count()) continue;
                if (!req.ingredient().test(stack)) continue;

                getInventory().extractItem(slot, req.count(), false);
                used[i] = true;
                matched = true;
                break;
            }

            if (!matched) {
                return;
            }
        }
    }

    @Override
    public RecipeType<FabricationRecipe> getRecipeType() {
        return CoreRecipes.FABRICATION_RECIPE_TYPE.get();
    }

}
