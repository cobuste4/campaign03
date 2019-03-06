package edu.isu.cs.cs3308.structures.impl;

import java.util.LinkedList;
import edu.isu.cs.cs3308.structures.BinaryTree;
import edu.isu.cs.cs3308.structures.Node;
import edu.isu.cs.cs3308.structures.Tree;

/**
 /**
 * A Binary Search Tree implementation of the Tree and BinaryTree classes
 *
 * @author Steve Coburn
 * @param <E> Type of data the traversal uses
 */
public class LinkedBinaryTree<E> implements BinaryTree<E>, Tree<E> {

    public BinaryTreeNode<E> root = null;
    private int size = 0;

    @Override
    public Node<E> left(Node<E> p) throws IllegalArgumentException {
        BinaryTreeNode<E> validated = (BinaryTreeNode<E>) validate(p);
        return validated.getLeft();
    }

    @Override
    public Node<E> right(Node<E> p) throws IllegalArgumentException {
        BinaryTreeNode<E> validated = (BinaryTreeNode<E>) validate(p);
        return validated.getRight();
    }

    @Override
    public Node<E> sibling(Node<E> p) throws IllegalArgumentException {
        BinaryTreeNode<E> validated = (BinaryTreeNode<E>) validate(p);
        BinaryTreeNode<E> parent = (BinaryTreeNode<E>) validated.getParent();
        if (parent.getRight() == validated) return parent.getLeft();
        else if (parent.getLeft() == validated) return parent.getRight();
        return null;
    }

    @Override
    public Node<E> addLeft(Node<E> p, E element) throws IllegalArgumentException {
        BinaryTreeNode<E> validated = (BinaryTreeNode<E>) validate(p);
        BinaryTreeNode<E> nodeToAdd = new BinaryTreeNode<>(element, validated, null, null);

        if (validated.getLeft() != null) throw new IllegalArgumentException("Left node not empty");
        validated.setLeft(nodeToAdd);
        size++;
        return nodeToAdd;
    }

    @Override
    public Node<E> addRight(Node<E> p, E element) throws IllegalArgumentException {
        BinaryTreeNode<E> validated = (BinaryTreeNode<E>) validate(p);
        BinaryTreeNode<E> nodeToAdd = new BinaryTreeNode<>(element, validated, null, null);

        if (validated.getRight() != null) throw new IllegalArgumentException("Right node not empty");
        validated.setRight(nodeToAdd);
        size++;
        return nodeToAdd;
    }

    @Override
    public Node<E> root() {
        if (isEmpty()) return null;
        return root;
    }

    @Override
    public Node<E> setRoot(E item) {
        if (item == null) {
            size = 0;
            return null;
        }
        if (root != null && root.getElement() == item) return root;

        BinaryTreeNode<E> nodeToAdd = new BinaryTreeNode<>(item, null, null, null);
        root = nodeToAdd;
        size = 1;
        return nodeToAdd;
    }

    @Override
    public Node<E> parent(Node<E> p) throws IllegalArgumentException {
        return (validate(p)).getParent();
    }

    @Override
    public Iterable<Node<E>> children(Node<E> p) throws IllegalArgumentException {
        BinaryTreeNode<E> validated = (BinaryTreeNode<E>) validate(p);
        LinkedList<Node<E>> children = new LinkedList<>();
        if (validated.getLeft() != null) children.add(validated.getLeft());
        if (validated.getRight() != null) children.add(validated.getRight());
        return children;
    }

    @Override
    public int numChildren(Node<E> p) throws IllegalArgumentException {
        LinkedList list = (LinkedList) children(p);
        return list.size();
    }

    @Override
    public boolean isInternal(Node<E> p) throws IllegalArgumentException {
        return numChildren(validate(p)) > 0;
    }

    @Override
    public boolean isExternal(Node<E> p) throws IllegalArgumentException {
        return numChildren(validate(p)) == 0;
    }

    @Override
    public boolean isRoot(Node<E> p) throws IllegalArgumentException {
        return root.getElement() == validate(p).getElement();
    }

    @Override
    public Node<E> insert(E item, Node<E> p) throws IllegalArgumentException {
        if (item == null) throw new IllegalArgumentException("Item is null");
        BinaryTreeNode<E> validated = (BinaryTreeNode<E>) validate(p);
        if (validated.getLeft() == null) return addLeft(p, item);
        else if (validated.getRight() == null) return addRight(p, item);
        throw new IllegalArgumentException("Cannot add; left and right are not empty");
    }

    @Override
    public boolean remove(E item, Node<E> p) throws IllegalArgumentException {
        if (p == null || item == null) return false;
        BinaryTreeNode<E> validated = (BinaryTreeNode<E>) validate(p);
        if (validated.getLeft().getElement() == item) {
            size -= subTreeSize(validated) + 1;
            validated.getLeft().setParent(null);
            validated.setLeft(null);
            return true;
        } else if (validated.getRight().getElement() == item) {
            size -= subTreeSize(validated) + 1;
            validated.getRight().setParent(null);
            validated.setRight(null);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    // Returns element which was successfully updated (the new element)
    @Override
    public E set(Node<E> node, E element) throws IllegalArgumentException {
        if (element == null) throw new IllegalArgumentException("Element is null");
        validate(node).setElement(element);
        return element;
    }

    @Override
    public Node<E> validate(Node<E> p) throws IllegalArgumentException {
        if (p == null) throw new IllegalArgumentException("Passed in node is null");
        if (!(p instanceof BinaryTreeNode)) throw new IllegalArgumentException("Not a valid BinaryTreeNode");
        BinaryTreeNode<E> node = (BinaryTreeNode<E>) p;
        while (p.getParent() != null) {
            p = p.getParent();
        }
        if (p != root) throw new IllegalArgumentException("Node is not in the tree");
        return node;
    }

    // Code from page 315 of the book
    @Override
    public int depth(Node<E> node) throws IllegalArgumentException {
        validate(node);
        if (isRoot(node)) return 0;
        else return 1 + depth(parent(node));
    }

    @Override
    public int subTreeSize(Node<E> node) throws IllegalArgumentException {
        BinaryTreeNode<E> validated = (BinaryTreeNode<E>) validate(node);
        int count = 0;
        if (validated.getLeft() != null) count += subTreeSize(validated.getLeft());
        if (validated.getRight() != null) count += subTreeSize(validated.getRight());
        return count;
    }

    @Override
    public boolean isLastChild(Node<E> node) throws IllegalArgumentException {
        validate(node);
        return isRoot(node) || sibling(node) == null;
    }

    /**
     * Constructs a new BinaryTreeNode holding the specified data and linked to
     * the provided parent, left child and right child.
     *
     * @param element The data element to hold
     * @param parent  The parent of this node (can be null)
     * @param left    The left child of this node (can be null)
     * @param right   The right child of this node (can be null)
     * @return The newly constructed BinaryTreeNode.
     */
    protected BinaryTreeNode<E> createNode(E element, BinaryTreeNode<E> parent, BinaryTreeNode<E> left, BinaryTreeNode<E> right) {
        if (element == null) {
            throw new IllegalArgumentException();
        }

        return new BinaryTreeNode<>(element, parent, left, right);
    }


    /**
     * Class that extends the nodeImpl class to be specific to Binary Trees
     *
     * @param <E> The data type to be used
     */
    public static class BinaryTreeNode<E> extends NodeImpl<E> {
        private BinaryTreeNode<E> right = null;
        private BinaryTreeNode<E> left = null;

        public BinaryTreeNode(E data, Node<E> parent, BinaryTreeNode<E> leftChild, BinaryTreeNode<E> rightChild) {
            super(data);
            this.setParent(parent);
            this.setLeft(leftChild);
            this.setRight(rightChild);
        }

        public BinaryTreeNode<E> getRight() {
            return right;
        }

        public void setRight(BinaryTreeNode<E> right) {
            this.right = right;
        }

        public BinaryTreeNode<E> getLeft() {
            return left;
        }

        public void setLeft(BinaryTreeNode<E> left) {
            this.left = left;
        }
    }
}
