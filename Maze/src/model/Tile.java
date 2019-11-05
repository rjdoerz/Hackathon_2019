package model;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile {
	private Coordinate coordinate;
	private Button button;
	private Image image;
	private boolean hasUp, hasDown, hasLeft, hasRight, isWaypoint, isStart, isEnd, isEmpty;
	
	private final String IMG_DIR = "img/";
	
	public Tile(Coordinate c) {
		super();
		this.coordinate = c;
		this.button = new Button();
		this.image = null;
		setDefault(false);
		assignArt();
	}

	protected void setDefault(boolean b) {
		this.hasUp = b;
		this.hasDown = b;
		this.hasLeft = b;
		this.hasRight = b;
		this.isWaypoint = b;
		this.isStart = b;
		this.isEnd = b;
		this.isEmpty = !b;
	}
	
	public void setPathFlags(Tile prev, Tile next) {
		int pRow = this.getCoordinate().getRow() - prev.getCoordinate().getRow();
		int pCol = this.getCoordinate().getColumn() - prev.getCoordinate().getColumn();
		
		int nRow = this.getCoordinate().getRow() - next.getCoordinate().getRow();
		int nCol = this.getCoordinate().getColumn() - next.getCoordinate().getColumn();
		
		// PREVIOUS CHECKER
		if(pRow < 0)
			setDown(true);
		else if(pRow > 0)
			setUp(true);
		if(pCol < 0)
			setRight(true);
		else if(pCol > 0)
			setLeft(true);
		
		
		// NEXT CHECKER
		if(nRow < 0)
			setDown(true);
		else if(nRow > 0)
			setUp(true);
		if(nCol < 0)
			setRight(true);
		else if(nCol > 0)
			setLeft(true);
		
	}
	
	public void assignArt() {
		assignArt(false);
	}
	
	public void assignArt(boolean isVert) {
		
		// START
		if(isStart) {
			if(this.getCoordinate().getColumn() == 0)
				applyArt(new File(IMG_DIR + "SideStart.jpg"));
			else
				applyArt(new File(IMG_DIR + "TopStart.jpg"));
		}
		// FINISH
		else if(isEnd) {
			if(!isVert)
				applyArt(new File(IMG_DIR + "SideFinish.jpg"));
			else
				applyArt(new File(IMG_DIR + "BottomFinish.jpg"));
		}
		
		// ALL-WAY
		else if(hasUp == true && hasDown == true && hasLeft == true && hasRight == true) {
			applyArt(new File(IMG_DIR + "AllWay.jpg"));
		} 
		
		// NO-WAY
		else if(hasUp == false && hasDown == false && hasLeft == false && hasRight == false) {
			applyArt(new File(IMG_DIR + "Blank.jpg"));
		} 
		
		// CORNER DOWN-LEFT
		else if(hasUp == false && hasDown == true && hasLeft == true && hasRight == false) {
			applyArt(new File(IMG_DIR + "CnrDownLeft.jpg"));
		} 
		
		// CORNER DOWN-RIGHT
		else if(hasUp == false && hasDown == true && hasLeft == false && hasRight == true) {
			applyArt(new File(IMG_DIR + "CnrDownRight.jpg"));
		} 
		
		// CORNER UP-LEFT
		else if(hasUp == true && hasDown == false && hasLeft == true && hasRight == false) {
			applyArt(new File(IMG_DIR + "CnrUpLeft.jpg"));
		}
		
		// CORNER UP-RIGHT
		else if(hasUp == true && hasDown == false && hasLeft == false && hasRight == true) {
			applyArt(new File(IMG_DIR + "CnrUpRight.jpg"));
		}
		
		// HORIZONTAL
		else if(hasUp == false && hasDown == false && hasLeft == true && hasRight == true) {
			applyArt(new File(IMG_DIR + "Horizontal.jpg"));
		}
		
		// HORIZONTAL DOWN 
		else if(hasUp == false && hasDown == true && hasLeft == true && hasRight == true) {
			applyArt(new File(IMG_DIR + "HorzDown.jpg"));
		}
		
		// HORIZONTAL UP
		else if(hasUp == true && hasDown == false && hasLeft == true && hasRight == true) {
			applyArt(new File(IMG_DIR + "HorzUp.jpg"));
		}
		
		// VERTICAL
		else if(hasUp == true && hasDown == true && hasLeft == false && hasRight == false) {
			applyArt(new File(IMG_DIR + "Vertical.jpg"));
		}
		
		// VERTICAL LEFT 
		else if(hasUp == true && hasDown == true && hasLeft == true && hasRight == false) {
			applyArt(new File(IMG_DIR + "VertLeft.jpg"));
		}
		
		// VERTICAL RIGHT
		else if(hasUp == true && hasDown == true && hasLeft == false && hasRight == true) {
			applyArt(new File(IMG_DIR + "VertRight.jpg"));
		}
		
		// END DOWN
		else if(hasUp == false && hasDown == true && hasLeft == false && hasRight == false) {
			applyArt(new File(IMG_DIR + "EndDown.jpg"));
		}
		
		// END LEFT
		else if(hasUp == false && hasDown == false && hasLeft == true && hasRight == false) {
			applyArt(new File(IMG_DIR + "EndLeft.jpg"));
		}
		
		// END RIGHT
		else if(hasUp == false && hasDown == false && hasLeft == false && hasRight == true) {
			applyArt(new File(IMG_DIR + "EndRight.jpg"));
		}
		
		// END UP
		else if(hasUp == true && hasDown == false && hasLeft == false && hasRight == false) {
			applyArt(new File(IMG_DIR + "EndUp.jpg"));
		}
	}
	
	private void applyArt(File file) {
		ImageView image = new ImageView(new Image(file.toURI().toString()));
//		image.setFitWidth(20);
//		image.setFitHeight(20);
		image.fitWidthProperty().bind(this.button.minWidthProperty());
		image.fitHeightProperty().bind(this.button.minHeightProperty());
		this.button.setText("");
		this.button.setGraphic((image));
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
		this.isEmpty = false;
//		assignArt();
	}
	public void toggleUp() {
		this.hasUp = !hasUp;
		this.isEmpty = false;
//		assignArt();
	}
	
	
	public boolean hasDown() {
		return hasDown;
	}
	public void setDown(boolean hasDown) {
		this.hasDown = hasDown;
		this.isEmpty = false;
//		assignArt();
	}
	public void toggleDown() {
		this.hasDown = !hasDown;
		this.isEmpty = false;
//		assignArt();
	}
	
	
	public boolean hasLeft() {
		return hasLeft;
	}
	public void setLeft(boolean hasLeft) {
		this.hasLeft = hasLeft;
		this.isEmpty = false;
//		assignArt();
	}
	public void toggleLeft() {
		this.hasLeft = !hasLeft;
		this.isEmpty = false;
//		assignArt();
	}

	
	public boolean hasRight() {
		return hasRight;
	}
	public void setRight(boolean hasRight) {
		this.hasRight = hasRight;
		this.isEmpty = false;
//		assignArt();
	}
	public void toggleRight() {
		this.hasRight = !hasRight;
		this.isEmpty = false;
//		assignArt();
	}

	
	public boolean isWaypoint() {
		return isWaypoint;
	}
	public void setWaypoint(boolean isWaypoint) {
		this.isWaypoint = isWaypoint;
		this.isEmpty = false;
	}
	public void toggleWaypoint() {
		this.isWaypoint = !isWaypoint;
		this.isEmpty = false;
	}
	
	
	public boolean isStart() {
		return isStart;
	}
	public void setStart(boolean isStart) {
		this.isStart = isStart;
		this.isWaypoint = isStart;
		this.isEmpty = false;
	}
	public void toggleStart() {
		this.isStart = !isStart;
		this.isWaypoint = this.isStart;
		this.isEmpty = false;
	}
	
	public boolean isEnd() {
		return isEnd;
	}
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
		this.isWaypoint = isEnd;
		this.isEmpty = false;
	}
	public void toggleEnd() {
		this.isEnd = !isEnd;
		this.isWaypoint = this.isEnd;
		this.isEmpty = false;
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}
	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	@Override
	public String toString() {
		return "Tile [" + (isEmpty ? " Empty" : "") + (hasUp ? " UP" : "") + (hasDown ? " DOWN" : "") + (hasLeft ? " LEFT" : "") + (hasRight ? " RIGHT" : "")
				+ " ]";
	}
	
	
}
