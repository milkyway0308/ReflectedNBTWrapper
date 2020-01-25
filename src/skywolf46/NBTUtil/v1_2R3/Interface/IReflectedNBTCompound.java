package skywolf46.NBTUtil.v1_2R3.Interface;

import java.util.HashMap;
import java.util.List;

public interface IReflectedNBTCompound extends IReflectedNBTBase<HashMap<String, Object>> {
    Object get(String str);

    IReflectedNBTBase<?> getNBT(String str);

    IReflectedNBTCompound set(String str, Object o);

    IReflectedNBTCompound setNBT(String str, IReflectedNBTBase<?> o);

    IReflectedNBTBase<?> remove(String str);

    List<String> keyset();

    void collapse(IReflectedNBTCompound ref);

    void clear();

    boolean containsKey(String key);

    HashMap<String, IReflectedNBTBase<?>> getNBTMap();

}
