import processing.core.PApplet;
import processing.core.PVector;

public class TileDrawer {

    private PApplet sketch;
    private Map map;
    private float sizeX, sizeY;

    public TileDrawer(PApplet sketch, Map map, int dimX, int dimY) {
        this.sketch = sketch;
        this.map = map;
        sizeX = 1f * dimX / map.getDimX();
        sizeY = 1f * dimY / map.getDimY();
    }

    public void drawTile(int x, int y, int r, int g, int b, int alpha) {
        sketch.pushStyle();
        sketch.stroke(0);
        sketch.strokeWeight(1f);
        sketch.fill(r, g, b, alpha);
        sketch.rect(x * sizeX, y * sizeY, sizeX, sizeY);
        sketch.popStyle();
    }

    public void drawTile(int c, int r, int g, int b, int alpha) {
        PVector vec = map.linearToCoordinates(c);
        drawTile((int)vec.x, (int)vec.y, r, g, b, alpha);
    }

}
