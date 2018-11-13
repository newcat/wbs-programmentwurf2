import processing.core.PApplet;

public class Main {

    public static void main(String[] args) {
        String[] processingArgs = {"WbsSketch"};
        WbsSketch mySketch = new WbsSketch();
        PApplet.runSketch(processingArgs, mySketch);
    }

}
