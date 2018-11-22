/**
 * Encoder.java
 * Code used to encode a text file by creating a binary tree and outputting the result to a text file
 * Author: Kevin Jain
 * Date: April 18th 2018
 */

// Imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.io.PrintWriter;

/**
 *  Encoder
 *  Class to encode files
 */
public class Encoder {

	private static MyLinkedList<Character> inputText = new MyLinkedList<Character>(); // LinkedList to represent the input text
	private static int[] charFreq = new int[256]; // Index -> character, value -> frequency
	private static HashMap<Integer, String> key = new HashMap<Integer, String>(); // HashMap to encode output by matching string to character
	private static String fileName = "original.txt"; // File name
	private static BinaryTree<Integer> finalTree; // Final tree
	
	// Main method
	public static void main(String[] args) throws Exception{
		
		FileInputStream inputStream = null; // Creates an input stream
		
		try { // Try catch t read file
			
			inputStream = new FileInputStream(fileName);
			int c;
			
			while ((c = inputStream.read()) != -1) { // Iterates through each character
				
				inputText.add((char)c); // Adds to linkedlist
				charFreq[c]++; // Increases the character frequency array
				
			}
			
			inputStream.close(); // Close input
			
		} catch (IOException e) { // Exception
			
			System.out.println("File could not be found.");
			
		}
		
		PriorityQueue<BinaryTreeNode<Integer>> nodes = new PriorityQueue<BinaryTreeNode<Integer>>(); // PriorityQueue to represent additions to binary tree
		PriorityQueue<BinaryTree<Integer>> queueRight = new PriorityQueue<BinaryTree<Integer>>(); // Queue of binary trees to be used in creation of main tree
		
		for (int i = 0; i < charFreq.length; i ++) { // Iterates through character array
			
			if (charFreq[i] > 0) { // Checks if characters appear
				
				nodes.insert(new BinaryTreeNode<Integer>(i), charFreq[i]); // Adds characters that appear to a priority queue based on frequency
				
			}
			
		}
		
		while (nodes.size() > 1) { // Checks if there are items in the queue to be processed
			
			PriorityNode<BinaryTreeNode<Integer>> node1 = nodes.dequeueReverse(); // Dequeues lowest frequency item in priority queue
			PriorityNode<BinaryTreeNode<Integer>> node2 = nodes.dequeueReverse(); // Dequeues next lowest frequency item
			BinaryTree<Integer> subTree = new BinaryTree<Integer>(); // Creates a temporary subtree
			subTree.makeTree(node2.getItem(), node1.getItem()); // Creates a subtree
			queueRight.insert(subTree, node1.getPriority() + node2.getPriority()); // Inserts the temporary tree to the binary tree queue
			
		}
		
		if (nodes.size() > 0) { // If there is one node left
			
			PriorityNode<BinaryTreeNode<Integer>> node1 = nodes.dequeueReverse();
			BinaryTree<Integer> subTree = new BinaryTree<Integer>();
			subTree.makeTree(node1.getItem());
			queueRight.insert(subTree, node1.getPriority());
			
		}
		
		while (queueRight.size() > 1) { // Goes through queue of subtrees
			
			PriorityNode<BinaryTree<Integer>> subTree1 = queueRight.dequeueReverse(); // Dequeues lowest priority item
			PriorityNode<BinaryTree<Integer>> subTree2 = queueRight.dequeueReverse(); // Dequeues next lowest priority item
			BinaryTree<Integer> tempTree = new BinaryTree<Integer>(); // Creates temporary tree
			tempTree.makeTree(subTree2.getItem().getRoot(), subTree1.getItem().getRoot()); // Creates temp tree by merging two dequeued trees
			queueRight.insert(tempTree,  subTree1.getPriority() + subTree2.getPriority()); // Inserts tree back into queue
			
		}
		
		finalTree = new BinaryTree<Integer>(); // Initializes final tree
		finalTree.makeTree(queueRight.dequeue().getItem().getRoot()); // Makes the tree by loading the last item in the tree queue
		
		setEncoding(finalTree.getRoot(), ""); // Encodes the tree with a hashmap
		
		print(); // Prints to file
		
	}
	
	/**
	    * print
	    * Prints relevant output to text file
	    * @param none
	    * @return void
	    */ 
	private static void print() throws Exception{
		
		// Variable declaration
		File file = new File("compressed.txt");
		String temp = "";
		String output = "";
		
		for (int i = 0; i < inputText.size(); i ++) { // Goes through the input text
			
			temp += key.get((int)(inputText.get(i).charValue())); // Adds to temp string based on encoding
			
			if (temp.length() > 8) { // Once temp string is more than 8 characters, converts it into a character
				
				output += (char)(Integer.parseInt(temp.substring(0, 8), 2)); // Converts the temp string into a char
				temp = temp.substring(9); // Removes first 8 characters
				
			}
			
		}
		
		int extraBits = temp.length(); // Gets number of extra bits at the end
		
		// Prints output according to specifications
		PrintWriter writer = new PrintWriter(file);
		writer.println(fileName.substring(0,fileName.indexOf(".")) + "." + fileName.substring(fileName.indexOf(".") + 1).toUpperCase());
		writer.println(finalTree.makeTreeString(finalTree.getRoot()));
		writer.println(extraBits);
		writer.println(output);
		writer.close();
		
	}
	
	/**
	    * setEncoding
	    * Recursive method to determine the unique binary encoding for each different character through binary tree traversal
	    * @param BinaryTreeNode<Integer> that represents the node to be encoded
	    * @param String encoding representing the binary path to traverse the tree from the root
	    * @return void
	    */ 
	private static void setEncoding(BinaryTreeNode<Integer> node, String encoding) {
		
		if (node.getLeft() != null) { // Checks left node
			
			setEncoding(node.getLeft(), "0" + encoding); // Recursively calls encoding on left node, adds 0 for directions
			
		}
		
		if (node.getRight() != null) { // Checks right node
			
			setEncoding(node.getRight(), "1" + encoding); // Recursively calls on right node adding 1 for direction
			
		}
		
		if (node.getLeft() == node.getRight() && node.getRight() == null) { // If the node is a leaf, ends call by adding encoding string to hashmap
			
			key.put(node.getItem(), encoding);
			
		}
		
	}
	
}