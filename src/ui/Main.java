package ui;

import java.util.LinkedList;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<Stack> top = new LinkedList<>();
		top.add(new Stack<Integer>());
		top.add(new Stack<String>());
		System.out.println(top + "  " + top.poll() + "  " + top.poll());
		String r = "%d%s%.2f%2f\n\r";
		System.out.println(r);
	}

}
