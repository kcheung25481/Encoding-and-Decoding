/**
 * Decoder.java
 * Used to convert string representation into binary tree representaiton
 * Author: Angus Tai
 * Date: April 18th 2018
 */

public class Decoder {
  private BinaryTree<String> tree = new BinaryTree<>();
  private String chain;
  private Stack<String> stack;
  
   /**
    * Decoder
    * Initializes variables of Decoder
    * @param chain - string that needs to be put into a tree
    */ 
  public Decoder(String chain){
    this.chain = chain;
  }
  
  /**
   * setTree
   * A method that is the setter for the tree
   * @param tree - a binary tree which holds Strings
   */ 
  public void setTree(BinaryTree<String> tree) {
    this.tree = tree;
  }
  
  /**
   * getTree
   * A method that is the getter for the tree
   * @return tree - a binary tree
   */ 
  public BinaryTree<String> getTree() {
    return tree;
  }
  
  /**
   * setChain
   * Setter for the variable chain
   * @param chain
 */
  public void setChain(String chain) {
    this.chain = chain;
  }
  
  /**
   * getChain
   * This method returns chain
   * @return chain
 */
  public String getChain() {
    return chain;
  }
   /**
   * setStack
   * A method that is the setter for the stack
   * @param stack - a stack holding Strings
   */ 
  public void setStack(Stack<String> stack) {
    this.stack = stack;
  }
  
  /**
   * getStack
   * A method that is the getter for the stack
   * @return stack - a stack
   */ 
  public Stack<String> getStack() {
    return stack;
  }
  
  /**
   * switchString
   * This method switches from an ascii representation to a string representation
   * @param chain - the initial string of ascii values
   * @return String converted from ascii
 */
  public String switchString(String chain){ //this turns an ascii represented string tree to a character represented string tree
    for(int i = 0; i<chain.length(); i++){
      int end = i;
      String temp = "";
      if (!(chain.substring(i,i+1).equals("("))&& !(chain.substring(i,i+1).equals(" "))&& !(chain.substring(i,i+1).equals(")")) ) {
        do{
          end++;
        }while(!(chain.substring(end,end+1).equals("("))&& !(chain.substring(end,end+1).equals(" "))&& !(chain.substring(end,end+1).equals(")")) );
        temp = chain.substring(i,end);
        int value = Integer.parseInt(temp);
        Character replacement = (char)value;
        chain = chain.substring(0, i) + replacement + chain.substring(end);
      }
    }
    return chain;
  }
  
  /**
   * switchAscii
   * This method switches from a string representation to an ascii representaiton
   * @param chain - the initial string
   * @return String converted to ascii
 */
  public String switchAscii(String chain){//this turns a character represented string tree to a ascii represented string tree
    String asciiValue;
    for(int i = 0; i<chain.length(); i++){     
      if (!(chain.substring(i,i+1).equals("("))&& !(chain.substring(i,i+1).equals(" "))&& !(chain.substring(i,i+1).equals(")")) ) {//if the string is an actual character in the tree
        asciiValue = (int)chain.charAt(i)+"";
        chain = chain.substring(0, i) + asciiValue + chain.substring(i + 1);
        i = i + asciiValue.length();//accounts for ascii values being more than 1 character
      }
    }
    return chain;
  }
   
  /**
   * stringStackConversion
   * This method changes the string representation into a stack of characters
   * @param chain - the initial string
   * @return stringStack - stack of characters
 */
  public Stack stringStackConversion(String chain){
    Stack<String> stringStack = new Stack<>();
    for(int i = 0; i< chain.length(); i++){
      stringStack.push(chain.substring(i, i+1));
    }
    return stringStack;
  }
   /**
   * stackRootConversion
   * This method changes an item in a stack into the root of a binary tree
 */
  public void stackRootConversion(){
    stack.pop();//pops the first character from the stack (right bracket) 
    tree.addOrdered(null);//represents the root of the tree
  }
  
   /**
   * stackTreeConversion
   * This method converts the characters in the stack into items in a binary tree
   * @param focusNode - a BinaryTreeNode holding a String, shiftLeft - a boolean representing whether next focus node is to the left or not
 */
  public void stackTreeConversion(BinaryTreeNode<String> focusNode, boolean shiftLeft){
    if (stack.isEmpty() == true){//if the stack is empty
      return;//base case
    }else{   
      String temp = stack.pop("");
      if(temp.equals("(")){//if the character popped is a left bracket 
        
        return;//end of recursive calls
        
      }else if(temp.equals(" ")){//if the character popped is a space 
        
        stackTreeConversion(focusNode, true);//this means that the next character will be on the left       
      
      }else if (temp.equals(")")){//if the character popped is a right bracket 
        
        if (shiftLeft == true){
          focusNode.setLeft(new BinaryTreeNode<>(null));//make a new node left of the current node
          stackTreeConversion(focusNode.getLeft(), false);//recursive call on the node to the left of the current node
          stackTreeConversion(focusNode, false);//recursive call on current node               
        }else{
          focusNode.setRight(new BinaryTreeNode<>(null));//make a new node right of the current node 
          stackTreeConversion(focusNode.getRight(), false);//recursive call on the node to the right of the current node
          stackTreeConversion(focusNode, false);//recursive call on current node        
        }  
        
      }else{//if the character popped is actually part of the message
        
        System.out.println("letter! is ! "+ temp);
        if (shiftLeft == true){
          focusNode.setLeft(new BinaryTreeNode<>(temp));//set the left node to holding the character as the item      
        }else{
          focusNode.setRight(new BinaryTreeNode<>(temp));//set the right node to holding the character as the item
        }
        stackTreeConversion(focusNode,false);       
      }
    }
  }
}
