package mechanical_force.type;

import mindustry.maps.generators.PlanetGenerator;
import mindustry.type.Planet;

/**
 * @author dg
 */
public class MFPlanet extends Planet {

    public MFPlanet(String name, Planet parent, float radius) {
        super(name, parent, radius);
    }

    public MFPlanet(String name, Planet parent, float radius, int sectorSize) {
        super(name, parent, radius, sectorSize);
    }

    public MFPlanet(String name, Planet parent, float radius, int sectorSize, PlanetGenerator generator) {
        this(name, parent, radius, sectorSize);
        this.generator = generator;
    }
}
