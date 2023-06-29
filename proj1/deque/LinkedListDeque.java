package deque;

public class LinkedListDeque<T> implements Deque<T> {

    private IntNode sentinel;

    private int size;

    public LinkedListDeque(){
        sentinel =  new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
        size = 0;
    }

    private class IntNode{
        private T thisItem;
        private IntNode next;
        private IntNode previous;

        public IntNode(T item, IntNode nextIntNode, IntNode prevIntNode){
            thisItem = item;
            next = nextIntNode;
            previous = prevIntNode;
        }
    }

    @Override
    public void addFirst(T item){

        IntNode addNode = new IntNode(item, sentinel.next, sentinel);
        if (this.size == 0){
            sentinel.previous = addNode;
        }
        sentinel.next.previous = addNode;
        sentinel.next = addNode;
        size++;
    }

    @Override
    public void addLast(T item){
        IntNode addNode = new IntNode(item, sentinel, sentinel.previous);
        if (this.size == 0){
            sentinel.next = addNode;
        }
        sentinel.previous.next = addNode;
        sentinel.previous = addNode;
        size++;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        IntNode thisNode = sentinel.next;
        while (thisNode != sentinel && thisNode != null) {
            System.out.printf(thisNode.thisItem + " ");
            thisNode = thisNode.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst(){
        if (sentinel.next != sentinel){
            IntNode removedNode = sentinel.next;
            sentinel.next = removedNode.next;
            size--;
            return removedNode.thisItem;
        }else return null;
    }

    @Override
    public T removeLast(){
        if (sentinel.previous != sentinel){
            IntNode removedNode = sentinel.previous;
            sentinel.previous = removedNode.previous;
            sentinel.next = removedNode.next;
            size--;
            return removedNode.thisItem;
        }else return null;
    }

    @Override
    public T get(int index){
        int thisIndex = index;
        IntNode thisNode = sentinel.next;
        while (thisIndex > 0 && thisNode != null){
            thisNode = thisNode.next;
            thisIndex--;
        }
        return thisNode.thisItem;
    }

}
