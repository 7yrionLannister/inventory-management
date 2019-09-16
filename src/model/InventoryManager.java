package model;

import java.security.SecureRandom;
import java.util.ArrayList;


public class InventoryManager {
	private OpenAddressingHashTable<String, Stack<SetOfBlocks>> keyRegistry;
	private OpenAddressingHashTable<String, SetOfBlocks> inventory;
	private Queue<Stack<SetOfBlocks>> quickAccessBars;
	private Queue<Stack<SetOfBlocks>> quickAccessBars2;
	private ArrayList<String> randomlyGeneratedKeys;

	public final static LongTranslator<String> longTranslator = new LongTranslator<String>() {
		/**The method allows to obtain an integer representation of key
		 * @param The String to convert to a natural number<br>Characters in key must be in <b>ASCII{46..122}</b> set
		 * @return An integer representing key
		 * @throws IllegalArgumentException if key contains characters not allowed
		 * */
		@Override
		public long keyToLong(String key) {
			long length = key.length();
			long intKey = 0;
			for (int i = 0; i < length; i++) {
				char chari = key.charAt(i);
				if(chari > 122 || chari < 46) {
					throw new IllegalArgumentException("The key contains an invalid character: " + chari);
				} else {
					intKey += chari*Math.pow(76, length - i - 1);
				}
			}
			return intKey;
		}
	};

	private SecureRandom sr;

	public InventoryManager() {
		keyRegistry = new OpenAddressingHashTable<>(27, longTranslator);
		inventory = new OpenAddressingHashTable<>(27, longTranslator);
		quickAccessBars = new Queue<>();
		quickAccessBars2 = new Queue<>();
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
		Stack<SetOfBlocks>  registryStack = keyRegistry.search(typeOfBlock);
		if(registryStack == null && inventory.getStoredItems() < inventory.getItems().length) {
			//The registry stack for the type typeOfBlock is not in the registry hash table
			//But the inventory hash table does have space to allocate a new type of block
			//So a stack is created, mapped and added to the registry hash table and later
			//to the inventory hash table with the random key
			registryStack = new Stack<SetOfBlocks>();
			keyRegistry.add(typeOfBlock, registryStack);
			quickAccessBars.enqueue(registryStack);
		}
		try {
			if(registryStack != null && inventory.getStoredItems() < inventory.getItems().length) {
				generateKeyAndAdd(typeOfBlock, registryStack, amount);
			}
		} catch(IllegalStateException ise) {
			System.out.println("FULL");
			SetOfBlocks sob = registryStack.pop();
			inventory.remove(sob.getTypeOfBlocks());
		}
	}

	/**The method allows to take blocks out of the current quick access bar and therefore of the inventory
	 * @param amount The number of blocks to consume<br>amount < currentQuickAccessBar.size()
	 * @throws IllegalArgumentException if the requested number of blocks exceeds the number of blocks in the inventory
	 * */
	public void consume(int amount) throws Exception {
		Stack<SetOfBlocks> currentQAB = quickAccessBars.front();
		SetOfBlocks topOfFront = currentQAB.top();
		SetOfBlocks backup = topOfFront;
		int toConsume = Math.min(topOfFront.getBlocks(), amount);
		int remainingToConsume = amount - toConsume;
		topOfFront.consume(toConsume);

		String typeOfBlocks = topOfFront.getTypeOfBlocks();

		if(topOfFront.getBlocks() == 0) {
			topOfFront = currentQAB.pop();
			randomlyGeneratedKeys.remove(typeOfBlocks);
			inventory.remove(typeOfBlocks);
			topOfFront = currentQAB.top();
		}
		if(topOfFront == null) {
			topOfFront = backup;
		}
		if(currentQAB.isEmpty()) {
			//It's not possible to continue consuming
			keyRegistry.remove(typeOfBlocks.substring(0, topOfFront.getTypeOfBlocks().length()-5));
			quickAccessBars.dequeue();
			if(quickAccessBars.isEmpty()) { //exchange the queues if the main of them gets empty
				Queue<Stack<SetOfBlocks>> temp = quickAccessBars;
				quickAccessBars = quickAccessBars2;
				quickAccessBars2 = temp;
			}
			return;
		}

		if(remainingToConsume > 0 && !quickAccessBars.front().isEmpty()) {
			//Consume until everything last or there are not available blocks to consume
			consume(remainingToConsume);
		}
	}

	/**The method allows to add blocks until the requested amount of blocks is added or the inventory fills
	 * @param typeOfBlock The type of blocks to be added
	 * @param blocks The total number of blocks to be added
	 * */
	private void generateKeyAndAdd(String typeOfBlock, Stack<SetOfBlocks> sob, int blocks) {
		String sequence = "";
		for (int i = 0; i < 5; i++) {
			sequence += (char) (sr.nextInt(46)+77);
		}
		String randomKey = typeOfBlock + sequence;
		while(randomlyGeneratedKeys.contains(randomKey)) {
			sequence = "";
			for (int i = 0; i < 5; i++) {
				sequence += (char) (sr.nextInt(46)+77);
			}
			randomKey = typeOfBlock + sequence;
		}
		//TODO descomentar si no funciona o dar ctrl z hasta la muerte
		//randomlyGeneratedKeys.add(randomKey);

		int remaining = blocks;
		if(!sob.isEmpty()) {
			remaining = sob.top().collect(blocks);
		}
		int blocksToBeAdded = Math.min(remaining, SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		remaining -= blocksToBeAdded;
		if(blocksToBeAdded > 0) {
			SetOfBlocks blocksOnTop = new SetOfBlocks(randomKey, blocksToBeAdded);
			sob.push(blocksOnTop);
			inventory.add(randomKey, blocksOnTop);
			randomlyGeneratedKeys.add(randomKey);
		}

		if(remaining < blocks && inventory.getStoredItems() < inventory.getItems().length) {
			//repeat until inventory is full or every block is added
			generateKeyAndAdd(typeOfBlock, sob, remaining);
		} else if (inventory.getStoredItems() == inventory.getItems().length) {
			//Throw the exception to let the user know that the hash table is full
			inventory.add(randomKey, null);
		}
	}

	public OpenAddressingHashTable<String, Stack<SetOfBlocks>> getKeyRegistry() {
		return keyRegistry;
	}

	public void setKeyRegistry(OpenAddressingHashTable<String, Stack<SetOfBlocks>> keyRegistry) {
		this.keyRegistry = keyRegistry;
	}

	public OpenAddressingHashTable<String, SetOfBlocks> getInventory() {
		return inventory;
	}

	public void setInventory(OpenAddressingHashTable<String, SetOfBlocks> inventory) {
		this.inventory = inventory;
	}

	public Queue<Stack<SetOfBlocks>> getQuickAccessBars() {
		return quickAccessBars;
	}

	public void setQuickAccessBars(Queue<Stack<SetOfBlocks>> quickAccessBars) {
		this.quickAccessBars = quickAccessBars;
	}

	public void nextQuickAccessBar() throws Exception {
		Stack<SetOfBlocks> current = quickAccessBars.dequeue(); //temporal to pass elements in the stack to the hash table
		quickAccessBars2.enqueue(current); //to maintain order

		if(quickAccessBars.isEmpty()) { //exchange the queues if the main of them gets empty
			Queue<Stack<SetOfBlocks>> temp = quickAccessBars;
			quickAccessBars = quickAccessBars2;
			quickAccessBars2 = temp;
		}
	}

	public ArrayList<String> getRandomlyGeneratedKeys() {
		return randomlyGeneratedKeys;
	}
}
