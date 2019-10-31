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
	

	@Override
	public String toString() {
		return "Coordinate [Row=" + row + ", Column=" + column + "]";
	}
	
}
