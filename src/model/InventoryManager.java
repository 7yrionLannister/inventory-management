package model;

import blocks.*;

public class InventoryManager {
	private HashTable<String, Stack<SetOfBlocks>> keyRegistry;
	private HashTable<String, SetOfBlocks> inventory;
	private Queue<Stack<SetOfBlocks>> quickAccessBars;
	
	public InventoryManager() {
		keyRegistry = new HashTable<>(27);
		inventory = new HashTable<>(27);
		quickAccessBars = new Queue<>();
	}

	/**The method allows to add a set of blocks in a slot with 
	 * available space and the rest(if exist) in another slot of the inventory, 
	 * keeping each slot with blocks of the same type
	 * @param typeOfBlock The type of blocks that will be added to the inventory
	 * @param amount The total number of blocks of the specified type to be inserted
	 * @return The number of blocks that were not added because there was not available 
	 * space in slots containing the specified type of block in the inventory
	 * @throws IllegalArgumentException if amount < 0
	 * */
	public int collect(String typeOfBlock, int amount) {
		//TODO implement me
		return 0;
	}
	
	/**The method allows to take blocks out of the inventory
	 * @param amount The number of blocks to consume
	 * @throws IllegalArgumentException if the requested number of blocks exceeds the number of blocks in the inventory
	 * */
	public void consume(int amount) {
		//TODO implement me
	}
	
	/**The method allows to add a quick access bar<br>
	 * The total number of different quick access bars that can exist is <b>27</b>
	 * @param type The type of blocks this quick access bar will contain
	 * */
	public boolean addQuickAccessBar(String type) {
		//TODO implement me
		return false;
	}
	
	public void removeQuickAccessBar() {
		//TODO implement me and describe me
	}
	
	public HashTable<String, Stack<SetOfBlocks>> getKeyRegistry() {
		return keyRegistry;
	}

	public void setKeyRegistry(HashTable<String, Stack<SetOfBlocks>> keyRegistry) {
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
