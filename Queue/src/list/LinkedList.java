// Name: William Baldwin
// Computing ID: vvp4cx@virginia.edu
// Homework Name: HW 7 LinkedList Queue
// Resources used: None
package list;

import java.util.List;

/**
 * 
 * A custom built linked list
 * T here is the type the list stores
 */
public class LinkedList<T> implements SimpleList<T>{

	/* Dummy head and tail */
	private ListNode<T> head, tail;
	private int size;
	
	public LinkedList() {
		/* TODO: Implement this method */
		//initializing head and tail to null
		tail = new ListNode<>(null);
		head = new ListNode<>(null);
		//linking head an tail together circularly since at the beginning there is nothing
		//in between them in the list, and declaring size 0
		tail.prev = head;
		head.next = tail;
		size = 0;
	}
	
	public int size() {
		/* TODO: Implement this method */  
		return size;
	}
	
	/**
	 * Clears out the entire list
	 */
	public void clear() {
		/* TODO: Implement this method */
		//reset it to starting point with no data in between the dummies and size 0
		tail.prev = head;
		head.next = tail;
		size = 0;
	}
	
	/**
	 * Inserts new data at the end of the list (i.e., just before the dummy tail node)
	 * @param data
	 */
	public void insertAtTail(T data) {
		/* TODO: Implement this method */
		ListNode<T> newData = new ListNode<>(data);
		//First we set the references for newData to point the last data and the tail at the end
		newData.prev = tail.prev;
		newData.next = tail;
		//Then we have to go back and change the last data to point to newData for the next thing
		tail.prev.next = newData;
		//and change tail to point to newData as the new last data in the list
		tail.prev = newData;
		size++;
	}
	
	/**
	 * Inserts data at the front of the list (i.e., just after the dummy head node
	 * @param data
	 */
	public void insertAtHead(T data) {
		/* TODO: Implement this method */
		/*
		doing same thing as in insertAtTail but reversed for head so creating the data
		then putting it at the beginning and assigning its own references
		then changing the old references to the new data
		 */
		ListNode<T> newData = new ListNode<>(data);
		newData.next = head.next;
		newData.prev = head;
		head.next.prev = newData;
		head.next = newData;
		size++;
	}
	
	/**
	 * Inserts node such that index becomes the position of the newly inserted data
	 * @param data
	 * @param index
	 */
	public void insertAt(int index, T data) {
		/* TODO: Implement this method */
		if (index == 0) {
			insertAtHead(data);
		} else if (index == size) {
			insertAtTail(data);
		} else if (index > 0 && index < size) {
			ListNode<T> newData = new ListNode<>(data);
			ListNode<T> placeHolder = head;
			//go through list with a place holding variable until you get to index and then change values
			//to insert new data at that index
			for (int i = 0; i < index; i++) {
				placeHolder = placeHolder.next;
			}
			newData.prev = placeHolder;
			newData.next = placeHolder.next;
			placeHolder.next.prev = newData;
			placeHolder.next = newData;
			size++;
		}
	}
	
	/**
	 * Inserts data after the node pointed to by iterator
	 */
	public void insert(ListIterator<T> it, T data) {
		/* TODO: Implement this method */
// if it isnt null then create the placeholder and the newdata node then change references
		if (it != null) {
			ListNode<T> newData = new ListNode<>(data);
			ListNode<T> placeHolder = it.curNode;
			newData.prev = placeHolder;
			newData.next = placeHolder.next;
			placeHolder.next.prev = newData;
			placeHolder.next = newData;
			size++;
		} //if its trying to insert at head
		if (it.isPastBeginning()) {
			insertAtHead(data);
		} //if its trying to insert at tail
		if (it.isPastEnd()) {
			insertAtTail(data);
		}
	}
	
	
	
	public T removeAtTail(){
		/* TODO: Implement this method */
		//if empty do nothing return null
		if (size == 0) return null;
		//create remove node as placeholder
		ListNode<T> remove = tail.prev;
		//assign new references for tail and the node before the one being removed to tail
		tail.prev = remove.prev;
		remove.prev.next = tail;
		//clear the node being removed
		remove.next = null;
		remove.prev = null;
		size--;
		//return node that was removed
		return remove.getData();
	}
	
	public T removeAtHead(){
		/* TODO: Implement this method */
		//same thing as removeAtTail but switch all the prev with next and tail with head
		//if empty do nothing return null
		if (size == 0) return null;
		//create remove node as placeholder
		ListNode<T> remove = head.next;
		//assign new references for head and the node after the one being removed to head
		head.next = remove.next;
		remove.next.prev = head;
		//clear the node being removed
		remove.next = null;
		remove.prev = null;
		size--;
		//return node that was removed
		return remove.getData();
	}
	
	/**
	 * Remove based on Iterator position
	 * Sets the iterator to the node AFTER the one removed
	 */
	public T remove(ListIterator<T> it) {
		/* TODO: Implement this method */
		//return null for null iterator
		if (it == null) {
			return null;
		}
		ListNode<T> placeHolder = it.curNode;
		//check if node is null
		if (placeHolder == null) {
			return null;
		}
// store the data in remove before getting rid of it, set current node to node after placeholder
		//change the references for before and after the placeholder to get rid of them referencing placeholder
		//change placeholder values to null
		T remove = placeHolder.getData();
		it.curNode = placeHolder.next;
		placeHolder.prev.next = placeHolder.next;
		placeHolder.next.prev = placeHolder.prev;
		placeHolder.next = null;
		placeHolder.prev = null;
		size--;
		return remove;
	}
	
	/**
	 * Returns index of first occurrence of the data in the list, or -1 if not present
	 * @param data
	 * @return
	 */
	public int find(T data) {
		/* TODO: Implement this method */
		int index = 0;
		ListNode<T> placeHolder = head.next;
		//go through the list until either a null match is found or a data match is found
		while (placeHolder != tail) {
			if (data != null) {
				if (placeHolder.getData() == null) {
					return index;
				}
				if (data.equals(placeHolder.getData())) {
					return index;
				}
			}
			index++;
			placeHolder = placeHolder.next;
		}
		//if not found
		return -1;
	}
	
	/**
	 * Returns the data at the given index, null if anything goes wrong (index out of bounds, empty list, etc.)
	 * @param index
	 * @return
	 */
	public T get(int index) { 
		/* TODO: Implement this method */
		//make sure index is within list if not return null
		if (index >= size || index < 0) return null;
		ListNode<T> placeHolder = head.next;
		//loop through list until we arrive at index and set the node to placeholder to return it
		for (int i = 0; i < index; i++) {
			placeHolder = placeHolder.next;
		}
		return placeHolder.getData();
	}
	
	/**
	 * Returns the list as space separated values
	 */
	public String toString() {
		String toRet = "[";
		
		ListNode<T> curNode = head.next;
		while(curNode != tail) {
			toRet += curNode.getData() + ", ";
			curNode = curNode.next;
		}
		
		return toRet + "]";
	}
	
	/* Return iterators at front and end of list */
	public ListIterator<T> front(){ 
		/* TODO: Implement this method */
		return new ListIterator<>(head.next);
	}

	public ListIterator<T> back(){
		/* TODO: Implement this method */ 
		return new ListIterator<>(tail.prev);
	}
	
	
}
