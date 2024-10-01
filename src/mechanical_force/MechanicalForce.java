package mechanical_force;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import mechanical_force.annotations.Annotations.EntityDef;
import mechanical_force.content.*;
import mechanical_force.core.UI;
import mindustry.Vars;
import mindustry.ctype.Content;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.game.EventType.ResizeEvent;
import mindustry.game.Team;
import mindustry.gen.Mechc;
import mindustry.gen.Unitc;
import mindustry.mod.Mod;
import mindustry.type.UnitType;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsCategory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static mindustry.Vars.renderer;

public class MechanicalForce extends Mod {
    public static Team team = null;
    public static String name = "mechanicalforce";
    public static SettingsCategory MFSettingsCategory;
    public static @EntityDef({Unitc.class, Mechc.class}) UnitType a;
    /** size 1 的 边长   1m */
    public static String meter = "1";
    public static BigDecimal meterBD = new BigDecimal(meter);

    {
        try {
            // 获取类的构造函数
            Constructor<Team> constructor = Team.class.getDeclaredConstructor(int.class, String.class, Color.class);
            // 设置构造函数为可访问
            constructor.setAccessible(true);
            // 使用构造函数创建类的实例
            team = constructor.newInstance(5, "team", Color.valueOf("4da6ff"));
        } catch (IllegalAccessException e) {
            Log.info(e);
        } catch (IllegalArgumentException e) {
            Log.info(e);
        } catch (InstantiationException e) {
            Log.info(e);
        } catch (NoSuchMethodException e) {
            Log.info(e);
        } catch (SecurityException e) {
            Log.info(e);
        } catch (InvocationTargetException e) {
            Log.info(e);
        }
    }


    public MechanicalForce() {
        Log.info("机械动力加载成功");

        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup启动时显示对话框
            Time.runTask(10f, () -> {

//                BaseDialog dialog = new BaseDialog("机械动力");
////                dialog.cont.add("behold").row();
//                //mod sprites are prefixed with the mod name (this mod is called 'mechanical_force-java-mod' in its config)
////                dialog.cont.image(Core.atlas.find("mechanical_force-java-mod-frog")).pad(20f).row();
//                dialog.cont.button("确定", dialog::hide).size(100f, 50f);
//                dialog.show();

            });
        });

        //调整视野缩放
        if (!Vars.headless && (renderer.minZoom != 1.5f || renderer.maxZoom != 6f)) {
            renderer.minZoom = 0.9F;
            renderer.maxZoom = 30f;
        }
    }

    /** 加载贴图 */
    public static TextureRegion atlasFind(String name, String def) {
        return atlasFind(name, atlasFind(def));
    }

    /** 加载贴图 */
    public static TextureRegion atlasFind(String name, TextureRegion def) {
        String n = MechanicalForce.name + "-" + name;
        return Core.atlas.find(n, def);
    }

    /** 加载贴图 */
    public static TextureRegion atlasFind(String name) {
        String n = MechanicalForce.name + "-" + name;
        return Core.atlas.find(n);
    }

    @Override
    public void init() {
        UI.load();

        Seq<SettingsCategory> categories = Vars.ui.settings.getCategories();

        MFSettingsCategory = new SettingsCategory("机械动力", Core.atlas.getDrawable("mechanicalforce"), MFSetting -> Events.on(ResizeEvent.class, event -> {
            if (Vars.ui.settings.isShown() && Core.scene.getDialog() == Vars.ui.settings) {
                MFSetting.rebuild();
                Vars.ui.settings.updateScrollFocus();

            }
        })){
            {
//                table
            }
        };
//       MFSettingsCategory.table.checkPref("测试模式", false, b -> {
//            if (b) {
////                BaseDialog dialog = new BaseDialog("机械动力--测试模式");
////
////                TextField textField = new TextField("");
////                dialog.cont.label(()->"请输入测试密码: ");
////                dialog.cont.add(textField);
////                dialog.cont.button("确定",()->{
////                    if ("123456".equals(textField.getText())){
////                    }
////                }).size(100f, 50f);
////                dialog.show();
//                Time.runTask(1, this::unlockTechnologies);
//            }
//        });
        categories.add(MFSettingsCategory);
    }

    public void unlockTechnologies() {
        for (Seq<Content> contents : Vars.content.getContentMap()) {
            if (contents.size == 0) continue;
            for (Content content : contents) {
                if (content instanceof UnlockableContent u) {
                    u.unlock();
                }
            }
        }
    }

    @Override
    public void loadContent() {
        EmasculateBlocks.load();
        MFItems.load();
        MFBlocks.load();
        MFPlanets.load();
        MFLiquids.load();
//        Log.info("加载");
//        for (Class<?> declaredClass : Block.class.getDeclaredClasses()) {
//            Log.info(declaredClass.getName());
//        }
//        Vars.content.blocks().forEach(block -> {
//            Log.info(block.localizedName+":"+block.getClass());
//        });
//        Log.info(Arrays.toString(Block.class.getDeclaredClasses()));
    }

}
