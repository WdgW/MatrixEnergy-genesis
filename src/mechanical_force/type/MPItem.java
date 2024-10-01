package mechanical_force.type;

import arc.graphics.Color;
import arc.util.Nullable;
import mindustry.type.Item;

/**
 * @author dg
 */
public class MPItem extends Item {
public @Nullable Item imitateIcon;
    public MPItem(String name, Color color) {
        this(name, color, name);
    }

    public MPItem(String name, Color color, String localizedName) {
        this(name,color,localizedName,null);
    }
    public MPItem(String name, Color color, String localizedName, Item imitateIcon) {
        super(name, color);
        this.localizedName = localizedName;
        this.imitateIcon = imitateIcon;
    }

    public MPItem(String name) {
        this(name,Color.black.cpy());
    }

    @Override
    public void loadIcon() {
        super.loadIcon();
        if (imitateIcon != null) {
            if (fullIcon == null) {
                fullIcon = imitateIcon.fullIcon;
            }
            if (uiIcon == null) {
                uiIcon = imitateIcon.uiIcon;
            }
        }
    }
}
