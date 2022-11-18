package datastructures.stack;

import datastructures.linkedlist.Node;

public class StackCustom {

	Node top;

	public void push(int value) {
		Node element = new Node(value, null);

		if (top == null) {
			top = element;
		} else {
			element.next = top;
			top = element;
		}
	}

	public int pop() {
		int value = 0;
		if (top != null) {
			value = top.value;
			top = top.next;
		}
		return value;
	}
	
	public int peek() {
		return top != null ? top.value : 0;
	}

	public void show() {
		Node temp = top;
		while (temp != null) {
			System.out.println(temp.value);
			temp = temp.next;
		}
	}
	
	public void size() {
		int count = 0;
		Node temp = top;
		while(temp != null) {
			temp = temp.next;
			count++;
		}
		System.out.println("Size of the stack is " + count);
	}
	
	public boolean isEmpty() {
		return top == null;
	}
}
