package mechanical_force.content;

import arc.graphics.Color;
import mechanical_force.type.MPItem;
import mindustry.content.Items;

/**
 * @author dg
 */
public class MFItems {
    public static MPItem
            coal, iron,lead, copper, gold, leadZinc, nickel, tin, starch, plastic, sand, saltpeter,
            sulfide, blackPowder, carbonFiber, glass
            ;

    /*

     * ((20240725154244-ri31nrz "淀粉"))
     * ((20240725154309-5rkz0lw "塑钢"))
     * ((20240725154331-gk9v045 "沙子"))
     *  ((20240725154349-rovf956 "火硝"))
     * ((20240725154400-oxhg1ut "硫化物"))
     *  ((20240725154412-7cqb1if "黑火药"))
     *  ((20240725154422-to9pp4p "碳纤维"))
     *  ((20240725154427-d0fx0oi "玻璃"))
     * ((20240725154559-l3d9x5v "铜"))
     * ((20240725154607-2s4mwjw "铅"))
     */
    public static void load() {
        coal = new MPItem("MP-coal", new Color(0, 0, 0), "煤", Items.coal);
        copper = new MPItem("MP-copper", new Color(255, 140, 0), "铜",Items.copper);
        iron = new MPItem("MP-iron", new Color(192, 192, 192), "铁");
        lead = new MPItem("MP-lead", Color.valueOf("2s4mwjw"), "铅", Items.lead);
        gold = new MPItem("MP-gold", new Color(255, 215, 0), "金");
        leadZinc = new MPItem("MP-lead-zinc", new Color(165, 165, 165), "铅锌");
        nickel = new MPItem("MP-nickel", new Color(185, 185, 185), "镍");
        tin = new MPItem("MP-tin", new Color(220, 220, 220), "锡");
        starch = new MPItem("MP-starch", Color.valueOf("ri31nrz"), "淀粉");
        plastic = new MPItem("MP-plastic", Color.valueOf("5rkz0lw"), "塑钢");
        sand = new MPItem("MP-sand", Color.valueOf("gk9v045"), "沙子",Items.sand);
        saltpeter = new MPItem("MP-saltpeter", Color.valueOf("rovf956"), "火硝");
        sulfide = new MPItem("MP-sulfide", Color.valueOf("oxhg1ut"), "硫化物", Items.pyratite);
        blackPowder = new MPItem("MP-blackPowder", Color.valueOf("7cqb1if"), "黑火药");
        carbonFiber = new MPItem("MP-carbonFiber", Color.valueOf("to9pp4p"), "碳纤维");
        glass = new MPItem("MP-glass", Color.valueOf("d0fx0oi"), "玻璃",Items.metaglass);

    }
}
