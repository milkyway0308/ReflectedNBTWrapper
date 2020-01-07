package skywolf46.NBTUtil.v1_2.Exception;

public class UndefinedNBTException extends RuntimeException {
    public UndefinedNBTException(Class c) {
        super("Class " + c.getSimpleName() + " not has nbt exchanger");
    }
}
