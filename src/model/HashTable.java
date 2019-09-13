package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HashTable<K, V> implements IHashTable<K, V> {
	private HNode<K, V>[] items; //The array or table
	private int storedItems;
	
	public HashTable(int size) {
		items = (HNode<K, V>[])new HNode[size];
		storedItems = 0;
	}
	
	@Override
	public V search(K searchKey) {
		// TODO ¿¿¿Usamos nuestra proppia funcion hash o hashCode de Object???
		return null;
	}

	@Override
	public V remove(K searchKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(K seachKey, V value) {
		// TODO Auto-generated method stub
		
	}

}
