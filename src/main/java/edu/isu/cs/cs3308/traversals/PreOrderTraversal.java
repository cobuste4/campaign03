package edu.isu.cs.cs3308.traversals;

import edu.isu.cs.cs3308.structures.Node;
import edu.isu.cs.cs3308.structures.Tree;
import edu.isu.cs.cs3308.structures.impl.LinkedBinaryTree;
import java.util.List;

/**
 * A pre-order traversal implementations of the Depth First Traversal
 *
 * @author Steve Coburn
 * @param <E> Type of data the traversal uses
 */
public class PreOrderTraversal<E> extends DepthFirstTraversal<E> {

    public PreOrderTraversal(Tree<E> tree) {
        super(tree);
    }

    public void subtree(Node<E> node, List<Node<E>> list) {
        if (list == null || node == null) throw new IllegalArgumentException();
        LinkedBinaryTree.BinaryTreeNode<E> temp = (LinkedBinaryTree.BinaryTreeNode<E>) node;
        list.add(node);
        if (cmd != null) cmd.execute(tree, node);
        if (temp.getLeft() != null) subtree(temp.getLeft(), list);
        if (temp.getRight() != null) subtree(temp.getRight(), list);
    }
}
