package general.mechanics.datagen.data;

import general.mechanics.GM;
import general.mechanics.api.util.IDataProvider;
import general.mechanics.registries.CoreSounds;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import org.jetbrains.annotations.NotNull;

public class SoundProvider extends SoundDefinitionsProvider implements IDataProvider {

    public SoundProvider (PackOutput output, ExistingFileHelper helper) {
        super(output, GM.MODID, helper);
    }

    @Override
    public @NotNull String getName () {
        return GM.NAME + " Sounds";
    }

    @Override
    public void registerSounds () {
        add(CoreSounds.PLASTIC_BLOCK_PLACE, SoundDefinition.definition()
                .with(sound(GM.MODID + ":plastic_block_place", SoundDefinition.SoundType.SOUND)
                        .volume(1.f).pitch(1.f).weight(1).stream(true))
                .subtitle("subtitles.gm.plastic_block_place"));

        add(CoreSounds.PLASTIC_BLOCK_BREAK, SoundDefinition.definition()
                .with(sound(GM.MODID + ":plastic_block_break", SoundDefinition.SoundType.SOUND)
                        .volume(1.f).pitch(1.f).weight(1).stream(true))
                .subtitle("subtitles.gm.plastic_block_break"));
    }

}

