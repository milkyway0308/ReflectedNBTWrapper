package skywolf46.NBTUtil.v1_1R1.Exception;

public class CollectionMismatchedException extends RuntimeException {
    public CollectionMismatchedException(Class bef, Class nex) {
        super("Mismatching collection class: expected " + bef.getSimpleName() + " but " + nex.getSimpleName() + " detected");
    }
}
