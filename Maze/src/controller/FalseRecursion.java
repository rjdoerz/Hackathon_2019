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
	private final static int ODDS = 1;
	
	public static void falsePathing(Tile[][] tileGrid, TileList tileList, Tile t) {
		FalseRecursion.grid = tileGrid;
		FalseRecursion.tileList = tileList;
		
		falsePathing(t);
	}
	
	private static void falsePathing(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(checkUp(t) && random.nextInt(ODDS) == 0) {
			t.setUp(true);
			falsePathing(grid[thisCol][thisRow-1]);
		}
		if(checkRight(t) && random.nextInt(ODDS) == 0) {
			t.setRight(true);
			falsePathing(grid[thisCol+1][thisRow]);
		}
		if(checkDown(t) && random.nextInt(ODDS) == 0) {
			t.setDown(true);
			falsePathing(grid[thisCol][thisRow+1]);
		}
		if(checkLeft(t) && random.nextInt(ODDS) == 0) {
			t.setLeft(true);
			falsePathing(grid[thisCol-1][thisRow]);
		}
		
	}
	
	private static boolean checkUp(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisRow == 0) {
			return false;
		} else if(grid[thisCol][thisRow-1].isEmpty()) {
			return true;
		}
		return false;
	}
	
	private static boolean checkDown(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisRow == grid.length-1) {
			return false;
		} else if(grid[thisCol][thisRow+1].isEmpty()) {
			return true;
		}
		return false;
	}
	
	private static boolean checkLeft(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisCol == 0) {
			return false;
		} else if(grid[thisCol-1][thisRow].isEmpty()) {
			return true;
		}
		return false;
	}
	
	private static boolean checkRight(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisCol == grid.length-1) {
			return false;
		} else if(grid[thisCol+1][thisRow].isEmpty()) {
			return true;
		}
		return false;
	}
}
