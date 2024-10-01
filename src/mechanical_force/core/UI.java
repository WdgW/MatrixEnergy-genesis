package mechanical_force.core;

import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.ui.fragments.MenuFragment.MenuButton;

import static mindustry.Vars.ui;

/**
 * @author dg
 */
public class UI{

    public static void load() {
            menufragAddButton(new MenuButton("@galaxy", Icon.planet,()-> ui.planet.show()));

    }
    public static void menufragAddButton(MenuButton... menuButtons){
        for (MenuButton menuButton : menuButtons) {
            Vars.ui.menufrag.addButton(menuButton);
        }
    }
}
