import processing.core.PApplet;
import processing.core.PVector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

class Map {

    private int dimX;
    private int dimY;
    private int[] fields;
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
        this.fields = new int[dimX * dimY];
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
                    fields[coordinatesToLinear(x, y)] = Integer.parseInt(splittedLine[x]);
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
                Color c = colors[fields[coordinatesToLinear(x, y)]];
                drawer.drawTile(x, y, c.r, c.g, c.b, 255);
            }
        }
    }

    List<Edge> getConnectionsFrom(int linearCoord) {
        List<Edge> edges = null;
        edges.add(new Edge(linearCoord,linearCoord+1));
        edges.add(new Edge(linearCoord,linearCoord-1));
        edges.add(new Edge(linearCoord,linearCoord+dimX));
        edges.add(new Edge(linearCoord,linearCoord-dimX));
        return edges;
    }

    int getField(int x, int y) {
        return getField(coordinatesToLinear(x, y));
    }

    int getField(int c) {
        return fields[c];
    }

    int getDimX() {
        return dimX;
    }

    int getDimY() {
        return dimY;
    }

    int coordinatesToLinear(int x, int y) {
        return y * dimX + x;
    }

    PVector linearToCoordinates(int v) {
        return new PVector(v / dimX, v % dimX);
    }

}
