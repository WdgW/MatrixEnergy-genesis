package mechanical_force.maps.planet;

import arc.func.Boolf;
import arc.func.Intc2;
import arc.graphics.Color;
import arc.math.geom.Vec3;
import arc.struct.Seq;
import mindustry.ai.Astar.DistanceHeuristic;
import mindustry.ai.Astar.TileHueristic;
import mindustry.game.Rules;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.type.Sector;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.TileGen;
import mindustry.world.Tiles;

/**
 * @author dg
 */
public class MPPlanetGenerator extends PlanetGenerator {
    public MPPlanetGenerator() {
        super();
    }

    @Override
    public float getHeight(Vec3 position) {
        return 0;
    }

    @Override
    public Color getColor(Vec3 position) {
        return null;
    }

    @Override
    public boolean skip(Vec3 position) {
        return super.skip(position);
    }

    @Override
    public void postGenerate(Tiles tiles) {
        super.postGenerate(tiles);
    }

    @Override
    public void generate(Tiles tiles) {
        super.generate(tiles);
    }

    @Override
    protected void generate() {
        super.generate();
    }

    @Override
    public void median(int radius) {
        super.median(radius);
    }

    @Override
    public void median(int radius, double percentile) {
        super.median(radius, percentile);
    }

    @Override
    public void median(int radius, double percentile, Block targetFloor) {
        super.median(radius, percentile, targetFloor);
    }

    @Override
    public void ores(Seq<Block> ores) {
        super.ores(ores);
    }

    @Override
    public void ore(Block dest, Block src, float i, float thresh) {
        super.ore(dest, src, i, thresh);
    }

    @Override
    public void oreAround(Block ore, Block wall, int radius, float scl, float thresh) {
        super.oreAround(ore, wall, radius, scl, thresh);
    }

    @Override
    public void wallOre(Block src, Block dest, float scl, float thresh) {
        super.wallOre(src, dest, scl, thresh);
    }

    @Override
    public void cliffs() {
        super.cliffs();
    }

    @Override
    public void terrain(Block dst, float scl, float mag, float cmag) {
        super.terrain(dst, scl, mag, cmag);
    }

    @Override
    public void noise(Block floor, Block block, int octaves, float falloff, float scl, float threshold) {
        super.noise(floor, block, octaves, falloff, scl, threshold);
    }

    @Override
    public void overlay(Block floor, Block block, float chance, int octaves, float falloff, float scl, float threshold) {
        super.overlay(floor, block, chance, octaves, falloff, scl, threshold);
    }

    @Override
    public void tech() {
        super.tech();
    }

    @Override
    public void tech(Block floor1, Block floor2, Block wall) {
        super.tech(floor1, floor2, wall);
    }

    @Override
    public void distort(float scl, float mag) {
        super.distort(scl, mag);
    }

    @Override
    public void scatter(Block target, Block dst, float chance) {
        super.scatter(target, dst, chance);
    }

    @Override
    public void each(Intc2 r) {
        super.each(r);
    }

    @Override
    public void cells(int iterations) {
        super.cells(iterations);
    }

    @Override
    public void cells(int iterations, int birthLimit, int deathLimit, int cradius) {
        super.cells(iterations, birthLimit, deathLimit, cradius);
    }

    @Override
    protected float noise(float x, float y, double scl, double mag) {
        return super.noise(x, y, scl, mag);
    }

    @Override
    protected float noise(float x, float y, double octaves, double falloff, double scl) {
        return super.noise(x, y, octaves, falloff, scl);
    }

    @Override
    public void pass(Intc2 r) {
        super.pass(r);
    }

    @Override
    public boolean nearWall(int x, int y) {
        return super.nearWall(x, y);
    }

    @Override
    public boolean nearAir(int x, int y) {
        return super.nearAir(x, y);
    }

    @Override
    public void removeWall(int cx, int cy, int rad, Boolf<Block> pred) {
        super.removeWall(cx, cy, rad, pred);
    }

    @Override
    public boolean near(int cx, int cy, int rad, Block block) {
        return super.near(cx, cy, rad, block);
    }

    @Override
    public void decoration(float chance) {
        super.decoration(chance);
    }

    @Override
    public void blend(Block floor, Block around, float radius) {
        super.blend(floor, around, radius);
    }

    @Override
    public void brush(Seq<Tile> path, int rad) {
        super.brush(path, rad);
    }

    @Override
    public void erase(int cx, int cy, int rad) {
        super.erase(cx, cy, rad);
    }

    @Override
    public Seq<Tile> pathfind(int startX, int startY, int endX, int endY, TileHueristic th, DistanceHeuristic dh) {
        return super.pathfind(startX, startY, endX, endY, th, dh);
    }

    @Override
    public void trimDark() {
        super.trimDark();
    }

    @Override
    public void inverseFloodFill(Tile start) {
        super.inverseFloodFill(start);
    }

    @Override
    public void generateSector(Sector sector) {
        super.generateSector(sector);
    }

    @Override
    public boolean allowLanding(Sector sector) {
        return super.allowLanding(sector);
    }

    @Override
    public void addWeather(Sector sector, Rules rules) {
        super.addWeather(sector, rules);
    }

    @Override
    protected void genTile(Vec3 position, TileGen tile) {
        super.genTile(position, tile);
    }

    @Override
    protected float noise(float x, float y, double octaves, double falloff, double scl, double mag) {
        return super.noise(x, y, octaves, falloff, scl, mag);
    }

    @Override
    public float getSizeScl() {
        return super.getSizeScl();
    }

    @Override
    public int getSectorSize(Sector sector) {
        return super.getSectorSize(sector);
    }

    @Override
    public void generate(Tiles tiles, Sector sec, int seed) {
        super.generate(tiles, sec, seed);
    }
}
