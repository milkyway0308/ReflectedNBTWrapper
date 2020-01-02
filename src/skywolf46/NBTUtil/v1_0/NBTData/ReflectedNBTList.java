package skywolf46.NBTUtil.v1_0.NBTData;

import skywolf46.NBTUtil.v1_0.BukkitVersionUtil;
import skywolf46.NBTUtil.v1_0.ReflectedNBTBase;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class ReflectedNBTList extends ReflectedNBTBase {
    private Class nbtClass = null;
    private List<ReflectedNBTBase> nb = new ArrayList<>();

    private static Class NBT_CLASS;
    private static Constructor NBT_CONSTRUCTOR;
    private static Field CONTENT_FIELD;
    private static Field CONTENT_NUMbER_FIELD;
    private static Method TYPE_NUMBER_METHOD;

    static {
        try {
            TYPE_NUMBER_METHOD = BukkitVersionUtil.getNMSClass("NBTBase").getMethod("getTypeId");
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagList");
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("list");
            CONTENT_FIELD.setAccessible(true);
            CONTENT_NUMbER_FIELD = NBT_CLASS.getDeclaredField("type");
            CONTENT_NUMbER_FIELD.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(CONTENT_FIELD, CONTENT_FIELD.getModifiers() & ~Modifier.FINAL);
            NBT_CONSTRUCTOR = NBT_CLASS.getConstructor();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTList(Object o) {
        if (!o.getClass().equals(NBT_CLASS)) {
            return;
        }
        try {
            List<Object> listNBT = (List<Object>) CONTENT_FIELD.get(o);
            for (Object obj : listNBT)
                nb.add(ReflectedNBTBase.createReflectedNBT(obj));
            if (listNBT.size() != 0)
                nbtClass = listNBT.getClass();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTList() {

    }

    public ReflectedNBTList addNBT(ReflectedNBTBase nbt) {
        if (nbtClass == null)
            nbtClass = nbt.getClass();
        else {
            if (!nbtClass.equals(nbt.getClass()))
                throw new IllegalStateException("Unexpected NBT class : list class is " + nbtClass.getSimpleName() + " but " + nbt.getClass().getSimpleName() + " detected");
        }
        nb.add(nbt);
        return this;
    }

    public int size() {
        return nb.size();
    }

    public ReflectedNBTBase get(int index) {
        return nb.get(index);
    }

    public ReflectedNBTBase remove(int index) {
        return nb.remove(index);
    }

    @Override
    public Object getNBTBase() {
        List<Object> listNBt = new ArrayList<>();
        for (ReflectedNBTBase nb : this.nb)
            listNBt.add(nb.getNBTBase());
        try {
            Object next = NBT_CONSTRUCTOR.newInstance();
            CONTENT_FIELD.set(next, listNBt);
            if (listNBt.size() != 0)
                CONTENT_NUMbER_FIELD.set(next, TYPE_NUMBER_METHOD.invoke(listNBt.get(0)));
            return next;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
