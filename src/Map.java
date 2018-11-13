import processing.core.PVector;

import java.util.HashMap;
import java.util.List;

public class Map {

    private int dimX;
    private int dimY;
    private int[] fields;
    private HashMap<Integer, Integer> weights;

    public Map(int dimX, int dimY, String filePath) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.fields = new int[dimX * dimY];
        // TODO: Load fields and weights map from file
    }

    public List<Edge> getConnectionsFrom(int linearCoord) {
        // TODO
        return null;
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
