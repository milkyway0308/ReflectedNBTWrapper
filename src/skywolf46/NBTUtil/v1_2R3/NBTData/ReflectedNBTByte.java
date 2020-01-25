package skywolf46.NBTUtil.v1_2R3.NBTData;

import skywolf46.NBTUtil.v1_2R3.BukkitVersionUtil;
import skywolf46.NBTUtil.v1_2R3.Interface.IReflectedNBTBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectedNBTByte implements IReflectedNBTBase<Byte> {
    private byte d;
    private static Class NBT_CLASS;
    private static Constructor NBT_CONSTRUCTOR;
    private static Field CONTENT_FIELD;

    static {
        try {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagByte");
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data");
            CONTENT_FIELD.setAccessible(true);
            NBT_CONSTRUCTOR = NBT_CLASS.getConstructor(Byte.TYPE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTByte(Object o) {
        if (!o.getClass().equals(NBT_CLASS)) {
            return;
        }
        try {
            this.d = (byte) CONTENT_FIELD.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTByte() {
        this.d = 0;
    }

    public Byte getValue() {
        return d;
    }

    @Override
    public IReflectedNBTBase<Byte> getNBTValue() {
        return new ReflectedNBTByte(this.d);
    }

    @Override
    public void setValue(Byte value) {
        this.d = value;
    }

    @Override
    public void setNBTValue(IReflectedNBTBase<Byte> base) {
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
