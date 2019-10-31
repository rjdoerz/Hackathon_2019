package model;

import java.util.LinkedList;

@SuppressWarnings("serial")
public class TileList extends LinkedList<Tile>{

	public boolean find(Coordinate e) {
		for(Tile elm : this) {
			if(elm.getCoordinate().getRow() == e.getRow()) {
				if(elm.getCoordinate().getColumn() == e.getColumn()) {
					return true;
				}
			}
		}
		return false;
	}
}
