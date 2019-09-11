package model;

//Un grupo de bloques tiene la variable de tipo T que indica que va a contener bloques T 
public class SetOfBlocks<T> {
	public final static int MAX_AMMOUNT_OF_BLOCKS = 64;

	private T typeOfBlocks;
	private int blocks;

	/**The constructor allows to create a new SetOfBlocks with the specified
	 * @param blocks The initial number of blocks for this set<br>blocks >= 0 & blocks <= MAX_AMMOUNT_OF_BLOCKS
	 * */
	public SetOfBlocks(int blocks) {
		if(blocks > MAX_AMMOUNT_OF_BLOCKS || blocks < 0) {
			throw new IllegalArgumentException("Can't hold the ammount requested");
		} else {
			this.blocks = blocks;
		}
	}

	/**Returns the amount of blocks stored in this set
	 * @return The total number of blocks inside this set
	 * */
	public int getBlocks() {
		return blocks;
	}
	
	/**Returns the type of blocks this set holds
	 * @return The type T of elements this set holds
	 * */
	public T getTypeOfBlocks() {
		return typeOfBlocks;
	}
	
	/**The method allows to add blocks to the set
	 * @param blocks The number of blocks to add<br>blocks >= 0
	 * @return The number of blocks that were not added because there was not space in the set
	 * @throws IllegalArgumentException if blocks < 0
	 * */
	public int collect(int blocks) {
		if(blocks < 0) {
			throw new IllegalArgumentException("The ammount of blocks to add must be positive");
		}
		int add = MAX_AMMOUNT_OF_BLOCKS - this.blocks;
		blocks += add;
		return blocks - add;
	}
	
	/**The method allows to take blocks out of the set
	 * @param blocks The number of blocks to consume
	 * @throws IllegalArgumentException if the requested number of blocks exceeds the number of blocks in the set
	 * */
	public void consume(int blocks) {
		if(blocks > this.blocks) {
			throw new IllegalArgumentException("There are not enough blocks");
		}
		this.blocks -= blocks;
	}
}
