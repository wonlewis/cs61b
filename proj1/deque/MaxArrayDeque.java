package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int firstElement;
    private int lastElement;
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c){
        items = (T []) new Object[8];
        size = 0;
        nextFirst = 0;
        firstElement = 0;
        nextLast = items.length-1;
        lastElement = items.length-1;
        comparator = c;
    }

    public T max(){
        return getT(comparator);
    }

    public T max(Comparator<T> c){
        return getT(c);
    }

    private T getT(Comparator<T> c) {
        T maxT = null;
        if (super.size() == 0) return null;
        for (int i=0; i < super.size() - 1; i++){
            if (c.compare(super.get(i), super.get(i+1)) == 0 || c.compare(super.get(i), super.get(i+1)) == 1) {
                if (maxT==null || c.compare(super.get(i), maxT) == 1) maxT = super.get(i);
            }
            if (c.compare(super.get(i), super.get(i+1)) == -1){
                if (maxT==null || c.compare(super.get(i+1), maxT) == 1) maxT = super.get(i);
            }
        }
        return maxT;
    }


}
