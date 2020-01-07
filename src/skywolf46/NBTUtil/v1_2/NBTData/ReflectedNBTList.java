package skywolf46.NBTUtil.v1_2.NBTData;

import skywolf46.NBTUtil.v1_2.BukkitVersionUtil;
import skywolf46.NBTUtil.v1_2.Exception.CollectionMismatchedException;
import skywolf46.NBTUtil.v1_2.Exception.UndefinedNBTException;
import skywolf46.NBTUtil.v1_2.Interface.IReflectedNBTBase;
import skywolf46.NBTUtil.v1_2.Interface.IReflectedNBTList;
import skywolf46.NBTUtil.v1_2.Iterator.LinearIterator;
import skywolf46.NBTUtil.v1_2.ReflectedNBTStorage;

import javax.annotation.Nonnull;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReflectedNBTList implements IReflectedNBTList {
    private Class nbtClass = null;
    private List<IReflectedNBTBase<?>> nb = new ArrayList<>();

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
                nb.add(ReflectedNBTStorage.createReflectedNBT(obj));
            if (listNBT.size() != 0)
                nbtClass = listNBT.getClass();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ReflectedNBTList() {

    }

    public int size() {
        return nb.size();
    }

    @Override
    public IReflectedNBTList add(Object o) {
        IReflectedNBTBase<?> ref = ReflectedNBTStorage.createReflectedNBT(o);
        if (ref == null)
            throw new UndefinedNBTException(o.getClass());
        return addNBT(ref);
    }

    @Override
    public IReflectedNBTList set(int index, Object o) {
        IReflectedNBTBase ref = ReflectedNBTStorage.createReflectedNBT(o);
        if (ref == null)
            throw new UndefinedNBTException(o.getClass());
        return setNBT(index, ref);
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        return nb.get(index).getValue();
    }

    @Override
    public IReflectedNBTList addNBT(IReflectedNBTBase<?> o) {
        if (nbtClass == null) {
            nbtClass = o.getClass();
            nb.add(o);
            return this;
        }
        if (!nbtClass.equals(o.getClass()))
            throw new CollectionMismatchedException(nbtClass, o.getClass());
        nb.add(o);
        return this;
    }

    @Override
    public IReflectedNBTList setNBT(int index, IReflectedNBTBase<?> o) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        if (!nbtClass.equals(o.getClass()))
            throw new CollectionMismatchedException(nbtClass, o.getClass());
        nb.set(index, o);
        return this;
    }

    @Override
    public IReflectedNBTBase<?> getNBT(int index) {
        return nb.get(index);
    }

    @Override
    public List<IReflectedNBTBase<?>> getNBTList() {
        return new ArrayList<>(nb);
    }

    @Override
    public List<Object> getValue() {
        List<Object> unwrap = new ArrayList<>();
        for (IReflectedNBTBase<?> ref : nb)
            unwrap.add(ref.getValue());
        return unwrap;
    }

    @Override
    public IReflectedNBTBase<List<Object>> getNBTValue() {
        ReflectedNBTList rl = new ReflectedNBTList();
        rl.nb = new ArrayList<>(nb);
        rl.nbtClass = nbtClass;
        return rl;
    }

    @Override
    public void setValue(List<Object> value) {
        nbtClass = null;
        nb.clear();
        for (Object o : value) {
            IReflectedNBTBase<?> ref = ReflectedNBTStorage.createReflectedNBT(o);
            if (ref == null)
                continue;
            if (nbtClass == null)
                nbtClass = ref.getClass();
            if (!nbtClass.equals(ref.getClass())) {
                nb.clear();
                nbtClass = null;
                throw new CollectionMismatchedException(nbtClass, ref.getClass());
            }
            nb.add(ref);
        }
    }

    @Override
    public void setNBTValue(IReflectedNBTBase<List<Object>> base) {
        setValue(base.getValue());
    }


    @Override
    public Object getNBTBase() {
        List<Object> listNBt = new ArrayList<>();
        for (IReflectedNBTBase<?> nb : this.nb)
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

    @Override
    @Nonnull
    public Iterator<Object> iterator() {
        return new LinearIterator(getValue().toArray());
    }
}
