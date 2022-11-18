package datastructures.linkedlist;

public class CircularSLLApp {
	public static void main(String[] args) {
		CircularSingleLinkedListCustom list = new CircularSingleLinkedListCustom();

		
		list.add(1);
		list.add(2);
		list.add(3);
		
		list.addLast(10);
		list.removeFirst();
		list.show();
	}
}
