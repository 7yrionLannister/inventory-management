package model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

public class InventoryManagerTest {
	private InventoryManager im;

	private void setupStage1() {
		im = new InventoryManager();
	}

	@Test
	public void collectTest() throws Exception {
		setupStage1();
		OpenAddressingHashTable<String, Stack<SetOfBlocks>> registry = im.getKeyRegistry();
		OpenAddressingHashTable<String, SetOfBlocks> inventory = im.getInventory();
		int itemsInKeyRegistry = 0;
		int itemsInInventory = 0;
		double blocks = 200;
		im.collect("Dirt", (int)blocks);
		itemsInKeyRegistry++;
		itemsInInventory += Math.ceil(blocks/SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		//System.out.println(registry.search("Dirt"));
		assertTrue(registry.getStoredItems() == itemsInKeyRegistry, "The amount of elements in here (" + registry.getStoredItems() + ") is not the expected: ("+ itemsInKeyRegistry+")");
		assertTrue(inventory.getStoredItems() == itemsInInventory, "The amount of elements in here (" + inventory.getStoredItems() + ") is not the expected: ("+itemsInInventory+")");

		blocks = 300;
		im.collect("Andesite", (int)blocks);
		itemsInKeyRegistry++;
		itemsInInventory += Math.ceil(blocks/SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		//System.out.println(registry.search("Andesite"));
		assertTrue(registry.getStoredItems() == itemsInKeyRegistry, "The amount of elements in here (" + registry.getStoredItems() + ") is not the expected: ("+ itemsInKeyRegistry+")");
		assertTrue(inventory.getStoredItems() == itemsInInventory, "The amount of elements in here (" + inventory.getStoredItems() + ") is not the expected: ("+itemsInInventory+")");

		blocks = 44;
		im.collect("Andesite", (int)blocks);
		//itemsInKeyRegistry does not change as the stack in the key registry hash table has a setOfBlocks with 44 available spaces
		itemsInInventory += Math.ceil(blocks/SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		//System.out.println(registry.search("Andesite"));
		assertTrue(registry.getStoredItems() == itemsInKeyRegistry, "The amount of elements in here (" + registry.getStoredItems() + ") is not the expected: ("+ itemsInKeyRegistry+")");
		assertTrue(inventory.getStoredItems() == itemsInInventory, "The amount of elements in here (" + inventory.getStoredItems() + ") is not the expected: ("+itemsInInventory+")");

		blocks = 768;
		im.collect("Cobblestone", (int)blocks);
		itemsInKeyRegistry++;
		itemsInInventory += Math.ceil(blocks/SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		//System.out.println(registry.search("Cobblestone"));
		assertTrue(registry.getStoredItems() == itemsInKeyRegistry, "The amount of elements in here (" + registry.getStoredItems() + ") is not the expected: ("+ itemsInKeyRegistry+")");
		assertTrue(inventory.getStoredItems() == itemsInInventory, "The amount of elements in here (" + inventory.getStoredItems() + ") is not the expected: ("+itemsInInventory+")");

		blocks = 400;
		try {
			im.collect("Bookshelf", (int)blocks);
			fail("The hash table is already full so the last operation must result in an exception");
		} catch(IllegalStateException ise) {
			assertTrue(true);
		}
	}

	@Test
	public void consumeTest() throws Exception {
		collectTest();
		int quickAccessBars = im.getQuickAccessBars().getSize();
		im.consume(200);
		assertTrue(quickAccessBars - 1 == im.getQuickAccessBars().getSize(), "The quick access bars must have been decreased by one as all Dirt was consumed");
		
		quickAccessBars = im.getQuickAccessBars().getSize();
		im.consume(300);
		assertTrue(quickAccessBars == im.getQuickAccessBars().getSize(), "The quick access bars must not have been decreased as there are still available Andesite blocks");
		
		quickAccessBars = im.getQuickAccessBars().getSize();
		im.consume(44);
		assertTrue(quickAccessBars - 1== im.getQuickAccessBars().getSize(), "The quick access bars must have been decreased by one as all Andesite was consumed");
		
		quickAccessBars = im.getQuickAccessBars().getSize();
		im.consume(768);
		assertTrue(quickAccessBars - 1 == im.getQuickAccessBars().getSize(), "The quick access bars must have been decreased by one as all Cobblestone was consumed");
		
		quickAccessBars = im.getQuickAccessBars().getSize();
		im.consume(320);
		assertTrue(quickAccessBars - 1 == im.getQuickAccessBars().getSize(), "The quick access bars must have been decreased by one as all BookShelf was consumed");
	}
	
	@Test
	public void nextQuickAccessBarTest() throws Exception {
		collectTest();
		assertTrue(im.getQuickAccessBars().front().top().getTypeOfBlocks().contains("Dirt"), "Dirt was added first so the first quick access bar must contain dirt");
		for (int i = 1; i < 36; i++) {
			im.nextQuickAccessBar();
			switch(i%4) {
			case 0:
				assertTrue(im.getQuickAccessBars().front().top().getTypeOfBlocks().contains("Dirt"), "This quick access bar was expected to contain dirt");
				break;
			case 1:
				assertTrue(im.getQuickAccessBars().front().top().getTypeOfBlocks().contains("Andesite"), "This quick access bar was expected to contain Andesite");
				break;
			case 2:
				assertTrue(im.getQuickAccessBars().front().top().getTypeOfBlocks().contains("Cobblestone"), "This quick access bar was expected to contain Cobblestone");
				break;
			case 3:
				assertTrue(im.getQuickAccessBars().front().top().getTypeOfBlocks().contains("Bookshelf"), "This quick access bar was expected to contain BookShelf");
				break;
			}
		}
	}
	
	@Test
	public void gameSimulationTest() throws Exception {
		consumeTest();
		
		OpenAddressingHashTable<String, Stack<SetOfBlocks>> registry = im.getKeyRegistry();
		OpenAddressingHashTable<String, SetOfBlocks> inventory = im.getInventory();
		int itemsInKeyRegistry = 0;
		int itemsInInventory = 0;
		double blocks = 200;
		im.collect("Dirt", (int)blocks);
		itemsInKeyRegistry++;
		itemsInInventory += Math.ceil(blocks/SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		//System.out.println(registry.search("Dirt"));
		assertTrue(registry.getStoredItems() == itemsInKeyRegistry, "The amount of elements in here (" + registry.getStoredItems() + ") is not the expected: ("+ itemsInKeyRegistry+")");
		assertTrue(inventory.getStoredItems() == itemsInInventory, "The amount of elements in here (" + inventory.getStoredItems() + ") is not the expected: ("+itemsInInventory+")");

		blocks = 300;
		im.collect("Andesite", (int)blocks);
		itemsInKeyRegistry++;
		itemsInInventory += Math.ceil(blocks/SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		//System.out.println(registry.search("Andesite"));
		assertTrue(registry.getStoredItems() == itemsInKeyRegistry, "The amount of elements in here (" + registry.getStoredItems() + ") is not the expected: ("+ itemsInKeyRegistry+")");
		assertTrue(inventory.getStoredItems() == itemsInInventory, "The amount of elements in here (" + inventory.getStoredItems() + ") is not the expected: ("+itemsInInventory+")");

		blocks = 44;
		im.collect("Andesite", (int)blocks);
		//itemsInKeyRegistry does not change as the stack in the key registry hash table has a setOfBlocks with 44 available spaces
		itemsInInventory += Math.ceil(blocks/SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		//System.out.println(registry.search("Andesite"));
		assertTrue(registry.getStoredItems() == itemsInKeyRegistry, "The amount of elements in here (" + registry.getStoredItems() + ") is not the expected: ("+ itemsInKeyRegistry+")");
		assertTrue(inventory.getStoredItems() == itemsInInventory, "The amount of elements in here (" + inventory.getStoredItems() + ") is not the expected: ("+itemsInInventory+")");

		blocks = 768;
		im.collect("Cobblestone", (int)blocks);
		itemsInKeyRegistry++;
		itemsInInventory += Math.ceil(blocks/SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		//System.out.println(registry.search("Cobblestone"));
		assertTrue(registry.getStoredItems() == itemsInKeyRegistry, "The amount of elements in here (" + registry.getStoredItems() + ") is not the expected: ("+ itemsInKeyRegistry+")");
		assertTrue(inventory.getStoredItems() == itemsInInventory, "The amount of elements in here (" + inventory.getStoredItems() + ") is not the expected: ("+itemsInInventory+")");

		blocks = 400;
		try {
			im.collect("Bookshelf", (int)blocks);
			fail("The hash table is already full so the last operation must result in an exception");
		} catch(IllegalStateException ise) {
			assertTrue(true);
		}
		
		im.nextQuickAccessBar();
		im.nextQuickAccessBar();
		
		System.out.println(im.getQuickAccessBars());
		assertTrue(im.getQuickAccessBars().front().top().getTypeOfBlocks().contains("Cobblestone"), "The current quick access bar must contain Cobblestone");
		blocks = inventory.getStoredItems();
		im.consume(128);
		assertTrue(im.getQuickAccessBars().front().top().getBlocks() == SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS, "Monitoring of the situation indicates that this should be the result");
		assertTrue(blocks - 2 == inventory.getStoredItems(), "Monitoring of the situation indicates that this should be the result");
		
		im.consume(20);
		assertTrue(im.getQuickAccessBars().front().top().getBlocks() == SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS - 20, "Monitoring of the situation indicates that this should be the result");
		
		im.consume(700);
		assertTrue(blocks - 12 == inventory.getStoredItems(), "Monitoring of the situation indicates that this should be the result");
		assertTrue(im.getQuickAccessBars().front().top().getTypeOfBlocks().contains("Bookshelf"), "The current quick access bar must contain Bookshelf");
		
		assertNotNull(registry.search("Dirt"), "Monitoring of the situation indicates that this should be the result");
		assertNotNull(registry.search("Andesite"), "Monitoring of the situation indicates that this should be the result");
		assertNull(registry.search("Cobblestone"), "Monitoring of the situation indicates that this should be the result");
		assertNull(registry.search("Bookshelf"), "Monitoring of the situation indicates that this should be the result");
	}
}
