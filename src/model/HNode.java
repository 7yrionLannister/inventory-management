package model;

public class HNode<K, V> {
	private int intKey;
	private K key;
	private V value;
	
	public HNode(int ik, K key, V value) {
		intKey = ik;
		this.key = key;
		this.value = value;
	}
	
	public V getValue() {
		return value;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}
	
	public void setKey(K key) {
		this.key = key;
	}
	
	public int getIntKey() {
		return intKey;
	}

	@Override
	public String toString() {
		return "("+key+","+value+")";
	}
}
