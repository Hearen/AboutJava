package site.lhearen.ajava.algorithm.base;

public class Node<T> {

    public T val;
    public Node next;

    public Node(T theVal, Node theNext) {
        this.val = theVal;
        this.next = theNext;
    }

    public Node(T theVal) {
        this(theVal, null);
    }
}
