package mechanical_force.content;

import arc.graphics.Color;
import mechanical_force.type.MPLiquid;
import mindustry.content.Liquids;

/**
 * @author dg
 */
public class MFLiquids {
    public static MPLiquid water, heavyWater, pureWater,
            ethanol, ethylene, ethanolFrozen,crudeOil,
            mineralOil, glycerin, propyleneGlycol,
            co2
            ;

    /*
     * ((20240725154222-0ez4m2i "CO₂"))
     * ((20240725154156-8x6wsus "水"))
     * ((20240725154438-sjkj1uc "重水"))
     * ((20240725154449-qipjubc "纯水"))
     *  ((20240725154301-0ba77ce "乙烯"))
     *  ((20240725154250-9bzl9zb "乙醇"))
     * ((20240725154531-xwvq1pn "无水型丙二醇冷冻液"))）
     *  ((20240725154524-hrhu6az "甘油"))
     * （((20240725154518-0e2r6j5 "矿物油"))
     * ((20240725154507-ehyjwfy "含水型乙醇冷冻液"))
     * ((20240725154321-asicgs9 "原油"))

     */
    public static void load(){
        water = new MPLiquid("MP-water", Color.valueOf("596ab8"), "水"){
            @Override
            public void loadIcon() {
                super.loadIcon();
                fullIcon = Liquids.water.fullIcon;
                uiIcon = Liquids.water.uiIcon;
            }
        };
        heavyWater = new MPLiquid("MP-heavyWater", Color.valueOf("sjkj1uc"), "重水");
        pureWater = new MPLiquid("MP-pureWater", Color.valueOf("qipjubc"), "纯水");
        ethanol = new MPLiquid("MP-ethanol", Color.valueOf("9bzl9zb"), "乙醇");
        ethylene = new MPLiquid("MP-ethylene", Color.valueOf("0ba77ce"), "乙烯");
        crudeOil = new MPLiquid("MP-crudeOil", Color.valueOf("asicgs9"), "原油");
        ethanolFrozen = new MPLiquid("MP-ethanolFrozen", Color.valueOf("ehyjwfy"), "含水型乙醇冷冻液");
        mineralOil = new MPLiquid("MP-mineralOil", Color.valueOf("0e2r6j5"), "矿物油");
        glycerin = new MPLiquid("MP-glycerin", Color.valueOf("hrhu6az"), "甘油");
        propyleneGlycol = new MPLiquid("MP-propyleneGlycol", Color.valueOf("xwvq1pn"), "无水型丙二醇冷冻液");
        co2 = new MPLiquid("MP-co2", Color.valueOf("0ez4m2i"), "CO₂"){
            {
                gas = true;
            }
        };
    }
}
