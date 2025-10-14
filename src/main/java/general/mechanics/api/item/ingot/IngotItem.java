package general.mechanics.api.item.ingot;

import general.mechanics.api.item.base.BaseItem;

public class IngotItem extends BaseItem {

    private final Properties properties;
    private final RawItem raw;
    private final NuggetItem nugget;

    public IngotItem(Properties properties) {
        super(properties);
        this.properties = properties;
        this.raw = new RawItem(this);
        this.nugget = new NuggetItem(this);
    }

    public RawItem getRawItem() {
        return raw;
    }

    public NuggetItem getNuggetItem() {
        return nugget;
    }

    public Properties getProperties() {
        return properties;
    }

}
