package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.StartupWindow;

public class Maze extends Application {
	
	private int defaultSize = 18;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new StartupWindow(primaryStage, defaultSize);
	}

}
