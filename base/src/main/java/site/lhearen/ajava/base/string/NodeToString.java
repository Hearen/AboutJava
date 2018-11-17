package site.lhearen.ajava.base.string;

public class NodeToString {
    static class Node {
        int val;
        Node next;
        public Node(int theVal, Node theNext) {
            this.val = theVal;
            this.next = theNext;
        }

        @Override
        public String toString() {
            return String.format("{value: %d, hasNext: %s}", this.val, this.next != null);
        }
    }

    public static void main(String... args) {
        Node theNode = new Node(2, null);
        Node head = new Node(1, theNode);
        System.out.println(head);
        Node p = head;
        while (p != null) {
            System.out.println(p.val);
            p = p.next;
        }
    }
}
