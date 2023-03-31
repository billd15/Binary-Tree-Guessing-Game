import java.io.*;
import java.util.Scanner;

public class GuessingGame {

    private static String answer = "";
    private static BinaryTree<String> tree = new BinaryTree<String>();
    private static Scanner in = new Scanner(System.in);

    public static void main(String args[]) {
        //Create filename to save and load to
        String filename = "tree.ser";
        tree = createDefaultTree();

        //Load custom tree at start unless asked to load default tree
        try {
            tree = loadBinaryTree(filename);
        } catch (IOException | ClassNotFoundException e) {
            tree = createDefaultTree();
        }

        while (true) {
            System.out.println("Think of a random capital city or food, I will attempt to guess it");

            //Getting the first Node of the tree
            BinaryNodeInterface<String> currentNode = tree.getRootNode();

            //After every answer this checks if the current Node is not a leaf
            while (!currentNode.isLeaf()) {
                System.out.println(currentNode.getData());
                answer = in.nextLine();

                //Left if correct, right if incorrect
                if (answer.toLowerCase().startsWith("y")) {
                    currentNode = currentNode.getLeftChild();
                } else {
                    currentNode = currentNode.getRightChild();
                }
            }

            //Guess based on current Node
            System.out.println("Is it (a) " + currentNode.getData() + "?");
            answer = in.nextLine();

            //When the answer is y on a leaf Node
            if (answer.toLowerCase().startsWith("y")) {
                System.out.println("\nI guessed it! What you would like to do now? Choose a number...");
                System.out.println("1. Load the default tree");
                System.out.println("2. Load a custom tree");
                System.out.println("3. Save the current tree");
                System.out.println("4. Quit");
            }

            //When the answer is n on a leaf Node
            else    {
                //Ask for the answer and save it
                System.out.println("I don’t know: what is the correct answer?");
                String newItem = in.nextLine();
                String oldItem = currentNode.getData();

                //Now ask for a question based on the answer
                System.out.println("Enter a question for which the answer is Yes for " + newItem + " and No for " + oldItem);
                String newQuestion = in.nextLine();

                //Ensure that the question will have a question mark at the end
                newQuestion = newQuestion.trim();
                if (!newQuestion.endsWith("?")) newQuestion += "?";

                //Save the updated node structure
                currentNode.setData(newQuestion);
                currentNode.setLeftChild(new BinaryNode<String>(newItem));
                currentNode.setRightChild(new BinaryNode<String>(oldItem));

                //Save to the file
                try {
                    saveBinaryTree(tree, filename);
                } catch (IOException e) {
                    System.out.println("Error saving binary tree: " + e.getMessage());
                }

            }

            String action = in.nextLine();

            if (action.equals("4")) break;

            //Options for user after correct guess
            switch (action) {
                case "1":
                    tree = createDefaultTree();
                    break;
                case "2":
                    try {
                        tree = loadBinaryTree(filename);
                    } catch (IOException | ClassNotFoundException e) {
                        tree = createDefaultTree();
                    }
                    break;
                case "3":
                    try {
                        saveBinaryTree(tree, filename);
                    }catch (IOException e){}
                    break;
                default:
                    tree = createDefaultTree();
                    break;
            }

            System.out.println("Would you like to play again? (y or n)");

            action = in.nextLine();
            if (!action.toLowerCase().startsWith("y")) {
                break;
            }




            }
    }


    //Creates a default tree with questions and potential answers
    public static BinaryTree<String> createDefaultTree() {
        BinaryTree<String> tree = new BinaryTree<String>();

        BinaryTree<String> h = new BinaryTree<String>("Paris");
        BinaryTree<String> i = new BinaryTree<String>("Athens");
        BinaryTree<String> j = new BinaryTree<String>("Tokyo");
        BinaryTree<String> k = new BinaryTree<String>("Washington D.C");
        BinaryTree<String> l = new BinaryTree<String>("Pizza");
        BinaryTree<String> m = new BinaryTree<String>("Burger");
        BinaryTree<String> n = new BinaryTree<String>("Caviar");
        BinaryTree<String> o = new BinaryTree<String>("Lasagna");

        BinaryTree<String> d = new BinaryTree<String>("Is it in Western Europe?", h, i);
        BinaryTree<String> e = new BinaryTree<String>("Is it in Asia?", j, k);
        BinaryTree<String> f = new BinaryTree<String>("Does it come in a square box?", l, m);
        BinaryTree<String> g = new BinaryTree<String>("Is it a delicacy?", n, o);

        BinaryTree<String> b = new BinaryTree<String>("Is it in Europe?", d, e);
        BinaryTree<String> c = new BinaryTree<String>("Is it a fast food item?", f, g);

        tree.setTree("Are you thinking of a capital city?", b, c);

        return tree;
    }



    //Saves the tree to a .ser file
    public static void saveBinaryTree(BinaryTree<String> tree, String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(tree);
        oos.close();
        System.out.println("The tree has been successfully saved\n");
    }

    //Loads the tree from a .ser file
    public static BinaryTree<String> loadBinaryTree(String filename) throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        BinaryTree<String> tree = (BinaryTree<String>) ois.readObject();
        ois.close();
        System.out.println("The tree has been successfully loaded\n");
        return tree;
    }


  }



