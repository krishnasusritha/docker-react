package datastructures.linkedlist;

public class LinkedListCustom {

	Node head;

	public void add(int value) {

		Node element = new Node(value, null);
		if (head == null) {
			head = element;
		} else {
			Node cur = head;
			while (cur.next != null) {
				cur = cur.next;
			}
			cur.next = element;
		}

	}

	public void addFirst(int value) {
		Node element = new Node(value, head);
		head = element;
	}

	public void add(int index, int value) {

		Node element = new Node(value, null);
		int i = 1;
		Node node = head;
		if (index == 0) {
			addFirst(value);
		} else {
			while (i != index - 1 && node.next != null) {
				node = node.next;
				i++;
			}
			if (i == index - 1) {
				Node temp = node.next;
				node.next = element;
				element.next = temp;
			} else {
				node.next = element;
			}
		}
	}

	public int delete(int index) {
		int i = 1;
		Node node = head;
		if (index == 0) {
			head = head.next;
		} else {
			while (i != index - 1 && node.next != null) {
				node = node.next;
				i++;
			}
			if (i == index - 1) {
				Node temp = node.next;
				node.next = temp.next;
				return temp.value;
			}
		}
		return 0;

	}

	public void show() {
		Node n = head;
		while (n != null) {
			System.out.println(n.value);
			n = n.next;
		}
	}
	
	public boolean search(int value) {
		boolean present = false;
		Node current = head;
		
		while(current != null) {
			if(current.value == value) {
				present = true;
				break;
			}
		}
		
		return present;
	}
	
	public void reverse() {
		Node current = head;
		Node prev = null;
		Node next = null;
		
		while(current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		head = prev;
		
	}
	
	public int middle() {
		Node slow = head;
		Node fast = head;
		while(fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow.value;
	}
	
	public void removeDupFromSortedList() {
		Node current = head;
		
		while(current != null && current.next!= null) {
			if(current.value == current.next.value) {
				current.next = current.next.next;
			}
			current = current.next;
		}
	}
	
	public void insertToSortedList(int value) {
		Node node = new Node(value, null);
		Node current = head;
		while(current.next != null && current.next.value < value) {
			current = current.next;
		}
		node.next = current.next;
		current.next = node;
	}
	
	public boolean detectLoop() {
		Node slow = head;
		Node fast = head;
		boolean loopDetected = false;
		while(fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if(slow == fast) {
				Node start = getStartPoint(slow);
				loopDetected = true;
				break;
			}
		}
		return loopDetected;
	}
	
	public Node getStartPoint(Node slow) {
		Node element = head;
		while(slow != element) {
			element = element.next;
			slow = slow.next;
		}
		return slow;
	}
	
	public Node removeLoop(Node slow) {
		Node element = head;
		while(slow != element) {
			element = element.next;
			slow = slow.next;
		}
		slow.next = null;
		return slow;		
	}
	
	public Node mergeLists(Node head1, Node head2) {
		Node temp = new Node(0, null);
		Node dummy = temp;
		Node first = head1;
		Node second = head2;
		while(first != null && second != null) {
			if(first.value <= second.value) {
				temp.next = first;
				first = first.next;
			} else  {
				temp.next = second;
				second = second.next;
			}
			temp = temp.next;
		} if(first ==  null) {
			temp.next = second;
		} else {
			temp.next = first;
		}
		return dummy.next;
	}
	
	public Node addLists(Node head1, Node head2) {
		Node temp = null;
		Node first = head1;
		Node second = head2;
		Node dummy = null;
		int carry = 0;
		while(first != null || second != null) {
			int x = first != null ? first.value : 0;
			int y = second != null ? second.value : 0;
			
			temp = new Node((x + y + carry) % 10, dummy);
			carry = (x + y + carry) / 10;
			dummy = temp;
			first = first != null ? first.next : null;
			second = second != null ? second.next : null;
		}
		if(carry > 0) {
			temp = new Node(carry, dummy);
		}
		return temp;
	}

	
 }
