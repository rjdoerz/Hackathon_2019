package controller;

import java.util.Random;

import model.Tile;

public abstract class FalseRecursion {
	private static Tile[][] grid;
	private static Random random = new Random();
	
	// Increase odds value to LOWER the initial complexity of false paths. (Smaller number = More complex)
	// As each false tile is created, odds value will grow automatically to balance out false pathing.
	/*
	 *  Logic: Random int from 0 to odds. If the generated number is <= branchLimiter, generate false tile.
	 * 
	 */
	private static int odds = 1, branchLimiter = 2;
//	private static int passCount, maxDepth = 10;
	
	public static void falsePathing(Tile[][] tileGrid, Tile t) {
		FalseRecursion.grid = tileGrid;
//		passCount = 0;
		falsePathing(t);
	}
	
	private static void falsePathing(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		Tile next;
		
//		if(passCount++ <= maxDepth) {
		
			if(checkUp(t) && random.nextInt(random.nextInt(odds) + 1) <= branchLimiter) {
				t.setUp(true);
				next = grid[thisRow-1][thisCol];
				next.setDown(true);
				oddsPlus(random.nextBoolean());
				falsePathing(next);
//				oddsMinus(random.nextBoolean());
				oddsMinus(true);
			}
			if(checkRight(t) && random.nextInt(random.nextInt(odds) + 1) <= branchLimiter) {
				t.setRight(true);
				next = grid[thisRow][thisCol+1];
				next.setLeft(true);
				oddsPlus(random.nextBoolean());
				falsePathing(next);
//				oddsMinus(random.nextBoolean());
				oddsMinus(true);
			}
			if(checkDown(t) && random.nextInt(random.nextInt(odds) + 1) <= branchLimiter) {
				t.setDown(true);
				next = grid[thisRow+1][thisCol];
				next.setUp(true);
				oddsPlus(random.nextBoolean());
				falsePathing(next);
//				oddsMinus(random.nextBoolean());
				oddsMinus(true);
			}
			if(checkLeft(t) && random.nextInt(random.nextInt(odds) + 1) <= branchLimiter) {
				t.setLeft(true);
				next = grid[thisRow][thisCol-1];
				next.setRight(true);
				oddsPlus(random.nextBoolean());
				falsePathing(next);
//				oddsMinus(random.nextBoolean());
				oddsMinus(true);
			}
//		}
		
		t.assignArt();
	}
	
	private static void oddsPlus(boolean add) {
		if(add)
			odds++;
	}
	
	private static void oddsMinus(boolean minus) {
		if(minus) {
			if(odds != 1) {
				odds--;
			}
		}
	}
	
	private static boolean checkUp(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisRow == 0) {
			return false;
//		} else if(!tileList.contains(grid[thisRow-1][thisCol])) {
		} else if(grid[thisRow-1][thisCol].isEmpty()){
			return true;
		} else
			return false;
	}
	
	private static boolean checkDown(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisRow == grid.length-1) {
			return false;
//		} else if(!tileList.contains(grid[thisRow+1][thisCol])) {
		} else if(grid[thisRow+1][thisCol].isEmpty()){
			return true;
		} else 
			return false;
	}
	
	private static boolean checkLeft(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisCol == 0) {
			return false;
//		} else if(!tileList.contains(grid[thisRow][thisCol-1])) {
		} else if(grid[thisRow][thisCol-1].isEmpty()){
			return true;
		} else 
			return false;
	}
	
	private static boolean checkRight(Tile t) {
		int thisRow = t.getCoordinate().getRow();
		int thisCol = t.getCoordinate().getColumn();
		
		if(thisCol == grid.length-1) {
			return false;
//		} else if(!tileList.contains(grid[thisRow][thisCol+1])) {
		} else if(grid[thisRow][thisCol+1].isEmpty()){
			return true;
		} else 
			return false;
	}
}
