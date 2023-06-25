package deque;

public class ArrayDeque<T> {
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
        for (int i = 0; i < nextFirst; i++){
            a[i] = items[firstElement+i];
        }
        int counter = 1;
        for (int j = lastElement; j > nextLast; j--){
            a[a.length - counter] = items[j];
            counter++;
        }
        nextLast = a.length - counter;
        firstElement = 0;
        lastElement = a.length - 1;
        items = a;
    }

    public void addFirst(T item){
        if (size == items.length){
            resize(size * RFACTOR);
        }
        items[nextFirst] = item;
        size++;
        nextFirst++;
    }

    public void addLast(T item){
        if (size == items.length){
            resize(size * RFACTOR);
        }
        items[nextLast] = item;
        size++;
        nextLast--;
    }

    public boolean isEmpty(){
        if (size==0) return true;
        return false;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        for (int i = nextFirst - 1; i >= firstElement; i--){
            System.out.println(items[i] + " ");
        }
        for (int i = nextLast + 1; i <= lastElement; i++){
            System.out.println(items[i] + " ");
        }
        System.out.println();
    }

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

    public T get(int index){
        if (index < nextFirst - firstElement){
            return items[nextFirst - index - 1 ];
        }
        return items[lastElement - (index - (nextFirst - firstElement))];
    }

}
