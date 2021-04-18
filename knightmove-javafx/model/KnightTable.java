package model;

import java.util.Set;

public class KnightTable {
	
	private int size;
	private Set<Coordinate> visited;
	private Coordinate knight;
	
	public void newGame(int size) {
		
	}

	public void step(Coordinate where) throws NotValidStepException {
		
	}
	
	public int getNumberOfFreeField() {
		return size;
		
	}
	
	public Coordinate getKnight() {
		return knight;
		
	}
	
	public int getSize() {
		return size;
		
	}
	
}
