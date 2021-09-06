package tm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateMapTM {

	private static final String ORIGINAL_MAP = "U,S,G,B,Y,R,U,K,R,G,B,R,K;" + "Y,I,I,U,K,I,I,Y,K,I,I,Y;"
			+ "I,I,K,I,S,I,G,I,G,I,S,I,I;" + "G,B,Y,I,I,R,B,I,R,I,R,U;" + "K,U,R,B,K,U,S,Y,I,I,G,K,B;"
			+ "S,G,I,I,Y,G,I,I,I,U,S,U;" + "I,I,I,S,I,R,I,G,I,Y,K,B,Y;" + "Y,B,U,I,I,I,B,K,I,S,U,S;"
			+ "R,K,S,B,R,G,Y,U,S,I,B,G,R;";

	private static final String FIRE_ICE_1_MAP = "U,I,U,K,Y,I,S,G,R,B,Y,B;" + "R,Y,I,B,S,R,I,I,I,Y,U,K,S;"
			+ "G,K,I,I,I,U,G,Y,I,I,I,I;" + "Y,S,G,Y,K,I,B,R,U,I,G,B,G;" + "I,I,U,I,I,R,K,G,S,I,U,K;"
			+ "G,R,I,I,G,I,I,I,U,B,I,S,R;" + "S,I,Y,S,B,R,G,I,R,S,I,K;" + "K,B,I,K,U,S,B,I,Y,K,I,R,B;"
			+ "S,G,I,R,Y,K,Y,I,B,U,I,U;";

	private static final String FIRE_ICE_2_MAP = "U,S,G,B,U,R,U,K,R,B,G,R,K;" + "Y,I,I,Y,K,I,I,Y,G,I,I,Y;"
			+ "I,I,K,I,S,I,G,I,K,I,R,I,I;" + "G,B,Y,I,I,R,B,I,R,I,S,U;" + "K,U,R,B,Y,U,G,Y,I,I,G,K,R;"
			+ "S,G,I,I,K,S,I,I,I,U,S,Y;" + "I,I,I,S,I,R,I,G,I,Y,K,B,U;" + "Y,B,U,I,I,I,B,K,I,S,U,R;"
			+ "B,K,S,B,R,G,Y,U,S,I,B,G,S;";

	private static final String LOON_LAKES_MAP = "S,B,R,U,Y,B,Y,R,I,I,G,B;" + "Y,K,G,I,I,K,U,I,G,S,I,U,K;"
			+ "U,I,I,G,R,S,I,K,B,R,I,Y;" + "B,R,S,I,Y,U,G,I,I,Y,I,K,R;" + "G,Y,I,K,B,I,I,R,I,S,G,U;"
			+ "S,I,U,S,I,Y,I,S,I,U,K,B,R;" + "R,I,I,I,R,G,U,K,Y,I,I,S;" + "Y,B,K,I,B,S,B,I,I,S,G,I,B;"
			+ "K,U,I,G,I,I,I,G,R,U,Y,K;";

	private static final String FJORDS_MAP = "G,K,I,U,Y,S,K,S,Y,R,K,B,Y;" + "B,U,I,B,G,R,I,I,I,I,I,U;"
			+ "S,G,R,I,I,U,I,K,S,U,Y,I,S;" + "I,I,I,S,I,I,G,R,B,G,R,I;" + "R,S,Y,I,B,R,I,U,Y,S,U,I,K;"
			+ "K,U,I,G,Y,G,I,S,B,G,I,S;" + "Y,B,I,K,S,K,B,I,U,K,I,G,R;" + "G,I,U,R,U,Y,R,I,I,I,R,B;"
			+ "K,I,I,G,B,S,B,I,G,Y,K,U,Y;";

	public static MapTM getOriginalMap() {
		return convertMap(ORIGINAL_MAP);
	}

	public static MapTM getFjords() {
		return convertMap(FJORDS_MAP);
	}

	public static MapTM getFireIce2() {
		return convertMap(FIRE_ICE_2_MAP);
	}

	private static MapTM convertMap(String string) {
		MapTM map = new MapTM();
		String[] rows = string.split(";", 0);
		for (int r = 0; r < rows.length; r++) {
			String row = rows[r];
			String[] columns = row.split(",", 0);
			for (int c = 0; c < columns.length; c++) {
				String column = columns[c];
				map.hexes[r][c] = convertLetter(column);
			}
		}
		return map;
	}

	private static TypeTerrain convertLetter(String letter) {
		switch (letter) {
		case "K":
			return TypeTerrain.BLACK;
		case "B":
			return TypeTerrain.BLUE;
		case "U":
			return TypeTerrain.BROWN;
		case "S":
			return TypeTerrain.GRAY;
		case "G":
			return TypeTerrain.GREEN;
		case "R":
			return TypeTerrain.RED;
		case "Y":
			return TypeTerrain.YELLOW;
		case "I":
			return TypeTerrain.RIVER;
		default:
			return TypeTerrain.NONE;
		}
	}

	public static MapTM generateRandomMapTM() {
		MapTM map = new MapTM();

		List<TypeTerrain> allTypeTerrain = landTiles();
		allTypeTerrain.addAll(riverTiles());
		Collections.shuffle(allTypeTerrain);

		ArrayList<Coordinate> allCoordinates = Coordinate.allCoordinates();
		for (Coordinate coordinate : allCoordinates) {
			map.hexes[coordinate.row][coordinate.column] = allTypeTerrain.remove(0);
		}

		return map;
	}

	/**
	 * Configuration that distributes just rivers on the map
	 * 
	 * @return
	 */
	public static MapTM generateRandomRiver() {
		MapTM riverMap = new MapTM();
		ArrayList<Coordinate> allCoordinates = Coordinate.allCoordinates();
		Collections.shuffle(allCoordinates);
		for (int i = 0; i < riverTiles().size(); i++) {
			Coordinate coordinate = allCoordinates.get(i);
			riverMap.hexes[coordinate.row][coordinate.column] = TypeTerrain.RIVER;
		}
		return riverMap;
	}

	/**
	 * Given a full map (river and land hexes have been set), generate a new full
	 * map considering that the rivers do NOT change their position.
	 * 
	 * @param river Fixed river hexes
	 * @return Map with the fixed rivers and randomly distributed land terrains
	 */
	public static MapTM generateRandomMapTM(MapTM river) {
		MapTM map = new MapTM();
		// TODO
		return map;
	}

	protected static List<TypeTerrain> landTiles() {
		List<TypeTerrain> listTypeTerrain = new ArrayList<TypeTerrain>();
		for (int i = 0; i < 11; i++) {
			listTypeTerrain.add(TypeTerrain.YELLOW);
			listTypeTerrain.add(TypeTerrain.BROWN);
			listTypeTerrain.add(TypeTerrain.BLACK);
			listTypeTerrain.add(TypeTerrain.BLUE);
			listTypeTerrain.add(TypeTerrain.GREEN);
			listTypeTerrain.add(TypeTerrain.GRAY);
			listTypeTerrain.add(TypeTerrain.RED);
		}
		return listTypeTerrain;
	}

	protected static List<TypeTerrain> riverTiles() {
		List<TypeTerrain> list = new ArrayList<TypeTerrain>();
		for (int i = 0; i < 36; i++)
			list.add(TypeTerrain.RIVER);
		return list;
	}

}