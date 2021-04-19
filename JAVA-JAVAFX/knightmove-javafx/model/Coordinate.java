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
		return (x + " " + y).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Coordinate)) {
			return false;
		}
		Coordinate other = (Coordinate) obj;
		return other != null && this.x == other.x && this.y == other.y;	
	}
}
