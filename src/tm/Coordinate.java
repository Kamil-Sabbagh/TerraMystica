package tm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Coordinate {
	public int row;
	public int column;

	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public boolean isBorder() {
		if (row == 0 || row == 8 || column == 0 || column == 12) {
			// border = true;
			return true;
		} else {
			if (row % 2 == 1 && column == 11) {
				// border = true;
				return true;
			} else {
				// border = false;
				return false;
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		Coordinate c = (Coordinate) obj;
		if (c.row == this.row && c.column == this.column)
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		return (this.row + "," + this.column).hashCode();
	}

	/**
	 * Returns the list of all valid coordinates in a map.
	 * 
	 * @return
	 */
	public static ArrayList<Coordinate> allCoordinates() {
		ArrayList<Coordinate> allCoordinates = new ArrayList<Coordinate>();
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 12; column++) {
				allCoordinates.add(new Coordinate(row, column));
			}
			if (row % 2 == 0)
				allCoordinates.add(new Coordinate(row, 12));
		}
		return allCoordinates;
	}

	public Set<Coordinate> neighbourCoordinates() {
		Set<Coordinate> neighbourCoordinates = new HashSet<Coordinate>();

		boolean isShorterRow = (row % 2 != 0);

		if (row > 0 && row < 8 && column > 0 && column < 11) {
			neighbourCoordinates.add(new Coordinate(row, column - 1));
			neighbourCoordinates.add(new Coordinate(row, column + 1));
			neighbourCoordinates.add(new Coordinate(row - 1, column));
			neighbourCoordinates.add(new Coordinate(row + 1, column));
			if (isShorterRow) {
				neighbourCoordinates.add(new Coordinate(row - 1, column + 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column + 1));
			} else {
				neighbourCoordinates.add(new Coordinate(row - 1, column - 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column - 1));
			}
		}

		if (row == 0) {
			if (column != 0) {
				neighbourCoordinates.add(new Coordinate(row, column - 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column - 1));
			}
			if (column != 12) {
				neighbourCoordinates.add(new Coordinate(row, column + 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column));
			}
		}

		if (row == 8) {
			if (column != 0) {
				neighbourCoordinates.add(new Coordinate(row, column - 1));
				neighbourCoordinates.add(new Coordinate(row - 1, column - 1));
			}
			if (column != 12) {
				neighbourCoordinates.add(new Coordinate(row, column + 1));
				neighbourCoordinates.add(new Coordinate(row - 1, column));
			}
		}

		if (column == 0) {
			neighbourCoordinates.add(new Coordinate(row - 1, column));
			neighbourCoordinates.add(new Coordinate(row, column + 1));
			neighbourCoordinates.add(new Coordinate(row + 1, column));
			if (isShorterRow) {
				neighbourCoordinates.add(new Coordinate(row - 1, column + 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column + 1));
			}
		}

		if (column == 12) {
			neighbourCoordinates.add(new Coordinate(row - 1, column - 1));
			neighbourCoordinates.add(new Coordinate(row, column - 1));
			neighbourCoordinates.add(new Coordinate(row + 1, column - 1));
			if (isShorterRow) {
				neighbourCoordinates.add(new Coordinate(row - 1, column + 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column + 1));
			}
		}

		if (column == 11) {
			if (!isShorterRow) {
				neighbourCoordinates.add(new Coordinate(row - 1, column - 1));
				neighbourCoordinates.add(new Coordinate(row - 1, column));
				neighbourCoordinates.add(new Coordinate(row, column - 1));
				neighbourCoordinates.add(new Coordinate(row, column + 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column - 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column));
			} else {
				neighbourCoordinates.add(new Coordinate(row - 1, column));
				neighbourCoordinates.add(new Coordinate(row - 1, column + 1));
				neighbourCoordinates.add(new Coordinate(row, column - 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column));
				neighbourCoordinates.add(new Coordinate(row + 1, column + 1));
			}
		}
		return neighbourCoordinates;
	}

}