package dimensional.core.datagen.recipes;

import dimensional.core.api.util.IDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public abstract class ModRecipeProvider extends RecipeProvider implements IDataProvider {

    public ModRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

}
