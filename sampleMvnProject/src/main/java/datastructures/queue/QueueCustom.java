package datastructures.queue;

import datastructures.linkedlist.Node;

public class QueueCustom {

	public Node start;
	
	public Node rear;
	
	public void enqueue(int value) {
		Node element = new Node(value, null);
		if(isEmpty()) {
			start = element;
			rear = element;
		} else {
			rear.next = element;
			rear = element;
		}
	}
	
	public int dequeue() {
		int value = 0;
		if(start != null) {
			value = start.value;
			start = start.next;
		}
		
		return value;
	}
	
	public void show() {
		Node temp = start;
		while (temp != null) {
			System.out.println(temp.value);
			temp = temp.next;
		}
	}
	
	public void size() {
		int count = 0;
		Node temp = start;
		while(temp != null) {
			temp = temp.next;
			count++;
		}
		System.out.println("Size of the stack is " + count);
	}
	
	public boolean isEmpty() {
		return start == null;
	}
	
}
