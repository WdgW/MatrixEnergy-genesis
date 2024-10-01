package mechanical_force.force;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.world.modules.BlockModule;

import java.math.BigDecimal;

/**
 * @author dg
 */
public class ForceModule extends BlockModule {
    /** 合力 */
    public Force resultantForce = new Force();
    /** 自身产生的力(动力 - 阻力) */
    public Force selfForce = new Force();
    public ForceModule() {
    }

    public ForceModule addForce(String s) {
        selfForce.add(new BigDecimal(s));
        return this;
    }

    public Force getResultantForce() {
        return resultantForce;
    }

    public void setResultantForce(Force resultantForce) {
        this.resultantForce = resultantForce;
    }

    public Force getSelfForce() {
        return selfForce;
    }

    public void setSelfForce(Force selfForce) {
        this.selfForce = selfForce;
    }


    public Force getForce() {
        return resultantForce;
    }

    @Override
    public void write(Writes write) {
        selfForce.write(write);
    }

    @Override
    public void read(Reads read) {
        selfForce.read(read);
    }

}
