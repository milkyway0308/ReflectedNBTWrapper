package skywolf46.NBTUtil.v1_2R4.Interface;

import java.util.List;

public interface IReflectedNBTList extends IReflectedNBTBase<List<Object>>, Iterable<Object> {

    int size();

    IReflectedNBTList add(Object o);

    IReflectedNBTList set(int index, Object o);

    Object remove(int index);

    Object get(int index);

    IReflectedNBTList addNBT(IReflectedNBTBase<?> o);

    IReflectedNBTList setNBT(int index, IReflectedNBTBase<?> o);

    IReflectedNBTBase<?> getNBT(int index);

    List<IReflectedNBTBase<?>> getNBTList();
}
