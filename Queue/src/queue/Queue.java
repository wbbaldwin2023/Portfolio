// Name: William Baldwin
// Computing ID: vvp4cx@virginia.edu
// Homework Name: HW 7 LinkedList Queue
// Resources used: None

package queue;

import list.LinkedList;
import queue.EmptyQueueException;


/**
 * A Linked-List based Queue
 * @param <T>
 */
public class Queue<T> implements IQueue<T>{
	private LinkedList<T> list;
	/**
	 * Constructor: Initialize the inner list
	 */
	public Queue(){
		/* TODO: Implement this method */
        list = new LinkedList<>();
	}

	/**
	 * Return the size by invoking the size of the list
	 */
	public int size() {
		/* TODO: Implement this method */
		return list.size();
	}

	/**
	 * Simply add the data to the tail of the linked list
	 */
	public void enqueue(T data) {
		/* TODO: Implement this method */
		/* Hint: Which method in LinkedList.java already accomplishes this? */
        list.insertAtTail(data);
	}

	/**
	 * Simply remove data from the head of the list, throw exception if list is empty.
	 */
	public T dequeue() throws EmptyQueueException {
		/* TODO: Implement this method */
		/* Hint: Which method in LinkedList.java already accomplishes this? */
		/* What should you do if the queue is empty? */
        if (list.size() == 0) {
            throw new EmptyQueueException("Empty Queue");
        }
        //store value then remove it and return it
        T data = list.front().value();
        list.removeAtHead();
        return data;
	}

	/**
	 * Simply return the value from the head of the list, list should remain unchanged
         * throw exception if list is empty.
	 */
	public T peek() throws EmptyQueueException {
		/* TODO: Implement this method */
		/* Hint: Which method in LinkedList.java already accomplishes this? */
		/* What should you do if the queue is empty? */
        //same thing as dequeue but no need to remove anything just return the front val
        if (list.size() == 0) {
            throw new EmptyQueueException("Empty Queue");
        }
        return list.front().value();
	}
}
