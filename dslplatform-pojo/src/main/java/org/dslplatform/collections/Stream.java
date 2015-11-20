package org.dslplatform.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Stream<T> {

    public static interface Predicate<T> {
        public boolean apply(T t);
    }

    public static interface Procedure<T> {
        public void apply(T t);
    }

    private final Collection<T> collection;
    private final Map<Predicate<T>, Procedure<T>> procedures = new HashMap<Predicate<T>, Procedure<T>>();

    public Stream(Collection<T> collection) {
        this.collection = collection;
    }

    public static Stream with(Collection collection) {
        return new Stream(collection);
    }

    public Stream<T> where(Predicate<T> predicate, Procedure<T> procedure) {
        procedures.put(predicate, procedure);
        return this;
    }

    public void process() {
        for(Predicate<T> predicate: procedures.keySet()) {
            for(T t: collection) {
                if(predicate.apply(t)) {
                    procedures.get(predicate).apply(t);
                }
            }
        }
    }
}
