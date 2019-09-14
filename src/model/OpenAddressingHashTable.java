package model;

import java.util.ArrayList;

public class OpenAddressingHashTable<K, V> implements IHashTable<K, V> {
	public final static double A = (Math.sqrt(5)-1.0)/2.0;

	private HNode<K, V>[] items; //The array or table
	private boolean[] DELETED; 
	private int storedItems;

	public OpenAddressingHashTable(int size) {
		items = (HNode<K, V>[])new HNode[size];
		DELETED = new boolean[size];
		storedItems = 0;
	}

	@Override
	public V search(int searchKey) {
		int h = hashFunction(true, searchKey);
		if(h != -1 && !DELETED[h]) {
			return items[h].getValue();
		}
		return null;
	}

	@Override
	public V remove(int searchKey) {
		int h = hashFunction(true, searchKey);
		if(h != -1 && !DELETED[h]) {
			DELETED[h] = true;
			storedItems--;
			return items[h].getValue();
		} 
		return null;
	}

	@Override
	public boolean add(int seachKey, K key, V value) {
		int h = hashFunction(false, seachKey);
		if(h != -1) {
			items[h] = new HNode<>(seachKey, key, value);
			storedItems++;
			DELETED[h] = false;
			return true;
		}
		return false;
	}

	public HNode<K, V>[] getItems() {
		return items;
	}

	public void setItems(HNode<K, V>[] items) {
		this.items = items;
	}

	public int getStoredItems() {
		return storedItems;
	}

	public boolean[] getDELETED() {
		return DELETED;
	}

	/**The method allows to compute the hash function for keys of type String
	 * The characters in the String are supposed to be in the set of ASCII[46, 90]
	 * @return An integer representing the slot computed by the hash function<br><b>-1</b> if  
	 * @throws Exception If the key contains an invalid character
	 * */
	public int hashFunction(boolean search, int key) {
		int hashCode = -1;

		int m = items.length;

		int hash1 = ((int) (m*((key*A) % 1))) % items.length;

		if((search && items[hash1] != null && items[hash1].getIntKey() == key) 
				|| (!search && items[hash1] == null || (DELETED[hash1] && items[hash1].getKey().equals(key)))) {
			hashCode = hash1;
		} else {
			int hash2 = key % m;
			int totalHash = (hash1+(int)(A*hash2)) % m;
			int i = 1;
			ArrayList<Integer> visited = new ArrayList<>();
			visited.add(hash1);
			int possibleDeletedSlot = -1;
			
			boolean found = false;
			while (visited.size() < m) {
				if(!search && possibleDeletedSlot == -1 && DELETED[totalHash] && !items[totalHash].getKey().equals(key)) {
					possibleDeletedSlot = totalHash;
				}
				if(items[totalHash] == null && !search) {
					found = true;
					break;
				} else if( search && items[totalHash] != null && items[totalHash].getIntKey() == key) {
					found = true;
					break;
				}
				++i;
				totalHash = (hash1 + (int)(i*A*hash2)) % m;
				if(!visited.contains(totalHash)) {
					visited.add(totalHash);
				}
			}

			if(found) {
			hashCode = totalHash;
			} else if(possibleDeletedSlot != -1) {
			hashCode = possibleDeletedSlot;
			}
		}

		return hashCode;
	}
}
