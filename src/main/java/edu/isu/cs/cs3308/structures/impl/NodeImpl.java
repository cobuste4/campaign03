package edu.isu.cs.cs3308.structures.impl;

import edu.isu.cs.cs3308.structures.Node;

/**
 * An implementation of the Node interface suitable for trees and graphs
 *
 * @author Steve Coburn
 * @param <E> Type of data the node uses
 */
public class NodeImpl<E> implements Node<E> {

    private Node<E> parent = null;
    private E element;

    public NodeImpl(E element) {
        this.element = element;
    }

    @Override
    public E getElement() {
        return this.element;
    }

    @Override
    public void setElement(E element) throws IllegalArgumentException {
        if (element == null) throw new IllegalArgumentException("Element is null");
        this.element = element;
    }

    @Override
    public Node<E> getParent() {
        return parent;
    }

    public void setParent(Node<E> parent) {
        this.parent = parent;
    }
}
