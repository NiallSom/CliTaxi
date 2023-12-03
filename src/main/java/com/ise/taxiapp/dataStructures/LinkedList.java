package com.ise.taxiapp.dataStructures;

public class LinkedList<T> {

    private ListNode<T> head;
    private ListNode<T> current;
    private static class ListNode<T>{
        private T data;
        private ListNode<T> next;
        // need to implement doubly linked list
        public ListNode(T data){
            this.data = data;
            this.next = null;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public ListNode<T> retrieve(T element) {
        if (isEmpty()){
            System.out.println("List is empty, cannot retrieve element: "+element); // not sure how to string format with generics
            return null;
        }
        ListNode<T> current = head;
        while (current != null) {
            if (current.data != null && current.data.equals(element)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void update(T element, T data) {
        retrieve(element).data = data;
    }

    public void insert(T element) {
        if (isEmpty()) {
            System.out.println("List is empty, element will be inserted at location 0");
            head = new ListNode<>(element);
            current = head;
            return;
        }
        ListNode<T> temp = head;
        ListNode<T> newNode = new ListNode<T>(element);
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
        current = newNode;
    }
}


