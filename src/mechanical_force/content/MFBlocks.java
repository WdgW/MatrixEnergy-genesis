package mechanical_force.content;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Time;
import mechanical_force.force.ForceConveyor;
import mechanical_force.force.PropShaft;
import mechanical_force.world.blocks.CoreShare;
import mechanical_force.world.blocks.environment.MPOreBlock;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.Conveyor.ConveyorBuild;
import mindustry.world.blocks.storage.CoreBlock.CoreBuild;
import mindustry.world.blocks.storage.Unloader;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

/**
 * @author dg
 */
public class MFBlocks {
    public static Block
            //forceBlock
            propShaft,
    //ore
    oreCoal, oreIron, oreCopper, oreGold, oreLeadZinc, oreNickel, oreTin, share, transmit
            ,forceConveyor;

    public static void load() {
        oreCoal = new MPOreBlock(MFItems.coal);
        oreIron = new MPOreBlock(MFItems.iron);
        oreCopper = new MPOreBlock(MFItems.copper);
        oreGold = new MPOreBlock(MFItems.gold);
        oreLeadZinc = new MPOreBlock(MFItems.leadZinc);
        oreNickel = new MPOreBlock(MFItems.nickel);
        oreTin = new MPOreBlock(MFItems.tin);
//        forceBlock = new ForceCore("f", new Force(new BigDecimal("1.1")), "动力核心"){{
//            requirements(Category.distribution,new ItemStack[]{new ItemStack(MFItems.iron,100)});
//            health = 100;
//        }};
        propShaft = new PropShaft("propShaft", "传动轴") {
            {
                requirements(Category.distribution, ItemStack.with(MFItems.iron, 100));
                health = 100;
            }
        };
        share = new CoreShare("share", "核心物资") {
            {
                requirements(Category.distribution, ItemStack.with(MFItems.iron, 100));
            }
        };
        transmit = new Unloader("transmit") {
            public final int range;
            {
                this.update = true;
                this.hasItems = true;
                this.destructible = true;
                this.localizedName = "传输器";
                this.itemCapacity = 100;
                this.acceptsItems = true;
                this.range = 45;
                requirements(Category.distribution, ItemStack.with(MFItems.iron, 100));
                this.buildType = () -> new UnloaderBuild() {
                    @Override
                    public void updateTile() {
                        super.updateTile();
                        if (sortItem == null) {
                            return;
                        }
                        CoreBuild core = this.team.core();
                        int item = items.get(sortItem), maximumAccepted = this.getMaximumAccepted(sortItem), coreItem = core.items.get(sortItem);
                        if (item < maximumAccepted && coreItem > 0) {
                            if (coreItem >= (maximumAccepted - item)) {
                                core.items.remove(sortItem, (maximumAccepted - item));
                                items.add(sortItem, (maximumAccepted - item));
                            }
                            else {
                                core.items.remove(sortItem, 1);
                                items.add(sortItem, 1);
                            }
                            Fx.itemTransfer.at(team.core().x,team.core().y,3,sortItem.color,this);
                        }
                        Vars.indexer.eachBlock(this, range, o -> o.acceptItem(this, sortItem) && o.items != null || !(o instanceof CoreBuild), other -> {
                            if (items.get(sortItem) > 0 && other.acceptItem(this, sortItem) && !(other instanceof ConveyorBuild) && other.items != null && other.items.length() >= sortItem.id + 1) {
                                if ((other.getMaximumAccepted(sortItem) - other.items.get(sortItem)) >= items.get(sortItem)) {
                                    int a = items.get(sortItem);
                                    items.remove(sortItem, a);
                                    other.handleStack(sortItem,a,this);
                                }
                                else {
                                    int a = other.getMaximumAccepted(sortItem) - other.items.get(sortItem);
                                    items.remove(sortItem, a);
                                    other.handleStack(sortItem,a,this);
                                }
                                Fx.itemTransfer.at(x,y,2,sortItem.color,other);
                                Draw.reset();
                                Draw.color(sortItem.color);
                                Lines.square(other.x, other.y, (float) (other.block.size * Vars.tilesize) / 2 + 1, 45);
                                Draw.flush();
                                Draw.reset();
                            }
                        });
                    }

                    @Override
                    public boolean acceptItem(Building source, Item item) {
                        return item == this.sortItem && this.items.get(item) < this.getMaximumAccepted(sortItem);
                    }

                    @Override
                    public void drawConfigure() {
                        super.drawConfigure();
                        Drawf.dashCircle(this.x, this.y, range, Pal.accent);
                    }

                    @Override
                    public void draw() {
                        super.draw();
                        if (sortItem == null) return;
                        Draw.color(sortItem.color);
                        Draw.alpha(0.05f * Time.time);
                        Lines.square(x,y, 8 * Mathf.sin(0.025f*Time.time), 45);
                    
                };
            };
            }

            @Override
            public boolean outputsItems() {
                return false;
            }

            @Override
            public void setStats() {
                super.setStats();
                stats.remove(Stat.speed);
                stats.add(Stat.range, (float) range / Vars.tilesize, StatUnit.blocks);
            }

            @Override
            public void drawPlace(int x, int y, int rotation, boolean valid) {
                super.drawPlace(x, y, rotation, valid);
                Drawf.dashCircle(x * Vars.tilesize +this.offset, y * Vars.tilesize + this.offset, range, Pal.accent);
            }
        };
        forceConveyor = new ForceConveyor("forceConveyor"){{
            localizedName = "动力传送带";
            speed = 0.03f;
            requirements(Category.distribution, ItemStack.with(MFItems.iron, 100));
        }};

    }
}
