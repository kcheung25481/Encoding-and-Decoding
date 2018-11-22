class MyLinkedList<E> {
  private Node<E> head;
  
  public boolean add(E item) {
    Node<E> tempNode = head;
    
    if (head==null) {
      head=new Node<E>(item,null);
      return true;
    }
    
    while(tempNode.getNext()!= null) {
      tempNode = tempNode.getNext();
    }
    
    tempNode.setNext(new Node<E>(item,null));
    return true;
  }
  
  
  
  public E get(int index) {
    Node<E> tempNode = head;
    if (tempNode == null || index > size()) {
      return null;
    }
    for (int i = 0; i < index; i++) {
      tempNode = tempNode.getNext();
    }
    
    
    return tempNode.getItem();
  }

  public int indexOf(E item) {
    Node<E> tempNode = head;
    int i = 0;
    if (tempNode == null) {
      return -1;
    }
    while(tempNode != null) {
      if (tempNode.getItem().equals(item)) {
        return i;
      } else {
        i++;
        tempNode = tempNode.getNext();
      }
    }
    return -1;
  }
  
  public E remove(int index) {
    Node<E> tempNode = head;
    E item;
    
    if (index == 0) {
      item = head.getItem();
      head = head.getNext();
    } else {
      for (int i = 0; i < index-1; i++) { //make tempNode the one before the one you wanna take out
        tempNode = tempNode.getNext();
      }
      item = tempNode.getNext().getItem(); //the item you're removing
      Node<E> newTempNode = tempNode.getNext().getNext(); //the one after the one you're removing
      tempNode.setNext(newTempNode);
    }
    return item;
  }

  public boolean remove(E item) {
    if (indexOf(item) == -1) {
      return false;
    } else {
      remove(indexOf(item));
      return true;
    }
  }

  
  
  public void clear() { //done
    head = null;
  }
  
  public int size() { //done
    Node<E> tempNode = head;
    int i = 1;
    if (tempNode == null) {
      return 0;
    }
    while(tempNode.getNext() != null) {
      tempNode = tempNode.getNext();
      i++;
    }
    return i;
  }
  
}