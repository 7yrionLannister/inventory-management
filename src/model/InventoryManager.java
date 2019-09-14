package model;

import java.security.SecureRandom;
import java.util.ArrayList;

import blocks.*;

public class InventoryManager {
	private HashTable<String, Stack<SetOfBlocks>> keyRegistry;
	private HashTable<String, SetOfBlocks> inventory;
	private Queue<Stack<SetOfBlocks>> quickAccessBars;
	private Stack<SetOfBlocks> currentQuickAccessBar;
	private ArrayList<String> randomlyGeneratedKeys;

	private SecureRandom sr;

	public InventoryManager() {
		keyRegistry = new HashTable<>(27);
		inventory = new HashTable<>(27);
		quickAccessBars = new Queue<>();
		randomlyGeneratedKeys = new ArrayList<>();
		sr = new SecureRandom();
	}

	/**The method allows to add a set of blocks in a slot with 
	 * available space and the rest(if exist) in another slot of the inventory, 
	 * keeping each slot with blocks of the same type
	 * @param typeOfBlock The type of blocks that will be added to the inventory
	 * @param amount The total number of blocks of the specified type to be inserted
	 * space in slots containing the specified type of block in the inventory
	 * @throws IllegalArgumentException if amount < 0
	 * */
	public void collect(String typeOfBlock, int amount) throws Exception {
		int key = StringToNatural(typeOfBlock);
		Stack<SetOfBlocks>  registryStack = keyRegistry.search(key);
		if(registryStack == null && inventory.getStoredItems() < inventory.getItems().length) {
			//The registry stack for the type typeOfBlock is not in the registry hash table
			//But the inventory hash table does have space to allocate a new type of block
			//So a stack is created, mapped and added to the registry hash table and later
			//to the inventory hash table with the random key
			registryStack = new Stack<SetOfBlocks>();
			keyRegistry.add(key, typeOfBlock, registryStack);
			quickAccessBars.enqueue(registryStack);
		}
	}

	/**The method allows to take blocks out of the current quick access bar and therefore of the inventory
	 * @param amount The number of blocks to consume<br>amount < currentQuickAccessBar.size()
	 * @throws IllegalArgumentException if the requested number of blocks exceeds the number of blocks in the inventory
	 * */
	public void consume(int amount) {
		SetOfBlocks top = currentQuickAccessBar.top();
		int toConsume = Math.min(top.getBlocks(), amount);
		int remainingToConsume = amount - toConsume;
		top.consume(toConsume);
		if(top.getBlocks() == 0) {
			currentQuickAccessBar.pop();
		}
		if(currentQuickAccessBar.isEmpty()) {
			int searchKey = StringToNatural(top.getTypeOfBlocks());
			swapBetweenStacks(keyRegistry.search(searchKey), currentQuickAccessBar);
			try {
				if(currentQuickAccessBar.isEmpty()) {
					quickAccessBars.dequeue();
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(remainingToConsume > 0 && !currentQuickAccessBar.isEmpty()) {
			consume(remainingToConsume);
		}
	}

	public void swapBetweenStacks(Stack<SetOfBlocks> src, Stack<SetOfBlocks> target) {
		if(src == target) {
			throw new IllegalArgumentException("Source and target stacks must be different");
		}
		SetOfBlocks topSrc = src.pop();
		if(src == currentQuickAccessBar) {
			while(!src.isEmpty()) {
				target.push(src.pop());
			}
			target.push(topSrc);
		} else {
			while(target.getSize() < 9 && !src.isEmpty()) {
				target.push(src.pop());
			}
			target.push(topSrc);
		}
	}

	/**The method allows to add blocks until the requested amount of blocks is added or the inventory fills
	 * @param typeOfBlock The type of blocks to be added
	 * @param blocks The total number of blocks to be added
	 * */
	private void generateKeyAndAdd(String typeOfBlock, Stack<SetOfBlocks> sob, int blocks) {
		String sequence = "";
		for (int i = 0; i < 5; i++) {
			sequence += (char) (sr.nextInt(47)+44);
		}
		String randomKey = typeOfBlock + sequence;
		while(randomlyGeneratedKeys.contains(randomKey)) {
			sequence = "";
			for (int i = 0; i < 5; i++) {
				sequence += (char) (sr.nextInt(47)+44);
			}
		}
		randomlyGeneratedKeys.add(randomKey);
		int intKey = StringToNatural(randomKey);

		int blocksToBeAdded = Math.min(blocks, SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		SetOfBlocks blocksOnTop = new SetOfBlocks(randomKey, blocksToBeAdded);
		sob.push(blocksOnTop);
		inventory.add(intKey, randomKey, blocksOnTop);

		if(blocksToBeAdded < blocks && inventory.getStoredItems() < inventory.getItems().length) {
			//repeat until inventory is full or every block is added
			generateKeyAndAdd(typeOfBlock, sob, blocks-blocksToBeAdded);
		}
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

	/**The method allows to obtain an integer representation of key
	 * @param The String to convert to a natural number<br>Characters in key must be in <b>ASCII{46..90}</b> set
	 * @return An integer representing key
	 * @throws IllegalArgumentException if key contains characters not allowed
	 * */
	public int StringToNatural(String key) {
		int length = key.length();
		int intKey = 0;
		for (int i = 0; i < length; i++) {
			char chari = key.charAt(i);
			if(chari > 90 || chari < 46) {
				throw new IllegalArgumentException("The key contains an invalid character: " + chari);
			} else {
				intKey += chari*Math.pow(128, length - i - 1);
			}
		}
		return intKey;
	}
}
