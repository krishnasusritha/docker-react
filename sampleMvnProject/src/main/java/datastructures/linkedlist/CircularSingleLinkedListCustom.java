package datastructures.linkedlist;

public class CircularSingleLinkedListCustom {

	public Node last;

	public boolean isEmpty() {
		return last == null;
	}

	public void add(int n) {
		Node node = new Node(n, null);
		if (isEmpty()) {
			last = node;
		} else {
			node.next = last.next;
		}
		last.next = node;
	}

	public void addLast(int n) {
		Node node = new Node(n, null);
		if (isEmpty()) {
			last = node;
			last.next = node;
		} else {
			node.next = last.next;
			last.next = node;
			last = node;
		}
	}

	public int removeFirst() {
		int value = last != null ? last.next.value : 0;
		Node temp = last.next;
		if (temp == last) {
			last = null;
		} else {
			last.next = temp.next;
			temp.next = null;
		}
		return value;

	}

	public void show() {
		Node temp = last.next;
		while (temp != last) {
			System.out.println(temp.value);
			temp = temp.next;
		}
		System.out.println(temp.value);
	}

}
