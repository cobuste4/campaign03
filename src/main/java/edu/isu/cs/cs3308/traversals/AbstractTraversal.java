package edu.isu.cs.cs3308.traversals;

import edu.isu.cs.cs3308.traversals.commands.TraversalCommand;
import edu.isu.cs.cs3308.structures.Node;


/**
 * Abstract class for all traversals
 *
 * @author Steve Coburn
 * @param <E> Type of data the traversal uses
 */
public abstract class AbstractTraversal<E> implements TreeTraversal<E> {

    @Override
    public abstract Iterable<Node<E>> traverse();

    @Override
    public abstract Iterable<Node<E>> traverseFrom(Node<E> node);

    @Override
    public abstract void setCommand(TraversalCommand cmd);
}
