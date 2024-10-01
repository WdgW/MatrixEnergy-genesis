package mechanical_force.content;

import arc.graphics.Color;
import arc.math.geom.Vec3;
import mechanical_force.type.MFPlanet;
import mindustry.content.Blocks;
import mindustry.content.Planets;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.ItemStack;
import mindustry.type.Planet;
import mindustry.world.TileGen;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Env;

import static mindustry.content.TechTree.nodeRoot;

/**
 * @author dg
 */
public class MFPlanets {
    public static Planet earth;

    public static void load() {
        new MFPlanet("test", Planets.sun, 5f){{
            generator= new ErekirPlanetGenerator();
//            meshLoader = () ->
//                    new HexMesh(this,1);
            landCloudColor = Color.valueOf("ffcca5");
            iconColor = Color.valueOf("00b3a4");
            atmosphereColor = Color.valueOf("c68400");
            lightColor = Color.valueOf("ffcca5");
            alwaysUnlocked = true;
            sectorApproxRadius = 0.1f;
        }};
        earth = new MFPlanet("earth", Planets.sun, 5f, 4, new SerpuloPlanetGenerator(){
            @Override
            public float getSizeScl() {
                return 0;
            }

            @Override
            public void genTile(Vec3 position, TileGen tile) {
                super.genTile(position, tile);
            }

            @Override
            public float getHeight(Vec3 position) {
//                    Log.info(position);
                return super.getHeight(position);
            }
        }) {{
            localizedName = "生态之球";
            /*generator = new PlanetGenerator() {
                @Override
                public float getHeight(Vec3 position) {
                    return 0;
                }

                @Override
                public Color getColor(Vec3 position) {
                    return null;
                }
            };*/
            //网格加载器
            meshLoader = () ->
                    new HexMesh(this,6);
            cloudMeshLoader = () -> new MultiMesh(
//                    new HexSkyMesh(this, -1, 0.54f, 0.2f, 8, Color.valueOf("ffcca5").a(0.8f), 8, 0.64f, 0.8f, 0.3f),
//                    new HexSkyMesh(this, -1, 0.5f, 0.2f, 8, Color.valueOf("ffcca5").a(0.8f), 8, 0.64f, 0.8f, 0.3f),
//                    new HexSkyMesh(this, -1, 0.96f, 0.4f, 10, Color.valueOf("ffcca5").a(0.8f).add(0, 0, 0, 0.1f), 10, 0.92f, 0.6f, 0.5f),
//                    new HexSkyMesh(this, -1, 0.9f, 0.4f, 10, Color.valueOf("ffcca5").a(0.8f).add(0, 0, 0, 0.1f), 10, 0.92f, 0.6f, 0.5f),
//                    new HexSkyMesh(this, -1,0.8f,0.5f, 9,Color.valueOf("2d8100"), 10,0.8f, 0.8f,0.45f),
//                    new HexSkyMesh(this, -1,0.8f,0.5f, 9,Color.valueOf("00bd65"), 10,0.8f, 0.8f,0.45f),
//                    new HexSkyMesh(this, -1,0.8f,0.5f, 9,Color.valueOf("25b865"), 10,0.8f, 0.8f,0.45f),
//                    new HexSkyMesh(this, -1,0.8f,0.5f, 9,Color.valueOf("be7200"), 10,0.8f, 0.8f,0.45f)
//                    new HexSkyMesh(this, -1,0.78f,0.46f, 9,Color.valueOf("878a8c"), 8,0.6f, 0.66f,0.4f),
                    new HexSkyMesh(this, -1,0.8f,0.2f, 9,Color.valueOf("878a8c"), 8,0.7f, 1.2f,0.4f),
//                    new HexSkyMesh(this, -1,0.82f,0.5f, 9,Color.valueOf("878a8c"), 10,0.8f, 0.76f,0.5f),
                    new HexSkyMesh(this, -1,0.84f,0.3f, 9,Color.valueOf("878a8c"), 10,0.8f, 0.9f,0.4f)
            );
            //对于太阳来说，这是照耀其他行星的颜色。对孩子没有任何作用。
            lightColor = Color.valueOf("ffcca5");
//            lightColor = Color.valueOf("59ff00");
            //可着陆行星的大气色调
            atmosphereColor = Color.valueOf("c68400");
//            atmosphereColor = Color.valueOf("59ff00");
            //行星列表中出现的图标的颜色。
//            iconColor = Color.valueOf("ffcca5");
            iconColor = Color.valueOf("00b3a4");
//            iconColor = Color.valueOf("59ff00");
            //着陆时显示的云彩色调。
            landCloudColor = Color.valueOf("ffcca5");
//            landCloudColor = Color.valueOf("59ff00");
            //启动时核心项目容量的乘数
            launchCapacityMultiplier = 0.5f;
//            用于在该星球上生成扇区基地的种子。-1使用基于 ID 的随机种子。
            sectorSeed = -1;
            //如果为 true，则在扇区损失时产生波。TODO 删除。
            allowWaves = true;
            //是否允许扇区在后台模拟波。
            allowWaveSimulation = true;
            //是否模拟来自敌方基地的扇区入侵。
            allowSectorInvasion = true;
            //是否允许用户为该地图指定自定义启动示意图。
            allowLaunchSchematics = true;
            //如果为 "true"，敌人的核心将被替换为该星球上的产卵点（用于入侵）。
            enemyCoreSpawnReplace = true;
            //是否允许用户指定他们带到该地图的资源
            allowLaunchLoadout = true;
            //如果为真，核心半径内的方块将被移除，并在着陆时以冲击波的形式 "堆积 "起来
            prebuildBase = false;
            //此内容是否始终在科技树中解锁。
            alwaysUnlocked = true;
            //这颗行星是否相对于母星被潮汐锁定
            tidalLock = true;
            //是否绘制轨道
//            drawOrbit = true;
            //是否启用 Bloom 渲染效果。
            bloom = true;
            //显示在地图对话框中的默认起始扇区
            startSector = 15;
            //一个扇形的大约半径
            sectorApproxRadius = 0.1f;
            //行星轨道之间的默认间距（以世界为单位）。这是按父级定义的！
            orbitSpacing = 2f;
            //这个星球及其所有孩子的总半径
            totalRadius += 5f;
            //大气半径调整参数
            atmosphereRadIn = 0.04f;
            atmosphereRadOut = 0.3f;
            //昼夜周期参数。
            lightSrcTo = 0.5f;
            lightDstFrom = 0.2f;
            //公转一圈时间
            orbitTime = 360 * 60;
            //自传一圈时间
            rotateTime = 60 * 60;

            //如果为 "true"，扇区保存会在丢失时被清除。
            clearSectorOnLose = true;
            //启动时的默认核心
            defaultCore = Blocks.coreBastion;
            //敌人重建速度的乘数；仅适用于战役（非标准规则）
            enemyBuildSpeedMultiplier = 0.4f;

            //如果为假，则玩家无法在该星球的编号区降落。
            allowLaunchToNumbered = true;

            //TODO 应该有照明吗？
            //如果为 true，则模拟昼夜循环。
            updateLighting = false;

            //环境属性
            defaultAttributes.set(Attribute.heat, 0.8f);
//            defaultAttributes.set(Attribute.light, 0.1f);

            defaultEnv = Env.groundOil | Env.terrestrial | Env.groundWater | Env.scorching | Env.spores | Env.underwater | Env.oxygen;
            //为这个星球上的任何区域设定游戏加载规则。
            ruleSetter = r -> {
                //敌方队伍
                r.waveTeam = Team.malis;
                //放置范围检查
                r.placeRangeCheck = false;
                //如果为 "true"，则显示单位生成点
                r.showSpawns = true;
                //如果为 "true"，则启用战争迷雾。除非在雷达视野内，否则敌方单位和建筑都会被隐藏。
                r.fog = true;
                //如果 fog = true，则表示是否启用了静态（黑色）雾。
                r.staticFog = true;
                //是否启用环境照明。
                r.lighting = false;
                //如果为 "true"，（敌人）核心半径内的所有敌方建筑都会在死亡时被摧毁。用于战役地图。
                r.coreDestroyClear = true;
                //如果为 true，则物品只能存放在核心中。
                r.onlyDepositCore = true;
            };

            //登陆后解锁的内容（通常是针对特定星球的）
            unlockedOnLand.add(Blocks.coreBastion);
            //本星球没有的项目。出于向后兼容性考虑而未提供。
//            hiddenItems.addAll(Items.serpuloItems).removeAll(Items.erekirItems);

            this.techTree = nodeRoot(this.localizedName, new UnlockableContent("mechanical_force") {
                {
                    this.localizedName = "机械动力";
                }

                @Override
                public ItemStack[] researchRequirements() {
                    return new ItemStack[]{};
                }

                @Override
                public ContentType getContentType() {
                    return ContentType.effect_UNUSED;
                }

                @Override
                public boolean isHidden() {
                    return true;
                }
            }, () -> {});
        }};


//        MFTechTree.load();
    }
}
