package skywolf46.NBTUtil.v1_1R1;

import skywolf46.NBTUtil.v1_1R1.Interface.IReflectedNBTBase;
import skywolf46.NBTUtil.v1_1R1.NBTData.*;

import java.util.HashMap;
import java.util.function.Function;

public final class ReflectedNBTStorage {
    private static HashMap<Class, Function<Object, ? extends IReflectedNBTBase<?>>> map = new HashMap<>();

    static {
        try {
            registerNBTBase(BukkitVersionUtil.getNMSClass("NBTTagInt"), ReflectedNBTInteger::new);
            registerNBTBase(BukkitVersionUtil.getNMSClass("NBTTagDouble"), ReflectedNBTDouble::new);
            registerNBTBase(BukkitVersionUtil.getNMSClass("NBTTagCompound"), ReflectedNBTCompound::new);
            registerNBTBase(BukkitVersionUtil.getNMSClass("NBTTagShort"), ReflectedNBTShort::new);
            registerNBTBase(BukkitVersionUtil.getNMSClass("NBTTagString"), ReflectedNBTString::new);
            registerNBTBase(BukkitVersionUtil.getNMSClass("NBTTagByte"), ReflectedNBTByte::new);
            registerNBTBase(BukkitVersionUtil.getNMSClass("NBTTagFloat"), ReflectedNBTFloat::new);
            registerNBTBase(BukkitVersionUtil.getNMSClass("NBTTagLong"), ReflectedNBTLong::new);
            registerNBTBase(BukkitVersionUtil.getNMSClass("NBTTagLongArray"), ReflectedNBTLongArray::new);
            registerNBTBase(BukkitVersionUtil.getNMSClass("NBTTagByteArray"), ReflectedNBTByteArray::new);
            registerNBTBase(BukkitVersionUtil.getNMSClass("NBTTagIntArray"), ReflectedNBTIntegerArray::new);
            registerNBTBase(BukkitVersionUtil.getNMSClass("NBTTagList"), ReflectedNBTList::new);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ReflectedNBTStorage() {

    }

    public static IReflectedNBTBase<?> createReflectedNBT(Object nbtData) {
        if (map.containsKey(nbtData.getClass()))
            return map.get(nbtData.getClass()).apply(nbtData);
        return null;
    }

    public static void registerNBTBase(Class c, Function<Object, ? extends IReflectedNBTBase<?>> funct) {
        map.put(c, funct);
    }

}
