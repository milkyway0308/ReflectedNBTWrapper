package skywolf46.NBTUtil.v1_2R4.Interface;

public interface IReflectedNBTBase<T> {
    T getValue();

    IReflectedNBTBase<T> getNBTValue();

    void setValue(T value);

    void setNBTValue(IReflectedNBTBase<T> base);

    Object getNBTBase();
}
