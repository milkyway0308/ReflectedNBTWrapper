package skywolf46.NBTUtil.v1_3;

import javassist.*;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.minecraft.server.v1_12_R1.TileEntity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import skywolf46.NBTUtil.v1_3.Interface.INBTCompatible;
import skywolf46.NBTUtil.v1_3.NBTData.ReflectedNBTCompound;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

public class ReflectedNBTWrapper extends JavaPlugin {
    public static final String VERSION = "v1_3";


    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §fReflectedNBTWrapper v1.3");
        Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization...");
        initBukkit();
    }

    public static void initBukkit() {
        ReflectedNBTCompound comp = new skywolf46.NBTUtil.v1_3.NBTData.ReflectedNBTCompound();
        try {
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 1 - Loading class...");

            Class.forName("skywolf46.NBTUtil.v1_3.NBTData.ReflectedNBTCompound");
//            Class.forName("skywolf46.NBTUtil.v1_3.Interface.INBTCompatible",true,ReflectedNBTWrapper.class.getClassLoader());
            Instrumentation instrumentation = ByteBuddyAgent.install();
            CtClass ctClass = ClassPool.getDefault().getCtClass(BukkitVersionUtil.getNMSClass("TileEntity").getCanonicalName());
//            ctClass.defrost();
//            CtField ct = new CtField(cl,"reflectedNBTField",ctClass);
//            ctClass.addField(ct);
////            ctClass.addField(CtField.make("private skywolf46.NBTUtil.v1_3.NBTData.ReflectedNBTCompound reflectedNBTField = new skywolf46.NBTUtil.v1_3.NBTData.ReflectedNBTCompound();", cl));
//            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 2 - Edit block load method...");
//            CtMethod execute = ctClass.getDeclaredMethod("load");
//            ctClass.defrost();
//            execute.setBody("{this.position = new " + BukkitVersionUtil.getNMSClass("BlockPosition").getName() + "($1.getInt(\"x\"),$1.getInt(\"y\"),$1.getInt(\"z\"));" +
//                    "if($1.hasKey(\"ReflectedField\"))" +
//                    "reflectedNBTField = new skywolf46.NBTUtil." + VERSION + ".NBTData.ReflectedNBTCompound($1.getCompound(\"ReflectedField\"));" +
//                    "System.out.println(\"Test\");}");
//
//            ClassDefinition classDefinition = new ClassDefinition(TileEntity.class, ctClass.toBytecode());
//            instrumentation.redefineClasses(classDefinition);
            String inter = "skywolf46.NBTUtil.v1_3.Interface.INBTCompatible";
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 2 - Creating TileEntity implemention...");
            ClassPool pool = ClassPool.getDefault();

            pool.insertClassPath(new ClassClassPath(ReflectedNBTCompound.class));
            CtClass cl = pool.getCtClass(ReflectedNBTCompound.class.getName());
            CtClass ct = pool.makeClass("skywolf46.BukkitBridge.NBTCompatibleTileEntity", ctClass);
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 3 - Implementing INBTCompatible...");
            ct.addInterface(pool.getCtClass(INBTCompatible.class.getName()));
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 4 - Adding field...");
            ct.addField(new CtField(cl, "nbtField", ct));
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 5 - Adding constructor...");
            CtConstructor co = CtNewConstructor.defaultConstructor(ct);
            co.insertBefore("{nbtField = new skywolf46.NBTUtil.v1_3.NBTData.ReflectedNBTCompound();}");
            ct.addConstructor(co);
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 6 - Adding methods...");
            ct.addMethod(CtMethod.make(
                    "public skywolf46.NBTUtil.v1_3.NBTData.ReflectedNBTCompound getNBT(){" +
                            "  return nbtField;" +
                            "}"
                    , ct));

            ct.addMethod(CtMethod.make(
                    "public void load(" + BukkitVersionUtil.getNMSClass("NBTTagCompound").getName() + " comp){" +
                            "  if(comp.hasKey(\"ReflectedNBT\"))" +
                            "    nbtField = new skywolf46.NBTUtil.v1_3.NBTData.ReflectedNBTCompound(comp.getCompound(\"ReflectedNBT\"));" +
                            "}"
                    , ct));
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 7 - Class declaring...");
            Class c = ct.toClass(ReflectedNBTWrapper.class.getClassLoader());
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 8 - Invoking test constructor...");
            Object o = c.getConstructor().newInstance();
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 9 - Testing Class.forName()...");
            Class d = Class.forName("skywolf46.BukkitBridge.NBTCompatibleTileEntity");
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 10 - Comparing class..");
            if (!d.equals(c)) {
                Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §cInitialization partial failure - Generated class and forName class not match");
            }
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 11 - Testing INBTCompatible implemention..");
            INBTCompatible nbtComp = (INBTCompatible) o;
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §eExtracted NBT toString: " + nbtComp.getNBT().toString());
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §7Initialization level 12 - Modifying TileEntity....");
            String tName = BukkitVersionUtil.getNMSClass("TileEntity").getName();
            CtMethod cf = ctClass.getDeclaredMethod("create");
            String mk = BukkitVersionUtil.getNMSClass("MinecraftKey").getName();
            ctClass.defrost();
//            cf.setBody("{" + tName + " entity = null;" +
//                    "java.lang.String s = $2.getString(\"id\");" +
//                    "try{" +
//                    "java.lang.Class oClass = (Class)f.get(new " + mk + "(s));" +
//                    "if(oClass != null){" +
//                    "  entity = (" + tName + ")oClass.newInstance();" +
//                    "}" +
//                    "} catch (java.lang.Throwable ex) {" +
//                    "  a.error(\"Failed to create block entity {}\",s,ex);" +
//                    "}" +
//                    "if(entity != null){" +
//                    "  try {" +
//                    "    entity.b($1);" +
//                    "    entity.load($2);" +
//                    "  } catch (java.lang.Throwable ex) {" +
//                    "  a.error(\"Failed to load block entity {}\",s,ex);" +
//                    "  entity = null;" +
//                    "  }" +
//                    "} else {" +
//                    "  System.out.println(\"Test\");" +
//                    "  entity = new skywolf46.BukkitBridge.NBTCompatibleTileEntity();" +
//                    "}" +
//                    "return entity;" +
//                    "}"
//            );
            cf.setBody("{"
                    + tName + " entity = null;" +
                    "System.out.println(\"Processed\");" +
                    "String s = $2.getString(\"id\");" +
                    "return entity;" +
                    "}");

            ClassDefinition classDefinition = new ClassDefinition(TileEntity.class, ctClass.toBytecode());
            instrumentation.redefineClasses(classDefinition);
            Bukkit.getConsoleSender().sendMessage("§5ReflectedNBTWrapper §7| §aInitialized! ");
        } catch (Exception ex) {
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(
                    "§5ReflectedNBTWrapper §7| §cBukkit System already initialized - continuing."
            );
        }
    }

    private static void registerCustomTileEntity() {

    }
}
