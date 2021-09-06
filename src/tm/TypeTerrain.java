package tm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public enum TypeTerrain implements Serializable {
	RIVER, YELLOW, RED, GRAY, GREEN, BLUE, BLACK, BROWN, NONE;

	public static int spadeDistance(TypeTerrain terrain1, TypeTerrain terrain2) {
		// circular buffer - was too lazy to build a 7:7 matrix :D
		TypeTerrain[] terraCircle = { YELLOW, BROWN, BLACK, BLUE, GREEN, GRAY, RED };
		int index1 = Arrays.asList(terraCircle).indexOf(terrain1);
		int index2 = Arrays.asList(terraCircle).indexOf(terrain2);
		int spade = 1; // start with checking whether they are 1 spade apart
		while (true) {
			// get the colours that are necessary spades away from the first argument
			int pos1 = index1 + spade;
			int pos2 = index1 - spade;
			if (pos1 < 0) {
				pos1 = 7 + pos1;
			}
			if (pos2 < 0) {
				pos2 = 7 + pos2;
			}
			if (pos1 > 6) {
				pos1 = pos1 - 7;
			}
			if (pos2 > 6) {
				pos2 = pos2 - 7;
			}
			// if the terrain in the second argument is "spade" spades away, return that
			// number
			if (index2 == pos1 || index2 == pos2) {
				return spade;
				// if not, increase the spade and check again
			} else {
				spade++;
			}
		}
	}
	
	public static ArrayList<TypeTerrain> getOneSpadeNeighbours(TypeTerrain terrain) {
		ArrayList<TypeTerrain> neighbours = new ArrayList<>();
		TypeTerrain[] terraCircle = { YELLOW, BROWN, BLACK, BLUE, GREEN, GRAY, RED };
		int pos1 = Arrays.asList(terraCircle).indexOf(terrain)+1;
		if (pos1 > 6) {
			pos1 = pos1 - 7;
		}
		neighbours.add(terraCircle[pos1]);
		int pos2 = Arrays.asList(terraCircle).indexOf(terrain)-1;
		if (pos2 < 0) {
			pos2 = pos2 + 7;
		}
		neighbours.add(terraCircle[pos2]);
		return neighbours;
	}

}