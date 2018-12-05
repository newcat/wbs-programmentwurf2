import processing.core.PApplet;

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

    void outlineTile(Vector v, int r, int g, int b, int alpha) {
        sketch.pushStyle();
        sketch.stroke(r, g, b, alpha);
        sketch.strokeWeight(2f);
        sketch.noFill();
        sketch.rect(v.x * sizeX, v.y * sizeY, sizeX, sizeY);
        sketch.popStyle();
    }

}
