package model;

import java.util.ArrayList;

public class OpenAddressingHashTable<K, V> implements IHashTable<K, V> {
	public final static double A = (Math.sqrt(5)-1.0)/2.0;

	private HNode<K, V>[] items; //The array or table
	private boolean[] DELETED; 
	private int storedItems;
	private LongTranslator<K> integerTranslator;

	public OpenAddressingHashTable(int size, LongTranslator<K> it) {
		items = (HNode<K, V>[])new HNode[size];
		DELETED = new boolean[size];
		storedItems = 0;
		integerTranslator = it;
	}

	@Override
	public V search(K key) {
		int h = hashFunction(true, key);
		if(h != -1 && !DELETED[h]) {
			return items[h].getValue();
		}
		return null;
	}

	@Override
	public V remove(K key) {
		int h = hashFunction(true, key);
		if(h != -1 && !DELETED[h]) {
			DELETED[h] = true;
			storedItems--;
			return items[h].getValue();
		} 
		return null;
	}

	@Override
	public void add(K key, V value) {
		if(storedItems == items.length) {
			throw new IllegalStateException("The hash table is already full");
		}
		int h = hashFunction(false, key);
		if(h != -1) {
			items[h] = new HNode<>(key, value);
			storedItems++;
			DELETED[h] = false;
		}
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
	public int hashFunction(boolean search, K key) {
		int hashCode = -1;
		long intKey = integerTranslator.keyToLong(key);
		int m = items.length;

		int hash1 = ((int) (m*((intKey*A) % 1))) % m;

		if((search && items[hash1] != null && (items[hash1].getKey().equals(key) ||
				(key.toString().length() > 5 && items[hash1].getKey().toString().contains(key.toString().substring(0, key.toString().length()-5))))
				&& !DELETED[hash1]) 
				|| (!search && (items[hash1] == null || DELETED[hash1]))) {
			hashCode = hash1;
		} else {
			long hash2 = (((long)(Math.PI*intKey*(A+1))) % m);
			int totalHash = (hash1+(int)(A*hash2)) % m;
			long i = 1;
			ArrayList<Integer> visited = new ArrayList<>();
			visited.add(hash1);
			int possibleDeletedSlot = -1;

			boolean found = false;
			while (visited.size() < m) {
				//TODO remove line
				//System.out.println(visited.size() + "..." + totalHash + " ("+hash1+","+hash2+") - key = "+ key);
				if(!search && possibleDeletedSlot == -1 && DELETED[totalHash]) {
					possibleDeletedSlot = totalHash;
					break;
				}
				if(items[totalHash] == null && !search) {
					found = true;
					break;
				} else if( search && items[totalHash] != null && !DELETED[totalHash] && 
						(items[totalHash].getKey().equals(key) ||
								(key.toString().length() > 5 && items[totalHash].getKey().toString().contains(key.toString().substring(0, key.toString().length()-5))))) {
					found = true;
					break;
				}
				++i;
				totalHash = (hash1 + (int)(i*A*hash2)) % m;
				if(!visited.contains(totalHash)) {
					visited.add(totalHash);
				}
			}

			if(possibleDeletedSlot != -1) {
				hashCode = possibleDeletedSlot;
			} else if(found) {
				hashCode = totalHash;
			}
		}
		return hashCode;
	}
}
