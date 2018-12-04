package net.tsystems.util;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtils <T> {
    public T get(Collection<T> coll, int index) {
        if (coll == null)
            return null;

        Iterator<T> iter = coll.iterator();   // Iterator of Strings
        T item = null;
        int i = 0;
        while (iter.hasNext() && i < index) {
            item = iter.next();  // compiler inserts downcast operator
            i++;
        }

        return item;
    }

    public T getFirst(Collection<T> coll) {
        return get(coll, 1);
    }

    public T getLast(Collection<T> coll) {
        Iterator<T> iter = coll.iterator();   // Iterator of Strings
        T item = null;
        while (iter.hasNext()) {
            item = iter.next();
        }

        return item;
    }
}
