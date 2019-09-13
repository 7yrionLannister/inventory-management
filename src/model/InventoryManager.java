package model;

import blocks.*;

public class InventoryManager {
	private HashTable<String, SetOfBlocks> keyRegistry;
	private HashTable<String, SetOfBlocks> inventory;
	private Queue<Stack<SetOfBlocks>> quickAccessBars;
	
	public InventoryManager() {
		keyRegistry = new HashTable<>(27);
		inventory = new HashTable<>(27);
		quickAccessBars = new Queue<>();
	}

	public HashTable<String, SetOfBlocks> getKeyRegistry() {
		return keyRegistry;
	}

	public void setKeyRegistry(HashTable<String, SetOfBlocks> keyRegistry) {
		this.keyRegistry = keyRegistry;
	}

	public HashTable<String, SetOfBlocks> getInventory() {
		return inventory;
	}

	public void setInventory(HashTable<String, SetOfBlocks> inventory) {
		this.inventory = inventory;
	}

	public Queue<Stack<SetOfBlocks>> getQuickAccessBars() {
		return quickAccessBars;
	}

	public void setQuickAccessBars(Queue<Stack<SetOfBlocks>> quickAccessBars) {
		this.quickAccessBars = quickAccessBars;
	}
	
	
}
