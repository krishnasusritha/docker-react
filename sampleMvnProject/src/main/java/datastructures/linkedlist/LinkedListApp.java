package datastructures.linkedlist;

public class LinkedListApp {

	public static void main(String[] args) {
		LinkedListCustom list = new LinkedListCustom();
		
		list.add(1);
		list.add(2);
		list.add(3);
		
		LinkedListCustom list1 = new LinkedListCustom();
		
		list1.add(9);
		list1.add(8);
		list1.add(7);	
		//Node node = list.mergeLists(list.head, list1.head);
		//list.head = node;
		Node add = list.addLists(list.head, list1.head);
		list.head = add;
		list.show();
		/*
		 * list.add(2, 5); list.add(6,10); list.add(2, 5); list.show();
		 * System.out.println("After deleting"); list.delete(4); list.show();
		 * 
		 * System.out.println("Insert first");
		 * 
		 * list.addFirst(5); list.addFirst(7); list.show();
		 * 
		 * list.reverse(); System.out.println("After reversing"); list.show();
		 * 
		 * System.out.println("Middle number is " + list.middle());
		 */
	}

}
