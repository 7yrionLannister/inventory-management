package model;

//Un grupo de bloques tiene la variable de tipo T que indica que va a contener bloques T 
public class SetOfBlocks<T> {
	public final static int MAX_AMMOUNT_OF_BLOCKS = 64;

	private int blocks;

	public SetOfBlocks(int b) {
		if(b > MAX_AMMOUNT_OF_BLOCKS || b < 0) {
			throw new IllegalArgumentException("Can't hold the ammount requested");
		} else {
			blocks = b;
		}
	}
	
	//metodos para aniadir n bloques y consumir n bloques
	//verificando 0 <= blocks <= MAX
}
