package com.ise.taxiapp.dataStructures;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LinkedList<T> {
    private ListNode head;
    private ListNode current;
    private int size;

    public LinkedList() {
        head = current = null;
        size = 0;
    }

    public void getNext() {
        if (this.current.next == null) {
            System.out.println("There is no next node");
            return;
        }
        this.current = this.current.next;
    }

    public void getPrevious() {
        if (this.current.previous == null) {
            System.out.println("There is no previous node");
            return;
        }
        this.current = this.current.previous;
    }

    public int size() {
        return size;
    }

    public boolean hasNext() {
        return !isEmpty() && this.current.next != null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public T retrieve() {
        if (isEmpty()) throw new UnsupportedOperationException("Cannot retrieve from an empty list");
        return this.current.data;
    }

    public void findFirst() {
        current = head;
    }

    public void forEach(Consumer<T> action) {
        findFirst();
        while (hasNext()) {
            action.accept(current.data);
            getNext();
        }
        action.accept(current.data);
    }

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


    @SuppressWarnings("unused")
    public void printList() {
        ListNode temp = head;
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println();
    }

    public void remove() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("Cannot remove from an empty list");
        }
        size--;

        if (this.current == head) {
            if (this.current.next != null) {
                current = head = this.current.next;
                return;
            }
            current = head = null;
        }
        if (this.current !=null) {
            if (this.current.next != null) {
                this.current.next.previous = this.current.previous;
                this.current.previous.next = this.current.next;
                this.current = this.current.next;
            } else {
                this.current.previous.next = null;
                this.current = this.current.previous;
            }
        }else {
            current = head = null;
        }
    }

    public boolean remove(T toRemove) {
        if (isEmpty()) {
            throw new UnsupportedOperationException("Cannot remove from an empty list");
        }
        while (hasNext()) {
            T data = retrieve();
            if (data == toRemove) {
                remove();
                return true;
            }
        }
        T data = retrieve();
        if (data == toRemove) {
            remove();
            return true;
        }
        return false;
    }

    public void clear() {
        head = current = null;
    }

    public Stream<T> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                new Iterator<>() {
                    ListNode current = head;

                    @Override
                    public boolean hasNext() {
                        return current != null;
                    }

                    @Override
                    public T next() {
                        T data = current.data;
                        current = current.next;
                        return data;
                    }
                },
                Spliterator.ORDERED
        ), false);
    }

    public boolean contains(T data) {
        return this.stream().anyMatch(item -> item.equals(data));
    }


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