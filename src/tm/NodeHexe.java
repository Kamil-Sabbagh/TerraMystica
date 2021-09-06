package tm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This class is used to run graph algorithms to calculate scores of a map
 * 
 * @author jonata
 *
 */
public class NodeHexe {

	// Information for node

	/**
	 * A collection that contains no duplicate elements, in this case, neighbours
	 */
	public HashSet<Coordinate> coordNeighbours = new HashSet<Coordinate>();
	
	public boolean processed = false;

	/**
	 * Color for effects of graph algorithms, not the terrain type
	 */
	public String color;

	// Information for hexes

	public Coordinate coordinate;

	public TypeTerrain typeTerrain;

	/**
	 * Position 0: total score; Position 1: number of colour-colour violations in
	 * which this hexe appears
	 */
	public double[] score;

	/**
	 * Just a heap area.
	 */
	public Map<String, String> dictionary = new HashMap<String, String>();

	public NodeHexe(int row, int column, TypeTerrain typeTerrain) {
		coordinate = new Coordinate(row, column);
		this.typeTerrain = typeTerrain;
	}

}