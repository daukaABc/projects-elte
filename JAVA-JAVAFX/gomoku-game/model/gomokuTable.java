package model;

import java.util.HashSet;
import java.util.Set;

public class gomokuTable extends SleazyGomokuTable {
	
	protected int size;
	private int[][] table;
	protected Set<Coordinate> visited = new HashSet<>();
	protected Coordinate piece;
	protected int numberOfFields;
	
	
	/*public void newGame(int size) {
		this.size = size;
		table = new int[size][size];
		visited.clear();
		piece = new Coordinate(size, size);
		numberOfFields = size * size -1;
	}*/
	
	public int getNumberOfFields(int i, int j) {
		return table[i][j];
	}
	
	public Coordinate getPiece() {
		return piece;
	}
	
	public int getSize() {
		return size;
	}
}
