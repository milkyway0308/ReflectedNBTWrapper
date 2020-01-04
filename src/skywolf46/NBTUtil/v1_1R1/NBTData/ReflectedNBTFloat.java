package skywolf46.NBTUtil.v1_1R1.NBTData;

import skywolf46.NBTUtil.v1_1R1.BukkitVersionUtil;
import skywolf46.NBTUtil.v1_1R1.Interface.IReflectedNBTBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectedNBTFloat implements IReflectedNBTBase<Float> {
    private float d;
    private static Class NBT_CLASS;
    private static Constructor NBT_CONSTRUCTOR;
    private static Field CONTENT_FIELD;

    static {
        try {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagFloat");
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data");
            CONTENT_FIELD.setAccessible(true);
            NBT_CONSTRUCTOR = NBT_CLASS.getConstructor(Float.TYPE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTFloat(Object o) {
        if (!o.getClass().equals(NBT_CLASS)) {
            return;
        }
        try {
            this.d = (float) CONTENT_FIELD.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTFloat() {
        this.d = 0;
    }

    @Override
    public Float getValue() {
        return d;
    }

    @Override
    public IReflectedNBTBase<Float> getNBTValue() {
        return new ReflectedNBTFloat(this.d);
    }

    @Override
    public void setValue(Float value) {
        this.d = value;
    }

    @Override
    public void setNBTValue(IReflectedNBTBase<Float> base) {
        this.d = base.getValue();
    }

    @Override
    public Object getNBTBase() {
        try {
            return NBT_CONSTRUCTOR.newInstance(d);
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
