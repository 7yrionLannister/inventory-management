package model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

public class InventoryManagerTest {
	private InventoryManager im;
	
	private void setupStage1() {
		im = new InventoryManager();
	}
	
	@Test
	public void stringToNaturalTest() {
		setupStage1();
		String key = "";
		for (int i = 46; i <= 122; i++) { //append all the valid ASCII characters to the string
			key += (char)i;
		}
		try {
			im.StringToNatural(key);
		} catch(IllegalArgumentException iae) {
			fail("All the characters were valid");
		}
		
		key = "+-[] {}~"; //some of the invalid characters
		try {
			im.StringToNatural(key);
			fail("SOme or all the characters were invalid");
		} catch(IllegalArgumentException iae) {
			assertTrue(true);
		}
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
		System.out.println(registry.search(im.StringToNatural("Dirt"), "Dirt"));
		assertTrue(registry.getStoredItems() == itemsInKeyRegistry, "The amount of elements in here (" + registry.getStoredItems() + ") is not the expected: ("+ itemsInKeyRegistry+")");
		assertTrue(inventory.getStoredItems() == itemsInInventory, "The amount of elements in here (" + inventory.getStoredItems() + ") is not the expected: ("+itemsInInventory+")");
		
		
		blocks = 300;
		im.collect("Andesite", (int)blocks);
		itemsInKeyRegistry++;
		itemsInInventory += Math.ceil(blocks/SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		System.out.println(registry.search(im.StringToNatural("Andesite"), "Andesite"));
		assertTrue(registry.getStoredItems() == itemsInKeyRegistry, "The amount of elements in here (" + registry.getStoredItems() + ") is not the expected: ("+ itemsInKeyRegistry+")");
		assertTrue(inventory.getStoredItems() == itemsInInventory, "The amount of elements in here (" + inventory.getStoredItems() + ") is not the expected: ("+itemsInInventory+")");
		
		blocks = 44;
		im.collect("Andesite", (int)blocks);
		//itemsInKeyRegistry does not change as the stack in the key registry hash table has a setOfBlocks with 44 available spaces
		itemsInInventory += Math.ceil(blocks/SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		System.out.println(registry.search(im.StringToNatural("Andesite"), "Andesite"));
		assertTrue(registry.getStoredItems() == itemsInKeyRegistry, "The amount of elements in here (" + registry.getStoredItems() + ") is not the expected: ("+ itemsInKeyRegistry+")");
		assertTrue(inventory.getStoredItems() == itemsInInventory, "The amount of elements in here (" + inventory.getStoredItems() + ") is not the expected: ("+itemsInInventory+")");
		
		blocks = 768;
		im.collect("Cobblestone", (int)blocks);
		itemsInKeyRegistry++;
		itemsInInventory += Math.ceil(blocks/SetOfBlocks.MAX_AMMOUNT_OF_BLOCKS);
		System.out.println(registry.search(im.StringToNatural("Cobblestone"), "Cobblestone"));
		assertTrue(registry.getStoredItems() == itemsInKeyRegistry, "The amount of elements in here (" + registry.getStoredItems() + ") is not the expected: ("+ itemsInKeyRegistry+")");
		assertTrue(inventory.getStoredItems() == itemsInInventory, "The amount of elements in here (" + inventory.getStoredItems() + ") is not the expected: ("+itemsInInventory+")");
	}
	
	@Test
	public void gameSimulationTest() {
		//TODO implement me and if everything here works, you can make the GUI!!!!!
	}
}
