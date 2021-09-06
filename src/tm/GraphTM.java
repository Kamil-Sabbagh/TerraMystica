package tm;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import tm.Coordinate;
import tm.NodeHexe;
import tm.TypeTerrain;

public class GraphTM {

	public Hashtable<Coordinate, NodeHexe> hash;

	/**
	 * Generate a graph
	 * 
	 * @param mapTM
	 * @return
	 */
	public GraphTM(MapTM mapTM) {
		hash = new Hashtable<Coordinate, NodeHexe>();
		for (int row = 0; row <= 8; row++) {
			for (int column = 0; column <= 12; column++) {
				if (row % 2 == 1 && column == 12)
					// this hexe does not exist
					continue;
				// Adding node to the hash table
				NodeHexe n = new NodeHexe(row, column, mapTM.hexes[row][column]);
				hash.put(new Coordinate(row, column), n);
				// Setting neighbours of node n

				boolean isShorterRow = (row % 2 != 0);

				// INTERIOR HEXES
				if (row > 0 && row < 8 && column > 0 && column < 11) {
					n.coordNeighbours.add(new Coordinate(row, column - 1));
					n.coordNeighbours.add(new Coordinate(row, column + 1));
					n.coordNeighbours.add(new Coordinate(row - 1, column));
					n.coordNeighbours.add(new Coordinate(row + 1, column));
					if (isShorterRow) {
						n.coordNeighbours.add(new Coordinate(row - 1, column + 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column + 1));
					} else {
						n.coordNeighbours.add(new Coordinate(row - 1, column - 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column - 1));
					}
					continue;
				}

				if (row == 0) {
					if (column != 0) {
						n.coordNeighbours.add(new Coordinate(row, column - 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column - 1));
					}
					if (column != 12) {
						n.coordNeighbours.add(new Coordinate(row, column + 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column));
					}
					continue;
				}

				if (row == 8) {
					if (column != 0) {
						n.coordNeighbours.add(new Coordinate(row, column - 1));
						n.coordNeighbours.add(new Coordinate(row - 1, column - 1));
					}
					if (column != 12) {
						n.coordNeighbours.add(new Coordinate(row, column + 1));
						n.coordNeighbours.add(new Coordinate(row - 1, column));
					}
					continue;
				}

				if (column == 0) {
					n.coordNeighbours.add(new Coordinate(row - 1, column));
					n.coordNeighbours.add(new Coordinate(row, column + 1));
					n.coordNeighbours.add(new Coordinate(row + 1, column));
					if (isShorterRow) {
						n.coordNeighbours.add(new Coordinate(row - 1, column + 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column + 1));
					}
					continue;
				}

				if (column == 12) {
					n.coordNeighbours.add(new Coordinate(row - 1, column - 1));
					n.coordNeighbours.add(new Coordinate(row, column - 1));
					n.coordNeighbours.add(new Coordinate(row + 1, column - 1));
					if (isShorterRow) {
						n.coordNeighbours.add(new Coordinate(row - 1, column + 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column + 1));
					}
					continue;
				}

				if (column == 11) {
					if (!isShorterRow) {
						n.coordNeighbours.add(new Coordinate(row - 1, column - 1));
						n.coordNeighbours.add(new Coordinate(row - 1, column));
						n.coordNeighbours.add(new Coordinate(row, column - 1));
						n.coordNeighbours.add(new Coordinate(row, column + 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column - 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column));
					} else {
						n.coordNeighbours.add(new Coordinate(row - 1, column));
						n.coordNeighbours.add(new Coordinate(row - 1, column + 1));
						n.coordNeighbours.add(new Coordinate(row, column - 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column));
						n.coordNeighbours.add(new Coordinate(row + 1, column + 1));
					}
					continue;
				}
			}
		}
	}
	
	public NodeHexe getFirstNode() {
		return hash.get(new Coordinate(0, 0));
	}

	public int DFS2(int length, Coordinate key) {
		this.hash.get(key).processed = true;
		NodeHexe current_node = this.hash.get(key);
		for (Coordinate c : current_node.coordNeighbours) {
			if (this.hash.containsKey(c) && this.hash.get(c).typeTerrain != TypeTerrain.RIVER
					&& this.hash.get(c).processed == false) {
				length++;
				length = this.DFS2(length, c);
			}
		}
		return length;
	}

	public int islandLength() {
		int errors = 0;
		ArrayList<Integer> cc = new ArrayList<>();
		Set<Coordinate> keys = this.hash.keySet();

		for (Coordinate key : keys) {
			if (this.hash.get(key).typeTerrain != TypeTerrain.RIVER && this.hash.get(key).processed == false) {
				int length = 1;
				cc.add(this.DFS2(length, key));
			}
		}
		for (Integer i : cc) {
			if (i < 4) {
				errors++;
			}
		}
		return errors;
	}
	
	private int DFS(int length, Coordinate key) {
		this.hash.get(key).processed = true;
		NodeHexe current_node = this.hash.get(key);
		for (Coordinate c : current_node.coordNeighbours) {
			if (this.hash.containsKey(c) && this.hash.get(c).typeTerrain == TypeTerrain.RIVER
					&& this.hash.get(c).processed == false) {
				length++;
				length = this.DFS(length, c);
			}
		}
		return length;
	}

	public int connectedRivers() {
		int errors = 0;
		ArrayList<Integer> cc = new ArrayList<>();
		Set<Coordinate> keys = this.hash.keySet();

		for (Coordinate key : keys) {
			if (this.hash.get(key).typeTerrain == TypeTerrain.RIVER && this.hash.get(key).processed == false) {
				int length = 1;
				cc.add(this.DFS(length, key));
			}
		}
		if (cc.size() <= 2) {
			errors = 0;
		} else {
			errors = cc.size() - 2;
		}

		return errors;
	}

	public int riverNeighbourhood() {
		int errors = 0;
		Set<Coordinate> keys = this.hash.keySet();

		for (Coordinate coordinate : keys) {
			if (this.hash.get(coordinate).typeTerrain == TypeTerrain.RIVER) {
				int nb_count = 0;
				NodeHexe current_node = this.hash.get(coordinate);
				ArrayList<Coordinate> nbs = new ArrayList<Coordinate>();
				for (Coordinate c : current_node.coordNeighbours) {
					NodeHexe nb_node = this.hash.get(c);
					if (nb_node.typeTerrain == TypeTerrain.RIVER) {
						nbs.add(c);
						nb_count++;
					}
				}
				if (nb_count == 0) {
					errors++;
				}
				if (nb_count == 1 && coordinate.isBorder() == false) {
					errors++;
				}
				if (nb_count == 2 && nbs.get(0).isBorder() == true && nbs.get(1).isBorder() == true) {
					errors++;
				}
				if (nb_count > 3) {
					errors++;
				}
			}
			if (this.hash.get(coordinate).typeTerrain != TypeTerrain.RIVER) {
				int nb_rivers = 0;
				NodeHexe current_node = this.hash.get(coordinate);
				for (Coordinate c : current_node.coordNeighbours) {
					NodeHexe nb_node = this.hash.get(c);
					if (nb_node.typeTerrain == TypeTerrain.RIVER) {
						nb_rivers++;
					}
				}
				if (nb_rivers == current_node.coordNeighbours.size()) {
					errors++;
				}
			}
		}
		return errors;
	}

	public int sameColorNeighbours() {
		int errors = 0;
		Set<Coordinate> keys = this.hash.keySet();

		for (Coordinate key : keys) {
			if (this.hash.get(key).typeTerrain != TypeTerrain.RIVER) {
				NodeHexe current_node = this.hash.get(key);
				for (Coordinate c : current_node.coordNeighbours) {
					NodeHexe nb_node = this.hash.get(c);
					// NONE is not considered. An empty map does not violate any rules
					if (current_node.typeTerrain.compareTo(nb_node.typeTerrain) == 0
							&& current_node.typeTerrain != TypeTerrain.NONE) {
						errors++;
					}
				}
			}
		}
		return errors;
	}
	
	public int oneSpadeNeighbour() {
		int errors = 0;
		Set<Coordinate> keys = this.hash.keySet();

		for (Coordinate currentC : keys) {
			NodeHexe currentN = this.hash.get(currentC);
			if (currentN.typeTerrain == TypeTerrain.RIVER || currentN.typeTerrain == TypeTerrain.NONE)
				continue;

			// From now on, assume that current_node is a land hexe
			int numLandNeighbours = 0;
			boolean found = false;
			for (Coordinate neighbourC : this.hash.get(currentC).coordNeighbours) {
				NodeHexe neighbourN = this.hash.get(neighbourC);
				if (neighbourN.typeTerrain != TypeTerrain.RIVER && neighbourN.typeTerrain != TypeTerrain.NONE)
					numLandNeighbours++;
				if (TypeTerrain.spadeDistance(currentN.typeTerrain, neighbourN.typeTerrain) == 1)
					found = true;
			}
			if (numLandNeighbours >= 3 && !found) {
				errors++;
			}
		}
		return errors;
	}
	
	public double evaluate() {
			// System.out.println(this.sameColorNeighbours());
			int req1 = 1 * this.sameColorNeighbours();
			// System.out.println(this.oneSpadeNeighbour());
			int req2 = 1 * this.oneSpadeNeighbour();
			// System.out.println(this.connectedRivers());
			int req3 = 1 * this.connectedRivers();
			// System.out.println(this.riverNeighbourhood());
			int req4 = 1 * this.riverNeighbourhood();
	
			int errors = req1 + req2 + req3 + req4;
			return 1.0 * errors;
	}
	
}