package view;



import controller.FalseRecursion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Coordinate;
import model.Tile;
import model.TileList;
import utility.TileGenerator;

public class PlayWindow {
	private GridPane grid;
	private TileGenerator tileGen;
	private Tile[][] tileGrid;
//	private Button leftBtn, ritBtn, upBtn, dwnBtn;
	private Button remap;
	private Label space;
	private int size, wPoints;
//	private Random random;
	private TileList tileList;
	
	public PlayWindow(int size, int wPoints) {
		this.size = size;
		this.wPoints = wPoints;
		initializeNodes();
		drawPane();
		callbacks();
		tileList = tileGen.connectWaypoints(wPoints);
		setTileFlags();
	}

	private void callbacks() {
		remap.setOnAction(e -> {
			System.out.println("\n== == ==\n");
			newTiles();
		});
	}

	private void newTiles() {
		grid.getChildren().clear();
		drawPane();
		tileList = tileGen.connectWaypoints(wPoints);
		setTileFlags();
	}


	private void setTileFlags() {
		for(Tile t : tileList) {
			if(t.isStart() || t.isEnd()) {
				t.assignArt();
				continue;
			}
			int index = -1;
			if(t != tileList.getFirst() && t != tileList.getLast()) {
				index = tileList.indexOf(t);
			} else {
				System.err.println("THIS SHOULD NEVER HAPPEN! See: PlayWindow/setTileFlags");
			}
			t.setPathFlags((tileList.get(index-1)), (tileList.get(index+1)));
//			t.assignArt();
		}
		generateFalsePaths();
	}

	private void generateFalsePaths() {
		for(Tile t : tileList) {
			if(t.isStart() || t.isEnd()) {
				t.assignArt();
				continue;
			}
			FalseRecursion.falsePathing(tileGrid, t);
		}
	}

	private void drawPane() {
		drawTiles();
		
		grid.add(space, size/2, size + 1);
		grid.add(remap, size /2, size + 2, 2, 2);
	}

	private void drawTiles() {
		// THESE ARE BACKWARDS. FIX???
//		for (int column = 0; column < size; column++) {
		for (int row = 0; row < size; row++) {
//			for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				int row1 = row;
				int col1 = column;
				tileGrid[column][row] = new Tile(new Coordinate(row, column));
				Button b = tileGrid[column][row].getButton();
				b.setPadding(new Insets(-5));
				b.setMinSize(50, 50);
				grid.add(b, row, column);
				
				b.setOnAction(e ->{
					System.out.println("Clicked: (" + row1 + ", " + col1 + ")");
					if(tileList != null) {
						if(tileList.contains(tileGrid[col1][row1])) {
							Tile p = tileList.get(tileList.indexOf(tileGrid[col1][row1]) - 1);
							Tile n = tileList.get(tileList.indexOf(tileGrid[col1][row1]) + 1);
							System.out.println("Prev: " + p.getCoordinate());
							System.out.println("Next: " + n.getCoordinate());
						}
					}
					System.out.println(tileGrid[col1][row1].toString());
				});
			}
		}
		tileGen.setTiles(tileGrid);
		tileGen.generateStartEnd();
		tileGen.generateWaypoints(wPoints);
		
	}


	private void initializeNodes() {
		tileGen = new TileGenerator();
		
		remap = new Button("Remap");
		remap.setPrefSize(60, 25);
		space = new Label(" ");
		
		grid = new GridPane();
		grid.setPadding(new Insets(5));
		grid.setHgap(-1.0);
		grid.setVgap(-1.0);
		grid.setAlignment(Pos.CENTER);
		
		tileGrid = new Tile[size][size];
		
		Scene scene = new Scene(grid, (size * 50), (size * 50) + 40);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
}
