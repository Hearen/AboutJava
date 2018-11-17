package site.lhearen.ajava.algorithm.tree;

public class BinarySearchTree {
    private BTNode root;

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();

        tree.add(70);
        tree.add(20);
        tree.add(10);
        tree.add(27);
        tree.add(150);
        tree.add(115);
        tree.add(300);
        System.out.println(tree.toString());
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public void add(int value) {
        root = add(root, value);
    }

    private BTNode add(BTNode node, int value) {
        if (node == null) {
            return new BTNode(value, null, null);
        }
        if (node.item > value)
            node.left = add(node.left, value);
        else if (node.item < value)
            node.right = add(node.right, value);
        return node;
    }

    static class BTNode {
        int item;
        BTNode left;
        BTNode right;

        BTNode(int i, BTNode l, BTNode r) {
            item = i;
            left = l;
            right = r;
        }

        @Override
        public String toString() { // return the pre-order traversal;
            StringBuilder stringBuilder = new StringBuilder();
            if (left != null) stringBuilder.append(left.toString());
            stringBuilder.append("[" + item + "]");
            if (right != null) stringBuilder.append(right.toString());
            return stringBuilder.toString();
        }

    }

}
