package mechanical_force.content;

import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import mechanical_force.annotations.Annotations.Load;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.Category;
import mindustry.type.Planet;
import mindustry.world.Block;
import mindustry.world.blocks.production.Drill;

import static mindustry.Vars.state;
import static mindustry.type.ItemStack.with;

/**
 * @author dg
 * <br>
 * 介绍:阉割原版的建筑
 */
public class EmasculateBlocks {
    public static Block
            //drill
            e_blastDrill;
    public static Seq<Block> emasculateBlocks;

    public static void load(){
        emasculateBlocks = new Seq<>();
        e_blastDrill = new Drill("e-blast-drill"){
            public @Load("blast-drill-rim") TextureRegion rimRegion;
            public @Load("blast-drill-rotator") TextureRegion rotatorRegion;
            public @Load("blast-drill-top") TextureRegion topRegion;
            public @Load(value = "blast-drill-item", fallback = "drill-item-@size") TextureRegion itemRegion;
            {
            localizedName = "爆破钻头";
            requirements(Category.production, with(Items.copper, 70, Items.silicon, 65, Items.titanium, 50, Items.thorium, 60));
            drillTime = 300;
            size = 4;
            drawRim = true;
            hasPower = true;
            tier = 5;
            updateEffect = Fx.pulverizeRed;
            updateEffectChance = 0.03f;
            drillEffect = Fx.mineHuge;
            rotateSpeed = 6f;
            warmupSpeed = 0.01f;
            itemCapacity = 25;

            consumePower(2.9f);
            consumeLiquid(Liquids.water, 0.1f).boost();
        }

            @Override
            public boolean isHidden() {
            return state == null || state.rules == null || state.rules.planet == null || state.rules.planet != MFPlanets.earth || super.isHidden();
            }

            @Override
            public boolean isVisible() {
                return !isHidden() && (state.rules.editor || (!state.rules.hideBannedBlocks || !state.rules.isBanned(this)));
            }

            @Override
            public boolean isVisibleOn(Planet planet) {
                return planet == MFPlanets.earth && super.isVisibleOn(planet);
            }

            @Override
            public void loadIcon() {
                super.loadIcon();
                uiIcon = Blocks.blastDoor.uiIcon;
                fullIcon = Blocks.blastDoor.fullIcon;
            }
        };
        emasculateBlocks.add(Blocks.blastDrill);
    }
}
