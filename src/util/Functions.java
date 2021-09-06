package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tm.GraphTM;
import tm.MapTM;
import tm.TypeTerrain;

public class Functions {
	
	public static MapTM rotateA(MapTM map) {
		MapTM map_rotated = map.copy();
		for(int row = 0; row < 5; row ++) {
			boolean isShorterRow = (row % 2 != 0);
			for(int column = 0; column < 6; column ++) {
				if (column == 5 && isShorterRow) {
					continue;
				}
				if (isShorterRow) {
					map_rotated.hexes[row][column] = map.hexes[4-row][4-column];
				}else {
					map_rotated.hexes[row][column] = map.hexes[4-row][5-column];
				}
			}
		}
		return map_rotated;
	}
	
	public static MapTM rotateC(MapTM map) {
		MapTM map_rotated = map.copy();
		for(int row = 0; row < 5; row ++) {
			boolean isShorterRow = (row % 2 != 0);
			int count = 5;
			for(int column = 7; column < 13; column ++) {
				if (column == 12 && isShorterRow) {
					continue;
				}
				if (isShorterRow) {
					map_rotated.hexes[row][column] = map.hexes[4-row][column + count - 1];
					count = count - 2;
				}else {
					map_rotated.hexes[row][column] = map.hexes[4-row][column + count];
					count = count - 2;
				}
			}
		}
		return map_rotated;
	}
	
	public static MapTM rotateD(MapTM map) {
		MapTM map_rotated = map.copy();
		int [] row_conversion = {4, 5, 6, 7, 8};
		for(int row = 4; row < 9; row ++) {
			boolean isShorterRow = (row % 2 == 0);
			for(int column = 0; column < 6; column ++) {
				if (column == 5 && isShorterRow) {
					continue;
				}
				if (isShorterRow) {
					map_rotated.hexes[row][column] = map.hexes[row_conversion[8-row]][4-column];
				}else {
					map_rotated.hexes[row][column] = map.hexes[row_conversion[8-row]][5-column];
				}
			}
		}
		return map_rotated;
	}
	
	public static MapTM rotateF(MapTM map) {
		MapTM map_rotated = map.copy();
		int [] row_conversion = {4, 5, 6, 7, 8};
		for(int row = 4; row < 9; row ++) {
			boolean isShorterRow = (row % 2 == 0);
			int count = 5;
			for(int column = 7; column < 13; column ++) {
				if (column == 12 && isShorterRow) {
					continue;
				}
				if (isShorterRow) {
					map_rotated.hexes[row][column] = map.hexes[row_conversion[8-row]][column + count - 1];
					count = count - 2;
				}else {
					map_rotated.hexes[row][column] = map.hexes[row_conversion[8-row]][column + count];
					count = count - 2;
				}
			}
		}
		return map_rotated;
	}
	
	public static MapTM swapAC(MapTM map, int map_mode) {
		MapTM map_swap = map.copy();
		int row_limit = 0;
		if (map_mode == 0){
			row_limit = 5;
		}else {
			row_limit = 4;
		}
		for(int row = 0; row < row_limit; row++) {
			boolean isShorterRow = false;
			if (map_mode == 0) {
				isShorterRow = (row % 2 != 0);
			}else {
				isShorterRow = (row % 2 == 0);
			}
			for(int column = 0; column < 6; column++) {
				if(column == 5 && isShorterRow) {
					continue;
				}
				map_swap.hexes[row][column] = map.hexes[row][column + 7];
			}
			for(int column = 7; column < 13; column++) {
				if(column == 12 && isShorterRow) {
					continue;
				}
				map_swap.hexes[row][column] = map.hexes[row][column - 7];
			}
		}
		return map_swap;
	}
	
	public static MapTM swapDF(MapTM map, int map_mode) {
		MapTM map_swap = map.copy();
		int row_start = 0;
		if (map_mode == 0){
			row_start = 5;
		}else {
			row_start = 4;
		}
		for(int row = row_start; row < 9; row++) {
			boolean isShorterRow = false;
			if (map_mode == 0) {
				isShorterRow = (row % 2 != 0);
			}else {
				isShorterRow = (row % 2 == 0);
			}
			for(int column = 0; column < 6; column++) {
				if(column == 5 && isShorterRow) {
					continue;
				}
				map_swap.hexes[row][column] = map.hexes[row][column + 7];
			}
			for(int column = 7; column < 13; column++) {
				if(column == 12 && isShorterRow) {
					continue;
				}
				map_swap.hexes[row][column] = map.hexes[row][column - 7];
			}
		}
		return map_swap;
	}
	
	public static <T> List<T> union(List<T> list1, List<T> list2) {
		Set<T> set = new HashSet<T>();

		set.addAll(list1);
		set.addAll(list2);

		return new ArrayList<T>(set);
	}

	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
		List<T> list = new ArrayList<T>();

		for (T t : list1) {
			if (list2.contains(t)) {
				list.add(t);
			}
		}

		return list;
	}

	public static <T> List<T> convertList(Set<T> set) {
		List<T> list = new ArrayList<T>();

		for (T t : set)
			list.add(t);

		return list;
	}

	public static <T> ArrayList<T> firstN(List<T> list, int n) {
		ArrayList<T> firstN = new ArrayList<T>();

		for (int i = 0; i < list.size() && i < n; i++)
			firstN.add(list.get(i));

		return firstN;
	}

	/**
	 * Auxiliary method for generating matrices Colour x Colour.
	 * 
	 * @param terrain
	 * @return
	 */
	public static int convertTerrainToInt(TypeTerrain terrain) {
		switch (terrain) {
		case BLACK:
			return 0;
		case BLUE:
			return 1;
		case GREEN:
			return 2;
		case GRAY:
			return 3;
		case RED:
			return 4;
		case YELLOW:
			return 5;
		case BROWN:
			return 6;
		case RIVER:
			return 7;
		default:
			// TypeTerrain.NONE
			return -1;
		}
	}
	
	public static TypeTerrain convertIntToTerrain(int i) {
		switch (i) {
		case 0:
			return TypeTerrain.BLACK;
		case 1:
			return TypeTerrain.BLUE;
		case 2:
			return TypeTerrain.GREEN;
		case 3:
			return TypeTerrain.GRAY;
		case 4:
			return TypeTerrain.RED;
		case 5:
			return TypeTerrain.YELLOW;
		case 6:
			return TypeTerrain.BROWN;
		case 7:
			return TypeTerrain.RIVER;
		default:
			return TypeTerrain.NONE;
		}
	}

	/**
     * Use Files class from Java 1.7 to write files, internally uses OutputStream
     * @param data
     */
    public static void writeFile(String data, String fileName) {
        try {
            Files.write(Paths.get(fileName), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}