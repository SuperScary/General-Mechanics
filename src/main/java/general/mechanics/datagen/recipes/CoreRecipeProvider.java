package general.mechanics.datagen.recipes;

import general.mechanics.api.util.IDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public abstract class CoreRecipeProvider extends RecipeProvider implements IDataProvider {

    public CoreRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

}
