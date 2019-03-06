package edu.isu.cs.cs3308.traversals;

import edu.isu.cs.cs3308.structures.Node;
import edu.isu.cs.cs3308.structures.Tree;
import edu.isu.cs.cs3308.traversals.commands.TraversalCommand;
import edu.isu.cs.cs3308.structures.impl.LinkedBinaryTree.BinaryTreeNode;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A breadth-first traversal implementation
 *
 * @author Steve Coburn
 * @param <E> Type of data the traversal uses
 */
public class BreadthFirstTraversal<E> extends AbstractTraversal<E> {

    private TraversalCommand cmd = null;
    private Tree<E> tree;

    public BreadthFirstTraversal(Tree<E> tree) {
        if (tree == null) throw new IllegalArgumentException();
        this.tree = tree;
    }

    @Override
    public Iterable<Node<E>> traverse() {
        if (tree.root() == null) {
            return null;
        }
        else {
            return traverseFrom(tree.root());
        }
    }

    @Override
    public Iterable<Node<E>> traverseFrom(Node<E> node) {
        tree.validate(node);
        Queue<Node<E>> Q = new LinkedList<>();
        Q.offer(node);
        LinkedList<Node<E>> L = new LinkedList<>();
        BinaryTreeNode<E> binNode = (BinaryTreeNode<E>) node;
        while (!Q.isEmpty()) {
            binNode = (BinaryTreeNode<E>) Q.poll();
            if (cmd != null) cmd.execute(tree, binNode);
            L.add(binNode);
            if (binNode.getLeft() != null) Q.offer(binNode.getLeft());
            if (binNode.getRight() != null) Q.offer(binNode.getRight());
        }
        return L;
    }

    @Override
    public void setCommand(TraversalCommand cmd) {
        this.cmd = cmd;
    }
}
