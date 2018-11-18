import processing.core.PVector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

public class Map {

    private int dimX;
    private int dimY;
    private int[] fields;
    private HashMap<Integer, Integer> weights = new HashMap<>();

    public Map(int dimX, int dimY, String filePath) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.fields = new int[dimX * dimY];
        BufferedReader stream;
        try {
            String line;
            stream = new BufferedReader(new FileReader(filePath));
            int counter = 0;
            while ((line = stream.readLine()) != null && counter < dimX * dimY) {
                String[] splittedLine = line.split(";");
                for (int i = 0; i < dimX; i++) {
                    fields[counter] = Integer.parseInt(splittedLine[i]);
                    counter++;
                }
            }
            while ((line = stream.readLine()) != null) {
                String[] splittedLine = line.split(";");
                if (splittedLine[1].contains("Ebene") || splittedLine[1].contains("Fluss")
                        || splittedLine[1].contains("Weg") || splittedLine[1].contains("Wald")
                        || splittedLine[1].contains("Br") || splittedLine[1].contains("Felswand")) {
                    weights.put(Integer.parseInt(splittedLine[0]), Integer.parseInt(splittedLine[2]));
                }
            }
            stream.close();
        } catch (Exception e) {
            System.out.println(e);
        }

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
