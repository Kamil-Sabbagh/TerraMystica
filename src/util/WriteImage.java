package util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import tm.Coordinate;
import tm.GraphTM;
import tm.MapTM;
import tm.TypeTerrain;

public class WriteImage {

	public static void generatePNG(MapTM map, String message, String fileName) {
		try {
			int width = 630, height = 400;

			// TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
			// into integer pixels
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			Graphics2D ig2 = bi.createGraphics();
			ig2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Plotting text
			Font font = new Font("TimesRoman", Font.BOLD, 20);
			ig2.setFont(font);
			// String message = "Score = ";
			// FontMetrics fontMetrics = ig2.getFontMetrics();
			// int stringWidth = fontMetrics.stringWidth(message);
			// int stringHeight = fontMetrics.getAscent();
			ig2.setPaint(Color.black);
			// ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight
			// / 4);
			ig2.drawString(message, 20, 20);

			// Adding polygon
			Point center = new Point(60, 60);
			int size = 25;
			int offsetX = (int) (2 * (Math.cos(30 * Math.PI / 180) * size));

			for (int row = 0; row < 9; row++) {
				if (row % 2 == 0)
					center.x = 60;
				else
					center.x = (int) (60 + offsetX / 2);

				for (int column = 0; column < 12; column++) {
					drawHexagon(center, size, convert(new Coordinate(row, column)), convert(map.hexes[row][column]),
							ig2);
					center.x += offsetX;
				}

				if (row % 2 == 0)
					drawHexagon(center, size, convert(new Coordinate(row, 12)), convert(map.hexes[row][12]), ig2);

				center.y += 1.5 * size;
			}

			/*
			 * // Plotting and image int WIDTH = 100;
			 * 
			 * int[] xPoints = { WIDTH / 3, (2 * WIDTH) / 3, WIDTH / 3 }; int[] yPoints = {
			 * 0, WIDTH / 2, WIDTH }; ig2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			 * RenderingHints.VALUE_ANTIALIAS_ON); ig2.setColor(Color.green);
			 * ig2.drawLine(0, WIDTH - 1, WIDTH, WIDTH - 1); ig2.drawLine(0, 0, WIDTH, 0);
			 * ig2.drawLine(WIDTH / 3, 0, WIDTH / 3, WIDTH); ig2.drawLine((2 * WIDTH / 3),
			 * 0, (2 * WIDTH / 3), WIDTH); ig2.setColor(Color.black);
			 * ig2.drawPolygon(xPoints, yPoints, xPoints.length); ig2.setColor(Color.black);
			 * ig2.fillPolygon(xPoints, yPoints, xPoints.length); ig2.dispose();
			 */

			ImageIO.write(bi, "PNG", new File(fileName));
			// ImageIO.write(bi, "JPEG", new File("output\\yourImageName.JPG"));
			// ImageIO.write(bi, "gif", new File("output\\yourImageName.GIF"));
			// ImageIO.write(bi, "BMP", new File("output\\yourImageName.BMP"));

		} catch (IOException ie) {
			ie.printStackTrace();
		}

	}

	private static void drawHexagon(Point center, double size, Color colorBorder, Color colorFill, Graphics2D g2D) {
		Polygon p = new Polygon();
		for (int i = 0; i < 6; i++) {
			double angle_deg = 60 * i - 30;
			double angle_rad = Math.PI / 180 * angle_deg;
			int x = (int) (center.x + size * Math.cos(angle_rad));
			int y = (int) (center.y + size * Math.sin(angle_rad));
			p.addPoint(x, y);
		}
		g2D.setColor(colorFill);
		g2D.fillPolygon(p);
		if (colorFill == Color.WHITE) {
			// It's river
			g2D.setColor(Color.BLUE);
			g2D.drawString("~", center.x - (int) size / 5, center.y - (int) size / 8);
			g2D.drawString("~", center.x - (int) size / 5, center.y + (int) size / 8);
			g2D.drawString("~", center.x - (int) size / 5, center.y + 3 * (int) size / 8);
		}
		g2D.setColor(colorBorder);
		g2D.setStroke(new BasicStroke(2));
		g2D.drawPolygon(p);
	}

	private static Color convert(TypeTerrain terrain) {
		if (terrain == TypeTerrain.RED)
			return Color.RED;

		if (terrain == TypeTerrain.YELLOW)
			return Color.YELLOW;

		if (terrain == TypeTerrain.BROWN)
			return new Color(102, 51, 0);

		if (terrain == TypeTerrain.BLACK)
			return Color.BLACK;

		if (terrain == TypeTerrain.BLUE)
			return new Color(0, 0, 153);

		if (terrain == TypeTerrain.GREEN)
			return new Color(0, 102, 0);

		if (terrain == TypeTerrain.GRAY)
			return Color.GRAY;

		if (terrain == TypeTerrain.RIVER)
			return Color.WHITE;

		return new Color(102, 0, 153);

	}

	private static Color convert(Coordinate c) {
		return Color.WHITE;
	}

	public static void serializeMapTM(MapTM mapTM, String fileName) {
		// Serialization
		try {
			// Saving of object in a file
			FileOutputStream file = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(file);
			// Method for serialization of object
			out.writeObject(mapTM);
			out.close();
			file.close();
			System.out.println("Object has been serialized");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static MapTM deserializeMapTM(String fileName) {
		MapTM map = null;
		// Deserialization
		try {
			// Reading the object from a file
			FileInputStream file = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(file);
			// Method for deserialization of object
			map = (MapTM) in.readObject();
			in.close();
			file.close();
			System.out.println("Object has been deserialized ");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	public static String reportMap(MapTM map) {
		StringBuilder sb = new StringBuilder();
		GraphTM graph_final = new GraphTM(map);
		sb.append("\nColor-color conflict (req01): " + graph_final.sameColorNeighbours());
		sb.append("\n3 or more land neighbours but no 1 spade distance neighbour (req09): "
				+ graph_final.oneSpadeNeighbour());
		sb.append("Map as text:\n");
		sb.append(Snellman.mapAsText(map));
		return sb.toString();
	}

}