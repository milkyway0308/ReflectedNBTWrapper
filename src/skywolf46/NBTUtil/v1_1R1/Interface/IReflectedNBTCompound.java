package skywolf46.NBTUtil.v1_1R1.Interface;

import java.util.HashMap;

public interface IReflectedNBTCompound extends IReflectedNBTBase<HashMap<String, IReflectedNBTBase<?>>> {
    Object get(String str);
    IReflectedNBTBase<?> getNBT(String str);

    IReflectedNBTCompound set(String str,Object o);
    IReflectedNBTCompound setNBT(String str,IReflectedNBTBase<?> o);
}
