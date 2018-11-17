package site.lhearen.ajava.algorithm.linkedlist;

import site.lhearen.ajava.algorithm.base.Node;

public class AddBeforeLast {

    Node first;
    int size = 0;


    public void addBeforeLast(int a) {
        if (first == null || first.next == null) { // we have to update first;
            first = new Node(a, first);
            return;
        }
        Node p = first;
        while (p.next.next != null) p = p.next; // p.next will be the last node now;
        p.next = new Node(a, p.next);
    }

}
