package datastructures.linkedlist;

public class DoubleLinkedListCustom {
	
	public DNode head;
	public DNode tail;
	
	public void add(int n) {
		DNode node = new DNode(null, null, n);
		if(isEmpty()) {
			head = node;
		} else {
			tail.next = node;
			node.prev = tail;
		}
		tail = node;
	}
	
	public void addAtFirst(int n) {
		DNode node = new DNode(null, null, n);
		if(isEmpty()) {
			tail = node;
		} else {
			head.prev = node;
			node.next = head;
		}
		head = node;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public void showList() {
		DNode temp = head;
		while(temp != null) {
			System.out.println(temp.value);
			temp = temp.next;
		}
	}
	
	public void showReverseList() {
		DNode temp = tail;
		while(temp != null) {
			System.out.println(temp.value);
			temp = temp.prev;
		}
	}
	
	public int deleteFirst() {
		int value = head.value;
		head = head.next;
		head.prev = null;
		return value;
	}
	
	public int deleteLast() {
		int value = tail.value;
		tail = tail.prev;
		tail.next = null;
		return value;
	}
}
