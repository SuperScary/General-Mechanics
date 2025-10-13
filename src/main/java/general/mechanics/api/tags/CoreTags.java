package general.mechanics.api.tags;

import general.mechanics.GM;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CoreTags {

    public static class Items {
        public static final TagKey<Item> RAW_PLASTIC = createTag("raw_plastic");
        public static final TagKey<Item> PLASTIC = createTag("plastic");
        public static final TagKey<Item> PLASTIC_BLOCKS = createTag("plastic_blocks");

        public static final TagKey<Item> WRENCHES = createTag("wrenches");

        private static TagKey<Item> createTag (String key) {
            return ItemTags.create(GM.getResource(key));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> PLASTIC = createTag("plastic");

        private static TagKey<Block> createTag (String key) {
            return BlockTags.create(GM.getResource(key));
        }
    }

}
