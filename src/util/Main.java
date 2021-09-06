package util;

import tm.GenerateMapTM;
import tm.GraphTM;
import tm.MapTM;

public class Main {

	public static void main(String[] args) {

		// Obtaining a map object from a textual representation of the map
		MapTM map = Snellman.getOriginalMap();
		// Creating a graph from the map
		GraphTM graph = new GraphTM(map);
		// Evaluating the map
		System.out.printf("Score = %.1f\n", graph.evaluate());
		// Plotting a map
		WriteImage.generatePNG(map, "Original map", "output/original-map.PNG");

		// Generating a random map (to start optimizing)
		MapTM random_map = GenerateMapTM.generateRandomMapTM();
	}

}