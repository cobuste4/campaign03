package edu.isu.cs.cs3308.traversals;

import edu.isu.cs.cs3308.structures.Node;
import edu.isu.cs.cs3308.structures.Tree;
import edu.isu.cs.cs3308.structures.impl.LinkedBinaryTree.BinaryTreeNode;
import java.util.List;

/**
 * A post-order traversal implementations of the Depth First Traversal
 *
 * @author Steve Coburn
 * @param <E> Type of data the traversal uses
 */
public class PostOrderTraversal<E> extends DepthFirstTraversal<E> {

    public PostOrderTraversal(Tree<E> tree) {
        super(tree);
    }

    @SuppressWarnings("Duplicates")
    public void subtree(Node<E> node, List<Node<E>> list) {
        if (list == null || node == null) throw new IllegalArgumentException();
        BinaryTreeNode<E> temp = (BinaryTreeNode<E>) node;
        if (temp.getLeft() != null) subtree(temp.getLeft(), list);
        if (temp.getRight() != null) subtree(temp.getRight(), list);
        list.add(node);
        if (cmd != null) cmd.execute(tree, node);
    }
}
