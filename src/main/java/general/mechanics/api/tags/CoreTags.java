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

        // Plastic type specific tags
        public static final TagKey<Item> POLYETHYLENE = createTag("polyethylene");
        public static final TagKey<Item> POLYPROPYLENE = createTag("polypropylene");
        public static final TagKey<Item> POLYSTYRENE = createTag("polystyrene");
        public static final TagKey<Item> POLYVINYL_CHLORIDE = createTag("polyvinyl_chloride");
        public static final TagKey<Item> POLYETHYLENE_TEREPHTHALATE = createTag("polyethylene_terephthalate");
        public static final TagKey<Item> ACRYLONITRILE_BUTADIENE_STYRENE = createTag("acrylonitrile_butadiene_styrene");
        public static final TagKey<Item> POLYCARBONATE = createTag("polycarbonate");
        public static final TagKey<Item> NYLON = createTag("nylon");
        public static final TagKey<Item> POLYURETHANE = createTag("polyurethane");
        public static final TagKey<Item> POLYTETRAFLUOROETHYLENE = createTag("polytetrafluoroethylene");
        public static final TagKey<Item> POLYETHERETHERKETONE = createTag("polyetheretherketone");

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
