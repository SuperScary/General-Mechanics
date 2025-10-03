package dimensional.core.api.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * World data for persisting multiblocks across sessions.
 * This class handles saving and loading multiblock states to/from NBT.
 */
public class MultiblockWorldData extends SavedData {
    
    private static final String DATA_NAME = "dimensionalcore_multiblocks";
    
    private final Map<BlockPos, MultiblockInfo> multiblocks = new HashMap<>();
    
    public MultiblockWorldData() {
        // Default constructor
    }
    
    public MultiblockWorldData(Map<BlockPos, MultiblockInfo> multiblocks) {
        this.multiblocks.putAll(multiblocks);
    }
    
    /**
     * Get the multiblock world data for the given level.
     */
    public static MultiblockWorldData get(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(
            new SavedData.Factory<MultiblockWorldData>(MultiblockWorldData::new, MultiblockWorldData::load),
            DATA_NAME
        );
    }
    
    /**
     * Load multiblock data from NBT.
     */
    public static MultiblockWorldData load(CompoundTag tag, net.minecraft.core.HolderLookup.Provider provider) {
        Map<BlockPos, MultiblockInfo> multiblocks = new HashMap<>();
        
        ListTag multiblockList = tag.getList("multiblocks", Tag.TAG_COMPOUND);
        for (int i = 0; i < multiblockList.size(); i++) {
            CompoundTag multiblockTag = multiblockList.getCompound(i);
            
            // Load position
            BlockPos pos = BlockPos.of(multiblockTag.getLong("pos"));
            
            // Load definition ID
            ResourceLocation definitionId = ResourceLocation.parse(multiblockTag.getString("definition"));
            MultiblockDefinition definition = MultiblockRegistry.REGISTRY.get(definitionId);
            if (definition == null) {
                System.err.println("WARNING: Could not find multiblock definition " + definitionId + " when loading world data");
                continue;
            }
            
            // Load facing
            String facingName = multiblockTag.getString("facing");
            net.minecraft.core.Direction facing = net.minecraft.core.Direction.byName(facingName);
            if (facing == null) {
                System.err.println("WARNING: Invalid facing direction " + facingName + " when loading world data");
                continue;
            }
            
            // Load mirrored flag
            boolean mirrored = multiblockTag.getBoolean("mirrored");
            
            multiblocks.put(pos, new MultiblockInfo(definition, pos, facing, mirrored));
        }
        
        return new MultiblockWorldData(multiblocks);
    }
    
    @Override
    public CompoundTag save(CompoundTag tag, net.minecraft.core.HolderLookup.Provider provider) {
        ListTag multiblockList = new ListTag();
        
        for (Map.Entry<BlockPos, MultiblockInfo> entry : multiblocks.entrySet()) {
            BlockPos pos = entry.getKey();
            MultiblockInfo info = entry.getValue();
            
            CompoundTag multiblockTag = new CompoundTag();
            multiblockTag.putLong("pos", pos.asLong());
            multiblockTag.putString("definition", info.definition().id().toString());
            multiblockTag.putString("facing", info.facing().getName());
            multiblockTag.putBoolean("mirrored", info.mirrored());
            
            multiblockList.add(multiblockTag);
        }
        
        tag.put("multiblocks", multiblockList);
        return tag;
    }
    
    /**
     * Get all multiblocks in this world data.
     */
    public Map<BlockPos, MultiblockInfo> getMultiblocks() {
        return multiblocks;
    }
    
    /**
     * Add a multiblock to the world data.
     */
    public void addMultiblock(BlockPos pos, MultiblockInfo info) {
        multiblocks.put(pos, info);
        setDirty();
    }
    
    /**
     * Remove a multiblock from the world data.
     */
    public void removeMultiblock(BlockPos pos) {
        multiblocks.remove(pos);
        setDirty();
    }
    
    /**
     * Check if a multiblock exists at the given position.
     */
    public boolean hasMultiblock(BlockPos pos) {
        return multiblocks.containsKey(pos);
    }
    
    /**
     * Get a multiblock at the given position.
     */
    public MultiblockInfo getMultiblock(BlockPos pos) {
        return multiblocks.get(pos);
    }
    
    /**
     * Clear all multiblocks from the world data.
     */
    public void clear() {
        multiblocks.clear();
        setDirty();
    }
}
