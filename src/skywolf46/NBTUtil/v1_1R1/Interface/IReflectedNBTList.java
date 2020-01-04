package skywolf46.NBTUtil.v1_1R1.Interface;

import java.util.List;

public interface IReflectedNBTList extends IReflectedNBTBase<List<Object>> {

    int size();

    IReflectedNBTList add(Object o);

    IReflectedNBTList set(int index, Object o);

    Object get(int index);

    IReflectedNBTList addNBT(IReflectedNBTBase<?> o);

    IReflectedNBTList setNBT(int index, IReflectedNBTBase<?> o);

    IReflectedNBTBase<?> getNBT(int index);

    List<IReflectedNBTBase<?>> getNBTList();
}
