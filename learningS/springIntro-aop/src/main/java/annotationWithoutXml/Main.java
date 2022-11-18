package annotationWithoutXml;

import java.util.LinkedList;
import java.util.ListIterator;

public class Main {
	
	public static void main(String[] args) {
		
		LinkedList<String> list = new LinkedList<String>();
		list.add("one");
		list.add("two");
		
		 ListIterator<String> it = list.listIterator();
		 while (it.hasNext()) {
			String next = null;
			String curr = null;
			if (next == null) {
				next = it.next();
			}
		}
	}
	
}
