package skywolf46.NBTUtil.v1_1R3.Iterator;

import java.util.Iterator;

public class LinearIterator implements Iterator<Object> {
    private Object[] data;
    private int pointer;

    public LinearIterator(Object[] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return pointer >= data.length;
    }

    @Override
    public Object next() {
        return data[pointer++];
    }
}
