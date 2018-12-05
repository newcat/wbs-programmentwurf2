import processing.core.PApplet;
import processing.core.PConstants;

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

    void drawTile(Vector v, int r, int g, int b, int alpha) {
        sketch.pushStyle();
        sketch.stroke(0, 0, 0, 64);
        sketch.strokeWeight(1f);
        sketch.fill(r, g, b, alpha);
        sketch.rect(v.x * sizeX, v.y * sizeY, sizeX, sizeY);
        sketch.popStyle();
    }

    void markTile(Vector v, int r, int g, int b, int alpha) {
        sketch.pushStyle();
        sketch.noStroke();
        sketch.fill(r, g, b, alpha);
        sketch.ellipse(v.x * sizeX + sizeX / 2f, v.y * sizeY + sizeY / 2f, sizeX / 3f, sizeY / 3f);
        sketch.popStyle();
    }

    void drawText(Vector v, String text) {
        sketch.pushStyle();
        sketch.stroke(0);
        sketch.textAlign(PConstants.CENTER, PConstants.CENTER);
        sketch.fill(0);
        sketch.text(text, v.x * sizeX + sizeX / 2f, v.y * sizeY + sizeY / 2f - 1);
        sketch.popStyle();
    }

}
