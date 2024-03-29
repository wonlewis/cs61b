package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    private IntNode sentinel;

    private int size;

    public LinkedListDeque() {
        sentinel =  new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
        size = 0;
    }


    private class IntNode {
        private T thisItem;
        private IntNode next;
        private IntNode previous;

        IntNode(T item, IntNode nextIntNode, IntNode prevIntNode) {
            thisItem = item;
            next = nextIntNode;
            previous = prevIntNode;
        }
    }

    @Override
    public void addFirst(T item) {

        IntNode addNode = new IntNode(item, sentinel.next, sentinel);
        if (this.size == 0) {
            sentinel.previous = addNode;
        }
        sentinel.next.previous = addNode;
        sentinel.next = addNode;
        size++;
    }

    @Override
    public void addLast(T item) {
        IntNode addNode = new IntNode(item, sentinel, sentinel.previous);
        if (this.size == 0) {
            sentinel.next = addNode;
        }
        sentinel.previous.next = addNode;
        sentinel.previous = addNode;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        IntNode thisNode = sentinel.next;
        while (thisNode != sentinel && thisNode != null) {
            System.out.printf(thisNode.thisItem + " ");
            thisNode = thisNode.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (sentinel.next != sentinel) {
            IntNode removedNode = sentinel.next;
            sentinel.next = removedNode.next;
            sentinel.next.previous = removedNode.previous;
            size--;
            return removedNode.thisItem;
        } else { return null; }
    }

    @Override
    public T removeLast() {
        if (sentinel.previous != sentinel) {
            IntNode removedNode = sentinel.previous;
            sentinel.previous = removedNode.previous;
            sentinel.previous.next = removedNode.next;
            size--;
            return removedNode.thisItem;
        } else { return null; }
    }

    @Override
    public T get(int index) {
        if (index >= size()) { return null; }
        int thisIndex = index;
        IntNode thisNode = sentinel.next;
        while (thisIndex > 0 && thisNode != null) {
            thisNode = thisNode.next;
            thisIndex--;
        }
        return thisNode.thisItem;
    }

    public T getRecursive(int index) {
        if (index > size()) { return null; }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, IntNode thisNode) {
        if (index == 0) { return thisNode.thisItem; }
        thisNode = thisNode.next;
        return getRecursiveHelper(index - 1, thisNode);
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this ) { return true; }
        if (!(o instanceof Deque)) { return false; }
        Deque<T> oAsDeque = (Deque<T>) o;
        if (!(oAsDeque.size() == size())) { return false; }
        int thisIndex = 0;
        while (thisIndex < size()) {
            if (this.get(thisIndex) != oAsDeque.get(thisIndex)) { return false; }
            thisIndex++;
        }
        return true;
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private int thisIndex;

        public LinkedListDequeIterator() {
            thisIndex = 0;
        }
        @Override
        public boolean hasNext() {
            return thisIndex < size();
        }

        @Override
        public T next() {
            T returnItem = get(thisIndex);
            thisIndex++;
            return returnItem;
        }
    }

}
