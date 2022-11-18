package datastructures.stack;

public class StackApp {

	public static void main(String[] args) {
		StackCustom stack = new StackCustom();
		System.out.println("Empty = " + stack.isEmpty());
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		stack.push(10);
		stack.show();
		System.out.println("After POP");
		stack.pop();
		stack.pop();
		
		stack.show();
		
		System.out.println("Top element " + stack.peek());
		
		System.out.println("Empty = " + stack.isEmpty());
		stack.size();
	}
}
