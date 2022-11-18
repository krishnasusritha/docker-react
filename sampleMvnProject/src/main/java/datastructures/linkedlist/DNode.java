package datastructures.linkedlist;

public class DNode {

	public DNode prev;
	
	public DNode next;
	
	public int value;

	public DNode(DNode prev, DNode next, int value) {
		super();
		this.prev = prev;
		this.next = next;
		this.value = value;
	}
	
}
