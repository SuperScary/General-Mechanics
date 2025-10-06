package dimensional.core.api.tags;

import dimensional.core.DimensionalCore;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CoreTags {

    public static class Items {
        public static final TagKey<Item> RAW_PLASTIC = createTag("raw_plastic");
        public static final TagKey<Item> PLASTIC = createTag("plastic");

        public static final TagKey<Item> WRENCHES = createTag("wrenches");

        private static TagKey<Item> createTag (String key) {
            return ItemTags.create(DimensionalCore.getResource(key));
        }
    }

}
