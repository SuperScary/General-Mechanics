package general.mechanics.block.machine.part;

import general.mechanics.api.entity.block.BaseBlockEntity;
import general.mechanics.api.entity.block.BaseEntityBlock;

public abstract class MachinePart<T extends BaseBlockEntity> extends BaseEntityBlock<T> {

    public MachinePart(Properties properties) {
        super(properties);
    }

}
