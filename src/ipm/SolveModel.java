package ipm;

import java.util.HashSet;

import ilog.concert.IloException;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import tm.Coordinate;
import tm.GraphTM;
import tm.MapTM;
import tm.TypeTerrain;
import util.WriteImage;

public class SolveModel {
	public static void main(String[] args) {
		// This map help to get the neighbour positions of each hexagon
		MapTM map = new MapTM();
		GraphTM graphTM = new GraphTM(map);

		// Creating the object that encapsulates the obj function and constraints
		IloCplex iloMPModeler = null;
		try {
			iloMPModeler = new IloCplex();
			iloMPModeler.setOut(null);

			// Creating the binary variable:
			// hexagon_2_3_red = 1 ; it means that the hexagon in row 2, column 3 is red.
			IloNumVar[][][] hexagon = new IloNumVar[9][13][9];
			for (int r = 0; r < 9; r++)
				for (int c = 0; c < 13; c++)
					for (int color = 0; color < 8; color++)
						hexagon[r][c][color] = iloMPModeler.boolVar("hex_" + r + "," + c + "," + toTypeTerrain(color));

			// Creating the constraints that each hexagon has only one color
			for (int r = 0; r < 9; r++)
				for (int c = 0; c < 13; c++) {
					IloNumExpr sum = iloMPModeler.constant(0);
					for (int color = 0; color < 8; color++)
						sum = iloMPModeler.sum(sum, hexagon[r][c][color]);
					if (r % 2 == 1 && c == 12)
						// this hexagon does not exist on the map
						iloMPModeler.addEq(sum, 0, "R1.1"); // hexagon has NO color and is not on the map
					else
						iloMPModeler.addEq(sum, 1, "R1.2"); // hexagon has only one color
				}

			// Creating the constraints that there are 11 hexagons of each land color, and
			// 36 river hexagons
			for (int color = 0; color < 8; color++) {
				IloNumExpr sum = iloMPModeler.constant(0);
				for (int r = 0; r < 9; r++)
					for (int c = 0; c < 13; c++) {
						if (r % 2 == 1 && c == 12)
							continue; // hexagon has NO color and is not on the map
						sum = iloMPModeler.sum(sum, hexagon[r][c][color]);
					}
				if (color == 0)
					iloMPModeler.addEq(sum, 36, "R2.1"); // there are 36 river hexagons
				else
					iloMPModeler.addEq(sum, 11, "R2.2"); // there are 11 hexagons of each land color
			}

			// Creating constraint that two neighbor hexagons can't have the same color
			for (int r = 0; r < 9; r++)
				for (int c = 0; c < 13; c++) {
					if (r % 2 == 1 && c == 12)
						continue; // hexagon has NO color and is not on the map
					for (int color = 0; color < 8; color++) {
						IloNumExpr sum = hexagon[r][c][color];
						HashSet<Coordinate> neighbours = graphTM.hash.get(new Coordinate(r, c)).coordNeighbours;
						for (Coordinate neighbour : neighbours)
							sum = iloMPModeler.sum(sum, hexagon[neighbour.row][neighbour.column][color]);
						iloMPModeler.addLe(sum, 1, "R3"); // No same-color neighbors
					}
				}

			// Creating a temporary obj function
			iloMPModeler.addMaximize(hexagon[0][0][0]);

			if (iloMPModeler.solve()) {
				System.out.println(iloMPModeler.getObjValue());

				for (int r = 0; r < 9; r++) {
					for (int c = 0; c < 13; c++) {
						if (r % 2 == 1 && c == 12)
							continue; // hexagon doesn't exist
						for (int color = 0; color < 8; color++) {
							if (iloMPModeler.getValue(hexagon[r][c][color]) == 1) {
								System.out.print(toTypeTerrain(color) + " ");
								map.hexes[r][c] = toTypeTerrain(color);
							}
						}
					}
					System.out.println("");
				}
				WriteImage.generatePNG(map, "Obj value = " + iloMPModeler.getObjValue(), "output/ipm-solution.PNG");

			}

		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			iloMPModeler.end();

		}
	}

	private static TypeTerrain toTypeTerrain(double d) {
		int i = (int) d;
		switch (i) {
		case 0:
			return TypeTerrain.RIVER;
		case 1:
			return TypeTerrain.YELLOW;
		case 2:
			return TypeTerrain.RED;
		case 3:
			return TypeTerrain.GRAY;
		case 4:
			return TypeTerrain.GREEN;
		case 5:
			return TypeTerrain.BLUE;
		case 6:
			return TypeTerrain.BLACK;
		case 7:
			return TypeTerrain.BROWN;
		default:
			return TypeTerrain.NONE;
		}
	}

}
