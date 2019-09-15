package model;

public interface IHashTable<K, V> {
	V search(K key);
	V remove(K key);
	void add(K key, V value);
}
