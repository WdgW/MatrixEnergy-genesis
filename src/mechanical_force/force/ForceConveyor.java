package mechanical_force.force;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Nullable;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.TargetPriority;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.gen.Teamc;
import mindustry.graphics.Layer;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.world.Edges;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.meta.BlockGroup;

import static mindustry.Vars.content;

/**
 * @author dg
 */
public class ForceConveyor extends Conveyor implements ForceBlock {
    public static final int[] blendresult = new int[5];
    public float itemSpace = 0.4f;

//    public TextureRegion[] regions;
    public int capacity = 3;
    public float speed = 0f;
    public float displayedSpeed = 0f;

    public ForceConveyor(String name, String localizedName) {
        this(name);
        this.localizedName = localizedName;
    }

    public ForceConveyor(String name) {
        super(name);
        solid = true;//固体
        group = BlockGroup.transportation;//分组
        rotate = true;//是否可旋转
        update = true;//此区块是否有更新
        hasItems = true;//有物品模块
        priority = TargetPriority.transport;//敌人看到的该区块的目标优先级。
        destructible = true;//可破坏的
        canOverdrive = true;//可以被加速
        itemCapacity = capacity;
        conveyorPlacement = true;
        underBullets = false;//为 true 时，除非明确指定目标，否则子弹无法击中该区块。

        ambientSound = Sounds.conveyor;//环境声音
        ambientSoundVolume = 0.0022f;//环境音量
        unloadable = true;//卸荷器是否在该区块工作
        noUpdateDisabled = false;//若为 true，则禁用时区块停止更新
//        noSideBlend = true;
        buildType = ForceConveyorBuild::new;
    }

//    @Override
//    public void loadIcon() {
//        super.loadIcon();
//        regions = new TextureRegion[4];
//        for (int i = 0; i < regions.length; i++) {
//            regions[i] = MechanicalForce.atlasFind(name + i);
//        }
//    }

//    @Override
//    public boolean blendsArmored(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock) {
//        return Point2.equals(tile.x + Geometry.d4(rotation).x, tile.y + Geometry.d4(rotation).y, otherx, othery)
//               || (
//                       (
//                               !otherblock.rotatedOutput(otherx, othery) && Edges.getFacingEdge(otherblock, otherx, othery, tile) != null &&
//                               Edges.getFacingEdge(otherblock, otherx, othery, tile).relativeTo(tile) == rotation
//                       ) ||
//                       (otherblock instanceof Conveyor && otherblock.rotatedOutput(otherx, othery) && Point2.equals(otherx + Geometry.d4(otherrot).x, othery + Geometry.d4(otherrot).y, tile.x, tile.y))
//               );
//    }

    @Override
    public void setStats() {
        super.setStats();

        //添加最大所需扭矩
    }
    /**
     * @return 一个混合值的数组：
     *         [0]：连接的类型：
     *         - 0：直线
     *         - 1：曲线（上）
     *         - 2：直线（下）
     *         - 3：所有方向
     *         - 4：直线（上）
     *         [1]：X 比例
     *         [2]：Y 比例
     *         [3]：一个 4 位掩码，位 0-3 表示该方向的混合状态（0 表示 0 度，1 表示 90 度，依此类推）
     *         [4]：与[3]相同，但仅与非方形图片混合
     */
    @Override
    public int[] buildBlending(Tile tile, int rotation, BuildPlan[] directional, boolean world) {
        int[] blendresult = ForceConveyor.blendresult;
        blendresult[0] = 0;
        blendresult[1] = blendresult[2] = 1;

        int num =
                // 根据不同的连接情况设置类型值
                //direction – 要检查的方向索引（0：上，1：右，2：下，3：左）
                (
                        blends(tile, rotation, directional, 2, world) //下
                        && blends(tile, rotation, directional, 1, world) //并且 右
                        && blends(tile, rotation, directional, 3, world)//并且 左
                ) ? 0 //返回 0
                  :
                (
                        blends(tile, rotation, directional, 1, world) //右
                        && blends(tile, rotation, directional, 3, world)//并且 左
                ) ? 1 //返回 1
                  :
                (
                        blends(tile, rotation, directional, 1, world)//右
                        && blends(tile, rotation, directional, 2, world)//并且 下
                ) ? 2 //返回 2
                  :
                (
                        blends(tile, rotation, directional, 3, world)//左
                        && blends(tile, rotation, directional, 2, world)//并且 下
                ) ? 3 //返回 3
                  :
                blends(tile, rotation, directional, 1, world) //右
                ? 4//返回 4
                :
                blends(tile, rotation, directional, 3, world) //左
                ? 5 //返回 5
                :
                -1;//返回 -1
        transformCase(num, blendresult);

        // Calculate bitmask for direction.计算方向的位掩码。
        blendresult[3] = 0;

        for (int i = 0; i < 4; i++) {
            if (blends(tile, rotation, directional, i, world)) {
                blendresult[3] |= (1 << i);
            }
        }

        // Calculate direction for non-square sprites. 计算非正方形图片的方向。

        blendresult[4] = 0;

        for (int i = 0; i < 4; i++) {
            int realDir = Mathf.mod(rotation - i, 4);
            if (blends(tile, rotation, directional, i, world) && (tile != null && tile.nearbyBuild(realDir) != null && !tile.nearbyBuild(realDir).block.squareSprite)) {
                blendresult[4] |= (1 << i);
            }
        }

        return blendresult;
    }

    @Override
    public boolean blends(Tile tile, int rotation, int direction) {
        Building other = tile.nearbyBuild(Mathf.mod(rotation - direction, 4));
        return other != null && other.team == tile.team() && blends(tile, rotation, other.tileX(), other.tileY(), other.rotation, other.block) && !(other instanceof ForceConveyorBuild);
    }


    public class ForceConveyorBuild extends ConveyorBuild implements ForceBuild {
        public ForceModule force;
        //并行阵列数据
        public Item[] ids = new Item[capacity];
        public float[] xs = new float[capacity], ys = new float[capacity];
        //物品数量，始终<容量
        public int len = 0;
        //next entity
        public @Nullable Building next;
        public @Nullable ForceConveyorBuild nextc;
        //下一个传送带的旋转是否 == 瓦片旋转
        public boolean aligned;

        public int lastInserted, mid;
        public float minitem = 1;

        public int blendbits, blending;
        public int blendsclx = 1, blendscly = 1;

        public float clogHeat = 0f;

        public ForceConveyorBuild() {
            super();
            force = new ForceModule();
        }

        @Override
        public byte version() {
            return 2;
        }

        @Override
        public void drawCracks() {
            Draw.z(Layer.block - 0.15f);
            super.drawCracks();
        }

        public boolean shouldAmbientSound() {
            return clogHeat <= 0.5f;
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();

            int[] bits = buildBlending(tile, rotation, null, true);
            blendbits = bits[0];
            blendsclx = bits[1];
            blendscly = bits[2];
            blending = bits[4];

            next = front();
            nextc = next instanceof ForceConveyorBuild && next.team == team ? (ForceConveyorBuild) next : null;
            aligned = nextc != null && rotation == next.rotation;
        }

        @Override
        public void updateTile() {
            super.updateTile();
            Log.info(1);
            //重置最小值
            minitem = 1f;
            mid = 0;

            //尽可能跳过更新
            if (len == 0) {
                clogHeat = 0f;
                sleep();
                return;
            }

            float nextMax = aligned ? 1f - Math.max(itemSpace - nextc.minitem, 0) : 1f;
            float moved = speed * edelta();

            for (int i = len - 1; i >= 0; i--) {
                float nextpos = (i == len - 1 ? 100f : ys[i + 1]) - itemSpace;
                float maxmove = Mathf.clamp(nextpos - ys[i], 0, moved);

                //向上移动
                ys[i] += maxmove;

                //如果向上移动超过nextMax，则移动到nextMax
                if (ys[i] > nextMax) ys[i] = nextMax;
                //如果向上移动超过0.5，则更新mid
                if (ys[i] > 0.5 && i > 0) mid = i - 1;
                //水平移动
                xs[i] = Mathf.approach(xs[i], 0, moved * 2);

                //如果完全移动到顶部并且通过，则向前传递
                if (ys[i] >= 1f && pass(ids[i])) {
                    //如果向前传递，则对准 X 位置
                    if (aligned) {
                        nextc.xs[nextc.lastInserted] = xs[i];
                    }
                    //删除最后一项
                    items.remove(ids[i], len - i);
                    len = Math.min(i, len);
                }
                //如果向下移动超过minitem，则更新minitem
                else if (ys[i] < minitem) {
                    minitem = ys[i];
                }
            }

            //如果minitem小于itemSpace + (blendbits == 1 ? 0.3f : 0f)，则增加clogHeat
            if (minitem < itemSpace + (blendbits == 1 ? 0.3f : 0f)) {
                clogHeat = Mathf.approachDelta(clogHeat, 1f, 1f / 60f);
            }
            //否则重置clogHeat
            else {
                clogHeat = 0f;
            }

            noSleep();
        }

        public boolean pass(Item item) {
            if (item != null && next != null && next.team == team && next.acceptItem(this, item)) {
                next.handleItem(this, item);
                return true;
            }
            return false;
        }

        @Override
        public int removeStack(Item item, int amount) {
            noSleep();
            int removed = 0;

            for (int j = 0; j < amount; j++) {
                for (int i = 0; i < len; i++) {
                    if (ids[i] == item) {
                        remove(i);
                        removed++;
                        break;
                    }
                }
            }

            items.remove(item, removed);
            return removed;
        }

        @Override
        public int acceptStack(Item item, int amount, Teamc source) {
            // 计算最小值
            return Math.min((int) (minitem / itemSpace), amount);
        }

        @Override
        public void handleStack(Item item, int amount, Teamc source) {
            amount = Math.min(amount, capacity - len);

            for (int i = amount - 1; i >= 0; i--) {
                add(0);
                xs[0] = 0;
                ys[0] = i * itemSpace;
                ids[0] = item;
                items.add(item, 1);
            }

            noSleep();
        }

        /**
         * 检查物品是否能够被接收
         *
         * @param source
         *         提供物品的建筑物
         * @param item
         *         要接收的物品
         *
         * @return true 如果物品可以被接收，否则 false
         */
        @Override
        public boolean acceptItem(Building source, Item item) {
            // 检查容器是否已满
            if (len >= capacity) return false;
            // 获取源建筑物相对于目标建筑物的方向
            Tile facing = Edges.getFacingEdge(source.tile, tile);
            if (facing == null) return false;
            // 计算方向差，使其范围在 [0, 3]
            int direction = Math.abs(facing.relativeTo(tile.x, tile.y) - rotation);
            return (
                           (
                                   (direction == 0) //方向一致
                                   && minitem >= itemSpace//物品空间足够
                           )
                           || (
                                   (direction % 2 == 1) //方向相差90度
                                   && minitem > 0.7f
                           )//有一定的物品空间
                   )
                   && !(source.block.rotate && next == source);
        }

        @Override
        public void handleItem(Building source, Item item) {
            if (len >= capacity) return;

            int r = rotation;
            Tile facing = Edges.getFacingEdge(source.tile, tile);
            int ang = ((facing.relativeTo(tile.x, tile.y) - r));
            float x = (ang == -1 || ang == 3) ? 1 : (ang == 1 || ang == -3) ? -1 : 0;

            noSleep();
            items.add(item, 1);

            if (Math.abs(facing.relativeTo(tile.x, tile.y) - r) == 0) { //idx = 0
                add(0);
                xs[0] = x;
                ys[0] = 0;
                ids[0] = item;
            }
            else { //idx = mid
                add(mid);
                xs[mid] = x;
                ys[mid] = 0.5f;
                ids[mid] = item;
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            force.write(write);
        }

        @Override
        public void read(Reads read, byte revision) {
            if (revision >= 2) {
                int amount = read.i();
                len = Math.min(amount, capacity);

                for (int i = 0; i < amount; i++) {
                    int val = read.i();
                    short id = (short) (((byte) (val >> 24)) & 0xff);
                    float x = (float) ((byte) (val >> 16)) / 127f;
                    float y = ((float) ((byte) (val >> 8)) + 128f) / 255f;
                    if (i < capacity) {
                        ids[i] = content.item(id);
                        xs[i] = x;
                        ys[i] = y;
                    }

                }
                force.read(read);
                updateTile();
            }
        }

        @Override
        public Object senseObject(LAccess sensor) {
            if (sensor == LAccess.firstItem && len > 0) return ids[len - 1];
            return super.senseObject(sensor);
        }

    }
}
