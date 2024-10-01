package mechanical_force.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dg
 */
public class Annotations {
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Load{
        /**
         * The region name to load. Variables can be used:
         * "@" -> block name
         * "@size" -> block size
         * "#" "#1" "#2" -> index number, for arrays
         * 要加载的区域名称。可以使用变量"@" -> 块名称 "@size" -> 块大小 "#" "#1" "#2" -> 索引号，用于数组
         * */
        String value();
        /** 1D Array length, if applicable.  */
        int length() default 1;
        /** 2D array lengths. */
        int[] lengths() default {};
        /** Fallback string used to replace "@" (the block name) if the region isn't found. 后备字符串，用于在未找到区域时替换"@"（区块名称）。*/
        String fallback() default "error";
    }
    /** Indicates an entity definition. 表示实体定义。*/
    @Retention(RetentionPolicy.SOURCE)
    public @interface EntityDef{
        /** List of component interfaces 组件接口列表*/
        Class[] value();
        /** Whether the class is final */
        boolean isFinal() default true;
        /** If true, entities are recycled. */
        boolean pooled() default false;
        /** Whether to serialize (makes the serialize method return this value).
         * If true, this entity is automatically put into save files.
         * If false, no serialization code is generated at all. */
        boolean serialize() default true;
        /** Whether to generate IO code. This is for advanced usage only. */
        boolean genio() default true;
        /** Whether I made a massive mistake by merging two different class branches */
        boolean legacy() default false;
    }
}
