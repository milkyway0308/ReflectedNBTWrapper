package skywolf46.NBTUtil.v1_2R3.NBTData;

import skywolf46.NBTUtil.v1_2R3.BukkitVersionUtil;
import skywolf46.NBTUtil.v1_2R3.Interface.IReflectedNBTBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectedNBTLongArray implements IReflectedNBTBase<Long[]> {
    private Long[] data;

    private static Class NBT_CLASS;
    private static Constructor NBT_CONSTRUCTOR;
    private static Field CONTENT_FIELD;

    static {
        try {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagLongArray");
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data");
            CONTENT_FIELD.setAccessible(true);
            NBT_CONSTRUCTOR = NBT_CLASS.getConstructor(new long[0].getClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTLongArray(Object o) {
        if (!o.getClass().equals(NBT_CLASS)) {
            return;
        }
        try {
            long[] data = (long[]) CONTENT_FIELD.get(o);
            this.data = new Long[data.length];
            for (int i = 0; i < data.length; i++)
                this.data[i] = data[i];
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTLongArray(Long[] arr) {
        this.data = arr;
    }

    @Override
    public Long[] getValue() {
        return Arrays.copyOf(data, data.length);
    }

    @Override
    public IReflectedNBTBase<Long[]> getNBTValue() {
        return new ReflectedNBTLongArray(getValue());
    }

    @Override
    public void setValue(Long[] value) {
        this.data = Arrays.copyOf(value, value.length);
    }

    @Override
    public void setNBTValue(IReflectedNBTBase<Long[]> base) {
        setValue(base.getValue());
    }

    @Override
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
