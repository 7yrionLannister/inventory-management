package model;

public interface IHashTable<K, V> {
	V search(int searchKey);
	V remove(int searchKey);
	boolean add(int seachKey, K key, V value);
}
