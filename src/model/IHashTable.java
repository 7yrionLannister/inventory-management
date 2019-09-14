package model;

public interface IHashTable<K, V> {
	V search(int intKey, K key);
	V remove(int intKey, K key);
	boolean add(int intKey, K key, V value);
}
