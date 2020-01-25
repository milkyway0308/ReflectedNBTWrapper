package skywolf46.NBTUtil.v1_2R3.Exception;

public class UndefinedNBTException extends RuntimeException {
    public UndefinedNBTException(Class c) {
        super("Class " + c.getSimpleName() + " not has nbt exchanger");
    }
}
