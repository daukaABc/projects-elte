package model;

public class Coordinate {
	
	private final int x;
	private final int y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public int hashCode() {
		return x + y;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Coordinate)) {
			return false;
		}
		Coordinate c = (Coordinate) o;
		if (c.x != x || c.y != y) {
			return false;
		}
		return true;
	}
	
}
