package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int firstElement;
    private int lastElement;
    private int RFACTOR = 2;

    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
        nextFirst = 0;
        firstElement = 0;
        nextLast = items.length-1;
        lastElement = items.length-1;

    }

    private void resize(int capacity){
        T[] a = (T []) new Object[capacity];
        for (int i = 0; i < nextFirst-firstElement; i++){
            a[i] = items[firstElement+i];
        }
        int counter = 1;
        for (int j = lastElement; j > nextLast; j--){
            a[a.length - counter] = items[j];
            counter++;
        }
        nextLast = a.length - counter;
        nextFirst = nextFirst-firstElement;
        firstElement = 0;
        lastElement = a.length - 1;
        items = a;
    }

    @Override
    public void addFirst(T item){
        if (size == items.length){
            resize(size * RFACTOR);
        }
        if (nextFirst == nextLast){
            resize(items.length);
        }
        items[nextFirst] = item;
        size++;
        nextFirst++;
    }

    @Override
    public void addLast(T item){
        if (size == items.length){
            resize(size * RFACTOR);
        }
        if (nextFirst == nextLast){
            resize(items.length);
        }
        items[nextLast] = item;
        size++;
        nextLast--;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        for (int i = nextFirst - 1; i >= firstElement; i--){
            System.out.println(items[i] + " ");
        }
        for (int i = nextLast + 1; i <= lastElement; i++){
            System.out.println(items[i] + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst(){
        T result;
        if (size == 0) return null;
        double capacityPercentage = (double) size/items.length;
        if (capacityPercentage < 0.25 && items.length/2 > 8){
            resize(items.length/2);
        }
        if (nextFirst == 0){
            result = items[lastElement];
            items[lastElement] = null;
            lastElement--;
        }else {
            result = items[nextFirst-1];
            items[nextFirst-1] = null;
            nextFirst--;

        }
        size--;
        return result;
    }

    @Override
    public T removeLast(){
        T result;
        if (size == 0) return null;
        double capacityPercentage = (double) size/items.length;
        if (capacityPercentage < 0.25 && items.length/2 > 8){
            resize(items.length/2);
        }
        if (nextLast == items.length - 1){
            result = items[firstElement];
            items[firstElement] = null;
            firstElement++;
        }else {
            result = items[nextLast+1];
            items[nextLast+1]=null;
            nextLast++;
        }
        size--;
        return result;
    }

    @Override
    public T get(int index){
        if (index < nextFirst - firstElement){
            return items[nextFirst - index - 1];
        }
        return items[lastElement - (index - (nextFirst - firstElement))];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Deque)) return false;
        Deque<T> oAsDeque = (Deque<T>) o;
        if (oAsDeque.size() == size()) return false;
        Iterator<T> original = this.iterator();
        Iterator<T> oObject = oAsDeque.iterator();
        while (oObject.hasNext()){
            if (oObject.next() != original.next()) return false;
        }
        return true;
    }

    private class ArrayDequeIterator implements Iterator<T> {

        private int thisIndex;

        public ArrayDequeIterator(){
            thisIndex=0;
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
