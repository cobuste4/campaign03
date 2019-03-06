package edu.isu.cs.cs3308;

import java.io.*;
import java.util.*;
import java.io.FileReader;

import edu.isu.cs.cs3308.structures.impl.*;
import edu.isu.cs.cs3308.traversals.*;
import edu.isu.cs.cs3308.traversals.commands.*;
import edu.isu.cs.cs3308.structures.Node;
import edu.isu.cs.cs3308.structures.impl.LinkedBinaryTree.BinaryTreeNode;

/**
 * A very simple classification tree example using a BinaryTree and console
 * input.
 *
 * @author Isaac Griffith
 */
public class ClassificationTree {

    private LinkedBinaryTree<Datum> tree;

    /**
     * Constructs a new Animal tree class which manages an underlying animal
     * tree
     */
    public ClassificationTree() {
        tree = new LinkedBinaryTree<>();
        load();
    }

    /**
     * Main method which controls the identification and tree management loop.
     */
    public void identify() {
        Scanner scan = new Scanner(System.in);
        BinaryTreeNode<Datum> currentNode = (BinaryTreeNode<Datum>) tree.root();
        Queue<String> characteristics = new LinkedList<>();
        String userInput = "";

        boolean running = true;
        do {
            System.out.printf("Is this animal %s? (Y/N) > ",
                    currentNode.getElement().getPrompt());
            userInput = scan.next();

            if (userInput.toUpperCase().equals("Y")) {
                if (tree.isExternal(currentNode)) {
                    System.out.println("Good.\r\n\r\n");
                    running = false;
                } else {
                    characteristics.offer(currentNode.getElement().getPrompt());
                    currentNode = currentNode.getLeft();
                }
            } else if (userInput.toUpperCase().equals("N") && currentNode.getRight() != null) {
                characteristics.offer("not " + currentNode.getElement().getPrompt());
                currentNode = currentNode.getRight();
            } else if (currentNode.getRight() == null || currentNode.getLeft() == null) {

                String tempStr = "";
                int sz = characteristics.size() - 1;
                for (int i = 0; i < sz; i++) {
                    tempStr = tempStr.concat(characteristics.poll() + ", ");
                }
                tempStr = tempStr.concat(characteristics.poll() + "");

                System.out.printf("I don’t know any %s animals that aren’t %s.\r\n",
                        tempStr, currentNode.getElement().getPrompt());
                System.out.printf("What is the new animal? > ");
                userInput = "a " + scan.next();
                Datum animalToAdd = new Datum(userInput);
                System.out.printf("What characteristic does %s have that %s does not? > ",
                        userInput, currentNode.getElement().getPrompt());
                userInput = scan.next();
                Datum charToAdd = new Datum(userInput);

                Datum current = new Datum(currentNode.getElement().getPrompt());
                currentNode.setElement(charToAdd);
                currentNode.setRight(new BinaryTreeNode<>(current, currentNode, null, null));
                currentNode.setLeft(new BinaryTreeNode<>(animalToAdd, currentNode, null, null));
                System.out.println("\r\n");
                running = false;
            }
        } while (running);
    }

    /**
     * Saves a tree to a file.
     */
    public void save() {
        TreeTraversal inOrder = new InOrderTraversal<>(tree);
        inOrder.setCommand(new EnumerationCommand());
        inOrder.traverse();


        TreeTraversal t = new BreadthFirstTraversal<>(tree);
        PrintWriter pw = null;
        Scanner scan = new Scanner(System.in);
        System.out.printf("Enter a filename to save the tree to --> ");
        String input = scan.next();

        try {
            File file = new File(input);
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            t.setCommand(new EnumeratedSaveCommand(pw));
            t.traverse();
            pw.close();
        } catch (IllegalArgumentException | IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Loads a tree from the given file, if an exception occurs during file
     * operations, a hardcoded basic tree will be loaded instead.
     */
    public void load() {
        Scanner scan = new Scanner(System.in);
        System.out.printf("Provide a filename for loading --> ");
        String input = scan.next();

        Queue<Integer> datumQueue = new LinkedList<>();
        Queue<String> nodesFromFile = new LinkedList<>();
        Queue<Node> nodeQueue = new LinkedList<>();
        BufferedReader br = null;
        String lineFromFile = "";
        String[] splitString;

        try {
            br = new BufferedReader(new FileReader(input));
            while ((lineFromFile = br.readLine()) != null) nodesFromFile.offer(lineFromFile);

            for (String node : nodesFromFile) {
                splitString = node.split(":");
                int parentNum = Integer.parseInt(splitString[0]);
                int datumNum = Integer.parseInt(splitString[1]);
                char sideOfParent = splitString[2].charAt(0);
                String datumPrompt = splitString[3];
                while (!datumQueue.isEmpty() && parentNum != datumQueue.element()) {
                    datumQueue.poll();
                    nodeQueue.poll();
                }
                if (datumQueue.isEmpty() || datumQueue.element() != datumNum) datumQueue.offer(datumNum);

                if (parentNum == -1) nodeQueue.offer(tree.setRoot(new Datum(datumPrompt, datumNum)));

                if (parentNum != -1 && sideOfParent == 'l') nodeQueue.offer(tree.addLeft(nodeQueue.element(), new Datum(datumPrompt, datumNum)));

                if (parentNum != -1 && sideOfParent == 'r') nodeQueue.offer(tree.addRight(nodeQueue.element(), new Datum(datumPrompt, datumNum)));
            }
            System.out.println("Tree loaded successfully.\r\n");

        } catch (IllegalArgumentException | IOException ex) {
            tree.setRoot(new Datum("furry"));
            tree.insert(new Datum("a dog"), tree.root());
            tree.insert(new Datum("a snake"), tree.root());
            System.out.println("An error occurred. Defaulting to base tree.\r\n");
        }
    }
}
