import processing.core.PApplet;

public class Main {

    public static void main(String[] args) {
        String[] processingArgs = {"WbsSketch"};
        WbsSketch sketch = new WbsSketch();
        Map map = new Map(15,15,"fields.csv");
        AStar aStar = new AStar(map,23,200 );
        aStar.step();
        PApplet.runSketch(processingArgs, sketch);
    }

}
