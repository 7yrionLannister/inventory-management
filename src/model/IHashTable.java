package model;

public interface IHashTable<K, V> {
	V search(K searchKey);
	V remove(K searchKey);
	void add(K seachKey, V value);
}
