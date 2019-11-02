package model;

public class Coordinate {
	private int row, column;

	public Coordinate() {
		this.row = 0;
		this.column = 0;
	}
	
	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	/*
	 * Thie methid will check if another coordinate is within a specified distance of this coordinate.
	 * False: coordinate is beyond the target distance
	 * True: coordinate is within the target distance
	 */
	public boolean compareCoordinate(Coordinate compared, int distance) {
		boolean inRange = false;
		
		if((Math.abs(compared.getRow() - this.row) <= distance) && (Math.abs(compared.getColumn() - this.column) <= distance)) {
			inRange = true;
		}
		
		return inRange;
	}
	

	@Override
	public String toString() {
		return "(" + row + ", " + column + ")";
	}
	
}
