package mechanical_force.world.blocks;

import arc.Events;
import arc.func.Cons;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.game.EventType.Trigger;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;

/**
 * @author dg
 */
public class CoreShare extends Block{
    public static byte amount = 0;
    public static byte max = 10;
    public static boolean alreadyWrite = false;
    public static boolean alreadyRead = false;
    public CoreShare(String name) {
        super(name);
        this.update = true;
        this.hasItems = true;
        this.destructible = true;
        this.buildType = CoreShareBuild::new;
    }

    public CoreShare(String name, String localizedName){
        this(name);
        this.localizedName = localizedName;
    }

    public static void reset(){
        amount = 0;
        alreadyWrite = false;
        alreadyRead = false;
    }

    @Override
    public void init() {
        super.init();
        Events.run(Trigger.newGame, CoreShare::reset);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return amount < max;
    }

    public class CoreShareBuild extends Building{
        @Override
        public Building create(Block block, Team team) {
            Building building =  super.create(block, team);
            this.items = team.items();
            return building;
        }
        @Override
        public boolean acceptItem(Building source, Item item){
            return items.get(item) < team.core().block.itemCapacity;
        }
        @Override
        public void drawSelect(){
            //do not draw a pointless single outline when there's no storage
            if(team.cores().size <= 1 && !proximity.contains(storage -> storage.items == items)) return;

            Lines.stroke(1f, Pal.accent);
            Cons<Building> outline = b -> {
                for(int i = 0; i < 4; i++){
                    Point2 p = Geometry.d8edge[i];
                    float offset = -Math.max(b.block.size - 1, 0) / 2f * tilesize;
                    Draw.rect("block-select", b.x + offset * p.x, b.y + offset * p.y, i * 90);
                }
            };
            team.cores().each(core -> {
                outline.get(core);
                core.proximity.each(storage -> storage.items == items, outline);
            });
            Draw.reset();
        }

        @Override
        public void playerPlaced(Object config) {
            super.playerPlaced(config);
            amount+=1;
        }

        @Override
        public void onRemoved() {
            super.onRemoved();
            amount-=1;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            if (!alreadyWrite) {
                write.b(amount);
                alreadyWrite = true;
            }
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if (revision >= 1){
                if (!alreadyRead) {
                    amount = read.b();
                    alreadyRead = true;
                }
            }
        }

        @Override
        public byte version() {
            return 1;
        }
    }
}
