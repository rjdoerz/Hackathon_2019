package utility;

import model.Tile;
import model.TileList;

public class Printer {
	
	public static void printList(TileList tileList) {
		for(Tile e : tileList) {
			System.out.println(e.getCoordinate());
		}
	}
	
	public static void printCoord(Tile tile) {
		System.out.println(tile.getCoordinate());
	}
	
}
