package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartupWindow {
	
	private GridPane grid;
	private Stage stage;
	private Spinner<Integer> sizeSpn;
	private Button okBtn;
	private Label sizeLbl;
	private int defaultSpinnerValue;

	public StartupWindow(Stage stage, int defaultSpinnerValue) {
		this.stage = stage;
		this.defaultSpinnerValue = defaultSpinnerValue;
		initializeNodes();
		drawPane();
		callbacks();
		stage.centerOnScreen();
		stage.show();
	}

	// TAKE VALUE AND USE TO MAKE BUTTON GRID
	private void callbacks() {
		okBtn.setOnAction(e -> {
			String str = sizeSpn.getValue().toString();
			int xyDim = Integer.valueOf(str);
			stage.close();
			int numOfWaypoints = 3;										// DIFFICULTY SETTING
			new PlayWindow(xyDim, numOfWaypoints);
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
		
		sizeSpn = new Spinner<Integer>(7, 40, defaultSpinnerValue);
		
		okBtn = new Button("GO!");
		okBtn.setMinHeight(50);
		okBtn.setMinWidth(140);
		
		sizeLbl = new Label("Maze Dimensions:");
	}
}
