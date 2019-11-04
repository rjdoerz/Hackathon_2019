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
	private final static int ODDS = 2;
	
	public static void falsePathing(Tile[][] tileGrid, Tile t) {
		FalseRecursion.grid = tileGrid;
		
		falsePathing(t);
	}
	
	private static void falsePathing(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		Tile next;
		
		if(checkUp(t) && random.nextInt(ODDS) == 0) {
			t.setUp(true);
			next = grid[thisCol][thisRow-1];
			next.setDown(true);
			falsePathing(next);
		}
		if(checkRight(t) && random.nextInt(ODDS) == 0) {
			t.setRight(true);
			next = grid[thisCol+1][thisRow];
			next.setLeft(true);
			falsePathing(next);
		}
		if(checkDown(t) && random.nextInt(ODDS) == 0) {
			t.setDown(true);
			next = grid[thisCol][thisRow+1];
			next.setUp(true);
			falsePathing(next);
		}
		if(checkLeft(t) && random.nextInt(ODDS) == 0) {
			t.setLeft(true);
			next = grid[thisCol-1][thisRow];
			next.setRight(true);
			falsePathing(next);
		}
		
	}
	
	private static boolean checkUp(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisRow == 0) {
			return false;
		} else if(grid[thisCol][thisRow-1].isEmpty()) {
			return true;
		} else
			return false;
	}
	
	private static boolean checkDown(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisRow == grid.length-1) {
			return false;
		} else if(grid[thisCol][thisRow+1].isEmpty()) {
			return true;
		} else 
			return false;
	}
	
	private static boolean checkLeft(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisCol == 0) {
			return false;
		} else if(grid[thisCol-1][thisRow].isEmpty()) {
			return true;
		} else 
			return false;
	}
	
	private static boolean checkRight(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisCol == grid.length-1) {
			return false;
		} else if(grid[thisCol+1][thisRow].isEmpty()) {
			return true;
		} else 
			return false;
	}
}
