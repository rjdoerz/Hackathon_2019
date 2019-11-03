package model;

import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class Tile {
	private Coordinate coordinate;
	private Button button;
	private Image image;
	private boolean hasUp, hasDown, hasLeft, hasRight, isWaypoint;
	
	public Tile(Coordinate c) {
		super();
		this.coordinate = c;
		this.button = new Button();
		this.image = null;
		setDefault(false);
	}

	protected void setDefault(boolean b) {
		this.hasUp = b;
		this.hasDown = b;
		this.hasLeft = b;
		this.hasRight = b;
		this.isWaypoint = b;
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate c) {
		this.coordinate = c;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button b) {
		this.button = b;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	
	public boolean hasUp() {
		return hasUp;
	}
	public void setUp(boolean hasUp) {
		this.hasUp = hasUp;
	}
	public void setUp() {
		this.hasUp = !hasUp;
	}
	
	
	public boolean hasDown() {
		return hasDown;
	}
	public void setDown(boolean hasDown) {
		this.hasDown = hasDown;
	}
	public void setDown() {
		this.hasDown = !hasDown;
	}
	
	
	public boolean hasLeft() {
		return hasLeft;
	}
	public void setLeft(boolean hasLeft) {
		this.hasLeft = hasLeft;
	}
	public void setLeft() {
		this.hasLeft = !hasLeft;
	}

	
	public boolean hasRight() {
		return hasRight;
	}
	public void setRight(boolean hasRight) {
		this.hasRight = hasRight;
	}
	public void setRight() {
		this.hasRight = !hasRight;
	}

	
	public boolean isWaypoint() {
		return isWaypoint;
	}
	public void setWaypoint(boolean isWaypoint) {
		this.isWaypoint = isWaypoint;
	}
	public void setWaypoint() {
		this.isWaypoint = !isWaypoint;
	}
	
	
	
}
