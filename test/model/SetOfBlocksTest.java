package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class SetOfBlocksTest {
	private SetOfBlocks sob;
	
	@Test
	public void createSetOfBlocksTest() {
		int blocks = -1;
		String typeOfBlock = "";
		try {
			blocks = 64;
			typeOfBlock = "Dirt";
			sob = new SetOfBlocks(typeOfBlock, blocks);
			assertTrue(sob.getTypeOfBlocks().equals(typeOfBlock), "The type of block is not the requested");
			assertTrue(sob.getBlocks() == blocks, "The number of blocks is not the requested");
		} catch(IllegalArgumentException iae) {
			fail("The arguments were valid");
		}
		
		try {
			blocks = 0;
			typeOfBlock = "Dirt";
			sob = new SetOfBlocks(typeOfBlock, blocks);
			assertTrue(sob.getTypeOfBlocks().equals(typeOfBlock), "The type of block is not the requested");
			assertTrue(sob.getBlocks() == blocks, "The number of blocks is not the requested");
		} catch(IllegalArgumentException iae) {
			fail("The arguments were valid");
		}
		
		try {
			blocks = -22;
			typeOfBlock = "Dirt";
			sob = new SetOfBlocks(typeOfBlock, blocks);
			fail("The arguments were not valid so the constructor should fail");
		} catch(IllegalArgumentException iae) {
			assertTrue(true);
		}
		
		try {
			blocks = 77;
			typeOfBlock = "Dirt";
			sob = new SetOfBlocks(typeOfBlock, blocks);
			fail("The arguments were not valid so the constructor should fail");
		} catch(IllegalArgumentException iae) {
			assertTrue(true);
		}
	}
	
	@Test
	public void collectTest() {
		sob = new SetOfBlocks("Andesite", 0);
		int toAdd = 200;
		int freeSpace = SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS-sob.getBlocks();
		int remainingToCollect = sob.collect(toAdd);
		assertTrue(toAdd - freeSpace == remainingToCollect, "The remaining blocks must be the difference between the blocks to add and the free space before collecting blocks(in this case)");
		assertTrue(sob.getBlocks() == SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS, "The set must be full(in this case)");
		
		sob = new SetOfBlocks("Andesite", 40);
		toAdd = 10;
		remainingToCollect = sob.collect(toAdd);
		assertTrue(remainingToCollect == 0, "There should not be any block to add");
		assertTrue(sob.getBlocks() == 50, "The set does not contain the expected amount of blocks");
		
		sob = new SetOfBlocks("Andesite", 36);
		toAdd = 200;
		freeSpace = SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS-sob.getBlocks();
		remainingToCollect = sob.collect(toAdd);
		assertTrue(toAdd - freeSpace == remainingToCollect, "The set must be full");
		assertTrue(remainingToCollect == toAdd - freeSpace, "The remaining blocks must be the difference between the blocks to add and the free space before collecting blocks(in this case)");
	}
	
	@Test
	public void consumeTest() {
		sob = new SetOfBlocks("Whool", 0);
		int blocksToRemove = 7;
		try {
			sob.consume(blocksToRemove);
			fail("There are not enough blocks to remove "+blocksToRemove+ " blocks");
		} catch(IllegalArgumentException iae) {
			assertTrue(true);
		}
		
		sob = new SetOfBlocks("Whool", 44);
		blocksToRemove = 45;
		try {
			sob.consume(blocksToRemove);
			fail("There are not enough blocks to remove "+blocksToRemove+ " blocks");
		} catch(IllegalArgumentException iae) {
			assertTrue(true);
		}
		
		sob = new SetOfBlocks("Whool", 44);
		blocksToRemove = 40;
		try {
			sob.consume(blocksToRemove);
			assertTrue(true);
		} catch(IllegalArgumentException iae) {
			fail("The "+blocksToRemove+ " blocks should have been removed as there are enough blocks to remove");
		}
	}
}
