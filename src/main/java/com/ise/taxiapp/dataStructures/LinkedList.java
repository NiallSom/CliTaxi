package com.ise.taxiapp.dataStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A linked list implementation that supports basic operations such as adding, removing,
 * retrieving, and checking for presence of elements.
 *
 * @param <T> the type of elements stored in the linked list
 */
public class LinkedList<T> implements List<T>, Iterable<T> {
    private ListNode head;
    private ListNode current;
    private int size;

    /**
     * Creates an empty linked list.
     */
    public LinkedList() {
        head = current = null;
        size = 0;
    }

    /**
     * Sets the pointer to the next node if it exists
     */
    public void getNext() {
        if (this.current.next == null) {
            System.out.println("There is no next node");
            return;
        }
        this.current = this.current.next;
    }

    /**
     * Sets the pointer to the previous node if it exists
     */
    public void getPrevious() {
        if (this.current.previous == null) {
            System.out.println("There is no previous node");
            return;
        }
        this.current = this.current.previous;
    }

    /**
     * Returns the size of the linked list
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the LinkedList has a next node
     *
     * @return true if there is a next node, false otherwise
     */
    public boolean hasNext() {
        return !isEmpty() && this.current.next != null;
    }

    /**
     * returns boolean true if linked list is empty
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Retrieves data of the current node
     */
    public T get() {
        if (isEmpty()) throw new UnsupportedOperationException("Cannot retrieve from an empty list");
        return this.current.data;
    }

    public T get(int index) {
        return this.stream().skip(index).findFirst().orElse(null);
    }

    /**
     * Sets the pointer to the head of the linked list
     */
    public void findFirst() {
        current = head;
    }

    /**
     * Applies the given action to each element in the linked list.
     *
     * @param action the action to be applied to each element
     */
    public void forEach(Consumer<? super T> action) {
        findFirst();
        while (hasNext()) {
            action.accept(current.data);
            getNext();
        }
        action.accept(current.data);
    }

    /**
     * Inserts a new node containing the specified data after the current pointer.
     * This method advances the pointer to the newly added node.
     *
     * @param data the data to be set in the new node
     */
    public void add(T data) {
        ListNode newNode = new ListNode(data);
        size++;
        if (isEmpty()) {
            this.current = head = newNode;
            return;
        }
        newNode.next = this.current.next;
        newNode.previous = this.current;
        if (this.current.next != null) {
            this.current.next.previous = newNode;
        }
        this.current.next = newNode;
        this.current = newNode;
    }

    /**
     * Removes the current node.
     * Throws UnsupportedOperationException if the list is empty.
     */
    public void remove() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("Cannot remove from an empty list");
        }
        size--;
        if (this.current == head) {
            if (this.current.next != null) {
                current = head = this.current.next;
            } else {
                current = head = null;
            }
        } else if (this.current != null) {
            if (this.current.next != null) {
                this.current.next.previous = this.current.previous;
                this.current.previous.next = this.current.next;
                this.current = this.current.next;
            } else {
                this.current.previous.next = null;
                this.current = this.current.previous;
            }
        } else {
            head = null;
        }
    }

    /**
     * Removes the first node with the given data, if it exists.
     * Returns a boolean stating if the operation was successful.
     *
     * @param toRemove The data to remove from the list
     * @return If the specified element was removed
     */
    public boolean remove(T toRemove) {
        if (isEmpty()) {
            throw new UnsupportedOperationException("Cannot remove from an empty list");
        }
        findFirst();
        do {
            T data = get();
            if (data.equals(toRemove)) {
                remove();
                return true;
            }
            getNext();
        } while (hasNext());
        return false;
    }

    /**
     * Clears the list.
     */
    public void clear() {
        head = current = null;
    }

    /**
     * Returns a Stream of elements from this LinkedList. The elements are retrieved from
     * a custom Iterator implementation that iterates over the nodes of the LinkedList.
     * The Stream is ordered.
     *
     * @return a Stream of elements from this LinkedList
     */
    public Stream<T> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator(),
                Spliterator.ORDERED
        ), false);
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        forEach(data -> sb.append(data).append(", "));
        sb.deleteCharAt(sb.length() - 3);  // remove trailing ", "
        sb.append("]");
        return sb.toString();
    }

    /**
     * Checks if the LinkedList contains the specified data.
     *
     * @param data the data to search for
     * @return {@code true} if the LinkedList contains the data, {@code false} otherwise
     */
    public boolean contains(T data) {
        return this.stream().anyMatch(item -> item.equals(data));
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            ListNode current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }


    /**
     * Represents a node in a linked list.
     */
    private class ListNode {
        private final T data;
        private ListNode next;
        private ListNode previous;

        public ListNode(T data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }
    }
}