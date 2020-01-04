package skywolf46.NBTUtil.v1_1.NBTData;

import skywolf46.NBTUtil.v1_1.BukkitVersionUtil;
import skywolf46.NBTUtil.v1_1.Interface.IReflectedNBTBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectedNBTByteArray implements IReflectedNBTBase<Byte[]> {
    private Byte[] data;

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
            byte[] val = (byte[]) CONTENT_FIELD.get(o);
            data = new Byte[val.length];
            for (int i = 0; i < val.length; i++)
                data[i] = val[i];
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTByteArray(Byte[] arr) {
        this.data = Arrays.copyOf(arr, arr.length);
    }

    @Override
    public Byte[] getValue() {
        return data;
    }

    @Override
    public IReflectedNBTBase<Byte[]> getNBTValue() {
        return new ReflectedNBTByteArray(this.data);
    }

    @Override
    public void setValue(Byte[] value) {
        this.data = value;
    }

    @Override
    public void setNBTValue(IReflectedNBTBase<Byte[]> base) {
        this.data = Arrays.copyOf(base.getValue(), base.getValue().length);
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
