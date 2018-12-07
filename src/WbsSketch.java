import processing.core.PApplet;

import java.text.MessageFormat;
import java.util.ArrayDeque;

public class WbsSketch extends PApplet {

    private float fps;
    private Vector start;
    private Vector end;
    private ArrayDeque<ListElement> path;

    private Map map;
    private TileDrawer tileDrawer;
    private AStar astar;

    private boolean finished = false;

    void initialize(String file, int fps, Vector start, Vector end) {

        this.fps = (float)fps;
        this.start = start;
        this.end = end;

        //load map file
        map = new Map(file);

    }

    // will be called automatically once at the beginning of the program
    // even before setup()
    public void settings() {
        size(map.getDimX() * 60, map.getDimY() * 60);
    }

    public void setup() {

        tileDrawer = new TileDrawer(this, map);
        astar = new AStar(map, start, end);

        frameRate(this.fps);
        surface.setTitle("A* Algorithm");
        surface.setAlwaysOnTop(true);

    }

    public void draw() {

        background(0);
        map.draw(tileDrawer);

        if (!finished) {
            int result = astar.step();
            if (result == 1) {

                // we have found a path
                finished = true;
                System.out.println("Found path");

                path = astar.getPath();
                path.descendingIterator().forEachRemaining((el) ->
                    System.out.println(MessageFormat.format("({0},{1}): {2} | {3}",
                        el.node.x, el.node.y, el.value, el.waterCrossings))
                );

                System.out.println();
                System.out.println("=> Total cost: " + path.getFirst().value);
                System.out.println("=> Total water crossings: " + path.getFirst().waterCrossings);

            } else if (result == -1) {
                // we have not found a path, but are done with the algorithm
                finished = true;
                System.out.println("Failed to find a path.");
            }
        }

        astar.draw(tileDrawer);
        if (path != null) {
            path.forEach((el) -> tileDrawer.drawTile(el.node, 255, 0, 0, 255));
        }

    }

}
