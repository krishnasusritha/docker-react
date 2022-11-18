package datastructures.queue;

public class QueueApp {

	public static void main(String[] args) {
		QueueCustom queue = new QueueCustom();
		
		queue.enqueue(4);
		queue.enqueue(5);
		queue.enqueue(6);
		queue.enqueue(7);
		queue.enqueue(8);
		queue.enqueue(9);
		queue.enqueue(1);
		queue.show();
		
		System.out.println("Dequeued " + queue.dequeue());
		
		queue.show();
	}

}
