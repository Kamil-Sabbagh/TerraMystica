package util;

import tm.MapTM;
import tm.TypeTerrain;

public class Snellman {

	static final String ORIGINAL_MAP = "U,S,G,B,Y,R,U,K,R,G,B,R,K;" + "Y,I,I,U,K,I,I,Y,K,I,I,Y;"
			+ "I,I,K,I,S,I,G,I,G,I,S,I,I;" + "G,B,Y,I,I,R,B,I,R,I,R,U;" + "K,U,R,B,K,U,S,Y,I,I,G,K,B;"
			+ "S,G,I,I,Y,G,I,I,I,U,S,U;" + "I,I,I,S,I,R,I,G,I,Y,K,B,Y;" + "Y,B,U,I,I,I,B,K,I,S,U,S;"
			+ "R,K,S,B,R,G,Y,U,S,I,B,G,R;";

	static final String FIRE_ICE_1_MAP = "U,I,U,K,Y,I,S,G,R,B,Y,B;" + "R,Y,I,B,S,R,I,I,I,Y,U,K,S;"
			+ "G,K,I,I,I,U,G,Y,I,I,I,I;" + "Y,S,G,Y,K,I,B,R,U,I,G,B,G;" + "I,I,U,I,I,R,K,G,S,I,U,K;"
			+ "G,R,I,I,G,I,I,I,U,B,I,S,R;" + "S,I,Y,S,B,R,G,I,R,S,I,K;" + "K,B,I,K,U,S,B,I,Y,K,I,R,B;"
			+ "S,G,I,R,Y,K,Y,I,B,U,I,U;";

	static final String FIRE_ICE_2_MAP = "U,S,G,B,U,R,U,K,R,B,G,R,K;" + "Y,I,I,Y,K,I,I,Y,G,I,I,Y;"
			+ "I,I,K,I,S,I,G,I,K,I,R,I,I;" + "G,B,Y,I,I,R,B,I,R,I,S,U;" + "K,U,R,B,Y,U,G,Y,I,I,G,K,R;"
			+ "S,G,I,I,K,S,I,I,I,U,S,Y;" + "I,I,I,S,I,R,I,G,I,Y,K,B,U;" + "Y,B,U,I,I,I,B,K,I,S,U,R;"
			+ "B,K,S,B,R,G,Y,U,S,I,B,G,S;";

	static final String LOON_LAKES_MAP = "S,B,R,U,Y,B,Y,R,I,I,G,B;" + "Y,K,G,I,I,K,U,I,G,S,I,U,K;"
			+ "U,I,I,G,R,S,I,K,B,R,I,Y;" + "B,R,S,I,Y,U,G,I,I,Y,I,K,R;" + "G,Y,I,K,B,I,I,R,I,S,G,U;"
			+ "S,I,U,S,I,Y,I,S,I,U,K,B,R;" + "R,I,I,I,R,G,U,K,Y,I,I,S;" + "Y,B,K,I,B,S,B,I,I,S,G,I,B;"
			+ "K,U,I,G,I,I,I,G,R,U,Y,K;";

	static final String FJORDS_MAP = "G,K,I,U,Y,S,K,S,Y,R,K,B,Y;" + "B,U,I,B,G,R,I,I,I,I,I,U;"
			+ "S,G,R,I,I,U,I,K,S,U,Y,I,S;" + "I,I,I,S,I,I,G,R,B,G,R,I;" + "R,S,Y,I,B,R,I,U,Y,S,U,I,K;"
			+ "K,U,I,G,Y,G,I,S,B,G,I,S;" + "Y,B,I,K,S,K,B,I,U,K,I,G,R;" + "G,I,U,R,U,Y,R,I,I,I,R,B;"
			+ "K,I,I,G,B,S,B,I,G,Y,K,U,Y;";
	
	static final String LAHC_MAP = "I,Y,U,I,K,U,I,I,G,R,U,I,I;" + "I,R,Y,I,R,I,G,K,B,Y,I,K;"
			+ "S,I,S,R,I,I,K,B,S,R,S,I,R;" + "B,I,B,I,R,I,I,I,G,Y,R,I;" + "K,Y,I,I,G,S,Y,I,B,U,S,G,U;"
			+ "I,I,U,K,U,B,I,R,I,I,U,K;" + "Y,G,I,G,R,K,Y,I,I,K,I,B,G;" + "S,K,I,B,S,U,G,R,G,B,I,S;"
			+ "Y,B,G,I,U,Y,S,U,Y,S,K,I,B;";
	
	public static MapTM getLAHCMap() {
		return convertMap(LAHC_MAP);
	}
	public static MapTM getOriginalMap() {
		return convertMap(ORIGINAL_MAP);
	}

	public static MapTM getFjords() {
		return convertMap(FJORDS_MAP);
	}

	public static MapTM getFireIce2() {
		return convertMap(FIRE_ICE_2_MAP);
	}
	
	public static MapTM getFireIce1() {
		return convertMap(FIRE_ICE_1_MAP);
	}
	
	public static MapTM getLoonLakes() {
		return convertMap(LOON_LAKES_MAP);
	}
	
	public static MapTM convertMap(String string) {
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

	public static TypeTerrain convertLetter(String letter) {
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

	/**
	 * Print out the version to be used with: https://terra.snellman.net/mapedit/#
	 * 
	 * @param riverABCDEF
	 */
	public static String mapAsText(MapTM riverABCDEF) {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 13; c++) {
				if (r % 2 == 1 && c == 12)
					continue;
				switch (riverABCDEF.hexes[r][c]) {
				case BLACK:
					sb.append("K");
					break;
				case BLUE:
					sb.append("B");
					break;
				case BROWN:
					sb.append("U");
					break;
				case GRAY:
					sb.append("S");
					break;
				case GREEN:
					sb.append("G");
					break;
				case RED:
					sb.append("R");
					break;
				case YELLOW:
					sb.append("Y");
					break;
				case RIVER:
					sb.append("I");
					break;
				default:
					break;
				}
				if (r % 2 == 1) {
					if (c < 11)
						sb.append(",");
					else if (c == 11)
						sb.append(",N,\n");
				} else {
					if (c < 12)
						sb.append(",");
					else if (c == 12)
						sb.append(",\n");
				}
			}
		}
		return sb.toString();
	}

}