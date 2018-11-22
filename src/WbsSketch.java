import processing.core.PApplet;
import processing.core.PVector;

public class WbsSketch extends PApplet {

    private final int DIM_X = 15;
    private final int DIM_Y = 15;

    private final PVector START = new PVector(0, 0);
    private final PVector END = new PVector(0, 0);

    private Map map;
    private TileDrawer tileDrawer;

    public void settings() {
        size(DIM_X * 20, DIM_Y * 20);
    }

    public void setup() {

        //load map file
        map = new Map(DIM_X, DIM_Y, ".\\fields.csv");
        tileDrawer = new TileDrawer(this, map);

    }

    public void draw() {

        background(0);
        map.draw(this, tileDrawer);

    }

}
