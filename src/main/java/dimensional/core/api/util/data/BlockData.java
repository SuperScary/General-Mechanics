package dimensional.core.api.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

/**
 * Interface for saving and loading client data.
 */
public interface BlockData {

    /**
     * Save client data.
     *
     * @param tag the tag
     * @param registries the registries
     */
    void saveClientData (CompoundTag tag, HolderLookup.Provider registries);

    /**
     * Load client data.
     *
     * @param tag the tag
     * @param registries the registries
     */
    void loadClientData (CompoundTag tag, HolderLookup.Provider registries);

}
