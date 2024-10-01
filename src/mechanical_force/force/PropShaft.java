package mechanical_force.force;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mechanical_force.MechanicalForce;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.draw.DrawFrames;

import java.math.BigDecimal;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

/**
 * @author dg
 */
public class PropShaft extends Block implements ForceBlock {
    /**
     * 为保持精度采用字符串形式
     * 单位: m
     */
    public String radius = "0.2";
    public BigDecimal radiusBD;
    public DrawFrames drawer = new DrawFrames() {
        {
            frames = 12;
            interval = 1f;
            sine = false;
        }

        @Override
        public void draw(Building build) {
            Draw.rect(
                    sine ?
                    regions[(int) Mathf.absin(build.totalProgress(), interval, frames - 0.001f)] :
                    regions[(int) ((build.totalProgress() / interval) % frames)],
                    build.x, build.y, build.rotation * 90
                     );
        }

        @Override
        public void load(Block block) {
            regions = new TextureRegion[frames];
//            Log.info(Core.atlas.getRegionMap());
            for (int i = 0; i < frames; i++) {
//                regions[i] = Core.atlas.find(block.name + i);
                regions[i] = Core.atlas.find(String.format("mechanicalforce-propShaft-%d", i));
            }
        }
    };
//    TextureRegion[] regions = new TextureRegion[12];

    public PropShaft(String name, String localizedName) {
        this(name);
        this.localizedName = localizedName;

    }

    public PropShaft(String name) {
        super(name);
        rotate = true;
        update = false;
        hasShadow = false;
        underBullets = true;
        canOverdrive = false;
        destructible = true;//可破坏的

        buildType = PropShaftBuild::new;
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("torsion", (PropShaftBuild param) -> new Bar(
                () -> Core.bundle.formatString("扭矩：{0}J", param.force.getForce().size.abs().multiply(radiusBD)),
                () -> mechanical_force.graphics.Pal.forceBar,
                () -> param.force.getForce().size.abs().multiply(radiusBD).floatValue()
        ));
        addBar("force", (PropShaftBuild param) -> new Bar(
                () -> Core.bundle.formatString("功率：{0}W", param.force.getForce().size.abs()),
                () -> mechanical_force.graphics.Pal.forceBar,
                () -> param.force.getForce().size.abs().floatValue()
        ));
        addBar("forceDirection", (PropShaftBuild param) -> new Bar(
                () -> "方向: " + (param.force.getForce().size.floatValue() >= 0 ? "顺时针" : "逆时针"),
                () -> mechanical_force.graphics.Pal.forceBar,
                () -> param.force.getForce().isClockwise() ? 1 : 0
        ) {
            @Override
            public void draw() {
                super.draw();
                Draw.reset();
                Draw.color(Color.white);
                TextureRegion regionArrow = MechanicalForce.atlasFind("clockwise");
                Draw.rect(regionArrow, x + 2 * width / 3, y + height / 2, regionArrow.width, regionArrow.height, rotation);
                Draw.flush();
            }
        });
    }

    @Override
    public void drawPlan(BuildPlan plan, Eachable<BuildPlan> list, boolean valid) {

        float trns = ((float) plan.block.size / 2) * tilesize;
        int dx = Geometry.d4(plan.rotation).x, dy = Geometry.d4(plan.rotation).y;
        float offsetx = plan.x * tilesize;
        float offsety = plan.y * tilesize;
        float offsetxc = plan.x * tilesize;
        float offsetyc = plan.y * tilesize;
        Draw.color(!valid ? Pal.removeBack : Pal.accentBack);
        TextureRegion regionArrow = Core.atlas.find("place-arrow");

        Draw.rect(
                regionArrow,
                offsetx,
                offsety - 1,
                regionArrow.width * regionArrow.scl(),
                regionArrow.height * regionArrow.scl(),
                plan.rotation * 90 - 90 - 180
                 );
        Draw.rect(
                regionArrow,
                offsetxc,
                offsetyc - 1,
                regionArrow.width * regionArrow.scl(),
                regionArrow.height * regionArrow.scl(),
                plan.rotation * 90 - 90
                 );

        Draw.color(!valid ? Pal.remove : Pal.accent);
        Draw.rect(
                regionArrow,
                offsetx,
                offsety,
                regionArrow.width * regionArrow.scl(),
                regionArrow.height * regionArrow.scl(),
                plan.rotation * 90 - 90 - 180
                 );

        Draw.rect(
                regionArrow,
                offsetxc,
                offsetyc,
                regionArrow.width * regionArrow.scl(),
                regionArrow.height * regionArrow.scl(),
                plan.rotation * 90 - 90
                 );
        super.drawPlan(plan, list, valid);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        super.drawPlanRegion(plan, list);
//        Log.info("drawPlanRegion");
    }

    @Override
    public boolean isAccessible() {
        return true;
    }

    // TODO 懒得写了

//    @Override
//    public void handlePlacementLine(Seq<BuildPlan> plans) {
//        Draw.reset();
//        Log.info("handlePlacementLine  " + plans.toString());
//        plans.forEach(p -> {
//            Log.info(p.toString());
//        });
//        BuildPlan plan = plans.first();
//        float trns = ((float) plan.block.size / 2) * tilesize;
//        int dx = Geometry.d4(plan.rotation).x, dy = Geometry.d4(plan.rotation).y;
//        float offsetx = plan.x * tilesize + plan.block.offset + dx*trns;
//        float offsety = plan.y * tilesize + plan.block.offset + dy*trns;
//        Draw.color(Pal.accentBack);
//        TextureRegion regionArrow = Core.atlas.find("place-arrow");
//
//        Draw.rect(regionArrow,
//                  offsetx,
//                  offsety - 1,
//                  regionArrow.width * regionArrow.scl(),
//                  regionArrow.height * regionArrow.scl(),
//                  plan.rotation * 90 - 180);
//
//        Draw.color(Pal.accent);
//        Draw.rect(regionArrow,
//                  offsetx,
//                  offsety,
//                  regionArrow.width * regionArrow.scl(),
//                  regionArrow.height * regionArrow.scl(),
//                  plan.rotation * 90 - 180);
//        Draw.flush();
//        Draw.stencil(()->{}, ()->{});
//        super.handlePlacementLine();
//    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{this.region};
    }

    @Override
    public void init() {
        super.init();
        radiusBD = new BigDecimal(radius);
    }

    @Override
    public void loadIcon() {
        super.loadIcon();
        drawer.load(this);
        uiIcon = Core.atlas.find(name);
//        customShadowRegion = Core.atlas.find(name + "-shadow");
    }

//    @Override
//    public void drawShadow(Tile tile) {
//        super.drawShadow(tile);
//    }

    public Runnable createOnRemovedFunc(PropShaftBuild build) {
        if (build.isTransverse) {
            return () -> {
                PropShaftBuild oFirst = null, oLast = null;
                for (int i = build.block().size * tilesize; i < world.width(); i += build.block().size * tilesize) {
                    Building other = world.buildWorld(build.x + i, build.y);
                    if (other instanceof PropShaftBuild o && o.force != null && o.isTransverse) {
                        o.setFirst(null);
                        o.setLast(null);
                    }
                    else {
                        PropShaftBuild it = (PropShaftBuild) world.buildWorld(build.x + i - build.block().size * tilesize, build.y);
                        it.setLast(it);
                        oLast = it;
                        break;
                    }
                }
                for (int i = build.block().size * tilesize; i < world.width(); i += build.block().size * tilesize) {
                    Building other = world.buildWorld(build.x - i, build.y);
                    if (other instanceof PropShaftBuild o && o.force != null && o.isTransverse) {
                        o.setFirst(null);
                        o.setLast(null);
                    }
                    else {
                        PropShaftBuild it = (PropShaftBuild) world.buildWorld(build.x - i + build.block().size * tilesize, build.y);
                        it.setFirst(it);
                        oFirst = it;
                        break;
                    }
                }
                assert oFirst != null;
                oFirst.setLast(oLast);
                assert oLast != null;
                oLast.setFirst(oFirst);
            };
        }
        else {
            return () -> {
                PropShaftBuild oFirst = null, oLast = null;
                for (int i = build.block().size * tilesize; i < world.height(); i += build.block().size * tilesize) {
                    Building other = world.buildWorld(build.x, build.y + i);
                    if (other instanceof PropShaftBuild o && o.force != null && !o.isTransverse) {
                        o.setFirst(null);
                        o.setLast(null);
                    }
                    else {
                        PropShaftBuild it = (PropShaftBuild) world.buildWorld(build.x, build.y + i - build.block().size * tilesize);
                        it.setLast(it);
                        oLast = it;
                        break;
                    }
                }
                for (int i = build.block().size * tilesize; i < world.height(); i += build.block().size * tilesize) {
                    Building other = world.buildWorld(build.x, build.y - i);
                    if (other instanceof PropShaftBuild o && o.force != null && !o.isTransverse) {
                        o.setFirst(null);
                        o.setLast(null);
                    }
                    else {
                        PropShaftBuild it = (PropShaftBuild) world.buildWorld(build.x, build.y - i + build.block().size * tilesize);
                        it.setFirst(it);
                        oFirst = it;
                        break;
                    }
                }
                assert oFirst != null;
                oFirst.setLast(oLast);
                assert oLast != null;
                oLast.setFirst(oFirst);
            };
        }
    }

    //TODO 还有一堆bug以后改
    public class PropShaftBuild extends Building implements ForceBuild {
        public ForceModule force;
        public BigDecimal torsion;
        public Seq<PropShaftBuild> forceLinks = new Seq<>();
        public Runnable onRemovedFunc, placedFunc;
        public boolean isTransverse;
        /***/
        protected PropShaftBuild first, last;

        public PropShaftBuild() {
            super();
            if (block instanceof ForceBlock forceBlock && forceBlock.hasForce && force == null) {
                force = new ForceModule();
            }
            forceLinks.add(this);
        }

        public PropShaftBuild getFirst() {
            return first;
        }

        public void setFirst(PropShaftBuild propShaftBuild) {
            first = propShaftBuild;
        }        @Override
        public Building create(Block block, Team team) {
            if (!(block instanceof ForceBlock b)) return super.create(block, team);
            if (b.hasForce) {
                force = new ForceModule();
            }
//            Log.info(rotation);
            isTransverse = rotation % 4 == 0 || rotation % 4 == 2;
//            if (force.selfForce.size)
            return super.create(block, team);
        }

        public PropShaftBuild getLast() {
            return last;
        }

        public void setLast(PropShaftBuild propShaftBuild) {
            last = propShaftBuild;
        }        @Override
        public void created() {
            super.created();
        }



        /** 建建造完成 */
        @Override
        public void placed() {
            super.placed();
            PropShaftBuild oFirst = null, oLast = null;
            if (isTransverse) {
                for (int i = this.block().size * tilesize; i < world.width(); i += this.block().size * tilesize) {
                    Building other = world.buildWorld(x + i, y);
                    if (other instanceof PropShaftBuild o && o.force != null && o.isTransverse) {
                        o.setFirst(null);
                        o.setLast(null);
                        if (this.rotation != o.rotation) {
                            this.rotation = o.rotation;
                        }
                    }
                    else {
                        PropShaftBuild it = (PropShaftBuild) world.buildWorld(x + i - this.block().size * tilesize, y);
                        it.setLast(it);
                        oLast = it;
                        break;
                    }
                }
                for (int i = this.block().size * tilesize; i < world.width(); i += this.block().size * tilesize) {
                    Building other = world.buildWorld(x - i, y);
                    if (other instanceof PropShaftBuild o && o.force != null && o.isTransverse) {
                        o.setFirst(null);
                        o.setLast(null);
                        if (this.rotation != o.rotation) {
                            this.rotation = o.rotation;
                        }
                    }
                    else {
                        PropShaftBuild it = (PropShaftBuild) world.buildWorld(x - i + this.block().size * tilesize, y);
                        it.setFirst(it);
                        oFirst = it;
                        break;
                    }
                }
            }
            else {
                for (int i = this.block().size * tilesize; i < world.height(); i += this.block().size * tilesize) {
                    Building other = world.buildWorld(x, y + i);
                    if (other instanceof PropShaftBuild o && o.force != null && !o.isTransverse) {
                        o.setFirst(null);
                        o.setLast(null);
                        if (this.rotation != o.rotation) {
                            this.rotation = o.rotation;
                        }
                    }
                    else {
                        PropShaftBuild it = (PropShaftBuild) world.buildWorld(x, y + i - this.block().size * tilesize);
                        it.setLast(it);
                        oLast = it;
                        break;
                    }
                }
                for (int i = this.block().size * tilesize; i < world.height(); i += this.block().size * tilesize) {
                    Building other = world.buildWorld(x, y - i);
                    if (other instanceof PropShaftBuild o && o.force != null && !o.isTransverse) {
                        o.setFirst(null);
                        o.setLast(null);
                        if (this.rotation != o.rotation) {
                            this.rotation = o.rotation;
                        }
                    }
                    else {
                        PropShaftBuild it = (PropShaftBuild) world.buildWorld(x, y - i + this.block().size * tilesize);
                        it.setFirst(it);
                        oFirst = it;
                        break;
                    }
                }
            }
            assert oFirst != null;
            oFirst.setLast(oLast);
            assert oLast != null;
            oLast.setFirst(oFirst);
        }

        @Override
        public void remove() {
            super.remove();
            if (isTransverse) {
                Building otherRight = world.buildWorld(x + block.size * tilesize, y);
                if (otherRight instanceof PropShaftBuild o && o.force != null && o.isTransverse) {
                    o.setFirst(o);
                }
                Building otherLeft = world.buildWorld(x - block.size * tilesize, y);
                if (otherLeft instanceof PropShaftBuild o && o.force != null && o.isTransverse) {
                    o.setLast(o);
                }
            }
            else {
                Building otherRight = world.buildWorld(x, y + block.size * tilesize);
                if (otherRight instanceof PropShaftBuild o && o.force != null && o.isTransverse) {
                    o.setFirst(o);
                }
                Building otherLeft = world.buildWorld(x, y - block.size * tilesize);
                if (otherLeft instanceof PropShaftBuild o && o.force != null && o.isTransverse) {
                    o.setLast(o);
                }
            }
        }

        @Override
        public void draw() {
            super.draw();
            drawer.draw(this);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if (revision >= 1) {
                if (force == null && block instanceof ForceBlock && ((ForceBlock) block).hasForce) force = new ForceModule();
                assert force != null;
                force.read(read);
                float firstX = read.f();
                float firstY = read.f();
                float lastX = read.f();
                float lastY = read.f();
                Time.runTask(0, () -> {
                    first = world.buildWorld(firstX, firstY) instanceof PropShaftBuild ? (PropShaftBuild) world.buildWorld(firstX, firstY) : null;
                    last = world.buildWorld(lastX, lastY) instanceof PropShaftBuild ? (PropShaftBuild) world.buildWorld(lastX, lastY) : null;
                });
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            if (force == null && !(block instanceof ForceBlock) && !((ForceBlock) block).hasForce) return;
            assert force != null;
            force.write(write);
            write.f(first.x);
            write.f(first.y);
            write.f(last.x);
            write.f(last.y);
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public PropShaft block() {
            return (PropShaft) this.block;
        }

    }
}
