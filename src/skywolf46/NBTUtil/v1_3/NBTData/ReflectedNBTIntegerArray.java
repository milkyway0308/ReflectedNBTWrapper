package skywolf46.NBTUtil.v1_3.NBTData;

import skywolf46.NBTUtil.v1_3.BukkitVersionUtil;
import skywolf46.NBTUtil.v1_3.Interface.IReflectedNBTBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectedNBTIntegerArray implements IReflectedNBTBase<Integer[]> {
    private Integer[] data;

    private static Class NBT_CLASS;
    private static Constructor NBT_CONSTRUCTOR;
    private static Field CONTENT_FIELD;

    static {
        try {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagIntArray");
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data");
            CONTENT_FIELD.setAccessible(true);
            NBT_CONSTRUCTOR = NBT_CLASS.getConstructor(int[].class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTIntegerArray(Object o) {
        if (!o.getClass().equals(NBT_CLASS)) {
            return;
        }
        try {
            int[] data = (int[]) CONTENT_FIELD.get(o);
            this.data = new Integer[data.length];
            for (int i = 0; i < data.length; i++)
                this.data[i] = data[i];
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTIntegerArray(Integer[] arr) {
        this.data = arr;
    }


    @Override
    public Integer[] getValue() {
        return Arrays.copyOf(data, data.length);
    }

    @Override
    public IReflectedNBTBase<Integer[]> getNBTValue() {
        return new ReflectedNBTIntegerArray(Arrays.copyOf(data, data.length));
    }

    @Override
    public void setValue(Integer[] value) {
        this.data = Arrays.copyOf(value, value.length);
    }

    @Override
    public void setNBTValue(IReflectedNBTBase<Integer[]> base) {
        setValue(base.getValue());
    }

    public Object getNBTBase() {
        try {
            int[] data = new int[this.data.length];
            for (int i = 0; i < data.length; i++)
                data[i] = this.data[i];
            return NBT_CONSTRUCTOR.newInstance(data);
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
