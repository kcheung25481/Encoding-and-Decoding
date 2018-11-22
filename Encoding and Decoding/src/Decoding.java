/**
 * Decoding.java
 * Code used to decode an encoded message using a binary tree
 * Author: Kevin Cheung
 * Date: April 18th 2018
 */

//File imports
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

/**
 * Decoding
 * Class to decode a message in a file.
 */
public class Decoding {

	public static void main(String[] args) throws IOException{

		FileInputStream in = null; //Initialize input
		PrintWriter out = null; //Initialize output

		try {
			in = new FileInputStream("compressed.txt"); //Create reader for file
			String fileName = ""; //String where the file is stored

			String tree = ""; //Create string for storage of binary tree
			String bytesToSkip = ""; //Store skipped bytes
			int checkChar = 0; //Storage of the character 2 ahead 
			int character; //Storage of character
			int skipBytes; //Integer where it is to be skipped

			while ((character = in.read()) !=  13){ //Until it reaches a vertical tab
				fileName += ((char)character); //Write file to the string
			}
			fileName = fileName.toLowerCase(); //Make the string lowercase
			out = new PrintWriter(fileName); //Create writer to new file

			System.out.println(fileName);//Output file name

			character = in.read();  //Read the empty space

			while ((character = in.read()) !=  13){ //Until it reaches a vertical tab
				tree += ((char)character); //Write tree to string
			}

			System.out.println(tree); //Output encoded tree

			character = in.read();  //Read the empty space

			while ((character = in.read()) !=  13){ //Until it reaches a vertical tab
				bytesToSkip += ((char)character); //Store the bytes to be skipped
			}

			System.out.println(bytesToSkip); //Output bytes to skip

			skipBytes = Integer.parseInt(bytesToSkip); //Change to int
			character = in.read();  //Read the empty space

			/**************************************BUILD AND USE TREE**********************************************/

			Decoder decoder = new Decoder(tree); //Create a decoder
			Stack<String> stack = new Stack<>(); //Create a stack for the decoder

			//Methods to turn the encoded tree from the file into a decoded tree
			decoder.setChain(decoder.switchString(tree));
			decoder.setStack(decoder.stringStackConversion(decoder.getChain()));
			decoder.stackRootConversion();
			decoder.stackTreeConversion(decoder.getTree().getRoot(), false);

			BinaryTree<String> decodedTree = decoder.getTree(); //Initialize the decoded tree

			decodedTree.preOrderTraverseTree(decodedTree.getRoot()); //Traverse the tree

			BinaryTreeNode <String> tempNode = decodedTree.getRoot(); //Create a temporary node for traversing the tree

			character = in.read(); //Remove useless character
			checkChar = in.read(); //Get the check character

			while (character != -1) { //While there is still code to be read
				if (checkChar != -1) { //While the byte ahead is not blank
					if (decodedTree.size() ==1) { //Case for single character
						for(int j=7; j>=0; j--){            //Read item from root
							out.print(tempNode.getItem()); //Print to the file
						}
					}else {
						for (int i = 7; i >=0; i--) { //8 cases for ascii digits
							if (character >= Math.pow(2, i)) { //Go right if the character is larger than the power
								character = (int) (character - Math.pow(2, i)); //Subtract the difference
								tempNode = tempNode.getRight();
							}else { //Go left if the character is less than the power
								tempNode=tempNode.getLeft(); 
							}

							if (tempNode.isLeaf()) { //If it hits the end of the tree
								out.print(tempNode.getItem()); //Write the result
								tempNode = decodedTree.getRoot(); //Reset the tempNode 
							}
						} 
					}

				}else {
					if (decodedTree.size() ==1) { //Special case for one character
						for(int j=7; j>= skipBytes; j--){            //Read item from root
							out.print(tempNode.getItem()); //Print it to the file
						}
					}else {
						//Case for the last byte
						for (int i = 7; i >= skipBytes; i--) {
							if (character >= Math.pow(2, i)) { //Go right if the character is larger than the power
								character = (int) (character - Math.pow(2, i)); //Subtract the difference
								tempNode = tempNode.getRight(); 
							}else { //Go left if the character is less than the power
								tempNode=tempNode.getLeft();
							}

							if (tempNode.isLeaf()) { //If it hits the end of the tree
								out.print(tempNode.getItem()); //Write the result
								tempNode = decodedTree.getRoot(); //Reset the tempNode 
							}
						} 
					}
				}
				character = checkChar; //Move to next character
				checkChar = in.read(); //Move to next character
			}




		}catch(FileNotFoundException e){ //If file is non existent
			System.out.println("file not found"); //Error message
		}finally {
			//Close input and output
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}

