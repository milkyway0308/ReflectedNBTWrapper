package skywolf46.NBTUtil.v1_3;

import skywolf46.NBTUtil.v1_3.Interface.IReflectedNBTBase;
import skywolf46.NBTUtil.v1_3.NBTData.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            registerNBTBase(ArrayList.class,a -> new ReflectedNBTList((List<Object>)a));
            registerNBTBase(List.class,a -> new ReflectedNBTList((List<Object>)a));

            registerNBTBase(String.class, str -> {
                ReflectedNBTString nbt = new ReflectedNBTString();
                nbt.setValue((String) str);
                return nbt;
            });

            registerNBTBase(Integer.class, str -> {
                ReflectedNBTInteger nbt = new ReflectedNBTInteger();
                nbt.setValue((Integer) str);
                return nbt;
            });

            registerNBTBase(Integer.TYPE, map.get(Integer.class));

            registerNBTBase(Double.class, str -> {
                ReflectedNBTDouble nbt = new ReflectedNBTDouble();
                nbt.setValue((Double) str);
                return nbt;
            });

            registerNBTBase(Double.TYPE, map.get(Double.class));


            registerNBTBase(Byte.class, str -> {
                ReflectedNBTByte nbt = new ReflectedNBTByte();
                nbt.setValue((Byte) str);
                return nbt;
            });

            registerNBTBase(Byte.TYPE, map.get(Byte.class));

            registerNBTBase(Float.class, str -> {
                ReflectedNBTFloat nbt = new ReflectedNBTFloat();
                nbt.setValue((Float) str);
                return nbt;
            });

            registerNBTBase(Float.TYPE, map.get(Float.class));

            registerNBTBase(Long.class, str -> {
                ReflectedNBTLong nbt = new ReflectedNBTLong();
                nbt.setValue((Long) str);
                return nbt;
            });

            registerNBTBase(Long.TYPE, map.get(Long.class));


            registerNBTBase(Short.class, str -> {
                ReflectedNBTShort nbt = new ReflectedNBTShort();
                nbt.setValue((Short) str);
                return nbt;
            });

            registerNBTBase(Short.TYPE, map.get(Short.class));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ReflectedNBTStorage() {

    }

    public static IReflectedNBTBase<?> createReflectedNBT(Object nbtData) {
        if (nbtData instanceof IReflectedNBTBase)
            return (IReflectedNBTBase<?>) nbtData;
        if (map.containsKey(nbtData.getClass()))
            return map.get(nbtData.getClass()).apply(nbtData);
        return null;
    }

    public static void registerNBTBase(Class c, Function<Object, ? extends IReflectedNBTBase<?>> funct) {

        map.put(c, funct);
    }

}
