package utility;

import java.util.Random;

import model.Tile;
import model.TileList;

public class TileGenerator {
	
	private Tile[][] tiles;
	private Tile startTile, endTile, wpCheck;
	private Random random;
	private int errCount;
	
	public TileGenerator() {
		this.startTile = null;
		this.endTile = null;
		this.random = new Random();
	}
	
	public TileGenerator(Tile[][] tiles) {
		this.tiles = tiles;
		this.startTile = null;
		this.endTile = null;
		this.random = new Random();
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public Tile[][] generateStartEnd() {
		int size = tiles.length;
		

		/*
		 * The following forces the start and end points to always
		 * be opposite of one another.
		 * 
		 * For randomized starting/ending points, see "code_bkup.txt" in workbench. [Line 12]
		 */
		if(random.nextBoolean() == true) {
			// Start location is on first row.
			startTile = tiles[0][random.nextInt(size - 3 - 3) + 3];
			endTile = tiles[size-1][random.nextInt(size - 3 - 3) + 3];
		} else {
			// Start location is on first column.
			startTile = tiles[random.nextInt(size - 3 - 3) + 3][0];
			endTile = tiles[random.nextInt(size - 3 - 3) + 3][size-1];
		}
		
		startTile.setStart(true);
		endTile.setEnd(true);
		
		// Apply Start and End markers to coordinates.
		tiles[startTile.getCoordinate().getColumn()][startTile.getCoordinate().getRow()].getButton().setStyle("-fx-font-weight: bold;");
		tiles[startTile.getCoordinate().getColumn()][startTile.getCoordinate().getRow()].getButton().setText("S");
		tiles[endTile.getCoordinate().getColumn()][endTile.getCoordinate().getRow()].getButton().setText("E");
		tiles[endTile.getCoordinate().getColumn()][endTile.getCoordinate().getRow()].getButton().setStyle("-fx-font-weight: bold;");
		return tiles;
	}
	
	
	public Tile[][] generateWaypoints(int numOfPoints){
		boolean isRow = checkOrientation(startTile);
		Tile thisTile = startTile;
		System.out.println(isRow ? "Horizontal" : "Vertical");
		
		int min = 1;
		int max;
		// Minus 2 because Starting and Ending columns/rows should not be included in sets.
		int bracket = ((tiles.length - 2) / numOfPoints);
		for(int i = 0; i < numOfPoints; i++) {
			max = (bracket * (i+1));
			if(max == tiles.length)
				max--;
			
			int pointA, pointB;
			
			// Depending on maze orientation, build waypoints in specified direction.
			if(isRow) {
					pointA = (random.nextInt(max - min + 1)) + min;	
					pointB = (random.nextInt(tiles.length));
			} else {
					pointB = (random.nextInt(max - min + 1)) + min;	
					pointA = (random.nextInt(tiles.length));
			}
			
			if(tiles[pointB][pointA].getButton().getText().matches("[S][E]")) {
				i--;
				continue;
			} else if (tiles[pointB][pointA].getCoordinate().compareCoordinate(thisTile.getCoordinate(), 2)) {
				i--;
				continue;
			}
			min = max + 1;
			
			thisTile = tiles[pointB][pointA];
			thisTile.setWaypoint(true);
			System.out.print("P" + (i+1) + " ");
			Printer.print(thisTile);
			thisTile.getButton().setText("P" + (i+1));
			thisTile.getButton().setStyle("-fx-font-weight: bold;");
		}
		
		System.out.println("---");
		return tiles;
	}

	private boolean checkOrientation(Tile startTile) {
		// This method identifies the orientation of the tiles based on the starting tile.
		int point;
		point = startTile.getCoordinate().getColumn();
		if(point == 0)
			return false; 	// Orientation in columns
		else
			return true;	// Orientation in rows
	}


	public TileList connectWaypoints(int numOfPoints) {
		TileList solution = new TileList();
		solution.add(startTile);
		
		errCount = 0;
		Tile[] wpTiles = getWaypoints(numOfPoints);	// List of all waypoints
			
		// Print waypoints to console
		System.out.println("ST: " + startTile.getCoordinate());
		for(int i = 0; i < wpTiles.length; i++) {
			System.out.println("WP " + (i+1) + ": " + wpTiles[i].getCoordinate().toString());
		}
		System.out.println("ED: " + endTile.getCoordinate() + "\n");
		startTile.setWaypoint(true);
		endTile.setWaypoint(true);
		Tile thisTile = startTile;	
		// Step counter
		int c = 1;
		
		System.out.println("Path Trace Start: " + thisTile.getCoordinate());
		for(int i = 0; i <= wpTiles.length;) {
			Tile wpTile = null;
			// wpTile = next Waypoint
			
			// If all Waypoints have been used, go to the endTile
			if(i == wpTiles.length) {
				wpTile = endTile;
			} else {
				wpTile = wpTiles[i];
			}
			
			// if thisTile.column = wpTile.column (Column values are equal)
			if(thisTile.getCoordinate().getColumn() == wpTile.getCoordinate().getColumn()) {
				// Move up/down by row
				thisTile = byRow(thisTile, wpTile, c);
				
			// else if thisTile.row = wpTile.row (row values are equal)
			} else if (thisTile.getCoordinate().getRow() == wpTile.getCoordinate().getRow()) {
				// Move left/right by column
				thisTile = byColumn(thisTile, wpTile, c);
				
			// else pick a random direction
			} else {
				boolean direct = random.nextBoolean();
				System.out.println(direct ? "Move row" : "Move column");
				if(direct) { // If true: move row. If false: move col
					thisTile = byRow(thisTile, wpTile, c);
				} else {
					thisTile = byColumn(thisTile, wpTile, c);
				}
			}
			if(thisTile == null) {
				System.err.println("\n *** Path Trace Error! *** \n");
				break;
			}
			errCount = 0;
			// If the new tile is a waypoint (waypoint is found), start looking for next waypoint.
			if(thisTile.isWaypoint()) {
				if((wpCheck == null || wpCheck != thisTile)) {
					wpCheck = thisTile;
					if(wpCheck == endTile) {
						System.out.println("Found: End " + thisTile.getCoordinate());
						System.out.println("Path Trace Complete.");
						break;
					}
					System.out.println("Found: Waypoint " + wpTiles[i].getButton().getText() + " " + thisTile.getCoordinate());
					i++;
				} 
			}
			
			solution.add(thisTile);
			
			// If the button has no text, print the step counter on button
			if(thisTile.getButton().getText().isEmpty()) {
				thisTile.getButton().setText(String.valueOf(c++));
			}
//			else if(thisTile.getButton().getText().contains("E")) {
//				break;
//			}
		}
		solution.add(endTile);
		return solution;
	}

	private Tile byColumn(Tile thisTile, Tile wpTile, int c) {
		int thisColumn = thisTile.getCoordinate().getColumn();
		int wpColumn = wpTile.getCoordinate().getColumn();
		
		if(errCount == 10) {
			return null;
		}
		
		if(thisColumn < wpColumn) {
			if(checkRight(thisTile)) {
				System.out.print(c + ": " + thisTile.getCoordinate() + " Row + ");
				return nextColumn(thisTile);
			} 
			else {
				System.out.println("Trying row...");
				thisTile = byRow(thisTile, wpTile, c);
			}
		} else if (thisColumn > wpColumn) {
			if(checkLeft(thisTile)) {
				System.out.print(c + ": " + thisTile.getCoordinate() + " Row - ");
				return lastColumn(thisTile);
			} 
			else {
				System.out.println("Trying row...");
				thisTile = byRow(thisTile, wpTile, c);
			}
		}
		return thisTile;
	}

	private Tile byRow(Tile thisTile, Tile wpTile, int c) {
		int thisRow = thisTile.getCoordinate().getRow();
		int wpRow = wpTile.getCoordinate().getRow();
		
		if(errCount == 10) {
			return null;
		}
		
		// If this row is less than the wpRow (higher up)
		if(thisRow < wpRow) {
			// Check the tile below
			if(checkDown(thisTile)) {
				System.out.print(c + ": " + thisTile.getCoordinate() + " Column + ");
				return nextRow(thisTile);
			} 
			else {
				System.out.println("Trying column...");
				thisTile = byColumn(thisTile, wpTile, c);
			}
		} else if (thisRow > wpRow) {
			// Check tile above
			if(checkUp(thisTile)) {
				System.out.print(c + ": " + thisTile.getCoordinate() + " Column - ");
				return lastRow(thisTile);
			} 
			else {
				System.out.println("Trying column...");
				thisTile = byColumn(thisTile, wpTile, c);
			}
		}
		return thisTile;
	}


	// Probably don't need return types... since objects are passed by reference. But w/e. >.<
	private Tile nextRow(Tile thisTile) {
		Tile t = tiles[thisTile.getCoordinate().getColumn()][thisTile.getCoordinate().getRow() + 1];
		System.out.println(t.getCoordinate());
		return t;
	}
	private Tile lastRow(Tile thisTile) {
		Tile t = tiles[thisTile.getCoordinate().getColumn()][thisTile.getCoordinate().getRow() - 1];
		System.out.println(t.getCoordinate());
		return t;
	}
	private Tile nextColumn(Tile thisTile) {
		Tile t = tiles[thisTile.getCoordinate().getColumn() + 1][thisTile.getCoordinate().getRow()];
		System.out.println(t.getCoordinate());
		return t;
	}
	private Tile lastColumn(Tile thisTile) {
		Tile t = tiles[thisTile.getCoordinate().getColumn() -1][thisTile.getCoordinate().getRow()];
		System.out.println(t.getCoordinate());
		return t;
	}
	
	/*
	 * Check next points need to be examined. 
	 */

	private boolean checkRight(Tile tile) {
		int row = tile.getCoordinate().getRow();
		int column = tile.getCoordinate().getColumn();
		
		if(column < tiles.length-1) {
			if(tiles[column + 1][row].isWaypoint())
				return true;
//			if(tiles[column + 1][row].getButton().getText().isEmpty()) 
			if(tiles[column + 1][row].isEmpty())
				return true;
		}
		System.out.println("Check failed: RIGHT");
		errCount++;
		return false;
	}
	private boolean checkLeft(Tile tile) {
		int row = tile.getCoordinate().getRow();
		int column = tile.getCoordinate().getColumn();
		
		if(column > 0) {
			if(tiles[column - 1][row].isWaypoint())
				return true;
//			if(tiles[column - 1][row].getButton().getText().isEmpty())
			if(tiles[column - 1][row].isEmpty())
				return true;
		}
		System.out.println("Check failed: LEFT");
		errCount++;
		return false;
	}
	private boolean checkUp(Tile tile) {
		int row = tile.getCoordinate().getRow();
		int column = tile.getCoordinate().getColumn();
		
		if(row > 0) {
			if(tiles[column][row - 1].isWaypoint())
				return true;
//			if(tiles[column][row - 1].getButton().getText().isEmpty()) 
			if(tiles[column][row - 1].isEmpty()) 
				return true;
		}
		System.out.println("Check failed: UP");
		errCount++;
		return false;
	}
	private boolean checkDown(Tile tile) {
		int row = tile.getCoordinate().getRow();
		int column = tile.getCoordinate().getColumn();
		
		if(row < tiles.length-1) {
			if(tiles[column][row + 1].isWaypoint())
				return true;
//			if(tiles[column][row + 1].getButton().getText().isEmpty()) 
			if(tiles[column][row + 1].isEmpty())
				return true;
		}
		System.out.println("Check failed: DOWN");
		errCount++;
		return false;
	}

	private Tile[] getWaypoints(int numOfPoints) {
		Tile[] wpTiles = new Tile[numOfPoints];
		int n = 0;
		
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles.length; j++) {
				Tile t;
				
				if(checkOrientation(startTile)) 
					t = tiles[j][i];
				else
					t = tiles[i][j];
				
				if(t.isWaypoint()) {
					wpTiles[n++] = t;
				}
			}
		}
		return wpTiles;
	}
	
}
