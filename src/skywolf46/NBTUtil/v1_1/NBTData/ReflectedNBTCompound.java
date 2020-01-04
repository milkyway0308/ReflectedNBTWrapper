package skywolf46.NBTUtil.v1_1.NBTData;

import skywolf46.NBTUtil.v1_1.BukkitVersionUtil;
import skywolf46.NBTUtil.v1_1.Interface.IReflectedNBTBase;
import skywolf46.NBTUtil.v1_1.Interface.IReflectedNBTCompound;
import skywolf46.NBTUtil.v1_1.ReflectedNBTStorage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ReflectedNBTCompound implements IReflectedNBTCompound {
    private HashMap<String, IReflectedNBTBase<?>> d = new HashMap<>();
    private static Class NBT_CLASS;
    private static Constructor NBT_CONSTRUCTOR;
    private static Field CONTENT_FIELD;

    static {
        try {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagCompound");
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("map");
            CONTENT_FIELD.setAccessible(true);
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

    public ReflectedNBTCompound(Object o) {
        if (!o.getClass().equals(NBT_CLASS)) {
            return;
        }
        try {
            HashMap<String, Object> compoundHashMap = (HashMap<String, Object>) CONTENT_FIELD.get(o);
            for (Map.Entry<String, Object> entry : compoundHashMap.entrySet()) {
                d.put(entry.getKey(), ReflectedNBTStorage.createReflectedNBT(entry.getValue()));
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTCompound() {

    }

    public Object getValue(String key) {
        return d.get(key);
    }

    public void setValue(String key, IReflectedNBTBase<?> val) {
        d.put(key, val);
    }

    public boolean containsValue(String key) {
        return d.containsKey(key);
    }

    @Override
    public HashMap<String, IReflectedNBTBase<?>> getValue() {
        return new HashMap<>(d);
    }

    @Override
    public IReflectedNBTBase<HashMap<String, IReflectedNBTBase<?>>> getNBTValue() {
        ReflectedNBTCompound ref = new ReflectedNBTCompound();
        ref.d = new HashMap<>(d);
        return ref;
    }

    @Override
    public void setValue(HashMap<String, IReflectedNBTBase<?>> value) {
        this.d = new HashMap<>(value);
    }

    @Override
    @Deprecated
    public void setNBTValue(IReflectedNBTBase<HashMap<String, IReflectedNBTBase<?>>> base) {
        this.d = new HashMap<>(base.getValue());
    }

    public Object getNBTBase() {
        try {
            HashMap<String, Object> val = new HashMap<>();
            for (Map.Entry<String, IReflectedNBTBase<?>> entry : d.entrySet()) {
                val.put(entry.getKey(), entry.getValue().getNBTBase());
            }
            Object v = NBT_CONSTRUCTOR.newInstance();
            CONTENT_FIELD.set(v, val);
            return v;
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
