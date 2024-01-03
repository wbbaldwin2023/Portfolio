// Name: William Baldwin
// Computing ID: vvp4cx@virginia.edu
// Homework Name: HW 7 LinkedList Queue
// Resources used: None
package list;

public class ListIterator<T> { 
	protected ListNode<T> curNode;  /* Current node (of type ListNode) */
	
        /* ListIterator constructor. Accepts the current node. */
	public ListIterator(ListNode<T> currentNode) { 
		/* TODO: Implement this method */
		//set the curNode stored here to the imported currentMode
		curNode = currentNode;
	}
	
	/**
	 * These two methods tell us if the iterator has run off
	 * the list on either side
	 */
	public boolean isPastEnd() { 
		/* TODO: Implement this method */
		/* Hint: How do you know if you’re at the dummy tail node? */
		//if its at the end the next node would be null
		return curNode == null && curNode.next == null;
	}
	
	public boolean isPastBeginning() { 
		/* TODO: Implement this method */
		return curNode == null && curNode.prev == null;
		//if its at the beginning the previous node would be null
		/* Hint: How do you know if you’re at the dummy head node? */
	}
	
	/**
	 * Get the data at the current iterator position
	 * Return the data if appropriate, otherwise return null
	 */
	public T value() {
		/* TODO: Implement this method */
		/* Hint: Remember to first validate the position of the Iterator */
		//make sure not null then return the data
		if (curNode == null) {
			return null;
		} else {
			return curNode.getData();
		}
	}
	
	/**
	 * These two methods move the cursor of the iterator
	 * forward / backward one position
	 */
	public void moveForward() { 
		/* TODO: Implement this method */
		/* Hint: Remember to check IF you can move forward before you do! */
                /*       (Otherwise, do not move at all) */
		//check null then set node to next node
		if (curNode.next != null) curNode = curNode.next;
	}
	
	public void moveBackward() { 
		/* TODO: Implement this method */
		/* Hint: Remember to check IF you can move backwards before you do! */
		/*       (Otherwise, do not move at all) */
		//check null then set node to previous node
		if (curNode.prev != null) curNode = curNode.prev;
	}
}


