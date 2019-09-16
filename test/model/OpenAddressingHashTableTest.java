package model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;


public class OpenAddressingHashTableTest {
	private OpenAddressingHashTable<Integer, Integer> ht;

	private LongTranslator<Integer> it = new LongTranslator<Integer>() {
		@Override
		public long keyToLong(Integer key) {
			return key;
		}
	};

	private void setupStage1() {
		ht = new OpenAddressingHashTable<>(400, it);
	}

	@Test
	public void createHashTableTest() {
		int tableLegth = 10;
		ht = new OpenAddressingHashTable<>(tableLegth, it);
		assertTrue(ht.getStoredItems() == 0 && ht.getItems().length == tableLegth, "The hash table must be initialy empty and the array must have " + tableLegth + " slots");
	}

	@Test
	public void hashFunctionTest() {
		setupStage1();
		for (int i = 0; i < 1000; i++) {
			int h = ht.hashFunction(false, i);
			assertTrue(h >= 0 && h < ht.getItems().length, "The hash function should return valid table slots");
		}

		for(int i = 0; i < ht.getItems().length; i++) {
			int h = ht.hashFunction(false, i);
			assertTrue(h >= 0 && h < ht.getItems().length, "The hash function should return valid table slots: ("+h+")");
			ht.add(i, (int) (Math.random()*80));
		}
	}

	@Test 
	public void addAndSearchTest() {
		setupStage1();
		for(int i = 0; i < ht.getItems().length; i++) {
			int h = ht.hashFunction(false, (i+1));
			assertTrue(h >= 0 && h < ht.getItems().length, "The hash function should return valid table slots: ("+h+")");
			ht.add((i+1), (int) (Math.random()*80));
			assertNotNull(ht.search((i+1)), "The element was just added so it should have been found");
		}
		try {
			ht.add(1, 2);
			fail("There is not possible to add more elements since the hash table is full");
		} catch(IllegalStateException ise) {
			assertTrue(true);
		}
	}

	@Test
	public void removeTest() {
		addAndSearchTest();
		for (int i = 0; i < ht.getItems().length; i++) {
			ht.remove((i+1));
			assertNull(ht.search((i+1)), "The element with key (i+1) was removed so it should not have been found");
		}
	}
}
