package model;

public interface IQueue<E> {
	E front();
	boolean isEmpty();
	void enqueue(E item);
	void dequeue();
}
