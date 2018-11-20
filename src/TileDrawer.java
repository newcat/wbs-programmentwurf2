import processing.core.PApplet;
import processing.core.PVector;

class TileDrawer {

    private PApplet sketch;
    private Map map;
    private float sizeX, sizeY;

    TileDrawer(PApplet sketch, Map map) {
        this.sketch = sketch;
        this.map = map;
        sizeX = 1f * sketch.width / map.getDimX();
        sizeY = 1f * sketch.height / map.getDimY();
    }

    void drawTile(int x, int y, int r, int g, int b, int alpha) {
        sketch.pushStyle();
        sketch.stroke(0, 0, 0, 64);
        sketch.strokeWeight(1f);
        sketch.fill(r, g, b, alpha);
        sketch.rect(x * sizeX, y * sizeY, sizeX, sizeY);
        sketch.popStyle();
    }

    void drawTile(int c, int r, int g, int b, int alpha) {
        PVector vec = map.linearToCoordinates(c);
        drawTile((int)vec.x, (int)vec.y, r, g, b, alpha);
    }

}
