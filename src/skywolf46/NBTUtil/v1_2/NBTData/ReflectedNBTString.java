package skywolf46.NBTUtil.v1_2.NBTData;

import skywolf46.NBTUtil.v1_2.BukkitVersionUtil;
import skywolf46.NBTUtil.v1_2.Interface.IReflectedNBTBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectedNBTString implements IReflectedNBTBase<String> {
    private String d;
    private static Class NBT_CLASS;
    private static Constructor NBT_CONSTRUCTOR;
    private static Field CONTENT_FIELD;

    static {
        try {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagString");
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data");
            CONTENT_FIELD.setAccessible(true);
            NBT_CONSTRUCTOR = NBT_CLASS.getConstructor(String.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTString(Object o) {
        if (!o.getClass().equals(NBT_CLASS)) {
            return;
        }
        try {
            this.d = (String) CONTENT_FIELD.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTString() {
        this.d = "";
    }

    @Override
    public String getValue() {
        return d;
    }

    @Override
    public IReflectedNBTBase<String> getNBTValue() {
        return new ReflectedNBTString(this.d);
    }

    @Override
    public void setValue(String d) {
        this.d = d;
    }

    @Override
    public void setNBTValue(IReflectedNBTBase<String> base) {
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
