package edu.isu.cs.cs3308.traversals;

import edu.isu.cs.cs3308.structures.Node;
import edu.isu.cs.cs3308.structures.Tree;
import edu.isu.cs.cs3308.traversals.commands.TraversalCommand;
import java.util.LinkedList;
import java.util.List;

/**
 * An abstract class representing all Depth First Traversals
 *
 * @author Steve Coburn
 * @param <E> Type of data the traversal uses
 */
public abstract class DepthFirstTraversal<E> extends AbstractTraversal<E> {

    TraversalCommand cmd = null;
    Tree<E> tree;

    DepthFirstTraversal(Tree<E> tree) throws IllegalArgumentException {
        if (tree == null) throw new IllegalArgumentException();
        this.tree = tree;
    }

    @Override
    public Iterable<Node<E>> traverse() {
        return traverseFrom(tree.root());
    }

    @Override
    public Iterable<Node<E>> traverseFrom(Node<E> node) {
        return subTreeTraverse(node);
    }

    public Iterable<Node<E>> subTreeTraverse(Node<E> node) throws IllegalArgumentException {
        LinkedList<Node<E>> list = new LinkedList<>();

        if (!tree.isEmpty()) {
            subtree(node, list);
        }
        return list;
    }

    @Override
    public void setCommand(TraversalCommand cmd) {
        this.cmd = cmd;
    }

    public abstract void subtree(Node<E> node, List<Node<E>> list);


}
