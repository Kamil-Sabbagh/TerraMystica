package tm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import tm.Coordinate;
import tm.GraphTM;
import tm.MapTM;
import tm.TypeTerrain;

public class Solution implements Comparable<Solution> {
	public MapTM map;
	public ArrayList<String> data;

	public Solution(MapTM map) {
		this.map = map.copy();
		this.score = Double.NaN;
		this.data = new ArrayList<String>();
	}

	private double score;
	
	public double getScore(Method fitnessFunction) {

		if (!Double.isNaN(score))
			return score;

		// calculate the score
		this.score = 0;
		MapTM ABCDEF = this.map.copy();
		this.score += getScore(ABCDEF, fitnessFunction);

		return this.score;
	}

	/**
	 * Calculating the score for a single configuration.
	 * 
	 * @param mapConfig
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private double getScore(MapTM mapConfig, Method fitness_function) {
		GraphTM graph = new GraphTM(mapConfig);
		double score = 0;
		try {
			score += (Double) fitness_function.invoke(graph);
		} catch (Exception e) {
			e.printStackTrace();
			return Double.NEGATIVE_INFINITY;
		}
		return score;
	}

	/**
	 * Randomly selecting a tile and then swapping two random hexes.
	 * 
	 * @param sol
	 * @return
	 */
	
	public static void perturb(Coordinate chosen, Coordinate nb, Solution sol) {
		
		int row1 = chosen.row;
		int row2 = nb.row;
		int col1 = chosen.column;
		int col2 = nb.column;
		
		TypeTerrain buff = sol.map.hexes[row1][col1];
		sol.map.hexes[row1][col1] = sol.map.hexes[row2][col2];
		sol.map.hexes[row2][col2] = buff;
	}
	
	public static void perturb(Solution sol) {

		// PERTURBING TILE A:
		// Rows in A: [0-4] Columns: [0-5] or [0-4]
		Random rng = new Random();
		int row1 = rng.nextInt(5);
		int col1;
		if (row1 % 2 == 1) {
			col1 = rng.nextInt(5);
		} else {
			col1 = rng.nextInt(6);
		}
		int row2 = rng.nextInt(5);
		int col2;
		if (row2 % 2 == 1) {
			col2 = rng.nextInt(5);
		} else {
			col2 = rng.nextInt(6);
		}
		TypeTerrain buff = sol.map.hexes[row1][col1];
		sol.map.hexes[row1][col1] = sol.map.hexes[row2][col2];
		sol.map.hexes[row2][col2] = buff;

		// PERTURBING TILE C:
		// Rows in C: [0-4] Columns: [7-12] or [7-11]
		row1 = rng.nextInt(5);
		if (row1 % 2 == 1) {
			col1 = rng.nextInt(5) + 7;
		} else {
			col1 = rng.nextInt(6) + 7;
		}
		row2 = rng.nextInt(5);
		if (row2 % 2 == 1) {
			col2 = rng.nextInt(5) + 7;
		} else {
			col2 = rng.nextInt(6) + 7;
		}
		buff = sol.map.hexes[row1][col1];
		sol.map.hexes[row1][col1] = sol.map.hexes[row2][col2];
		sol.map.hexes[row2][col2] = buff;

		// PERTURBING TILE D:
		// Rows in D: [5-8] Columns: [0-5] or [0-4]
		row1 = rng.nextInt(4) + 5;
		if (row1 % 2 == 1) {
			col1 = rng.nextInt(5);
		} else {
			col1 = rng.nextInt(6);
		}
		row2 = rng.nextInt(4) + 5;
		if (row2 % 2 == 1) {
			col2 = rng.nextInt(5);
		} else {
			col2 = rng.nextInt(6);
		}
		buff = sol.map.hexes[row1][col1];
		sol.map.hexes[row1][col1] = sol.map.hexes[row2][col2];
		sol.map.hexes[row2][col2] = buff;

		// PERTURBING TILE F:
		// Rows in F: [5-8] Columns: [7-12] or [7-11]
		row1 = rng.nextInt(4) + 5;
		if (row1 % 2 == 1) {
			col1 = rng.nextInt(5) + 7;
		} else {
			col1 = rng.nextInt(6) + 7;
		}
		row2 = rng.nextInt(4) + 5;
		if (row2 % 2 == 1) {
			col2 = rng.nextInt(5) + 7;
		} else {
			col2 = rng.nextInt(6) + 7;
		}
		buff = sol.map.hexes[row1][col1];
		sol.map.hexes[row1][col1] = sol.map.hexes[row2][col2];
		sol.map.hexes[row2][col2] = buff;

		// PERTURBING MIDDLE TILES
		row1 = rng.nextInt(9);
		if (row1 % 2 == 0) {
			col1 = 6;
		} else {
			col1 = rng.nextInt(2) + 5;
		}
		row2 = rng.nextInt(9);
		if (row2 % 2 == 0) {
			col2 = 6;
		} else {
			col2 = rng.nextInt(2) + 5;
		}
		buff = sol.map.hexes[row1][col1];
		sol.map.hexes[row1][col1] = sol.map.hexes[row2][col2];
		sol.map.hexes[row2][col2] = buff;
	}

	public static void perturbLands(Solution sol) {

		GraphTM graph = new GraphTM(sol.map.copy());
		Set<Coordinate> keys = graph.hash.keySet();
		ArrayList<Coordinate> acceptable_coordinates = new ArrayList<>();
		for (Coordinate c : keys) {
			if (graph.hash.get(c).typeTerrain != TypeTerrain.RIVER) {
				acceptable_coordinates.add(c);
			}
		}

		// PERTURBING TILE A:
		// Rows in A: [0-4] Columns: [0-5] or [0-4]
		ArrayList<Coordinate> tileA = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				if (i % 2 == 1 && j == 5) {
					continue;
				}
				Coordinate c = new Coordinate(i, j);
				if (acceptable_coordinates.contains(c)) {
					tileA.add(c);
				}
			}
		}

		Collections.shuffle(tileA);

		int row1 = tileA.get(0).row;
		int row2 = tileA.get(1).row;
		int col1 = tileA.get(0).column;
		int col2 = tileA.get(1).column;

		TypeTerrain buff = sol.map.hexes[row1][col1];
		sol.map.hexes[row1][col1] = sol.map.hexes[row2][col2];
		sol.map.hexes[row2][col2] = buff;

		// PERTURBING TILE C:
		// Rows in C: [0-4] Columns: [7-12] or [7-11]
		ArrayList<Coordinate> tileC = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			for (int j = 7; j < 13; j++) {
				if (i % 2 == 1 && j == 12) {
					continue;
				}
				Coordinate c = new Coordinate(i, j);
				if (acceptable_coordinates.contains(c)) {
					tileC.add(c);
				}
			}
		}

		Collections.shuffle(tileC);

		row1 = tileC.get(0).row;
		row2 = tileC.get(1).row;
		col1 = tileC.get(0).column;
		col2 = tileC.get(1).column;

		buff = sol.map.hexes[row1][col1];
		sol.map.hexes[row1][col1] = sol.map.hexes[row2][col2];
		sol.map.hexes[row2][col2] = buff;

		// PERTURBING TILE D:
		// Rows in D: [5-8] Columns: [0-5] or [0-4]
		ArrayList<Coordinate> tileD = new ArrayList<>();

		for (int i = 5; i < 9; i++) {
			for (int j = 0; j < 6; j++) {
				if (i % 2 == 1 && j == 5) {
					continue;
				}
				Coordinate c = new Coordinate(i, j);
				if (acceptable_coordinates.contains(c)) {
					tileD.add(c);
				}
			}
		}

		Collections.shuffle(tileD);

		row1 = tileD.get(0).row;
		row2 = tileD.get(1).row;
		col1 = tileD.get(0).column;
		col2 = tileD.get(1).column;

		buff = sol.map.hexes[row1][col1];
		sol.map.hexes[row1][col1] = sol.map.hexes[row2][col2];
		sol.map.hexes[row2][col2] = buff;

		// PERTURBING TILE F:
		// Rows in F: [5-8] Columns: [6-12] or [6-11]
		ArrayList<Coordinate> tileF = new ArrayList<>();

		for (int i = 5; i < 9; i++) {
			for (int j = 7; j < 13; j++) {
				if (i % 2 == 1 && j == 12) {
					continue;
				}
				Coordinate c = new Coordinate(i, j);
				if (acceptable_coordinates.contains(c)) {
					tileF.add(c);
				}
			}
		}

		Collections.shuffle(tileF);

		row1 = tileF.get(0).row;
		row2 = tileF.get(1).row;
		col1 = tileF.get(0).column;
		col2 = tileF.get(1).column;

		buff = sol.map.hexes[row1][col1];
		sol.map.hexes[row1][col1] = sol.map.hexes[row2][col2];
		sol.map.hexes[row2][col2] = buff;

		// PERTURBING MIDDLE TILES
		ArrayList<Coordinate> tileMid = new ArrayList<>();

		for (int i = 0; i < 9; i++) {
			for (int j = 5; j < 7; j++) {
				if (i % 2 == 0 && j == 5) {
					continue;
				}
				Coordinate c = new Coordinate(i, j);
				if (acceptable_coordinates.contains(c)) {
					tileMid.add(c);
				}
			}
		}

		Collections.shuffle(tileMid);

		row1 = tileMid.get(0).row;
		row2 = tileMid.get(1).row;
		col1 = tileMid.get(0).column;
		col2 = tileMid.get(1).column;

		buff = sol.map.hexes[row1][col1];
		sol.map.hexes[row1][col1] = sol.map.hexes[row2][col2];
		sol.map.hexes[row2][col2] = buff;

	}

	/**
	 * Special perturbation leveraging from knowledge from the domain. Swapping the
	 * two worse hexes from the worse tile.
	 * 
	 * @param sol
	 * @return
	 */
	public static Solution specialPerturb(Solution sol) {
		// TODO Implement the perturb within a tile
		return null;
	}

	@Override
	public int compareTo(Solution arg0) {
		if (this.score > arg0.score) {
			return 1;
		}
		if (this.score < arg0.score) {
			return -1;
		}
		return 0;
	}
}

