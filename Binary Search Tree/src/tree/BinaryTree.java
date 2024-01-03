package tree;
@SuppressWarnings("unchecked")
// Name: William Baldwin
// Computing ID: vvp4cx@virginia.edu
// Homework Name: Binary Search Tree
// Resources used: None

public class BinaryTree<T> {

    protected TreeNode<T> root = null;

    /* Tree Traversal methods */

    public String getInOrder() {
        return getInOrder(root); // call getInOrder starting at the root!
    }
    private String getInOrder(TreeNode<T> curNode) {
        //TODO: return the in order traversal of this tree, space separated
        if (curNode == null) return "";
        String currNode = curNode.data.toString() + " ";
        String leftSub = getInOrder(curNode.left);
        String rightSub = getInOrder(curNode.right);
        String result = leftSub + currNode + rightSub;
        return result;
    }


    public String getPreOrder() {
        return getPreOrder(root); // call getPreOrder starting at the root!
    }
    private String getPreOrder(TreeNode<T> curNode) {
        if (curNode == null) return "";
        String currNode = curNode.data.toString() + " ";
        String leftSub = getPreOrder(curNode.left);
        String rightSub = getPreOrder(curNode.right);
        String result = currNode + leftSub + rightSub;
        return result;
    }


    public String getPostOrder() {
        return getPostOrder(root);  // call getPostOrder starting at the root!
    }
    private String getPostOrder(TreeNode<T> curNode) {
        if (curNode == null) return "";
        String currNode = curNode.data.toString() + " ";
        String leftSub = getPostOrder(curNode.left);
        String rightSub = getPostOrder(curNode.right);
        String result = leftSub + rightSub + currNode;
        return result;
    }


    //------------------------------------------------------------------------
    //EVERYTHING BELOW THIS POINT IS IMPLEMENTED FOR YOU
    //YOU SHOULD STILL LOOK AT THIS CODE
    //------------------------------------------------------------------------

    /* A somewhat more pretty print method for debugging */
    public void printTree() {
        printTree(this.root, 0);
        System.out.println("\n\n");
    }
    private void printTree(TreeNode<T> curNode, int indentLev) {
        if(curNode == null) return;
        for(int i=0; i<indentLev; i++) {
            if(i == indentLev - 1) System.out.print("|-----");
            else System.out.print("      ");
        }
        System.out.println(curNode.data);
        printTree(curNode.left, indentLev+1);
        printTree(curNode.right, indentLev+1);
    }

    public int height() {
        return height(root);
    }

    /* Computes the height of the tree on the fly */
    protected int height(TreeNode<T> node) {
        if(node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BinarySearchTree<?> tree = (BinarySearchTree<?>) o;
        return equals(root, tree.root);
    }

    private boolean equals(TreeNode<?> node0, TreeNode<?> node1) {
        if (node0 == null && node1 == null) {
            return true;
        }
        if (node0 == null || node1 == null) {
            return false;
        }
        if (!node0.data.equals(node1.data)) {
            return false;
        }
        return equals(node0.right, node1.right) && equals(node0.left, node1.left);
    }
}
