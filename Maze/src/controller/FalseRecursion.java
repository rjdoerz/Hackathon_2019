package controller;

import java.util.Random;

import model.Tile;
import model.TileList;

public abstract class FalseRecursion {
	private static Tile[][] grid;
	private static TileList tileList;
	private static Random random = new Random();
	
	// Increase ODDS value to LOWER the complexity of false paths. (Smaller number = More complex)
	/*
	 *  Logic: 1 in ODDS chance of opening a false tile. 
	 *  If ODDS = 3, then there is a 1/3 chance of opening a false tile.
	 */
	private static int odds = 1;
	
	public static void falsePathing(Tile[][] tileGrid, Tile t) {
		FalseRecursion.grid = tileGrid;
		
		falsePathing(t);
	}
	
	private static void falsePathing(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		Tile next;
		
		if(random.nextBoolean())
			odds++;
		
		if(checkUp(t) && random.nextInt(random.nextInt(odds) + 1) == 0) {
			t.setUp(true);
			next = grid[thisRow-1][thisCol];
			next.setDown(true);
			falsePathing(next);
			odds = 1;
		}
		if(checkRight(t) && random.nextInt(random.nextInt(odds) + 1) == 0) {
			t.setRight(true);
			next = grid[thisRow][thisCol+1];
			next.setLeft(true);
			falsePathing(next);
			odds = 1;
		}
		if(checkDown(t) && random.nextInt(random.nextInt(odds) + 1) == 0) {
			t.setDown(true);
			next = grid[thisRow+1][thisCol];
			next.setUp(true);
			falsePathing(next);
			odds = 1;
		}
		if(checkLeft(t) && random.nextInt(random.nextInt(odds) + 1) == 0) {
			t.setLeft(true);
			next = grid[thisRow][thisCol-1];
			next.setRight(true);
			falsePathing(next);
			odds = 1;
		}
		t.assignArt();
	}
	
	private static boolean checkUp(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisRow == 0) {
			return false;
		} else if(grid[thisRow-1][thisCol].isEmpty()) {
			return true;
		} else
			return false;
	}
	
	private static boolean checkDown(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisRow == grid.length-1) {
			return false;
		} else if(grid[thisRow+1][thisCol].isEmpty()) {
			return true;
		} else 
			return false;
	}
	
	private static boolean checkLeft(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisCol == 0) {
			return false;
		} else if(grid[thisRow][thisCol-1].isEmpty()) {
			return true;
		} else 
			return false;
	}
	
	private static boolean checkRight(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisCol == grid.length-1) {
			return false;
		} else if(grid[thisRow][thisCol+1].isEmpty()) {
			return true;
		} else 
			return false;
	}
}
