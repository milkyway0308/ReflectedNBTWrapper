package skywolf46.NBTUtil.v1_0.NBTData;

import skywolf46.NBTUtil.v1_0.BukkitVersionUtil;
import skywolf46.NBTUtil.v1_0.ReflectedNBTBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectedNBTByteArray extends ReflectedNBTBase {
    private byte[] data;

    private static Class NBT_CLASS;
    private static Constructor NBT_CONSTRUCTOR;
    private static Field CONTENT_FIELD;

    static {
        try {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagByteArray");
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data");
            CONTENT_FIELD.setAccessible(true);
            NBT_CONSTRUCTOR = NBT_CLASS.getConstructor(new byte[0].getClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTByteArray(Object o) {
        if (!o.getClass().equals(NBT_CLASS)) {
            return;
        }
        try {
            this.data = (byte[]) CONTENT_FIELD.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTByteArray(byte[] arr) {
        this.data = arr;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Object getNBTBase() {
        try {
            return NBT_CONSTRUCTOR.newInstance(Arrays.copyOf(data, data.length));
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
