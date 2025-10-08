package fluxmachines.core.datagen.data;

import fluxmachines.core.FluxMachines;
import fluxmachines.core.api.util.IDataProvider;
import fluxmachines.core.registries.CoreSounds;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import org.jetbrains.annotations.NotNull;

public class SoundProvider extends SoundDefinitionsProvider implements IDataProvider {

    public SoundProvider (PackOutput output, ExistingFileHelper helper) {
        super(output, FluxMachines.MODID, helper);
    }

    @Override
    public @NotNull String getName () {
        return FluxMachines.NAME + " Sounds";
    }

    @Override
    public void registerSounds () {
        add(CoreSounds.PLASTIC_BLOCK_PLACE, SoundDefinition.definition()
                .with(sound(FluxMachines.MODID + ":plastic_block_place", SoundDefinition.SoundType.SOUND)
                        .volume(1.f).pitch(1.f).weight(1).stream(true))
                .subtitle("subtitles.fluxmachines.plastic_block_place"));

        add(CoreSounds.PLASTIC_BLOCK_BREAK, SoundDefinition.definition()
                .with(sound(FluxMachines.MODID + ":plastic_block_break", SoundDefinition.SoundType.SOUND)
                        .volume(1.f).pitch(1.f).weight(1).stream(true))
                .subtitle("subtitles.fluxmachines.plastic_block_break"));
    }

}

