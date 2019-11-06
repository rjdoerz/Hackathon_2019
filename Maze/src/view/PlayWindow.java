package view;



import java.io.File;
import java.util.Optional;

import controller.FalseRecursion;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
	private Tile player, lastSpace;
	private Button leftBtn, rightBtn, upBtn, downBtn;
	private Button remap, setBtn;
	private Label space;
	private int size, wPoints, steps;
//	private Random random;
	private TileList tileList;
	private Stage stage;
	private boolean disableBtns = true;
	
	private int btnSize = 40;
	
	public PlayWindow(int size, int wPoints) {
		this.size = size;
		this.wPoints = wPoints;
		initializeNodes();
		drawPane();
		tileList = tileGen.connectWaypoints(wPoints);
		setTileFlags();
		player = tileList.get(2);
		lastSpace = tileList.get(2);
		player.getButton().fire();
		
		steps = 0;
		
		actionCheck();
		
		callbacks();
	}

	private void actionCheck() {
		if(player.hasLeft())
			buttonDisable(leftBtn, false);
		else 
			buttonDisable(leftBtn, true);
		if(player.hasRight())
			buttonDisable(rightBtn, false);
		else 
			buttonDisable(rightBtn, true);
		if(player.hasUp())
			buttonDisable(upBtn, false);
		else 
			buttonDisable(upBtn, true);
		if(player.hasDown())
			buttonDisable(downBtn, false);
		else 
			buttonDisable(downBtn, true);
	}

	private void callbacks() {
		remap.setOnAction(e -> {
//			toggleBtnDisable();
			System.out.println("\n== == ==\n");
			newTiles();
			player = tileList.get(2);
			lastSpace = tileList.get(2);
			player.getButton().fire();
		});
		setBtn.setOnAction(e ->{
			new StartupWindow(stage, size);
		});
		
		//LEFT
		leftBtn.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.A), () ->{
			if(!leftBtn.isDisable()) {
				Tile temp = tileGrid[player.getCoordinate().getRow()][player.getCoordinate().getColumn() - 1];
				if(!temp.isStart())
					player = temp;
				moveAction();
			}
		});
		
		//RIGHT
		rightBtn.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.D), () ->{
			if(!rightBtn.isDisable()) {
				Tile temp = tileGrid[player.getCoordinate().getRow()][player.getCoordinate().getColumn() + 1];
				if(!temp.isStart())
					player = temp;
				moveAction();
			}
		});
		
		//UP
		upBtn.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.W), () ->{
			if(!upBtn.isDisable()) {
				Tile temp = tileGrid[player.getCoordinate().getRow() - 1][player.getCoordinate().getColumn()];
				if(!temp.isStart())
					player = temp;
				moveAction();
			}
		});
		
		//DOWN
		downBtn.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.S), () ->{
			if(!downBtn.isDisable()) {
				Tile temp = tileGrid[player.getCoordinate().getRow() + 1][player.getCoordinate().getColumn()];
				if(!temp.isStart())
					player = temp;
				moveAction();
			}
		});
		
	}
	
	private void moveAction() {
		if(player.isEnd()) {
			ButtonType regen = new ButtonType("Regenerate", ButtonBar.ButtonData.OK_DONE);
			ButtonType change = new ButtonType("Quit", ButtonBar.ButtonData.CANCEL_CLOSE);
			String winner = String.format("CONGRATULATION!!!  A WINNER IS YOU!!!\n\nMove Count: %d", steps);
			Alert alert = new Alert(AlertType.CONFIRMATION, winner, regen, change);
			alert.setHeaderText(null);
			alert.setGraphic(buttonArt(new File("img/trophy.png"), player.getButton()));
			Optional<ButtonType> result = alert.showAndWait();
			steps = 0;
			if(result.orElse(regen) != change) {
				remap.fire();
			} else {
				setBtn.fire();
			}
		} else {
			steps++;
			player.getButton().fire();
			lastSpace.getButton().fire();
			ImageView v = buttonArt(new File("img/trace_path.png"), lastSpace.getButton());
			grid.add(v, lastSpace.getCoordinate().getColumn(), lastSpace.getCoordinate().getRow());
			lastSpace = player;
			actionCheck(); 
		}
	}
	
	private void buttonDisable(Button b, boolean disable) {
		b.setDisable(disable);
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
		grid.add(remap, 0, size + 2);
		GridPane.setHalignment(remap, HPos.CENTER);
		grid.add(setBtn, size - 1, size + 2);
		GridPane.setHalignment(setBtn, HPos.CENTER);
		
		grid.add(leftBtn, 0, 0);
		grid.add(upBtn, 1, 0);
		grid.add(downBtn, 2, 0);
		grid.add(rightBtn, 3, 0);
		
	}

	private void drawTiles() {
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				int row1 = row;
				int col1 = column;
				tileGrid[row][column] = new Tile(new Coordinate(row, column));
				Button b = tileGrid[row][column].getButton();
				b.setMouseTransparent(true);
//				b.setDisable(disableBtns);
				b.setPadding(new Insets(-5));
				b.setMinSize(btnSize, btnSize);
				grid.add(b, column, row);
				ImageView v = buttonArt(new File("img/trace.png"), b);
				
				b.setOnAction(e ->{
					if(grid.getChildren().contains(v))
						grid.getChildren().remove(v);
					else
						grid.add(v, col1, row1);
					
					System.out.println("Selected: (" + row1 + ", " + col1 + ")");
					if(tileList != null) {
						if(tileList.contains(tileGrid[row1][col1])) {
							if(!tileGrid[row1][col1].isStart()) {
								Tile p = tileList.get(tileList.indexOf(tileGrid[row1][col1]) - 1);
								System.out.println("Prev: " + p.getCoordinate());
							}
							if(!tileGrid[row1][col1].isEnd()) {
								Tile n = tileList.get(tileList.indexOf(tileGrid[row1][col1]) + 1);
								System.out.println("Next: " + n.getCoordinate());
							}
						}
					}
					System.out.println(tileGrid[row1][col1].toString());
				});
			}
		}
		tileGen.setTiles(tileGrid);
		tileGen.generateStartEnd();
		tileGen.generateWaypoints(wPoints);
		
	}
	
	public void toggleBtnDisable() {
		disableBtns = !disableBtns;
		for(Tile[] ta : tileGrid) {
			for(Tile t : ta) {
				t.getButton().setDisable(disableBtns);
			}
		}
	}


	private void initializeNodes() {
		tileGen = new TileGenerator();
		
		leftBtn = new Button();
		leftBtn.setVisible(false);
		rightBtn = new Button();
		rightBtn.setVisible(false);
		upBtn = new Button();
		upBtn.setVisible(false);
		downBtn = new Button();
		downBtn.setVisible(false);
		
		remap = new Button();
		remap.setMaxSize(30, 30);
		remap.setMinSize(30, 30);
		remap.setGraphic(buttonArt(new File("img/editIcon.png"), remap));
		
//		remap.setStyle("-fx-focus-color: white;");
//		remap.setStyle("-fx-faint-focus-color: white;");
		
		setBtn = new Button();
		setBtn.setMaxSize(30, 30);
		setBtn.setMinSize(30, 30);
		setBtn.setGraphic(buttonArt(new File("img/gear.png"), setBtn));
		
		space = new Label(" ");
		
		grid = new GridPane();
		grid.setPadding(new Insets(5));
		grid.setHgap(-1.0);
		grid.setVgap(-1.0);
		grid.setAlignment(Pos.CENTER);
		
		tileGrid = new Tile[size][size];
		
		Scene scene = new Scene(grid, (size * btnSize), (size * btnSize) + 40);
		
		stage = new Stage();
		stage.setTitle(size + " x " + size + " Grid");
		stage.setScene(scene);
		stage.show();
	}

	private ImageView buttonArt(File file, Button button) {
		ImageView image = new ImageView(new Image(file.toURI().toString()));
		image.fitWidthProperty().bind(button.minWidthProperty());
		image.fitHeightProperty().bind(button.minHeightProperty());
		return image;
	}
}
