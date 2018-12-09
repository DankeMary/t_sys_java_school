package net.tsystems.util;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtils <T> {
    public static <T> T getFirst(final Collection<T> set) {
        if (isEmpty(set)) {
            return null;
        }
        return set.iterator().next();
    }

    public static <T> T get(Collection<T> collection, int index) {
        if (isEmpty(collection))
            return null;

        Iterator<T> iter = collection.iterator();
        T item = null;
        int i = 0;
        while (iter.hasNext() && i < index) {
            item = iter.next();
            i++;
        }

        return item;
    }

    public static <T> T getLast(Collection<T> collection) {
        if (isEmpty(collection))
            return null;

        Iterator<T> iter = collection.iterator();
        T item = null;
        while (iter.hasNext()) {
            item = iter.next();
        }

        return item;
    }

    public static <T> boolean isEmpty(final Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
}
