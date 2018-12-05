import processing.core.PApplet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Map {

    private int dimX;
    private int dimY;
    private int[][] fields;
    private HashMap<Integer, Integer> weights = new HashMap<>();

    private Color[] colors = new Color[]{
            new Color(150, 255, 150),
            new Color(0, 120, 255),
            new Color(200, 200, 200),
            new Color(0, 255, 0),
            new Color(255, 255, 255),
            new Color(60, 60, 60)
    };

    Map(int dimX, int dimY, String filePath) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.fields = new int[dimY][dimX];
        BufferedReader stream;
        try {
            String line;
            stream = new BufferedReader(new FileReader(filePath));

            // input fields
            for (int y = 0; y < dimY; y++) {
                line = stream.readLine();
                String[] splittedLine = line.split(";");
                if (splittedLine.length != dimX) {
                    throw new Exception("Input error: Incorrect X dimensions");
                }
                for (int x = 0; x < dimX; x++) {
                    fields[y][x] = Integer.parseInt(splittedLine[x]);
                }
            }

            // ignore headers
            stream.readLine();
            stream.readLine();

            // input field weights
            while ((line = stream.readLine()) != null) {
                String[] splittedLine = line.split(";");
                weights.put(Integer.parseInt(splittedLine[0]), Integer.parseInt(splittedLine[2]));
            }

            stream.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    void draw(PApplet sketch, TileDrawer drawer) {
        for (int y = 0; y < dimY; y++) {
            for (int x = 0; x < dimX; x++) {
                Color c = colors[fields[y][x]];
                drawer.drawTile(new Vector(x, y), c.r, c.g, c.b, 255);
            }
        }
    }

    List<Edge> getConnectionsFrom(Vector v) {
        List<Edge> edges = new ArrayList<>();
        int value = weights.get(getField(v));

        if (v.y > 0) {
            edges.add(new Edge(new Vector(v.x, v.y - 1), value));
        }
        if (v.y < dimY - 1) {
            edges.add(new Edge(new Vector(v.x, v.y + 1), value));
        }
        if (v.x > 0) {
            edges.add(new Edge(new Vector(v.x - 1, v.y), value));
        }
        if (v.x < dimX - 1) {
            edges.add(new Edge(new Vector(v.x + 1, v.y), value));
        }

        return edges;
    }

    int getField(Vector v) {
        return fields[v.y][v.x];
    }

    int getDimX() {
        return dimX;
    }

    int getDimY() {
        return dimY;
    }

}
