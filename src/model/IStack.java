package model;

public interface IStack<E> {
	E top();
	boolean isEmpty();
	void push(E item);
	E pop();
}
