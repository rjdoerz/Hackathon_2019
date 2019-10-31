package utility;

import java.util.Random;

import model.Tile;

public class TileGenerator {
	
	private Tile[][] tiles;
	private Tile startTile, endTile;
	private Random random;
	
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
		 * The code below randomizes the side of the maze in which the start and end points spawn at.
		 * Each point is independent of one another; meaning the Start point can spawn on either top or left side
		 * while the End point can spawn on either the bottom or right side.
		 * 
		 * While I would prefer this code to stay implemented, using the waypoint system I designed to steer the 
		 * maze from start to end works better if the start and end points are always opposite each other. 
		 * 
		 * Until I can devise a way to create sequential waypoint zones that work with the start and end points being 
		 * completely random, the following code cannot be implemented.
		 */
/*		if(random.nextBoolean() == true) {
			// Start location is on first row.
			startTile = tiles[0][random.nextInt(size - 3 - 3) + 3];
		} else {
			// Start location is on first column.
			startTile = tiles[random.nextInt(size - 3 - 3) + 3][0];
		}
		
		if(random.nextBoolean() == true) {
			// End location is on last row.
			endTile = tiles[size-1][random.nextInt(size - 3 - 3) + 3];
		} else {
			// End location is on last column.
			endTile = tiles[random.nextInt(size - 3 - 3) + 3][size-1];
		}
*/		
		/*
		 * Instead of the above code, the following forces the start and end points to always
		 * be opposite of one another.
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
		
		
		// Apply Start and End markers to coordinates.
		tiles[startTile.getCoordinate().getColumn()][startTile.getCoordinate().getRow()].getButton().setStyle("-fx-font-weight: bold;");
		tiles[startTile.getCoordinate().getColumn()][startTile.getCoordinate().getRow()].getButton().setText("S");
		tiles[endTile.getCoordinate().getColumn()][endTile.getCoordinate().getRow()].getButton().setText("E");
		tiles[endTile.getCoordinate().getColumn()][endTile.getCoordinate().getRow()].getButton().setStyle("-fx-font-weight: bold;");
		return tiles;
	}
	
	
	// THINK OF A WAY TO HONE IN ON END TILE
	public Tile[][] generateWaypoints(int numOfPoints){
		boolean isRow = checkOrientation(startTile);
		Tile thisTile = startTile;
		System.out.println("isRow : " + isRow);
		
		int min = 1;
		int max;
		// Minus 2 because Starting and Ending columns/rows should not be included in sets.
		int bracket = ((tiles.length - 2) / numOfPoints);
		for(int i = 0; i < numOfPoints; i++) {
			max = (bracket * (i+1));
			if(max == tiles.length)
				max--;
			
			int pointA, pointB;
			
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
				System.out.println(1);
				i--;
				continue;
			}
			min = max + 1;
			
			thisTile = tiles[pointB][pointA];
			thisTile.setWaypoint();
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
		// This is used to generate waypoints by placing them within groups of rows/columns between
		// the start and end points.
		int point;
		point = startTile.getCoordinate().getColumn();
		if(point == 0)
			return false; 	// Orientation in rows
		else
			return true;	// Orientation in columns
	}


	public void connectWaypoints(int numOfPoints) {
		Tile[] wpTiles = getWaypoints(numOfPoints);	// List of all waypoints
			
			// Print waypoints to console
			for(int i = 0; i < wpTiles.length; i++) {
				System.out.println("WP " + i + ": " + wpTiles[i].getCoordinate().toString());
			}
		
		Tile thisTile = startTile;	
		// Step counter
		int c = 1;
//		System.out.println(1);
		for(int i = 0; i <= wpTiles.length;) {
			Tile wpTile = null;
			// wpTile = next WP
			if(i == wpTiles.length) {
				wpTile = endTile;
			} else {
				wpTile = wpTiles[i];
			}
//			System.out.println(2);
			
			// if thisTile.column = wpTile.column (Column values are equal)
			if(thisTile.getCoordinate().getColumn() == wpTile.getCoordinate().getColumn()) {
				// Move up/down by row
				thisTile = byRow(thisTile, wpTile, c);
//				System.out.println(3);
				
			// else if thisTile.row = wpTile.row (row values are equal)
			} else if (thisTile.getCoordinate().getRow() == wpTile.getCoordinate().getRow()) {
				// Move left/right by column
				thisTile = byColumn(thisTile, wpTile, c);
//				System.out.println(4);
				
			// else pick a random direction
			} else {
				if(random.nextBoolean()) { // If true: move row. If false: move col
					thisTile = byRow(thisTile, wpTile, c);
//					System.out.println(5);
				} else {
					thisTile = byColumn(thisTile, wpTile, c);
//					System.out.println(6);
				}
			}
			
//			System.out.println(7);
			if(thisTile.isWaypoint()) {
				System.out.println("Waypoint " + wpTiles[i].getButton().getText());
				i++;
//				System.out.println(8);
			}
			if(thisTile.getButton().getText().isEmpty()) {
//				System.out.println(9);
				thisTile.getButton().setText(String.valueOf(c++));
			}else if(thisTile.getButton().getText().contains("E")) {
//				System.out.println(10);
				break;
			}
		}
	}

	private Tile byColumn(Tile thisTile, Tile wpTile, int c) {
		int thisColumn = thisTile.getCoordinate().getColumn();
		int wpColumn = wpTile.getCoordinate().getColumn();
		
		if(thisColumn < wpColumn) {
			if(checkRight(thisTile)) {
				System.out.println(c + " Column +");
				return nextColumn(thisTile);
			}
		} else if (thisColumn > wpColumn) {
			if(checkLeft(thisTile)) {
				System.out.println(c + " Column -");
				return lastColumn(thisTile);
			}
		}
		return thisTile;
	}

	private Tile byRow(Tile thisTile, Tile wpTile, int c) {
		int thisRow = thisTile.getCoordinate().getRow();
		int wpRow = wpTile.getCoordinate().getRow();
		
		// If this row is less than the wpRow (higher up)
		if(thisRow < wpRow) {
			// Check the tile below
			if(checkDown(thisTile)) {
				System.out.println(c + " Row +");
				return nextRow(thisTile);
			}
		} else if (thisRow > wpRow) {
			// Check tile above
			if(checkUp(thisTile)) {
				System.out.println(c + " Row -");
				return lastRow(thisTile);
			}
		}
		return thisTile;
	}

//	private void tryColumn(Tile thisTile, Tile wpTile, int c) {
//		int thisRow = thisTile.getCoordinate().getRow();
//		int thisColumn = thisTile.getCoordinate().getColumn();
//		int wpRow = wpTile.getCoordinate().getRow();
//		int wpColumn = wpTile.getCoordinate().getColumn();
//		
//		if(thisColumn == wpColumn) {
//			if(thisRow > wpRow) {
//				if(checkUp(thisTile)) {
//					System.out.println(c + " Row -");
//					thisTile = lastRow(thisTile);
//				} // else ?
//			} else {
//				if(checkDown(thisTile)) {
//					System.out.println(c + " Row +");
//					thisTile = nextRow(thisTile);
//				} // else ?
//			}
//		} else {
//			if(checkRight(thisTile)) {
//				System.out.println(c + " Column +");
//				thisTile = nextColumn(thisTile);
//			} else if(thisRow < wpRow) {
//				if(checkDown(thisTile)){
//					System.out.println(c + " Row +");
//					thisTile = nextRow(thisTile);
//				}
//			} else if(thisRow > wpRow) {
//				if(checkUp(thisTile)){
//					System.out.println(c + " Row -");
//					thisTile = lastRow(thisTile);
//				}
//			}
//		}
//	}
//	
//	private void tryRow(Tile thisTile, Tile wpTile, int c) {
//		int thisRow = thisTile.getCoordinate().getRow();
//		int thisColumn = thisTile.getCoordinate().getColumn();
//		int wpRow = wpTile.getCoordinate().getRow();
//		int wpColumn = wpTile.getCoordinate().getColumn();
//		if(thisRow == wpRow) {
//			if(thisColumn > wpColumn) {
//				if(checkLeft(thisTile)) {
//					System.out.println(c + " Column -");
//					thisTile = lastColumn(thisTile);
//				}
//			} else {
//				if(checkRight(thisTile)) {
//					System.out.println(c + " Column +");
//					thisTile = nextColumn(thisTile);
//				}
//			}
//		} else {
//			if(checkDown(thisTile)) {
//				System.out.println(c + " Row +");
//				thisTile = nextRow(thisTile);
//			} else if(thisColumn < wpColumn) {
//				if(checkRight(thisTile)) {
//					System.out.println(c + " Column +");
//					thisTile = nextColumn(thisTile);
//				}
//			} else if(thisColumn > wpColumn) {
//				if(checkLeft(thisTile)) {
//					System.out.println(c + " Column -");
//					thisTile = lastColumn(thisTile);
//				}
//			}
//		}
//	}

	// Probably don't need return types... since objects are passed by reference. But w/e. >.<
	private Tile nextRow(Tile thisTile) {
		return tiles[thisTile.getCoordinate().getColumn()][thisTile.getCoordinate().getRow() + 1];
	}
	private Tile lastRow(Tile thisTile) {
		return tiles[thisTile.getCoordinate().getColumn()][thisTile.getCoordinate().getRow() - 1];
	}
	private Tile nextColumn(Tile thisTile) {
		return tiles[thisTile.getCoordinate().getColumn() + 1][thisTile.getCoordinate().getRow()];
	}
	private Tile lastColumn(Tile thisTile) {
		return tiles[thisTile.getCoordinate().getColumn() -1][thisTile.getCoordinate().getRow()];
	}
	
	
	/*
	 * Check next points need to be examined. 
	 */

	private boolean checkRight(Tile tile) {
		int row = tile.getCoordinate().getRow();
		int column = tile.getCoordinate().getColumn();
		
		if(column < tiles.length-1) {
			if(tiles[row][column + 1].isWaypoint())
				return true;
			if(tiles[row][column + 1].getButton().getText().isEmpty()) 
				return true;
		}
		return false;
	}
	private boolean checkLeft(Tile tile) {
		int row = tile.getCoordinate().getRow();
		int column = tile.getCoordinate().getColumn();
		
		if(column > 0) {
			if(tiles[row][column - 1].isWaypoint())
				return true;
			if(tiles[row][column - 1].getButton().getText().isEmpty())
				return true;
		}
		return false;
	}
	private boolean checkUp(Tile tile) {
		int row = tile.getCoordinate().getRow();
		int column = tile.getCoordinate().getColumn();
		
		if(row > 0) {
			if(tiles[row - 1][column].isWaypoint())
				return true;
			if(tiles[row - 1][column].getButton().getText().isEmpty()) 
				return true;
		}
		return false;
	}
	private boolean checkDown(Tile tile) {
		int row = tile.getCoordinate().getRow();
		int column = tile.getCoordinate().getColumn();
		
		if(row < tiles.length-1) {
			if(tiles[row + 1][column].isWaypoint())
				return false;
			if(tiles[row + 1][column].getButton().getText().isEmpty()) 
				return true;
		}
		return false;
	}

	private Tile[] getWaypoints(int numOfPoints) {
		Tile[] wpTiles = new Tile[numOfPoints];
		int i = 0;
		for(Tile[] t : tiles) {
			for(Tile e : t) {
				if(e.isWaypoint()) {
					wpTiles[i++] = e;
				}
			}
		}
		return wpTiles;
	}
}
