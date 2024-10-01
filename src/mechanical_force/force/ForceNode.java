package mechanical_force.force;

import mindustry.gen.Building;
import mindustry.world.Block;

/**
 * @author dg
 */
public class ForceNode extends Block implements ForceBlock {

    public ForceNode(String name) {
        super(name);
        buildType = ForceNodeBuild::new;
    }

    public ForceNode(String name, String localizedName) {
        this(name);
        this.localizedName = localizedName;
    }


    @Override
    public void setStats(){
        super.setStats();
//        stats.add(Stat.powerRange, range, StatUnit.blocks);
    }
    @Override
    public void init(){
        super.init();
//        updateClipRadius((range + 1) * tilesize);
    }
    


    public class ForceNodeBuild extends Building implements ForceBuild {
        public ForceModule force;

        public ForceNodeBuild() {
            super();
            if (block instanceof ForceBlock forceBlock && forceBlock.hasForce && force == null) {
                force = new ForceModule();
            }
        }
    }
}
