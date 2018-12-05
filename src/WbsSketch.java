import processing.core.PApplet;
import processing.core.PVector;

import java.text.MessageFormat;
import java.util.ArrayDeque;

public class WbsSketch extends PApplet {

    private final int DIM_X = 15;
    private final int DIM_Y = 15;

    private final PVector START = new PVector(0, 0);
    private final PVector END = new PVector(0, 0);

    private String file;
    private Vector start;
    private Vector end;
    private ArrayDeque<ListElement> path;

    private Map map;
    private TileDrawer tileDrawer;
    private AStar astar;

    private boolean finished = false;

    public void initialize(String file, Vector start, Vector end) {
        this.file = file;
        this.start = start;
        this.end = end;
    }

    public void settings() {
        size(DIM_X * 60, DIM_Y * 60);
    }

    public void setup() {

        //load map file
        map = new Map(file);
        tileDrawer = new TileDrawer(this, map);
        astar = new AStar(map, start, end);

        frameRate(20f);

    }

    public void draw() {

        background(0);
        map.draw(this, tileDrawer);

        if (!finished) {
            int result = astar.step();
            if (result == 1) {

                // we have found a path
                finished = true;
                System.out.println("Found path");

                path = astar.getPath();
                path.descendingIterator().forEachRemaining((el) -> {
                    System.out.println(MessageFormat.format("({0},{1}): {2} | {3}",
                            el.node.x, el.node.y, el.value, el.waterCrossings));
                });

                System.out.println();
                System.out.println("=> Total cost: " + path.getFirst().value);
                System.out.println("=> Total water crossings: " + path.getFirst().waterCrossings);

            } else if (result == -1) {
                // we have not found a path, but are done with the algorithm
                finished = true;
                System.out.println("Failed to find a path.");
            } else {
                astar.draw(this, tileDrawer);
            }
        } else if (path != null) {
            path.forEach((el) -> tileDrawer.drawTile(el.node, 255, 0, 0, 255));
        }

    }

}
