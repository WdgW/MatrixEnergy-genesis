package mechanical_force.type;

import arc.graphics.Color;
import mindustry.type.Liquid;

/**
 * @author dg
 */
public class MPLiquid extends Liquid {
    public MPLiquid(String name, Color color, String localizedName) {
        super(name,color);
        this.localizedName = localizedName;
    }
}
