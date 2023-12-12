package com.ise.taxiapp.dataStructures;

import java.util.stream.Stream;

/**
 * The List interface defines a general-purpose list collection.
 *
 * @param <T> Type of elements in the list
 */
public interface List<T> {
    /**
     * Adds an element to the list.
     *
     * @param element element to be added to the list
     */
    void add(T element);

    /**
     * Returns the number of elements in the list.
     *
     * @return number of elements in the list
     */
    int size();

    /**
     * Retrieves an element from the list.
     *
     * @return an element from the list
     */
    T get();

    /**
     * Removes an element from the list.
     */
    void remove();

    /**
     * Removes the specified element from the list, if it is present.
     *
     * @param element element to be removed from the list, if present
     * @return true if the list contained the specified element
     */
    boolean remove(T element);

    /**
     * Removes all elements from the list.
     */
    void clear();

    /**
     * Returns true if the list contains no elements.
     *
     * @return true if the list contains no elements
     */
    boolean isEmpty();

    /**
     * Returns a sequential Stream with this list as its source.
     *
     * @return a sequential Stream over the elements in the list
     */
    Stream<T> stream();

    /**
     * Returns true if the list contains the specified element.
     *
     * @param element element whose presence in the list is to be tested
     * @return true if the list contains the specified element
     */
    boolean contains(T element);
}
