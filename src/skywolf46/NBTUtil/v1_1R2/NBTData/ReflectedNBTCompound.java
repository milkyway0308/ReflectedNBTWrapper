package skywolf46.NBTUtil.v1_1R2.NBTData;

import skywolf46.NBTUtil.v1_1R2.BukkitVersionUtil;
import skywolf46.NBTUtil.v1_1R2.Exception.UndefinedNBTException;
import skywolf46.NBTUtil.v1_1R2.Interface.IReflectedNBTBase;
import skywolf46.NBTUtil.v1_1R2.Interface.IReflectedNBTCompound;
import skywolf46.NBTUtil.v1_1R2.ReflectedNBTStorage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public Object get(String str) {
        IReflectedNBTBase<?> ref = getNBT(str);
        if(ref == null)
            return null;
        return ref.getValue();
    }

    @Override
    public IReflectedNBTBase<?> getNBT(String str) {
        IReflectedNBTBase<?> ref = d.get(str);
        if (ref == null)
            return null;
        return ref;
    }

    @Override
    public IReflectedNBTCompound set(String str, Object o) {
        IReflectedNBTBase<?> ref = ReflectedNBTStorage.createReflectedNBT(o);
        if (ref == null)
            throw new UndefinedNBTException(o.getClass());
        return setNBT(str, ref);
    }

    @Override
    public IReflectedNBTCompound setNBT(String str, IReflectedNBTBase<?> o) {
        d.put(str, o);
        return this;
    }

    @Override
    public List<String> keyset() {
        return new ArrayList<>(d.keySet());
    }

    public boolean containsKey(String key) {
        return d.containsKey(key);
    }

}
