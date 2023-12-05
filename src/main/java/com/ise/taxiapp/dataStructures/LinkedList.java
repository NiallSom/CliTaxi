package com.ise.taxiapp.dataStructures;

public class LinkedList<T> {
    private ListNode head;
    private ListNode current;
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
    public void getNext(){
        if (this.current.next == null) {
            System.out.println("There is no next node");
            return;
        }
        this.current = this.current.next;
    }
    public void getPrevious(){
        if (this.current.previous == null) {
            System.out.println("There is no previous node");
            return;
        }
        this.current = this.current.previous;
    }
    public boolean isLast() {
        return this.current.next == null;
    }
    public boolean isEmpty(){
        return head == null;
    }
    public T retrieve() {
        return this.current.data;
    }
    public void insert(T data){
        ListNode newNode = new ListNode(data);
        if (isEmpty()) {
            System.out.println("List is empty, creating a new head");
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
    public void printList(){
        ListNode temp = head;
        while (temp != null){
            System.out.print(temp.data+" -> ");
            temp = temp.next;
        }
        System.out.println();
    }
    public void remove() {
        if (isEmpty()) {
            System.out.println("List is empty cannot remove item");
            return;
        }
        if (this.current == head){
            if (this.current.next != null){
                head = this.current.next;
                return;
            }
            head = null;
        }

        if (this.current.next != null) {
            this.current.next.previous = this.current.previous;
            this.current.previous.next = this.current.next;
            this.current = this.current.next;
        }
        this.current.previous.next = null;
        this.current = this.current.previous;

    }
}
