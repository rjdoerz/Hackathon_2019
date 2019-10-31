package view;

import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Coordinate;
import model.Tile;
import model.TileList;
import utility.TileGenerator;

public class PlayWindow {
	private GridPane grid;
	private TileGenerator tileGen;
	private Tile[][] tiles;
//	private Button leftBtn, ritBtn, upBtn, dwnBtn;
	private Button remap;
	private int size;
//	private Random random;
//	private TileList tileList;
	
	public PlayWindow(int size) {
		this.size = size;
		initializeNodes();
		drawPane();
		callbacks();
		tileGen.connectWaypoints(4);
//		tracePath();
	}

	private void callbacks() {
		remap.setOnAction(e -> {
			newTiles();
		});
	}

	private void newTiles() {
		grid.getChildren().clear();
		drawPane();
	}


	private void drawPane() {
		drawTiles();
		
		grid.add(remap, size /2, size + 1, 2, 2);
	}

	private void drawTiles() {
		for (int column = 0; column < size; column++) {
			for (int row = 0; row < size; row++) {
				int row1 = row;
				int col1 = column;
				tiles[column][row] = new Tile(new Coordinate(row, column));
				Button b = tiles[column][row].getButton();
//				b.setText(row + ", " + column);
//				b.setStyle("-fx-font-size: 10px;");
				b.setMinSize(50, 50);
				grid.add(b, row, column);
				
				b.setOnAction(e ->{
					System.out.println(row1 + ", " + col1);
				});
			}
		}
		tileGen.setTiles(tiles);
		tileGen.generateStartEnd();
		tileGen.generateWaypoints(4);
		
	}


	private void initializeNodes() {
//		tileList = new TileList();
//		random = new Random();
		
		tileGen = new TileGenerator();
		
		remap = new Button("Remap");
		
		grid = new GridPane();
		grid.setPadding(new Insets(10));
		
		tiles = new Tile[size][size];
		
		Scene scene = new Scene(grid, (size * 50) + 20, (size * 50) + 80);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
}
