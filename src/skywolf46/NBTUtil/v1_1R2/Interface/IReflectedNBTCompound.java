package skywolf46.NBTUtil.v1_1R2.Interface;

import java.util.HashMap;
import java.util.List;

public interface IReflectedNBTCompound extends IReflectedNBTBase<HashMap<String, IReflectedNBTBase<?>>> {
    Object get(String str);
    IReflectedNBTBase<?> getNBT(String str);

    IReflectedNBTCompound set(String str,Object o);
    IReflectedNBTCompound setNBT(String str,IReflectedNBTBase<?> o);

    List<String> keyset();
}
