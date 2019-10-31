package workbench;

import model.Coordinate;

public class Methods {

	private void tracePath() {
		Coordinate thisCoord = new Coordinate();
		thisCoord = startCoord;
		
		int direction = 0;
		int s = 0;
		int x = 0;
		
		int failLoop = 0;
		while(thisCoord != endCoord && failLoop < 10) {
			boolean isX = random.nextBoolean();
			int moves = random.nextInt(4) + 1;
			x++;
			
			if(isX == true) {
				// X direction
				if(random.nextBoolean() == true) {
					// Towards end
					if(startCoord.getRow() <= endCoord.getRow()) {
						// Movement in positive direction
						direction = 1;
					} else {
						// Movement in negative direction
						direction = -1;
					}
				} else {
					// Away from end
					if(startCoord.getRow() <= endCoord.getRow()) {
						// Movement in negative direction
						direction = -1;
					} else {
						// Movement in positive direction
						direction = 1;
					}
				}
			} else {
				// Y direction
				if(random.nextBoolean() == true) {
					// Towards end
					if(startCoord.getColumn() <= endCoord.getColumn()) {
						// Movement in positive direction
						direction = 1;
					} else {
						// Movement in negative direction
						direction = -1;
					}
				} else {
					// Away from end
					if(startCoord.getColumn() <= endCoord.getColumn()) {
						// Movement in negative direction
						direction = -1;
					} else {
						// Movement in positive direction
						direction = 1;
					}
				}
			}
			for(int i = 0; i <= moves; i++) {
				Coordinate newCoord = new Coordinate(thisCoord.getRow(), thisCoord.getColumn());
//				String s = "";
				
				
				if(isX == true) {
					newCoord.setRow(thisCoord.getRow() + direction);
//					if(direction > 0)
//						s = "v";
//					else
//						s = "^";
				} else {
					newCoord.setColumn(thisCoord.getColumn() + direction);
//					if(direction > 0)
//						s = ">";
//					else
//						s = "<";
				}
//				printList();
//				System.out.println(failLoop);
				if(!coordList.find(newCoord)) {
					System.out.println(2);
					if ((newCoord.getRow() > 0 && newCoord.getRow() < size) || (newCoord.getColumn() > 0 && newCoord.getColumn() < size)) {
						System.out.println(3);
						coordList.add(newCoord);
						
						if(matrix[newCoord.getColumn()][newCoord.getRow()].getText().isEmpty())
							matrix[newCoord.getColumn()][newCoord.getRow()].setText(String.valueOf(x) + "-" + String.valueOf(s++));
						thisCoord = newCoord;
						failLoop = 0;
					} else {
						failLoop++;
					}
				}
				else {
					failLoop++;
				}
			}
		}
	}
}
