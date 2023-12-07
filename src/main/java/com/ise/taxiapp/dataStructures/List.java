package com.ise.taxiapp.dataStructures;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface List<T> {
    void add(T element);

    int size();

    T get();

    void forEach(Consumer<T> action);

    void remove();

    boolean remove(T element);

    void clear();

    boolean isEmpty();

    Stream<T> stream();

    boolean contains(T element);
}
