package workbench;

import model.Coordinate;
import model.Tile;

public class ShallowCopyDemo {

	public static void main(String[] args) {
		Tile t = new Tile(new Coordinate(1, 2));
		
		print(t);
		swap(t);
		print(t);
	}

	private static void print(Tile t) {
		System.out.println(t.getCoordinate().toString());
	}

	private static void swap(Tile t) {
		int temp = t.getCoordinate().getRow();
		t.getCoordinate().setRow(t.getCoordinate().getColumn());
		t.getCoordinate().setColumn(temp);
	}

}
