import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
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

    Map(String filePath) {

        BufferedReader stream;

        try {

            String line;
            String[] splitLine;
            ArrayList<int[]> rows = new ArrayList<>();
            stream = new BufferedReader(new FileReader(filePath));

            do {

                line = stream.readLine();
                splitLine = line.split(";");

                if (rows.isEmpty()) {
                    // this is the first line so infer x dimensions from the line
                    this.dimX = splitLine.length;
                }

                if (splitLine.length == 0) {
                    break;
                } else if (splitLine.length != dimX) {
                    throw new Exception("Input error: Inconsistent X dimensions");
                }

                int[] lineValues = Arrays.stream(splitLine).mapToInt(Integer::parseInt).toArray();
                rows.add(lineValues);

            } while (true);

            this.dimY = rows.size();
            this.fields = new int[this.dimY][];
            this.fields = rows.toArray(this.fields);

            // ignore headers
            stream.readLine();

            // input field weights
            while ((line = stream.readLine()) != null) {
                splitLine = line.split(";");
                weights.put(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[2]));
            }

            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void draw(TileDrawer drawer) {
        for (int y = 0; y < dimY; y++) {
            for (int x = 0; x < dimX; x++) {
                Color c = colors[fields[y][x]];
                drawer.drawTile(new Vector(x, y), c.r, c.g, c.b, 255);
            }
        }
    }

    List<Edge> getConnectionsFrom(Vector v) {
        List<Edge> edges = new ArrayList<>();

        if (v.y > 0) {
            edges.add(getEdge(v.x, v.y - 1));
        }
        if (v.y < dimY - 1) {
            edges.add(getEdge(v.x, v.y + 1));
        }
        if (v.x > 0) {
            edges.add(getEdge(v.x - 1, v.y));
        }
        if (v.x < dimX - 1) {
            edges.add(getEdge(v.x + 1, v.y));
        }

        return edges;
    }

    private Edge getEdge(int x, int y) {
        Vector u = new Vector(x, y);
        int value = weights.get(getField(u));
        return new Edge(u, value);
    }

    int getField(Vector v) {
        return fields[v.y][v.x];
    }

    int getMinimumWeight() {
        return weights.values().stream().min(Integer::compareTo)
                .orElse(1);
    }

    int getDimX() {
        return dimX;
    }

    int getDimY() {
        return dimY;
    }

}
