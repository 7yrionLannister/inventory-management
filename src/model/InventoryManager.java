package model;


//TODO cuidado con esos wildcards ? usense bien, parametricelos a la minima oportunidad
public class InventoryManager {
	private HashTable<?, SetOfBlocks<?>> keyRegistry;
	private HashTable<String, SetOfBlocks<?>> inventory;
	private Queue<Stack<?>> quickAccessBars;
	
	public InventoryManager() {
		keyRegistry = new HashTable<>();
		inventory = new HashTable<>();
		quickAccessBars = new Queue<Stack<?>>();
	}
}
