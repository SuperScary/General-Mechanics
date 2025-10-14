package general.mechanics.api.item.ingot;

import general.mechanics.api.item.base.BaseItem;

public class IngotItem extends BaseItem {

    private final Properties properties;
    private final RawItem raw;
    private final NuggetItem nugget;
    private final int tint;

    public IngotItem(Properties properties, int tint) {
        super(properties);
        this.tint = tint;
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

    public int getTint() {
        return tint;
    }

}
