import processing.core.PApplet;
import processing.core.PVector;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class WbsSketch extends PApplet {

    private class Color {
        public int r;
        public int g;
        public int b;
        public Color(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    final int FIELD_DIM = 15;
    Color[] colors = new Color[]{
            new Color(100, 100, 255),
            new Color(100, 255, 100),
            new Color(171, 60, 38),
            new Color(60, 60, 60),
            new Color(0, 100, 0),
            new Color(200, 200, 200)
    };
    int dimX = 0;
    int dimY = 0;
    ArrayList<ArrayList<Integer>> fields;
    ListGraph g;
    ArrayList<Integer> visited = new ArrayList<Integer>();
    int[] lengths;
    int min;
    int currentNode;
    int nodeCount;
    int startNode;
    int endNode;
    boolean doDijkstra = false;
    boolean doPath = false;
    ArrayList<Integer> path = new ArrayList<Integer>();
    int shortestValue;

    public void settings() {
        //size(56 * 20, 50 * 20);
        size(840, 750);
    }

    public void setup() {

        //load map file
        fields = new ArrayList<ArrayList<Integer>>();

        try {

            BufferedReader br = createReader(".\\fields.csv");

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                ArrayList<Integer> row = new ArrayList<Integer>();
                for (String s : values) {
                    row.add(Integer.parseInt(s));
                }
                fields.add(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        dimX = fields.get(0).size();
        dimY = fields.size();

        g = new ListGraph();
        g.initialize(dimX * dimY);

        for (int y = 0; y < dimY; y++) {
            for (int x = 0; x < dimX; x++) {

                int fieldValue = fields.get(y).get(x);
                if (fieldValue != -1) {
                    if (y > 0) {
                        g.addConnection(
                                coordinatesToLinear(x, y),
                                coordinatesToLinear(x, y - 1),
                                getWeightByField(fieldValue));
                    }
                    if (y < dimY - 1) {
                        g.addConnection(
                                coordinatesToLinear(x, y),
                                coordinatesToLinear(x, y + 1),
                                getWeightByField(fieldValue));
                    }
                    if (x > 0) {
                        g.addConnection(
                                coordinatesToLinear(x, y),
                                coordinatesToLinear(x - 1, y),
                                getWeightByField(fieldValue));
                    }
                    if (x < dimX - 1) {
                        g.addConnection(
                                coordinatesToLinear(x, y),
                                coordinatesToLinear(x + 1, y),
                                getWeightByField(fieldValue));
                    }
                }

            }
        }

        //noLoop();

        int start = coordinatesToLinear(53, 41);
        int end = coordinatesToLinear(13, 14);
        dijkstra(start, end);
        frameRate(200);

        /*int length = Dijkstra.DoDijkstra(start, end, g);
        System.out.println(length);*/

    }

    public void draw() {

        dijkstraStep();
        pathStep();

        background(0);

        for (int y = 0; y < dimY; y++) {
            for (int x = 0; x < dimX; x++) {
                if (path.contains(coordinatesToLinear(x, y))) {
                    fill(255, 0, 0);
                } else if (doDijkstra && visited.contains(coordinatesToLinear(x, y))) {
                    fill(0, 0, 100);
                } else {
                    Color c = colors[fields.get(y).get(x) + 1];
                    fill(c.r, c.g, c.b);
                }
                rect(x * FIELD_DIM, y * FIELD_DIM, FIELD_DIM, FIELD_DIM);
            }
        }

    }

    int coordinatesToLinear(int x, int y) {
        return y * dimX + x;
    }

    PVector linearToCoordinates(int v) {
        return new PVector(v / dimX, v % dimX);
    }

    int getWeightByField(int field) {
        switch (field) {
            case 0:
                return 2;
            case 1:
                return 4;
            case 2:
                return 6;
            case 3:
                return 3;
            case 4:
                return 1;
        }
        return -1;
    }

    void dijkstra(int start, int end) {

        nodeCount = g.getNodeCount();
        lengths = new int[nodeCount + 1];
        min = -1;
        currentNode = start;
        startNode = start;
        endNode = end;

        visited = new ArrayList<Integer>();
        visited.add(start);

        for (int i = 1; i <= nodeCount; i++) {
            if (i == start) {
                lengths[i] = 0;
            } else {
                lengths[i] = Integer.MAX_VALUE;
            }
        }

        doDijkstra = true;

    }

    void dijkstraStep() {

        if (!doDijkstra)
            return;

        int currentPathLength = lengths[currentNode];

        for (int i = 1; i <= nodeCount; i++) {
            int d = g.getDistance(currentNode, i);
            if (d != -1 && lengths[i] > currentPathLength + d) {
                lengths[i] = currentPathLength + d;
            }
        }

        min = Integer.MAX_VALUE;
        int old = currentNode;
        for (int i = 1; i <= nodeCount; i++) {
            if (!visited.contains(i) && lengths[i] != -1 && lengths[i] < min) {
                min = lengths[i];
                currentNode = i;
            }
        }
        visited.add(currentNode);

        if (currentNode == endNode || old == currentNode) {
            doDijkstra = false;

            path = new ArrayList<Integer>();
            shortestValue = Integer.MAX_VALUE;
            path.add(endNode);
            frameRate(30);

            doPath = true;
        }

        redraw();

    }

    void pathStep() {

        if (!doPath)
            return;
        println(shortestValue);
        println(path.get(0));
        ArrayList<Edge> edges = g.getEdgesTo(path.get(0));
        int shortestNode = -1;
        for (Edge e : edges) {
            if (lengths[e.start] < shortestValue) {
                shortestValue = lengths[e.start];
                shortestNode = e.start;
            }
        }
        if (shortestNode == -1) {
            doPath = false;
            println("No node found");
        } else {
            path.add(0, shortestNode);
            if (shortestNode == startNode) {
                doPath = false;
            }
        }

    }

}
