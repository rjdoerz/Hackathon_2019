package utility;

import model.Tile;
import model.TileList;

public abstract class Printer {
	
	public static void printList(TileList tileList) {
		for(Tile e : tileList) {
			System.out.println(e.getCoordinate());
		}
	}
	
	public static void printCoord(Tile tile) {
		System.out.println(tile.getCoordinate());
	}
	
	@SafeVarargs
	public static <T> void print(T ...items) {
		for(T item : items) {
			if(item instanceof Tile) {
				printCoord((Tile) item);
			} else 
			System.out.println(item);
		}
	}
	
}
