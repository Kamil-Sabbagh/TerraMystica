
import tm.GenerateMapTM;
import tm.GraphTM;
import tm.MapTM;
import util.Snellman;
import util.WriteImage;

public class Main {

	public static void main(String[] args) {

		// Obtaining a map object from a textual representation of the map
		MapTM map = Snellman.getOriginalMap();
		// Creating a graph from the map
		GraphTM graph = new GraphTM(map);
		// Evaluating the map
		System.out.printf("Score = %.1f\n", graph.evaluate());

		// Generating a random map (to start optimizing)
		MapTM random_map = GenerateMapTM.generateRandomMapTM();
		// Creating a graph from the map
		GraphTM g2 = new GraphTM(random_map);
		// Evaluating the map
		System.out.printf("Score = %.1f\n", g2.evaluate());

		WriteImage.generatePNG(random_map, "Random map", "output/random-map.PNG");

	}

}