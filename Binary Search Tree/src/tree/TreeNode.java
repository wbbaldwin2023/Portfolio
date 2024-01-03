package tree;
@SuppressWarnings("unchecked")
// Name: William Baldwin
// Computing ID: vvp4cx@virginia.edu
// Homework Name: Binary Search Tree
// Resources used: None
/**
 * Simple BST Node. Contains the data in this node, and pointers to left and right children
 * @author CS2100 Team
 *
 * @param <T>
 */
public class TreeNode<T>{
    // Every TreeNode has a left and right reference (pointer), and a data item (currently null)
    protected TreeNode<T> left = null;
    protected TreeNode<T> right = null;
    protected int height = 0;
    protected T data = null;

    public TreeNode(T data) { // Constructor
        this.data = data;
    }

    public TreeNode(T data, int height) { // Another Constructor
        this(data);
        this.height = height;
    }

}
