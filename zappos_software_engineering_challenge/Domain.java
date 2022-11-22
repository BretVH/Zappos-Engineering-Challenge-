package zappos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import aima.core.util.ArrayIterator;
import java.util.Arrays;

/**
 * A domain Di consists of a set of allowable values {v1, ... , vk} for the
 * corresponding variable Xi and defines a default order on those values. This
 * implementation guarantees, that domains are never changed after they have
 * been created. Domain reduction is implemented by replacement instead of
 * modification. So previous states can easily and safely be restored.
 *
 * @author Ruediger Lunde
 * @author Bret Van Hof
 */
public class Domain implements Iterable<Object> {

    private static final long serialVersionUID = 1L;

    private final Object[] values;

    public Domain(List<?> values) {
        this.values = new Object[values.size()];
        for (int i = 0; i < values.size(); i++) {
            this.values[i] = values.get(i);
        }
    }

    public Domain(Object[] values) {
        this.values = new Object[values.length];
        System.arraycopy(values, 0, this.values, 0, values.length);
    }

    public int size() {
        return values.length;
    }

    public Object get(int index) {
        return values[index];
    }

    public boolean isEmpty() {
        return values.length == 0;
    }

    public boolean contains(Object value) {
        for (Object v : values) {
            if (v.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Object> iterator() {
        // TODO Auto-generated method stub
        return new ArrayIterator<>(values);
    }

    /**
     * Not very efficient...
     *
     * @return
     */
    public List<Object> asList() {
        List<Object> result = new ArrayList<>();
        result.addAll(Arrays.asList(values));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Domain) {
            Domain d = (Domain) obj;
            if (d.size() != values.length) {
                return false;
            } else {
                for (int i = 0; i < values.length; i++) {
                    if (!values[i].equals(d.values[i])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 9; // arbitrary seed value
        int multiplier = 13; // arbitrary multiplier value
        for (Object value : values) {
            hash = hash * multiplier + value.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        boolean comma = false;
        for (Object value : values) {
            if (comma) {
                result.append(", ");
            }
            result.append(value.toString());
            comma = true;
        }
        result.append("}");
        return result.toString();
    }
}
