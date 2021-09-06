package tm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Plain old Java object (POJO)
 * 
 * @author l.araujo
 *
 */
@SuppressWarnings("serial")
public class MapTM implements Serializable {

	public TypeTerrain[][] hexes = new TypeTerrain[9][13];

	public double score = Double.NaN;

	/**
	 * Just a heap area.
	 */
	public Map<String, String> dictionary = new HashMap<String, String>();

	public MapTM copy() {
		MapTM copy = new MapTM();
		copy.hexes = new TypeTerrain[9][13];
		for (int r = 0; r < 9; r++)
			for (int c = 0; c < 13; c++)
				copy.hexes[r][c] = this.hexes[r][c];

		copy.score = this.score;

		copy.dictionary = new HashMap<String, String>();
		for (String key : this.dictionary.keySet())
			copy.dictionary.put(key, this.dictionary.get(key));

		return copy;
	}

}