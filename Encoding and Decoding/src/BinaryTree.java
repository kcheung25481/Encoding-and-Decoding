
// E is comparable
public class BinaryTree<E extends Comparable>{

    //head of the tree
    private BinaryTreeNode<E> root;

    /**
     *  calculates the size with recursion
     * @param focusNode
     * @return
     */
    public int size(BinaryTreeNode<E> focusNode){
        if (focusNode == null){
            return 0;//base case
        }else{
            return (size(focusNode.getLeft())+ 1 + size(focusNode.getRight())); //go through each node
        }

    }

    /**
     * returns the size of the tree
     * @return
     */
    public int size(){
        return size(root);
    }

    public boolean contains(E item){
        BinaryTreeNode<E> focusNode = root;

        while (focusNode != null && !focusNode.getItem().equals(item)){
            if (item.compareTo(focusNode.getItem())<0){
                focusNode = focusNode.getLeft();
            }else{
                focusNode = focusNode.getRight();
            }
        }
        if (focusNode == null){
            return false;
        }
        return true;
    }

    /**
     * returns the specific tree node by taking in an item
     * @param item
     * @return
     */
    public BinaryTreeNode findTreeNode(E item){
        BinaryTreeNode<E> focusNode = root;

        while (focusNode != null && !focusNode.getItem().equals(item)){
            if (item.compareTo(focusNode.getItem())<0){
                focusNode = focusNode.getLeft();
            }else{
                focusNode = focusNode.getRight();
            }
        }
        if (focusNode == null){
            return null;
        }
        return focusNode;
    }

    /**
     * adds an item to the tree
     * @param item
     */

    public void addOrdered(E item){

        BinaryTreeNode<E> newNode = new BinaryTreeNode<E>(item);

        //If there is not root
        if (root == null){
            root = newNode;
        }else{
            BinaryTreeNode<E> focusNode = root;
            BinaryTreeNode<E> parent;

            while (true){
                parent = focusNode;
                if (item.compareTo(focusNode.getItem())<0){ //when the value is less than the focused node stick it to the left
                    focusNode = focusNode.getLeft();

                    if (focusNode == null){ //if the left node of current parent has no child then stick it in
                        parent.setLeft(newNode);
                        return; //end
                    }
                }else{
                    //put it to the right
                    focusNode = focusNode.getRight();

                    if (focusNode == null){ //if the right node of current parent has no child then stick it in
                        parent.setRight(newNode);
                        return; //end
                    }

                }
            }
        }

    }

    public void addLeft(E item){
    }
    public void addRight(E item){

    }

    public void makeTree(BinaryTreeNode<E> left, BinaryTreeNode<E> right) {
    	
    	BinaryTreeNode<E> tempNode = new BinaryTreeNode<E>(left, right);
    	root = tempNode;
    	
    }
    
    public void makeTree(BinaryTreeNode<E> root) {
    	
    	this.root = root;
    	
    }
    
    public String makeTreeString(BinaryTreeNode<E> focusNode){
        if (focusNode.getItem() == null){ //if the focus is not a leaf
           return "(" + makeTreeString(focusNode.getLeft()) +" "+ makeTreeString(focusNode.getRight()) + ")";
        }else if (focusNode==null){
             return null;
        }else{
           return "" + focusNode.getItem();
        }
    }




    /**
     * takes in an item and removes it
     * returns boolean indicating if remove is successful
     * @param item
     * @return
     */
    public boolean remove(E item){
        BinaryTreeNode<E> focusNode = root;
        BinaryTreeNode<E> parent = root;
        boolean isLeft = true;

        while(focusNode!= null && !focusNode.getItem().equals(item)){
            parent = focusNode;

            //serach left
            if (item.compareTo(focusNode.getItem())<0){
                isLeft = true;
                focusNode = focusNode.getLeft();
            }else{
                //search right

                isLeft = false;
                focusNode = focusNode.getRight();
            }
            //not found
            if (focusNode == null){
                return false;
            }


        }
        //if there is no children of the found node
        if (focusNode.isLeaf()){
            if (focusNode.equals(root)){
                //delete it if it is root
                root = null;
            }else if(isLeft){
                parent.setLeft(null);
            }else{
                parent.setRight(null);
            }
        }else if(focusNode.getRight() == null){
            //if no right child
            if(focusNode.equals(root)){
                //if it is a root then assign the left child value as root
                root = focusNode.getLeft();
            }else if(isLeft){
                //if it is a left child then assign the focus node's left child to the parent as a left child
                parent.setLeft(focusNode.getLeft());

            }else{
                parent.setRight(focusNode.getLeft());
            }
        }else if(focusNode.getLeft() == null){
            //if no left child
            if(focusNode.equals(root)){
                //if it is a root then assign the left child value as root
                root = focusNode.getRight();
            }else if(isLeft){
                //if it is a left child then assign the focus node's right child to the parent as a left child
                parent.setLeft(focusNode.getRight());

            }else{
                parent.setRight(focusNode.getRight());
            }
        }else{
            //if there are 2 children, find the replacement
            BinaryTreeNode<E> replacement = findReplacement(focusNode);

            if (focusNode == root){
                root = replacement;

            }else if(isLeft){
                parent.setLeft(replacement);
            }else{
                parent.setRight(replacement);
            }
            replacement.setLeft(focusNode.getLeft());
        }
        return true;
    }

    /**
     * method that find the replacement of a to-be-deleted node
     * @param replacedNode
     * @return replacement
     */
    public BinaryTreeNode<E> findReplacement(BinaryTreeNode<E> replacedNode){
        BinaryTreeNode<E> replacementParent = replacedNode;
        BinaryTreeNode<E> replacement = replacedNode;

        BinaryTreeNode<E> focusNode = replacedNode.getRight();
        //until there is no more right child
        while(focusNode!=null){
            //find replacement by going through each left child
            replacementParent = replacement;
            replacement = focusNode;
            focusNode = focusNode.getLeft();

        }

        if(replacement != replacedNode.getRight()){
            replacementParent.setLeft(replacement.getRight());
            replacement.setRight(replacedNode.getRight());
        }

        return replacement;
    }

    /**
     * recursively print out all the nodes
     * @param focusNode
     */

    public void preOrderTraverseTree(BinaryTreeNode focusNode){

        if (focusNode != null) {
            //go through the items in order
            System.out.println(focusNode.getItem());
            preOrderTraverseTree(focusNode.getLeft());
            preOrderTraverseTree(focusNode.getRight());

        }
    }

    /**
     *
     * @return root
     */
    public BinaryTreeNode<E> getRoot(){
        return this.root;
    }
}
