package site.lhearen.ajava.algorithm.tree;

import java.util.ArrayList;
import java.util.List;

public class PathFind {
    public static void main(String... args) {
        Node dNode = new Node("D", null, null);
        Node bNode = new Node("B", dNode, null);

        Node eNode = new Node("E");
        Node fNode = new Node("F");
        Node cNode = new Node("C", eNode, fNode);

        Node aNode = new Node("A", bNode, cNode);

        System.out.println(aNode.find(null));
        System.out.println(aNode.find(bNode));
        System.out.println(aNode.find(cNode));
        System.out.println(aNode.find(eNode));
        System.out.println(bNode.find(dNode));

        System.out.println(bNode.find(eNode));
        System.out.println(cNode.find(dNode));
    }


    static class Node {
        Node left, right;
        String val;

        public Node(String theVal, Node theLeft, Node theRight) {
            this.val = theVal;
            this.left = theLeft;
            this.right = theRight;
        }

        public Node(String theVal) {
            this(theVal, null, null);
        }

        @Override
        public String toString() {
            return val;
        }

        /**
         * 1. you can start from any node to any node in the tree;
         * 2. the pre-order first path will be returned if the target node is null;
         * 3. if there is no such path, the path will be null to indicate there is NO such path;
         * @param theNode the target node to search for the path;
         * @return
         */
        public List<Node> find(Node theNode) {
            List<Node> path = new ArrayList<>();
            if (find(this, theNode, path)) return path;
            else return null;
        }



        /**
         * search a path from the start node to the target node (theNode);
         * @param startNode start from the startNode to search;
         * @param theNode the node to search;
         * @param path using a list to record the path along the way;
         * @return to indicate whether there is a path or not;
         */
        private boolean find(Node startNode, Node theNode, List<Node> path) {
            path.add(startNode);
            if (startNode == theNode) return true;
            if (startNode == null) return false;
            if (find(startNode.left, theNode, path)) return true;
            else path.remove(path.size() - 1); // remove the last for the right search;
            if (find(startNode.right, theNode, path)) return true;
            else path.remove(path.size() - 1);
            return false;
        }
    }

}
