package site.lhearen.ajava.algorithm.linkedlist;

import site.lhearen.ajava.algorithm.base.Node;

public class ReplaceValue {
    public void replace(Node first, String search, String replace) {
        Node p = first;
        while (p != null) { // traverse from the very beginning till the last;
            if (p.val.equals(search)) { // check equality and replace;
                p.val = replace;
            }
            p = p.next;
        }
    }
}
