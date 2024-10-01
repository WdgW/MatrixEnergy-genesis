package mechanical_force.force;

import arc.util.io.Reads;
import arc.util.io.Writes;

import java.math.BigDecimal;

/**
 * @author dg
 */
public class Force {
    /** 力的大小 力的正负表示方向 <br>使用BigDecimal确保数值精确*/
    public BigDecimal size;

    public Force(){
        this(new BigDecimal("0.0"));
    }
    public Force(float size){
        this(new BigDecimal(size));
    }
    public Force(BigDecimal size){
        this.size = size;
    }

    public void setSize(BigDecimal size){
        this.size = size;
    }
    public void setSize(float size){
        this.setSize(BigDecimal.valueOf(size));
    }
    public void setSize(int size){
        this.setSize(BigDecimal.valueOf(size));
    }

    public void add(float size) {
        this.add(BigDecimal.valueOf(size));
    }

    public void add(int size) {
        this.add(BigDecimal.valueOf(size));
    }

    public void add(BigDecimal bigDecimal) {
        size = size.add(bigDecimal);
    }

    /** 减去一个数,但好像和加没啥区别,不建议使用 */
    public void subtract(BigDecimal bigDecimal) {
        size = size.subtract(bigDecimal);
    }

    public boolean isClockwise(){
        return !(size.floatValue() < 0);
    }

    public void write(Writes write) {
        write.str(size.toString());
    }

    public void read(Reads read) {
        size = new BigDecimal(read.str());
    }
}
