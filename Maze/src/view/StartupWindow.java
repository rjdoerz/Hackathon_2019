package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartupWindow {
	
	private GridPane grid;
	private Stage stage;
	private Spinner<Integer> sizeSpn;
	private Button okBtn;
	private Label sizeLbl;
	private int defaultSpinnerValue;
	private static int maxSpinnerValue;
	
	private int numOfWaypoints;

	public StartupWindow(Stage stage, int defaultSpinnerValue, int btnSize, int maxSpinnerSize) {
		this.stage = stage;
		this.defaultSpinnerValue = defaultSpinnerValue;
		StartupWindow.maxSpinnerValue = maxSpinnerSize;
		initializeNodes();
		drawPane();
		callbacks(btnSize);
		stage.centerOnScreen();
		stage.show();
	}
	
	public static int getMaxSize() {
		return maxSpinnerValue;
	}
	
	// TAKE VALUE AND USE TO MAKE BUTTON GRID
	private void callbacks(int btnSize) {
		okBtn.setOnAction(e -> {
			String str = sizeSpn.getValue().toString();
			int xyDim = Integer.valueOf(str);
			numOfWaypoints = (int) (xyDim * 0.2);
			stage.close();
			new PlayWindow(xyDim, numOfWaypoints, btnSize);
		});
		okBtn.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), () ->{
			okBtn.fire();
		});
	}

	private void drawPane() {
		grid.setAlignment(Pos.CENTER);
		grid.add(sizeLbl, 0, 0);
		grid.add(sizeSpn, 0, 1);
		GridPane.setHalignment(sizeSpn, HPos.CENTER);
		grid.add(okBtn, 0, 2);
		GridPane.setHalignment(okBtn, HPos.CENTER);
		
		Scene scene = new Scene(grid, 200, 150);
		stage.setTitle("Setup");
		stage.setScene(scene);
	}

	private void initializeNodes() {
		grid = new GridPane();
		grid.setPadding(new Insets(10));
		grid.setVgap(10);
		grid.setHgap(10);
		
		sizeSpn = new Spinner<Integer>(7, maxSpinnerValue, defaultSpinnerValue);
		
		okBtn = new Button("GO!");
		okBtn.setMinHeight(50);
		okBtn.setMinWidth(140);
		
		sizeLbl = new Label("Maze Dimensions:");
	}
}
