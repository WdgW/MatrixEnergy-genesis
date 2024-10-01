package mechanical_force.world.blocks.storage;

import arc.Core;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mechanical_force.force.Force;
import mechanical_force.force.ForceModule;
import mechanical_force.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.storage.CoreBlock;

/**
 * @author dg
 */
public class ForceCore extends CoreBlock {
    public Force selfForce = new Force();

    public ForceCore(String name) {
        super(name);
        this.destructible = true;
        this.update = true;
        this.buildType = ForceCoreBuild::new;
    }

    public ForceCore(String name, float selfForce) {
        this(name,new Force(selfForce));
    }
    public ForceCore(String name, Force selfForce, String localizedName){
        this(name, selfForce);
        this.localizedName = localizedName;
    }

    public ForceCore(String name, Force selfForce) {
        super(name);
        this.selfForce = selfForce;
        this.destructible = true;
        this.update = true;
        this.buildType = ForceCoreBuild::new;
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("force", (ForceCoreBuild param) -> new Bar(
                () -> Core.bundle.formatString("功率：{0}W", param.forces.getForce().toString()),
                () -> Pal.forceBar,
                () -> param.forces.getForce().size.floatValue()
        ));
    }

    public class ForceCoreBuild extends CoreBuild {
        public ForceModule forces = new ForceModule();

        public ForceCoreBuild() {
            super();
            if (forces == null) {
                forces = new ForceModule();
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            if (forces == null) forces = new ForceModule();
            forces.write(write);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if (revision >= 1) {
                if (forces == null) forces = new ForceModule();
                forces.read(read);
            }
        }

        @Override
        public byte version() {
            return 1;
        }
    }
}
