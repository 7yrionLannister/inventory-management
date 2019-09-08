package model;

import blocks.Block;

//Un grupo de bloques tiene la variable de tipo T que indica que va a contener bloques y 
//como hereda de Block<?>, inicialmente en el constructor puede definir si sera Block<Dirt>,
//Block<Diamond>, Block<etc...>. Pero una vez se defina de que van a ser los blocks,
//seran Block<XXX> todos en el set
public class SetOfBlocks<T extends Block<?>> {
	//Aqui adentro habra un Stack<T> que alcanzara para maximo 64 bloques
}
