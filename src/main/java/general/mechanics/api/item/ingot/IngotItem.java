package general.mechanics.api.item.ingot;

import general.mechanics.api.item.base.BaseItem;
import lombok.Getter;

public class IngotItem extends BaseItem {

    @Getter
    private final Properties properties;

    @Getter
    private final RawItem rawItem;

    @Getter
    private final NuggetItem nuggetItem;

    @Getter
    private final int tint;

    public IngotItem(Properties properties, int tint) {
        super(properties);
        this.tint = tint;
        this.properties = properties;
        this.rawItem = new RawItem(this);
        this.nuggetItem = new NuggetItem(this);
    }

}
