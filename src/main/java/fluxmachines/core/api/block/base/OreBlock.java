package fluxmachines.core.api.block.base;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;

public class OreBlock extends DropExperienceBlock {

    public OreBlock(IntProvider xpRange, Properties properties) {
        super(xpRange, properties);
    }

    public OreBlock(int min, int max, Type type) {
        this(UniformInt.of(min, max), type.getProperties());
    }

    public OreBlock (int min, int max, Properties properties) {
        this(UniformInt.of(min, max), properties);
    }

    public enum Type {
        STONE(Blocks.IRON_ORE.properties()),
        DEEPSLATE(Blocks.DEEPSLATE_IRON_ORE.properties()),
        NETHER(Blocks.NETHER_GOLD_ORE.properties()),
        END(Blocks.END_STONE.properties());

        private final Properties properties;
        Type(Properties properties) {
            this.properties = properties;
        }

        public Properties getProperties () {
            return properties;
        }
    }

}
