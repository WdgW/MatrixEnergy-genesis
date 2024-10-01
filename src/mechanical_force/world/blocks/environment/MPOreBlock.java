package mechanical_force.world.blocks.environment;

import mechanical_force.type.MPItem;
import mindustry.world.blocks.environment.OreBlock;

/**
 * @author dg
 */
public class MPOreBlock extends OreBlock {
    public MPOreBlock(String name, MPItem ore) {
        super(name, ore);
        this.localizedName = ore.localizedName;
        this.itemDrop = ore;
        this.variants = 3;
        this.mapColor.set(ore.color);
        this.useColor = true;
    }

    public MPOreBlock(MPItem ore) {
        this("MPore-" + ore.name, ore);
    }

    public MPOreBlock(String name) {
        super(name);
    }
}
