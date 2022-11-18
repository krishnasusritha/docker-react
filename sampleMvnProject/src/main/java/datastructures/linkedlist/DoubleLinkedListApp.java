package datastructures.linkedlist;

public class DoubleLinkedListApp {
	public static void main(String[] args) {
		DoubleLinkedListCustom list = new DoubleLinkedListCustom();

		list.addAtFirst(10);
		
		list.add(1);
		list.add(2);
		list.add(3);
		
		list.deleteLast();
		list.deleteFirst();
		
		/*
		 * DoubleLinkedListCustom list1 = new DoubleLinkedListCustom();
		 * 
		 * list1.add(9); list1.add(8); list1.add(7);
		 */
		//Node node = list.mergeLists(list.head, list1.head);
		//list.head = node;
		///Node add = list.addLists(list.head, list1.head);
		//list.head = add;
		list.showList();
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
